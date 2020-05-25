package com.github.threefish.nutz.sqltpl.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/3/14
 */
public interface Resource {

    /**
     * @return xml输入流
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;

    /**
     * 取得文件
     *
     * @return 取得文件
     */
    File getFile();

}
