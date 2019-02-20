package com.github.threefish.nutz.utils;

import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.resource.impl.FileResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/2/19
 */
public class XmlUtils {

    private static final Log LOG = Logs.get();

    /**
     * 加载XML Document
     *
     * @param resource
     * @return
     */
    public static Document loadDocument(FileResource resource) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setXIncludeAware(false);
            dbf.setExpandEntityReferences(false);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            InputStream ins = resource.getInputStream();
            Document document = builder.parse(ins);
            document.normalizeDocument();
            Streams.safeClose(ins);
            return document;
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * 设置缓存
     *
     * @param document
     * @param tag
     * @param attrName
     * @param hashMap
     */
    public static void setCache(Document document, String tag, String attrName, HashMap hashMap) {
        NodeList nodeList = document.getElementsByTagName(tag);
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                Element element = (Element) nodeList.item(i);
                if (element.hasAttribute(attrName) && Strings.isNotBlank(element.getAttribute(attrName))) {
                    String id = element.getAttribute(attrName);
                    hashMap.put(id, element.getTextContent());
                }
            }
        }
    }
}
