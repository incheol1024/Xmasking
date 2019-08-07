package com.inzent.initialize.thread;

import com.inzent.initialize.Initializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface ThreadInitializer extends Initializer {

    int DEFAULT_THREAD_COUNT = 1;

    enum Action {
        LOOKUP,
        LOOKUP_ALL,
        DOWNLOAD,
        REPLACE,
        SCHEDULER
    }

    default ExecutorService generateExcecutorService(int threadCount) {
        return Executors.newFixedThreadPool(threadCount);
    }

}
