package com.github.threefish.nutz.sqltpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author huchuc@vip.qq.com
 * <p>Date: 2019/06/05</p>
 */
public class JarResource {
    /**
     * jar文件（通过jar文件的修改时间判断是否重新加载xml文件）
     */
    File jarFile;
    /**
     * xml资源路径
     */
    URL xmlFileUrl;

    public JarResource(URL xmlFileUrl, File jarFile) {
        this.jarFile = jarFile;
        this.xmlFileUrl = xmlFileUrl;
    }

    public InputStream getInputStream() throws IOException {
        return xmlFileUrl.openStream();
    }

    public File getJarFile() {
        return jarFile;
    }
}
