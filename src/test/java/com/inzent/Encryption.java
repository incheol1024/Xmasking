package com.inzent;

import com.inzent.util.AppProperty;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class Encryption {

    Properties properties = null;

    @Before
    public void setUp() {
        properties = AppProperty.getProperties();
    }

    @Test
    public void encTest() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        standardPBEStringEncryptor.setPassword("HDMF2019!!");
//        String dbPwd = standardPBEStringEncryptor.encrypt(properties.getProperty("DB_MASK_PASSWORD"));
        String dbPwd = standardPBEStringEncryptor.encrypt("xtorm");
        System.out.println(dbPwd); //2DwfdOEuC0gD+8vttgVCeKyqIAWhcEaK
    }

    @Test
    public void decTest() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        standardPBEStringEncryptor.setPassword("HDMF2019!!");
        String decPwd = standardPBEStringEncryptor.decrypt("2DwfdOEuC0gD+8vttgVCeKyqIAWhcEaK");
        System.out.println(decPwd);
    }
}
