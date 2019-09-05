package com.inzent.util;

import com.windfire.apis.asys.asysUsrElement;
import com.windfire.apis.asysConnectData;
import com.windfire.apis.asysConnectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Vector;

public final class XtormUtil {

    public enum XtormServer {EDMS_XTORM, MASK_XTORM;}

    private static Logger logger = LoggerFactory.getLogger(XtormUtil.class);

    private static String TARGET_ARCHIVE = AppProperty.getValue("REPLACE_TARGET_ARCHIVE");


    private XtormUtil() {
        throw new AssertionError();
    }


    public static Optional<String> uploadElement(String localFile) {

//        asysConnectData connection = XtormConnection.getXtormConnection();
//        asysUsrElement element = getAsysUsrElement("", connection);
//
//        element.m_localFile = localFile;
//        element.m_descr = "TEST_IMAGE";
//        element.m_cClassId = "BASIC"; //.m_archive = "CAS_ARC";
//        element.m_userSClass = "SUPER";
//        element.m_eClassId = "IMAGE";
//
//        int result = element.create("XVARM_MAIN");
//        String elementId = element.m_elementId.split("::")[1];
//
//        if (result == 0) {
//            return Optional.of(elementId);
//        }
//
//        logger.error("Xtorm Upload Fail. return code {}, error msg - {}", result, element.m_lastError);
        return Optional.empty();

    }


    public static int replaceElement(XtormServer xtormServer, String elementId, String localFile) {
        int result = 0;

        asysConnectData connectData = null;
        asysUsrElement element = null;

        if (xtormServer == XtormServer.EDMS_XTORM) {
            connectData = XtormConnection.getConnectionInpool(xtormServer);
            element = getAsysUsrElement(elementId, connectData);
            result = element.replaceContent(localFile, "", "");
            XtormConnection.edmsConnectionPool.releaseConnect(connectData, false);
        } else if (xtormServer == XtormServer.MASK_XTORM) {
            connectData = XtormConnection.getConnectionInpool(xtormServer);
            element = getAsysUsrElement(elementId, connectData);
            result = element.replaceContent(localFile, "$TARGETARC$", TARGET_ARCHIVE);
            XtormConnection.maskConnectionPool.releaseConnect(connectData, false);
        }

        if (result != 0)
            logger.error("{} Download Fail {} . return code {}, error msg - {}", xtormServer.name(), element.m_elementId, result, element.m_lastError);

        logger.debug("{} Download Success. {} : {}", xtormServer.name(), element.m_elementId, localFile);

        return result;
    }


    public static int downloadElement(XtormServer xtormServer, String elementId, String localFile) {
        int result = 0;

        asysConnectData connectData = null;
        asysUsrElement element = null;
        if (xtormServer == XtormServer.EDMS_XTORM) {
            connectData = XtormConnection.getConnectionInpool(xtormServer);
            element = getAsysUsrElement(elementId, connectData);
            result = element.getContent(localFile, "", "");
            XtormConnection.edmsConnectionPool.releaseConnect(connectData, false);
        } else if (xtormServer == XtormServer.MASK_XTORM) {
            connectData = XtormConnection.getConnectionInpool(xtormServer);
            element = getAsysUsrElement(elementId, connectData);
            result = element.getContent(localFile, "", "");
            XtormConnection.maskConnectionPool.releaseConnect(connectData, false);
        }

        if (result != 0) {
            logger.error("{} Download Fail {} . return code {}, error msg - {}", xtormServer.name(), element.m_elementId, result, element.m_lastError);
            return result;
        }

        logger.debug("{} Download Success. {} : {}", xtormServer.name(), element.m_elementId, localFile);
        return result;
    }

    public void closeConnectionPool() {

        if(XtormConnection.edmsConnectionPool != null){
            logger.debug("EDMS XTORM Pooling stop ========================");
            XtormConnection.edmsConnectionPool.stop();
            logger.debug("EDMS XTORM POOL COUNT:" + XtormConnection.edmsConnectionPool.getInUseCount());
            XtormConnection.edmsConnectionPool = null;
        }

        if(XtormConnection.maskConnectionPool != null) {
            logger.debug("XMASK XTORM Pooling stop ========================");
            XtormConnection.maskConnectionPool.stop();
            logger.debug("XMASK XTORM POOL COUNT:" + XtormConnection.maskConnectionPool.getInUseCount());
            XtormConnection.maskConnectionPool = null;
        }

    }

    private static asysUsrElement getAsysUsrElement(String elementId, asysConnectData connection) {
        asysUsrElement element = new asysUsrElement(connection);
        element.m_elementId = XtormConnection.EDMS_GATEWAY + "::" + elementId + "::" + XtormConnection.EDMS_ECLASS;
        return element;
    }



    private static class XtormConnection {

        private static String EDMS_SERVER = AppProperty.getValue("XTORM_EDMS_SERVER");
        private static int EDMS_PORT = Integer.parseInt(AppProperty.getValue("XTORM_EDMS_PORT"));
        private static String EDMS_CLIENT = AppProperty.getValue("XTORM_EDMS_CLIENT");
        private static String EDMS_USER = AppProperty.getValue("XTORM_EDMS_USER");
        private static String EDMS_PASSWORD = AppProperty.getValue("XTORM_EDMS_PASSWORD");
        private static String EDMS_GATEWAY = AppProperty.getValue("XTORM_EDMS_GATEWAY");
        private static String EDMS_ECLASS = AppProperty.getValue("XTORM_EDMS_ECLASS");
        private static String EDMS_POOL = AppProperty.getValue("XTORM_EDMS_POOL");

        private static String XMASK_SERVER = AppProperty.getValue("XTORM_MASK_SERVER");
        private static int XMASK_PORT = Integer.parseInt(AppProperty.getValue("XTORM_MASK_PORT"));
        private static String XMASK_CLIENT = AppProperty.getValue("XTORM_MASK_CLIENT");
        private static String XMASK_USER = AppProperty.getValue("XTORM_MASK_USER");
        private static String XMASK_PASSWORD = AppProperty.getValue("XTORM_MASK_PASSWORD");
        private static String XMASK_GATEWAY = AppProperty.getValue("XTORM_MASK_GATEWAY");
        private static String XMASK_ECLASS = AppProperty.getValue("XTORM_MASK_ECLASS");
        private static String XMASK_POOL = AppProperty.getValue("XTORM_MASK_POOL");

        private static asysConnectPool edmsConnectionPool = null;

        private static asysConnectPool maskConnectionPool = null;

        private static Logger logger = LoggerFactory.getLogger(XtormConnection.class);

        static {
            Vector edmsVector = new Vector();
            edmsVector.add(new Object[]{EDMS_SERVER, new Integer(EDMS_PORT), EDMS_CLIENT, EDMS_USER, EDMS_PASSWORD});
            //vArgs.add(new Object[]{"failover1,failover2", new Integer(2102), "myTest", "SUPER", "SUPER"});
            /*
             * @param name Pool name
             * @param classname A name of connection class to use
             * @param vArgs A list of the arguments for using in connection class
             * @param count total connection count
             * @param precon pre-connection flag
             * @param autoext connection auto-extension flag
             * @param roundrobin if true, we'll use arguments in vArgs with round-robin
             */
            edmsConnectionPool = new asysConnectPool("EDMS XTORM POOL", "com.windfire.apis.asysConnectData", edmsVector, Integer.valueOf(EDMS_POOL), false, true, false);

            logger.debug("EDMS Xtorm Pooling start ========================");
            boolean ok = edmsConnectionPool.start();
            if (!ok) {
                logger.error("EDMS Xtorm Pooling ERROR: {}", maskConnectionPool.getLastError());
            }

            Vector maskVector = new Vector();
            maskVector.add(new Object[]{XMASK_SERVER, new Integer(XMASK_PORT), XMASK_CLIENT, XMASK_USER, XMASK_PASSWORD});
            maskConnectionPool = new asysConnectPool("MASK XTORM POOL", "com.windfire.apis.asysConnectData", maskVector, Integer.valueOf(XMASK_POOL), false, true, false);

            logger.debug("MASK Xmask Pooling start ========================");

            ok = maskConnectionPool.start();
            if (!ok) {
                logger.error("MASK Xmask Pooling ERROR: {}", maskConnectionPool.getLastError());
            }


        }

        private static void closeXtormConnection(asysConnectData xtormConnection) {
            if (xtormConnection == null)
                return;

            xtormConnection.close();
        }

        private static asysConnectData getConnectionInpool(XtormServer xtormServer) {

            if (XtormServer.EDMS_XTORM == xtormServer)
                return (asysConnectData) edmsConnectionPool.getConnect();

            else if (XtormServer.MASK_XTORM == xtormServer)
                return (asysConnectData) maskConnectionPool.getConnect();

            throw new RuntimeException("Cannot obtain xtorm connection for xtormServer " + xtormServer.name());
        }
    }

}
