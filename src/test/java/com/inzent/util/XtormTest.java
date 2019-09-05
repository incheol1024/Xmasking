package com.inzent.util;

import com.windfire.apis.asys.asysUsrElement;
import com.windfire.apis.asysConnectData;
import org.junit.Test;

public class XtormTest {

    @Test
    public void replaceTest() {

        asysConnectData connectData = new asysConnectData("127.0.0.1", 2102, "Test", "SUPER", "SUPER");
        asysUsrElement asysUsrElement = new asysUsrElement(connectData);
        asysUsrElement.m_elementId = "XVARM_MAIN::" +  "2019090417124700" + "::IMAGE";
        int ret = asysUsrElement.replaceContent("D:/msdia80.dll", "", "");
        System.out.println(ret);
        System.out.println(asysUsrElement.m_lastError);
        connectData.close();
    }
}
