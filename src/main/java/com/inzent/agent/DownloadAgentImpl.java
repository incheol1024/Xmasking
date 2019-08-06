package com.inzent.agent;

import com.inzent.dto.agent.DownloadResultParamDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.util.CommonUtil;
import com.inzent.util.XtormUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DownloadAgentImpl implements DownloadAgent {

    @Override
    public void run() {

        QueryRunner queryRunner = CommonUtil.getQueryRunner(DatabaseName.MASK);

        downTargetQueue.stream()
                .limit(1000)
                .parallel()
                .map((elementId) -> {
                    String downPath = getDownPath() + File.separator + elementId;
                    int downloadResult = XtormUtil.downloadElement(elementId, downPath);
                    DownloadResultParamDto downloadResultParamDto = new DownloadResultParamDto();
                    downloadResultParamDto.setElementId(elementId);
                    if (downloadResult == 0) {
                        downloadResultParamDto.setImgDir(downPath);
                    }else {
                        downloadResultParamDto.setImgDir(" ");
                    }
                    downloadResultParamDto.setDownTime(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
                    downloadResultParamDto.setDownloadResult(downloadResult);
                    return downloadResultParamDto;
                })
                .forEach((downloadResultParamDto) -> {
                    try {
                        if (downloadResultParamDto.getDownloadResult() == 0) {
                            queryRunner.update(DOWN_SUCCESS_SQL, downloadResultParamDto.toParamArray());
                        } else {
                            queryRunner.update(DOWN_FAIL_SQL, downloadResultParamDto.toParamArray());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        //update xtorm.Ezs_XIF_TBL set if_stat = '12', if_img_dir = ?, if_down_etime = ?, if_reg_time = sysdate where m_sys_id = ?

    }


}
