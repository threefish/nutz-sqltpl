package com.github.threefish.nutz.utils;

import org.junit.Test;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/2/25
 */
public class XmlTest {

    @Test
    public void loadXMl() {
        InputStream in = this.getClass().getResourceAsStream("/test1.xml");
        Document document = XmlUtils.loadDocument(in);
        HashMap<String, String> cache = new HashMap<>();
        XmlUtils.setCache(document, "sql", "id", cache);
        String json = Json.toJson(cache, JsonFormat.compact());
        assert "{\"queryAll\":\" SELECT * from ${tableName} <exp> if(isNotEmpty(name)){ </exp> where name like @name and 1>0 <exp> } </exp>  \"}".equals(json);
    }
}
