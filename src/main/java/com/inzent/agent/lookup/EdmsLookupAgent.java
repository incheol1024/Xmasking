package com.inzent.agent.lookup;

import com.inzent.dto.edms.EdmsLookupDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class EdmsLookupAgent implements EdmsAgent {

    private EdmsFindMode edmsFindMode;

    private EdmsTableName edmsTableName;

    private String beginDcmId;

    private String endDcmId;

    private String sql;
    private QueryRunner queryRunner;

    public EdmsLookupAgent(EdmsFindMode edmsFindMode, EdmsTableName edmsTableName) {
        this.edmsFindMode = edmsFindMode;
        this.edmsTableName = edmsTableName;
        this.queryRunner = getQueryRunner();
    }

    @Override
    public void run() {
        if (edmsFindMode == EdmsFindMode.ALL)
            findAllOfDcmIdRange(this.edmsTableName);
        else if (edmsFindMode == EdmsFindMode.MASK_TARGET)
            findMaskTarget(this.edmsTableName);
    }

    private void findAllOfDcmIdRange(EdmsTableName edmsTableName) {

        if (edmsTableName == EdmsTableName.EDM_INFO_DET_T)
            findAllOfDetTable();
        else if (edmsTableName == EdmsTableName.EDM_INFO_DET_VER_T)
            findAllOfDetVerTable();
    }


    private void findAllOfDetTable() {
        ResultSetHandler<EdmsLookupDto> resultSetHandler = new BeanListHandler(EdmsLookupDto.class);
        List<EdmsLookupDto> lookupDtos = null;
        try {
            lookupDtos = this.queryRunner.execute("select dcm_id, reg_date from xtorm.edm_info_det_t", resultSetHandler);
            filterDto(lookupDtos)
                    .filter((edmsLookupDto) -> {
                        System.out.println("이지핌스 테이블에 insert");
                        return false;// 이지핌스 테이블에 insert
                    })
                    .forEach(edmsLookupDto -> {
                            System.out.println("마지막 elementId 조건 테이블에 Update");
                    });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Stream<EdmsLookupDto> filterDto(List<EdmsLookupDto> lookupDtos) {
        return lookupDtos.stream().filter((edmsLookupDto) -> {
            return false;             //dcm_id 가 이지핌스 테이블에 존재하지 않는것만 필터링
        });
    }



    private void findAllOfDetVerTable() {


    }


    private void findMaskTarget(EdmsTableName edmsTableName) {


    }

    private QueryRunner getQueryRunner() {
        QueryRunnerPool queryRunnerPool = QueryRunnerPool.getInstance();
        return queryRunnerPool.getQueryRunner(DatabaseName.EDMS)
                .orElseThrow(() -> new RuntimeException("Not Found QueryRunner DatabaseName: " + DatabaseName.EDMS.name()));
    }

}
