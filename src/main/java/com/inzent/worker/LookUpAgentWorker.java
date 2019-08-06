package com.inzent.worker;

import com.inzent.agent.lookup.EdmsLookupAgent;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import com.inzent.util.AppProperty;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

import static com.inzent.agent.lookup.EdmsAgent.EdmsFindMode.ALL;
import static com.inzent.agent.lookup.EdmsAgent.EdmsFindMode.MASK_TARGET;
import static com.inzent.agent.lookup.EdmsAgent.EdmsTableName.*;

public class LookUpAgentWorker {

    private ExecutorService executorService;
    private static Properties properties = AppProperty.getProperties();

    private static boolean isLookUp = Boolean.valueOf(properties.getProperty("LOOKUP"));

    private static boolean isLookUpAll = Boolean.valueOf(properties.getProperty("LOOKUP_ALL"));

    public LookUpAgentWorker() {
        this.executorService = getExecutorService();
    }

    private ExecutorService getExecutorService() {
        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        return executorServicePool.getExecutorService(ThreadInitializer.Action.DOWNLOAD);
    }

    public void doWork() {
        if(isLookUp)
            executeLookUp();
        if(isLookUpAll)
            executeLookUpAll();
    }

    private void executeLookUp() {
        executorService.execute(new EdmsLookupAgent(MASK_TARGET, EDM_INFO_DET_T));
        executorService.execute(new EdmsLookupAgent(MASK_TARGET, EDM_INFO_DET_VER_T));
    }

    private void executeLookUpAll() {
        executorService.execute(new EdmsLookupAgent(ALL, EDM_INFO_DET_T));
        executorService.execute(new EdmsLookupAgent(ALL, EDM_INFO_DET_VER_T));
    }

}
