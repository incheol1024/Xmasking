package com.inzent.pool.database;

import com.inzent.entity.DatabaseEntity;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class DatabaseConfigPoolTest {



    @Test
    public void putDatabaseEntityTest() {

        DatabaseConfigPool databaseConfigPool = DatabaseConfigPool.getInstance();
        databaseConfigPool.putDatabaseEntity(DatabaseName.EDMS, new DatabaseEntity());

        Optional<DatabaseEntity> getDatabaseEntity = databaseConfigPool.getDatabaseEntity(DatabaseName.EDMS);

        Assertions.assertThat(getDatabaseEntity).isNotNull().containsInstanceOf(DatabaseEntity.class);
    }
}
