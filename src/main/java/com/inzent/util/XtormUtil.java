package com.inzent.util;

import com.windfire.apis.asys.asysUsrElement;
import com.windfire.apis.asysConnectData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Properties;

public class XtormUtil {

    private static Logger logger = LoggerFactory.getLogger(XtormUtil.class);

    private static Properties properties = AppProperty.getProperties();

    private static String server = properties.getProperty("XTORM_MASK_SERVER");
    private static int port = Integer.parseInt(properties.getProperty("XTORM_MASK_PORT"));
    private static String client = properties.getProperty("XTORM_MASK_CLIENT");
    private static String user = properties.getProperty("XTORM_MASK_USER");
    private static String password = properties.getProperty("XTORM_MASK_PASSWORD");
    private static String gateway = properties.getProperty("XTORM_MASK_GATEWAY");
    private static String eClass = properties.getProperty("XTORM_MASK_ECLASS");

    private XtormUtil() {
        throw new AssertionError();
    }


    public static Optional<String> uploadElement(String localFile) {

        asysConnectData connection = XtormConnection.getXtormConnection();
        asysUsrElement element = getAsysUsrElement("", connection);

        element.m_localFile = localFile;
        element.m_descr = "TEST_IMAGE";
        element.m_cClassId = "BASIC"; //.m_archive = "CAS_ARC";
        element.m_userSClass = "SUPER";
        element.m_eClassId = "IMAGE";

        int result = element.create("XVARM_MAIN");
        String elementId = element.m_elementId.split("::")[1];

        if (result == 0) {
            return Optional.of(elementId);
        }

        logger.error("Xtorm Upload Fail. return code {}, error msg - {}", result, element.m_lastError);
        return Optional.empty();

    }


    public static int replaceElement(String elementId, String localFile) {
        asysConnectData connection = XtormConnection.getXtormConnection();
        int result = getAsysUsrElement(elementId, connection).replaceContent(localFile, "", "");
        XtormConnection.closeXtormConnection(connection);
        return result;
    }

    public static int replaceElementForArchive(String elementId, String localFile, String archive) {
        asysConnectData connection = XtormConnection.getXtormConnection();
        int result = getAsysUsrElement(elementId, connection).replaceContent(localFile, "", "");
        XtormConnection.closeXtormConnection(connection);
        return result;
    }

    public static int downloadElement(String elementId, String localFile) {
        asysConnectData connection = XtormConnection.getXtormConnection();
        asysUsrElement element = getAsysUsrElement(elementId, connection);
        int result = element.getContent(localFile, "", "");
        XtormConnection.closeXtormConnection(connection);

        if (result != 0)
            logger.error("Xtorm Download Fail {} . return code {}, error msg - {}",element.m_elementId, result, element.m_lastError);

        logger.debug("Xtorm Download Success. {} : {}", element.m_elementId, localFile);
        return result;
    }

    private static asysUsrElement getAsysUsrElement(String elementId, asysConnectData connection) {
        asysUsrElement element = new asysUsrElement(connection);
        element.m_elementId = gateway + "::" + elementId + "::" + eClass;
        return element;
    }


    private static class XtormConnection {

        private static asysConnectData getXtormConnection() {
            return new asysConnectData(server, port, client, user, password);
        }

        private static void closeXtormConnection(asysConnectData xtormConnection) {
            if (xtormConnection == null)
                return;

            xtormConnection.close();
        }
    }

}
