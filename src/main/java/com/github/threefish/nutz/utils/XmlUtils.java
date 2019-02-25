package com.github.threefish.nutz.utils;

import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
public class XmlUtils {

    private static final Log LOG = Logs.get();


    /**
     * 加载XML Document
     *
     * @param ins 资源文件流
     * @return 文档
     */
    public static Document loadDocument(InputStream ins) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setXIncludeAware(false);
            dbf.setExpandEntityReferences(false);
            DocumentBuilder builder = dbf.newDocumentBuilder();
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
     * @param document 文档
     * @param tag      标签
     * @param attrName 属性
     * @param hashMap  map对象
     */
    public static void setCache(Document document, String tag, String attrName, HashMap hashMap) {
        NodeList nodeList = document.getElementsByTagName(tag);
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                Element element = (Element) nodeList.item(i);
                if (element.hasAttribute(attrName) && Strings.isNotBlank(element.getAttribute(attrName))) {
                    String id = element.getAttribute(attrName);
                    hashMap.put(id, getContent(element));
                }
            }
        }
    }

    /**
     * 取得模版内容支持 exp 标签
     *
     * @param element
     * @return
     */
    private static String getContent(Node element) {
        NodeList childs = element.getChildNodes();
        StringBuffer sb = new StringBuffer();
        if (childs.getLength() > 0) {
            for (int i = 0; i < childs.getLength(); i++) {
                Node node = childs.item(i);
                if ("#text".equals(node.getNodeName())) {
                    sb.append(" " + Strings.sNull(node.getTextContent()).trim() + " ");
                } else if ("exp".equals(node.getNodeName())) {
                    sb.append("<exp>" + getContent(node) + "</exp>");
                } else {
                    sb.append(getContent(node));
                }
            }
            return sb.toString();
        }
        return "";
    }
}
