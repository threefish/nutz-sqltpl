package com.github.threefish.nutz.sqltpl;

import com.github.threefish.nutz.sqltpl.annotation.SqlsXml;
import com.github.threefish.nutz.sqltpl.exception.NutzSqlTemplateXmlNotFoundError;
import com.github.threefish.nutz.sqltpl.resource.FileResource;
import com.github.threefish.nutz.sqltpl.resource.JarResource;
import com.github.threefish.nutz.sqltpl.templte.ISqlTemplteEngine;
import com.github.threefish.nutz.sqltpl.service.ISqlTpl;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.IocEventListener;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
@IocBean(args = "refer:$ioc")
public class SqlTplIocEventListener implements IocEventListener {
    private static final Log LOG = Logs.get();
    private static final String JAR = "jar";
    private static final String XML = ".xml";
    private final Ioc ioc;

    public SqlTplIocEventListener(Ioc ioc) {
        this.ioc = ioc;
    }

    @Override
    public Object afterBorn(Object obj, String beanName) {
        return obj;
    }

    @Override
    public Object afterCreate(Object bean, String beanName) {
        if (bean instanceof ISqlTpl) {
            ISqlTpl iSqlTpl = (ISqlTpl) bean;
            Class klass = bean.getClass();
            SqlsXml sqls = (SqlsXml) klass.getAnnotation(SqlsXml.class);
            if (sqls != null) {
                iSqlTpl.setSqlTpl(getSqlsTplHolder(klass, getXmlName(klass), ioc.getByType(sqls.klass())));
            }
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 取得xml文件名
     * @param klass
     * @return
     */
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
        try {
            String xmlPath = klass.getPackage().getName().replace(".", File.separator) + File.separator + fileName;
            URL url = klass.getClassLoader().getResource(xmlPath);
            if (Objects.isNull(url)) {
                throw new NutzSqlTemplateXmlNotFoundError(String.format("sqls xml [%s] is not exists!!!", xmlPath));
            }
            if (JAR.equals(url.getProtocol())) {
                return new SqlsTplHolder(
                        new SqlTplResourceLoader(new JarResource(new File(klass.getProtectionDomain().getCodeSource().getLocation().getPath()), url),
                                sqlTemplteEngine));
            } else {
                File file = new File(URLDecoder.decode(url.getFile(), Charset.defaultCharset().name()));
                if (file.exists()) {
                    return new SqlsTplHolder(new SqlTplResourceLoader(new FileResource(file), sqlTemplteEngine));
                }
            }
            throw new NutzSqlTemplateXmlNotFoundError(String.format("sqls xml [%s] is not exists!!!", xmlPath));
        } catch (UnsupportedEncodingException e) {
            LOG.error("不支持的编码异常", e);
            throw new NutzSqlTemplateXmlNotFoundError(e);
        }
    }
}
