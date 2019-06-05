package com.github.threefish.nutz.sqltpl;

import java.io.File;
import java.io.InputStream;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/6/5
 */
public class JarResource {

    Class klass;

    /**
     * xml路径
     */
    String xmlPath;

    /**
     * jar文件（通过jar文件的修改时间判断是否重新加载xml文件）
     */
    File jarFile;

    public JarResource(Class klass, String xmlPath, File jarFile) {
        this.klass = klass;
        this.xmlPath = xmlPath;
        this.jarFile = jarFile;
    }

    public InputStream getInputStream() {
        return klass.getClassLoader().getResourceAsStream(xmlPath);
    }

    public File getJarFile() {
        return jarFile;
    }
}
