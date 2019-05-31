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
import java.util.ArrayList;
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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
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
                    boolean wrap = Boolean.parseBoolean(element.getAttribute("wrap"));
                    if (wrap) {
                        hashMap.put(id, getContent(element).replace("\n", " "));
                    } else {
                        hashMap.put(id, getContent(element));
                    }
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
        ArrayList<String> sb = new ArrayList();
        if (childs.getLength() > 0) {
            for (int i = 0; i < childs.getLength(); i++) {
                Node node = childs.item(i);
                if (node.getNodeType() == Node.TEXT_NODE) {
                    sb.add(node.getTextContent().trim());
                } else if ("exp".equals(node.getNodeName())) {
                    sb.add("<exp>" + getContent(node) + "</exp>");
                } else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
                    sb.add(node.getTextContent().trim());
                } else {
                    sb.add(getContent(node));
                }
            }
            return Strings.join(" ", sb.toArray()).trim();
        }
        return "";
    }
}
