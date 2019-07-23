package com.inzent.db;

import org.junit.Test;

import java.sql.*;
import java.util.List;

public class SampleDaoTest {

    @Test
    public void findElementsTest() {
        SampleDao sampleDao = new SampleDao();
        List<Object[]> list = sampleDao.findElementIds();
        list.stream().forEach(objects -> System.out.println(objects[0]));
    }


    @Test
    public void ConnectionTest() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://teamkms.c4hnk07tn7ap.ap-northeast-2.rds.amazonaws.com:3306/xtorm", "incheol", "frenchpie");
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from xtorm.XMASK_FIND_RANGE");
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }
}
