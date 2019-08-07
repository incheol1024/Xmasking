package com.inzent.agent.download;

import com.inzent.agent.Agent;

import java.io.File;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.io.File.separator;

public interface DownloadAgent extends Agent {

    Boolean IS_DOWNLOAD = Boolean.valueOf(properties.getProperty("DOWNLOAD"));

    String DOWN_TARGET_SQL = properties.getProperty("DOWN_TARGET_SQL");

    String DOWN_SUCCESS_SQL = properties.getProperty("DOWN_SUCCESS_SQL");

    String DOWN_FAIL_SQL = properties.getProperty("DOWN_FAIL_SQL");

    String DOWN_ROOT_PATH = properties.getProperty("DOWN_ROOT_PATH");

    String DOWN_SUCCESS_PARAM = "12";

    String DOWN_FAIL_PARAM = "19";

    int DOWN_FOLDER_NUMBER = Integer.valueOf(properties.getProperty("DOWN_FOLDER_NUMBER"));

    int DOWN_TARGET_QUEUE_SIZE = Integer.valueOf(properties.getProperty("DOWN_TARGET_QUEUE_SIZE"));

    AtomicInteger FOLDER_ROUND_POINT = new AtomicInteger();

    Queue<String> downTargetQueue = new ConcurrentLinkedQueue<>();

    default String getDownRootPath() {
        return DOWN_ROOT_PATH;
    }

    default String getDatePath() {
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    default String getDownPath() {
        FOLDER_ROUND_POINT.compareAndSet(DOWN_FOLDER_NUMBER, BigInteger.ZERO.intValue());
        String path = getDownRootPath() + separator + getDatePath() + separator + FOLDER_ROUND_POINT.toString();
        validateDirectory(path);
        FOLDER_ROUND_POINT.addAndGet(BigInteger.ONE.intValue());
        return path;
    }

    default void validateDirectory(String directory) {

        File file = new File(directory);

        if(file.exists())
            return;

        file.mkdir();
    }


}
