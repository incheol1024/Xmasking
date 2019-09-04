package com.inzent.util;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getQueryRunner() {
    }

    @Test
    public void checkElementId() {

        int ret = CommonUtil.checkElementId("ED1@abcbcb");
        Assertions.assertThat(ret).isEqualTo(3);
        System.out.println(ret);
        ret = CommonUtil.checkElementId("201904031738390011");
        Assertions.assertThat(ret).isEqualTo(2);
        System.out.println(ret);
        ret = CommonUtil.checkElementId("301902011203441200");
        Assertions.assertThat(ret).isEqualTo(3);
    }
}