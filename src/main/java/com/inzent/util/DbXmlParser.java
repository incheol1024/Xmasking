package com.inzent.util;

import com.inzent.entity.DatabaseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class DbXmlParser {


    private static XPath xpath = XPathFactory.newInstance().newXPath();


    /**
     * 임시 데이터 저장 확인용
     */

    DatabaseEntity databaseEntity = new DatabaseEntity();


    public DatabaseEntity paser() {

        Document document = XmlLoader.getDocument();

        try {
            Node node = (Node) xpath.evaluate("//DBMS", document, XPathConstants.NODE);

            databaseEntity.setDriver(node.getAttributes().getNamedItem("driver").getTextContent());
            databaseEntity.setUrl(node.getAttributes().getNamedItem("url").getTextContent());
            databaseEntity.setUser(node.getAttributes().getNamedItem("user").getTextContent());
            databaseEntity.setPassword(node.getAttributes().getNamedItem("password").getTextContent());


        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return databaseEntity;

    }
}
