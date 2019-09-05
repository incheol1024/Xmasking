package com.inzent.agent.Replace;

import com.inzent.dao.MaskDao;
import com.inzent.dto.agent.ReplaceTargetDto;
import com.inzent.dto.agent.ReplaceResultDto;
import com.inzent.util.CommonUtil;
import com.inzent.util.Stoper;
import com.inzent.util.XtormUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.inzent.util.XtormUtil.XtormServer.EDMS_XTORM;
import static com.inzent.util.XtormUtil.XtormServer.MASK_XTORM;

public class ReplaceAgentImpl implements ReplaceAgent {


    private Logger logger = LoggerFactory.getLogger(ReplaceAgentImpl.class);

    private static String agentName = "replace";

    private String orderId;

    public ReplaceAgentImpl(String order) {
        this.orderId = order;
        Stoper.addStopIdTable(agentName + order, false);
    }

    @Override
    public void run() {

        while (!Stoper.isStop()) {

            String date = MaskDao.findReplaceDate();
            if (date == null || date.equals("")) {
                logger.debug("orderId - {} No longer exist replace target date", orderId);
                Stoper.modifyStatusInStopIdTable(agentName + orderId, true);
                return;
            }

            List<ReplaceTargetDto> replaceList = MaskDao.getElementIdListOfReplace(orderId, date);

            if (replaceList.size() == 0) {
                logger.debug("orderId - {}, date - {} is not ", orderId, date);
                MaskDao.updateDownDateSuccess(orderId, date);
                logger.debug("update for orderId - {}, date - {}  success param ", orderId, date);
                continue;
            }

            Stream<ReplaceTargetDto> filteredReplaceDtoStream = this.filterElementIdStream(replaceList);

            int replaceSuccessCount = 0;
            filteredReplaceDtoStream.
                    map(replaceTarget -> {
                        int result = 0;

                        if (CommonUtil.isPastElementId(replaceTarget.getM_sys_id()))
                            result = XtormUtil.replaceElement(MASK_XTORM, replaceTarget.getM_sys_id(), replaceTarget.getIf_img_dir());
                        else
                            result = XtormUtil.replaceElement(EDMS_XTORM, replaceTarget.getM_sys_id(), replaceTarget.getIf_img_dir());

                        ReplaceResultDto replaceResultDto = new ReplaceResultDto();
                        replaceResultDto.setElementId(replaceTarget.getM_sys_id());
                        replaceResultDto.setReplaceResult(result);
                        return replaceResultDto;
                    })
                    .map(replaceResultDto -> {
                        int result = 0;
                        if (replaceResultDto.getReplaceResult() == 0) {
                            MaskDao.updateReplaceSuccess(replaceResultDto.getElementId());
                        } else {
                            MaskDao.updateReplaceFail(replaceResultDto.getElementId());
                        }
                        return result;
                    })
                    .filter(result -> result == 1)
                    .count();

            logger.debug("Replace Success Count = {}", replaceSuccessCount);
        }
        Stoper.modifyStatusInStopIdTable(agentName + orderId, true);
    }


    private Stream<ReplaceTargetDto> filterElementIdStream(List<ReplaceTargetDto> elementIdList) {
        return elementIdList
                .stream()
                .filter((replaceTargetDto) -> replaceTargetDto.getM_sys_id() != null && !replaceTargetDto.getM_sys_id().equals(""));
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
