package com.inzent;

import com.inzent.agent.download.DownTargetCollector;
import com.inzent.initialize.database.DataSourcePoolInitializer;
import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.initialize.database.QueryRunnerInitializer;
import com.inzent.initialize.thread.AgentThreadInitializer;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Boot {

    private DatabaseConfigInitializer databaseConfigInitializer = DatabaseConfigInitializer.getInstance();
    private DataSourcePoolInitializer dataSourcePoolInitializer = DataSourcePoolInitializer.getInstance();
    private QueryRunnerInitializer queryRunnerInitializer = QueryRunnerInitializer.getIntance();

    private AgentThreadInitializer agentThreadInitializer = AgentThreadInitializer.getInstance();


    public void boot() {

        checkNull();

        databaseConfigInitializer.initialize();
        dataSourcePoolInitializer.initialize();
        queryRunnerInitializer.initialize();
        agentThreadInitializer.initialize();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(new DownTargetCollector());
    }

    private void checkNull() {

        if(Objects.isNull(databaseConfigInitializer))
            throw new NullPointerException(DatabaseConfigInitializer.class.getName() + " is null reference");

        if(Objects.isNull(dataSourcePoolInitializer))
            throw new NullPointerException(DataSourcePoolInitializer.class.getName() + " is null reference");

        if(Objects.isNull(queryRunnerInitializer))
            throw new NullPointerException(QueryRunnerInitializer.class.getName() + " is null reference");

        if(Objects.isNull(agentThreadInitializer))
            throw new NullPointerException(AgentThreadInitializer.class.getName() + " is null reference");

    }



}
