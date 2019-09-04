package com.inzent.agent.Replace;

import com.inzent.dao.MaskDao;
import com.inzent.dto.agent.ReplaceTargetDto;
import com.inzent.dto.agent.ReplaceTargetParamDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.util.CommonUtil;
import com.inzent.util.XtormUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReplaceAgentImpl implements ReplaceAgent {

    private Logger logger = LoggerFactory.getLogger(ReplaceAgentImpl.class);

    private String order;

    public ReplaceAgentImpl(String order) {
        this.order = order;
    }

    @Override
    public void run() {

//        QueryRunner queryRunner = CommonUtil.getQueryRunner(DatabaseName.MASK);

        String date = MaskDao.findReplaceDate();

        MaskDao.getElementIdListOfReplace(order, date);

        int replaceSuccessCount = 0;
        getReplaceTargetDtos().
                map(replaceTarget -> {
                    int result = 0;
                    if (REPLACE_ARCHIVE_MODE.equalsIgnoreCase("TRUE")) {
                        result = XtormUtil.replaceElementForArchive(replaceTarget.getM_sys_id(), replaceTarget.getIf_img_dir(), REPLACE_ARCHIVE);
                    } else {
                        result = XtormUtil.replaceElement(replaceTarget.getM_sys_id(), replaceTarget.getIf_img_dir());
                    }
                    ReplaceTargetParamDto replaceTargetParamDto = new ReplaceTargetParamDto();
                    replaceTargetParamDto.setElementId(replaceTarget.getM_sys_id());
                    replaceTargetParamDto.setReplaceResult(result);
                    return replaceTargetParamDto;
                })
                .map(replaceTargetParamDto -> {
                    int result = 0;
                        if (replaceTargetParamDto.getReplaceResult() == 0) {
                            MaskDao.updateReplaceSuccess(replaceTargetParamDto.getElementId());
                        } else {
                            MaskDao.updateReplaceFail(replaceTargetParamDto.getElementId());
                        }
                    return result;
                })
                .filter(result -> result == 1)
                .count();

        logger.debug("Replace Success Count = {}", replaceSuccessCount);
    }

    private Stream<ReplaceTargetDto> getReplaceTargetDtos() {

        List<ReplaceTargetDto> replaceTargetDtos = new ArrayList<>();
        ReplaceTargetDto replaceTarget = null;

        for (int i = 0; i < 1000; i++) {
            replaceTarget = replaceTargetQueue.poll();

            if (replaceTarget == null)
                break;

            replaceTargetDtos.add(replaceTarget);
        }

        return replaceTargetDtos.stream();
    }
}
