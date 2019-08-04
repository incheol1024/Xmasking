package com.inzent.agent;

import com.inzent.dto.mask.DownTargetDto;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

interface DownloadAgent extends Agent {

    Boolean download = Boolean.valueOf(properties.getProperty("DOWNLOAD"));

    String downTargetSql = properties.getProperty("DOWN_TARGET_SQL");

    String downSuccessSql = properties.getProperty("DOWN_SUCCESS_SQL");

    String downFailSql = properties.getProperty("DOWN_FAIL_SQL");

    Queue<DownTargetDto> downTargetQueue = new ConcurrentLinkedQueue<>();

}
