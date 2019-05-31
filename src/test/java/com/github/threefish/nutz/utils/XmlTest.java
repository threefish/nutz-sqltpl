package com.github.threefish.nutz.utils;

import com.github.threefish.nutz.sqltpl.BeetlSqlTemplteEngineImpl;
import com.github.threefish.nutz.sqltpl.ISqlTemplteEngine;
import org.junit.Assert;
import org.junit.Test;
import org.nutz.lang.util.NutMap;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/2/25
 */
public class XmlTest {

    @Test
    public void testBeetl() {
        InputStream in = this.getClass().getResourceAsStream("/testBeetl.xml");
        Document document = XmlUtils.loadDocument(in);
        HashMap<String, String> cache = new HashMap<>();
        XmlUtils.setCache(document, "sql", "id", cache);
        ISqlTemplteEngine engine = new BeetlSqlTemplteEngineImpl();
        ((BeetlSqlTemplteEngineImpl) engine).init();
        String sql = engine.render(cache.getOrDefault("queryAll", ""), NutMap.NEW().setv("tableName", "table_a").setv("name", "aaa"));
        String wrapLine = engine.render(cache.getOrDefault("wrapLine", ""), NutMap.NEW().setv("tableName", "table_a").setv("name", "aaa"));
        Assert.assertTrue(("SELECT * from table_a  where name like @name and 1>0".equals(sql)));
        Assert.assertTrue(("SELECT * from table_a \"实打实的哈桑\\n撒大苏         打的撒的哈\"    where name like         @name and 1>0   fsadf         sdfasdf         sdf".equals(wrapLine)));
    }
}
