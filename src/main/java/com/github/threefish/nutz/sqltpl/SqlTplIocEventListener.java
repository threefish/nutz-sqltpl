package com.github.threefish.nutz.sqltpl;

import org.nutz.ioc.IocEventListener;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.resource.impl.FileResource;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date 2019/2/9
 */
@IocBean
public class SqlTplIocEventListener implements IocEventListener {

    @Override
    public Object afterBorn(Object obj, String beanName) {
        return obj;
    }

    @Override
    public Object afterCreate(Object obj, String beanName) {
        Class klass = obj.getClass();
        SqlsXml sqls = (SqlsXml) klass.getAnnotation(SqlsXml.class);
        if (sqls != null) {
            FileResource resource = getXmlFileResource(klass, sqls.value());
            SqlsTplHolder holder = new SqlsTplHolder(resource, Mvcs.ctx().getDefaultIoc().getByType(sqls.klass()));
            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == SqlsTplHolder.class) {
                    try {
                        field.setAccessible(true);
                        field.set(obj, holder);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
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

    /**
     * 取得xml文件
     *
     * @param klass
     * @param fileName
     * @return
     */
    private FileResource getXmlFileResource(Class klass, String fileName) {
        String pn = klass.getPackage().getName().replace(".", "/");
        URL url = klass.getClassLoader().getResource("/" + pn + "/" + fileName);
        if (url != null && new File(url.getFile()).exists()) {
            return new FileResource(new File(url.getFile()));
        }
        throw new RuntimeException(String.format("sqls xml [%s] is not exists!!!", fileName));
    }
}
