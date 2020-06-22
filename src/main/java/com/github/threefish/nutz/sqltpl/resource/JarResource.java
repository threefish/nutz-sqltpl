package com.github.threefish.nutz.sqltpl.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/3/11
 */
public class JarResource extends FileResource {

    /**
     * xml资源路径
     */
    URL xmlFileUrl;

    public JarResource(File file, URL xmlFileUrl) {
        super(file);
        this.xmlFileUrl = xmlFileUrl;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return xmlFileUrl.openStream();
    }

}
