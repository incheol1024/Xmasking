package com.inzent.intialize;

import com.inzent.entity.DatabaseEntity;
import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.pool.database.DatabaseConfigPool;
import com.inzent.pool.database.DatabaseName;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

public class DatabaseConfigInitializerTest {


    @Test
    public void initializeTest() {

        DatabaseConfigInitializer databaseConfigInitailizer = DatabaseConfigInitializer.getInstance();
        databaseConfigInitailizer.initialize();

        DatabaseConfigPool databaseConfigPool = DatabaseConfigPool.getInstance();
        Optional<DatabaseEntity> databaseEntityEDMS = databaseConfigPool.getDatabaseEntity(DatabaseName.EDMS);
        Optional<DatabaseEntity> databaseEntityMASK = databaseConfigPool.getDatabaseEntity(DatabaseName.MASK);

        Assertions.assertThat(databaseEntityEDMS).isNotNull().containsInstanceOf(DatabaseEntity.class);
        Assertions.assertThat(databaseEntityMASK).isNotNull().containsInstanceOf(DatabaseEntity.class);
    }
}
