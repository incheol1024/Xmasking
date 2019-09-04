package com.inzent.agent.download;

import com.inzent.dao.MaskDao;
import com.inzent.dto.agent.DownloadResultParamDto;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import com.inzent.util.AppProperty;
import com.inzent.util.CommonUtil;
import com.inzent.util.Stoper;
import com.inzent.util.XtormUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import static com.inzent.util.XtormUtil.XtormServer.EDMS_XTORM;
import static com.inzent.util.XtormUtil.XtormServer.MASK_XTORM;

public class DownloadAgentImpl implements DownloadAgent {

    private ConcurrentLinkedQueue<String> downTargetQueue = new ConcurrentLinkedQueue<>();

    private volatile static boolean isCollecting = false;

    private static boolean parallelOption = Boolean.valueOf(AppProperty.getValue("PARALLEL_OPTION"));

    Logger logger = LoggerFactory.getLogger(DownloadAgentImpl.class);

    private String order;

    public DownloadAgentImpl(String order) {
        if (order.equals("0001") || order.equals("0002") || order.equals("0003")) {
            this.order = order;
        } else {
            throw new RuntimeException("차수가 잘못 입력 되었습니다. 가능 차수: 0001, 0002, 0003");
        }

    }

    @Override
    public void run() {

        while (!Stoper.isStop()) {
            logger.debug("Getting download target date");
            String date = MaskDao.findDownDate();
            if (date == null || date.equals("")) {
                logger.debug("order - {} No longer exist target date", order);
                return;
            }

            logger.debug("Getting download target list...");
            List<String> elementIdList = MaskDao.getElementIdListOfDownload(order, date);

            if (elementIdList.size() == 0) {
                logger.debug("order - {}, date - {} is not exist target ElementId.", order, date);
                MaskDao.updateDownDateSuccess(order, date);
                logger.debug("order - {}, date - {} updated row ", order, date);
                continue;
            }

            logger.debug("download start!");
            Stream<String> filteredElementIdStream = this.filterElementId(elementIdList);

            if (parallelOption == true) {
                filteredElementIdStream = filteredElementIdStream.parallel();
            }

            Stream<DownloadResultParamDto> resultStream = this.executeDownloadAndGetResultStream(filteredElementIdStream);
            this.updateResult(resultStream);

        }
    }

    private void updateResult(Stream<DownloadResultParamDto> resultStream) {
        resultStream.forEach((downloadResultParamDto) -> {
            if (downloadResultParamDto.getDownloadResult() == 0)
                MaskDao.updateDownSuccess(downloadResultParamDto);
            else
                MaskDao.updateDownFail(downloadResultParamDto);
        });
    }

    private Stream<String> getElementIdStream() {
        ArrayList<String> elementIdList = new ArrayList<>();
        String elementId = "";
        for (int i = 0; i < 300; i++) {
            elementId = downTargetQueue.poll();
            if (elementId == null || elementId.equals(""))
                break;

            elementIdList.add(elementId);
        }
        return elementIdList.stream();
    }

    private Stream<String> filterElementId(List<String> elementIdList) {
        return elementIdList
                .stream()
                .filter((elementId) -> elementId != null && !elementId.equals(""));
    }

    private Stream<DownloadResultParamDto> executeDownloadAndGetResultStream(Stream<String> elementIdStream) {
        return elementIdStream
                .map((elementId) -> {
                    String downPath = getDownPath() + File.separator + elementId;

                    int downloadResult = 0;
                    if (CommonUtil.isPastElementId(elementId))
                        downloadResult = XtormUtil.downloadElement(MASK_XTORM, elementId, downPath);
                    else
                        downloadResult = XtormUtil.downloadElement(EDMS_XTORM, elementId, downPath);

                    DownloadResultParamDto downloadResultParamDto = new DownloadResultParamDto();
                    downloadResultParamDto.setElementId(elementId);

                    if (downloadResult == 0) {
                        downloadResultParamDto.setImgDir(downPath);
                    } else {
                        downloadResultParamDto.setImgDir(" ");
                    }

                    downloadResultParamDto.setDownTime(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
                    downloadResultParamDto.setDownloadResult(downloadResult);
                    return downloadResultParamDto;
                });
    }

    private static ThreadPoolExecutor getExecutorService() {
        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        return (ThreadPoolExecutor) executorServicePool.getExecutorService(ThreadInitializer.Action.DOWNLOAD);
    }

    public static boolean isCollecting() {
        return isCollecting;
    }

}
