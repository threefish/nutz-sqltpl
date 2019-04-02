package com.github.threefish.nutz.sqls;

import com.github.threefish.nutz.sqltpl.SqlsXml;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/2/25
 */
public class LoadTest {

    private static final String XML = ".xml";

    @Test
    public void testBean() {
        Assert.assertTrue(("Bean.xml".equals(getXmlName(Bean.class))));
        Assert.assertTrue(("Bean1.xml".equals(getXmlName(Bean1.class))));
        Assert.assertTrue(("/test/Bean2.xml".equals(getXmlName(Bean2.class))));
    }

    private String getXmlName(Class klass) {
        SqlsXml sqls = (SqlsXml) klass.getAnnotation(SqlsXml.class);
        if (sqls != null) {
            return "".equals(sqls.value()) ? klass.getSimpleName().concat(XML) : sqls.value();
        }
        return null;
    }
}
