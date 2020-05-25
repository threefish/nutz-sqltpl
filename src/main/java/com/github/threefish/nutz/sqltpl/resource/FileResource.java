package com.github.threefish.nutz.sqltpl.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/3/11
 */
public class FileResource implements Resource {
    /**
     * 文件（通过文件的修改时间判断是否重新加载xml文件）
     */
    protected File file;

    public FileResource(File file) {
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public File getFile() {
        return file;
    }

}
