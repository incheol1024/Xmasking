package com.inzent.util;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class XmlLoaderTest {

    @Before
    public void setUp() {

    }

    @Test
    public void loadTest() {

        XmlLoader xmlLoader = new XmlLoader();

    }

    @Test
    public void xPathTest() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {

        InputStream inputStream = XmlLoaderTest.class.getResourceAsStream("test.xml");

        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<DBMS driver=\"oracle.jdbc.driver.OracleDriver\"\n" +
                "      url=\"jdbc:oracle:thin:@jpa.c4hnk07tn7ap.ap-northeast-2.rds.amazonaws.com:1521:ORCL\"\n" +
                "      user=\"xtorm\"\n" +
                "      password=\"xtorm\">\n" +
                "    <agent type=\"selector\" name=\"sample1\">\n" +
                "        <select column=\"elementid\"> select * from asyscontentelement </select>\n" +
                "    </agent>\n" +
                "\n" +
                "    <agent type=\"selector\" name=\"sample2\">\n" +
                "        <select column=\"elementid\"> select * from asyselement</select>\n" +
                "    </agent>\n" +
                "</DBMS>";

//        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        XPath xPath = XPathFactory.newInstance().newXPath();
//        Node col2 = (Node)xPath.evaluate("//*[@id='c2']", document, XPathConstants.NODE);

        Node node = (Node) xPath.evaluate("//DBMS", document, XPathConstants.NODE);

        System.out.println(node.getNodeName());

        System.out.println(node.hasChildNodes());
        NodeList nodeList = node.getChildNodes();
        System.out.println(nodeList.item(0).getNodeName());
        System.out.println(nodeList.item(1).getNodeName());
        System.out.println(nodeList.item(2).getNodeName());
        System.out.println(nodeList.item(3).getNodeName());



        String value = node.getAttributes().getNamedItem("driver").getTextContent();

        System.out.println(value);

    }


}
