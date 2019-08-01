package com.inzent.initialize.database;

import com.inzent.entity.DatabaseEntity;
import com.inzent.pool.database.DatabaseConfigPool;
import com.inzent.pool.database.DatabaseName;
import com.inzent.util.AppProperty;
import com.inzent.util.ValidateUtil;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Properties;

public class DatabaseConfigInitializer implements DatabaseInitializer {

    private Logger logger = LoggerFactory.getLogger(DatabaseConfigInitializer.class);

    private static final DatabaseConfigInitializer databaseConfigInitializer = new DatabaseConfigInitializer();

    private DatabaseConfigInitializer() {
    }

    public static DatabaseConfigInitializer getInstance() {
        return databaseConfigInitializer;
    }

    @Override
    public void initialize() {

        logger.trace("Initialize DatabaseConfigInitializer");

        Properties properties = AppProperty.getProperties();

        DatabaseConfigPool databaseConfigPool = DatabaseConfigPool.getInstance();

        DatabaseEntity edmsDatabaseEntity = new DatabaseEntity();
        edmsDatabaseEntity.setDriver(properties.getProperty("DB_EDMS_DRIVER"));
        edmsDatabaseEntity.setUrl(properties.getProperty("DB_EDMS_URL"));
        edmsDatabaseEntity.setUser(properties.getProperty("DB_EDMS_USER"));
        edmsDatabaseEntity.setPassword(decryptPassword(properties.getProperty("DB_EDMS_PASSWORD")));

        String connectionCountprop = properties.getProperty("DB_EDMS_CONNECTION_COUNT");
        if (connectionCountprop == null || connectionCountprop.equals("")) {
            edmsDatabaseEntity.setConnectionPoolCount(DatabaseInitializer.DEFUALT_CONNECTION_COUNT);
        } else {
            edmsDatabaseEntity.setConnectionPoolCount(Integer.valueOf(connectionCountprop));
        }

        databaseConfigPool.putDatabaseEntity(DatabaseName.EDMS, edmsDatabaseEntity);

        logger.trace("Complete initializing EDMS DatabaseEntity {}", databaseConfigPool.getDatabaseEntity(DatabaseName.EDMS).get());

        DatabaseEntity maskDatabaseEntity = new DatabaseEntity();
        maskDatabaseEntity.setDriver(properties.getProperty("DB_MASK_DRIVER"));
        maskDatabaseEntity.setUrl(properties.getProperty("DB_MASK_URL"));
        maskDatabaseEntity.setUser(properties.getProperty("DB_MASK_USER"));
        maskDatabaseEntity.setPassword(decryptPassword(properties.getProperty("DB_MASK_PASSWORD")));

        connectionCountprop = properties.getProperty("DB_MASK_CONNECTION_COUNT");
        if (connectionCountprop == null || connectionCountprop.equals("")) {
            maskDatabaseEntity.setConnectionPoolCount(DatabaseInitializer.DEFUALT_CONNECTION_COUNT);
        } else {
            maskDatabaseEntity.setConnectionPoolCount(Integer.valueOf(connectionCountprop));
        }

        databaseConfigPool.putDatabaseEntity(DatabaseName.MASK, maskDatabaseEntity);

        logger.trace("Complete initializing MASK DatabaseEntity {}", databaseConfigPool.getDatabaseEntity(DatabaseName.MASK).get());

        logger.trace("Validate completed DatabaseEntity");
        validateDatabaseProperties(databaseConfigPool);
    }


    private void validateDatabaseProperties(DatabaseConfigPool databaseConfigPool) {

        Optional<DatabaseEntity> optionalDatabaseEntityEDMS = databaseConfigPool.getDatabaseEntity(DatabaseName.EDMS);
        DatabaseEntity databaseEntityEDMS = optionalDatabaseEntityEDMS.orElseThrow(() -> new RuntimeException("EDMS DatabaseEntity is null."));
        validateMandatoryProperties(databaseEntityEDMS);
        validateOptionalProperties(databaseEntityEDMS);

        Optional<DatabaseEntity> optionalDatabaseEntityMASK = databaseConfigPool.getDatabaseEntity(DatabaseName.MASK);
        DatabaseEntity databaseEntityMask = optionalDatabaseEntityMASK.orElseThrow(() -> new RuntimeException("MASK DatabaseEntity is null."));
        validateMandatoryProperties(databaseEntityMask);
        validateOptionalProperties(databaseEntityMask);
    }

    private void validateMandatoryProperties(DatabaseEntity databaseEntity) {

        boolean validationResult = ValidateUtil.validateStringsIsNull(
                databaseEntity.getDriver(),
                databaseEntity.getUrl(),
                databaseEntity.getUser(),
                databaseEntity.getPassword());

        if (validationResult == false)
            throw new RuntimeException("Database mandatory configuration is driver, url, user, password");
    }

    private void validateOptionalProperties(DatabaseEntity databaseEntity) {


    }

    private String decryptPassword(String encryptedPassword) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        standardPBEStringEncryptor.setPassword("HDMF2019!!");
        return standardPBEStringEncryptor.decrypt(encryptedPassword);
    }
}
