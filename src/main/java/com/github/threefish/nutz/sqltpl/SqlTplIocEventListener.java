package com.github.threefish.nutz.sqltpl;

import com.github.threefish.nutz.error.NutzSqlTemplateXmlNotFoundError;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.IocEventListener;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.resource.impl.FileResource;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
@IocBean(args = "refer:$ioc")
public class SqlTplIocEventListener implements IocEventListener {
    private static final Log LOG = Logs.get();
    private static final String JAR = "jar";
    private static final String XML = ".xml";
    private Ioc ioc;

    public SqlTplIocEventListener(Ioc ioc) {
        this.ioc = ioc;
    }

    @Override
    public Object afterBorn(Object obj, String beanName) {
        return obj;
    }

    @Override
    public Object afterCreate(Object obj, String beanName) {
        Class klass = obj.getClass();
        SqlsXml sqls = (SqlsXml) klass.getAnnotation(SqlsXml.class);
        if (sqls != null) {
            String xmlName = getXmlName(klass);
            SqlsTplHolder holder = getSqlsTplHolder(klass, xmlName, ioc.getByType(sqls.klass()));
            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == SqlsTplHolder.class) {
                    try {
                        field.setAccessible(true);
                        field.set(obj, holder);
                    } catch (IllegalAccessException e) {
                        LOG.error(e);
                    }
                }
            }
        }
        return obj;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private String getXmlName(Class klass) {
        SqlsXml sqls = (SqlsXml) klass.getAnnotation(SqlsXml.class);
        return "".equals(sqls.value()) ? klass.getSimpleName().concat(XML) : sqls.value();
    }

    /**
     * 取得xml文件
     *
     * @param klass
     * @param fileName
     * @return
     */
    private SqlsTplHolder getSqlsTplHolder(Class klass, String fileName, ISqlTemplteEngine sqlTemplteEngine) {
        String xmlPath = klass.getPackage().getName().replace(".", File.separator) + File.separator + fileName;
        URL url = klass.getClassLoader().getResource(xmlPath);
        try {
            if (url != null) {
                if (JAR.equals(url.getProtocol())) {
                    return new SqlsTplHolder(new JarResource(klass, xmlPath, new File(klass.getProtectionDomain().getCodeSource().getLocation().getPath())),
                            sqlTemplteEngine);
                } else {
                    File file = new File(URLDecoder.decode(url.getFile(), Encoding.defaultEncoding()));
                    if (file.exists()) {
                        return new SqlsTplHolder(new FileResource(file), sqlTemplteEngine);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
            throw new NutzSqlTemplateXmlNotFoundError(e);
        }
        throw new NutzSqlTemplateXmlNotFoundError(String.format("sqls xml [%s] is not exists!!!", fileName));
    }
}
