package com.inzent.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XmlLoader {

    private static InputStream inputStream = XmlLoader.class.getClassLoader().getResourceAsStream("test.xml");
    private static Document document;

    static {

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Document getDocument() {
        return document;
    }

}
