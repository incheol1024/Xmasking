package com.inzent;

import com.inzent.initialize.database.DataSourcePoolInitializer;
import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.initialize.database.QueryRunnerInitializer;
import com.inzent.initialize.thread.AgentThreadInitializer;

import java.util.Objects;

import static jdk.internal.dynalink.support.Guards.isNull;

class Boot {

    private DatabaseConfigInitializer databaseConfigInitializer = DatabaseConfigInitializer.getInstance();
    private DataSourcePoolInitializer dataSourcePoolInitializer = DataSourcePoolInitializer.getInstance();
    private QueryRunnerInitializer queryRunnerInitializer = QueryRunnerInitializer.getIntance();

    private AgentThreadInitializer agentThreadInitializer = AgentThreadInitializer.getInstance();


    void boot() {

        checkNull();

        databaseConfigInitializer.initialize();
        dataSourcePoolInitializer.initialize();
        queryRunnerInitializer.initialize();
        agentThreadInitializer.initialize();
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
