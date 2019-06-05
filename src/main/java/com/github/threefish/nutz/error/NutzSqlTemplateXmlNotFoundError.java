package com.github.threefish.nutz.error;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/6/5
 */
public class NutzSqlTemplateXmlNotFoundError extends RuntimeException {

    public NutzSqlTemplateXmlNotFoundError(String message) {
        super(message);
    }

    public NutzSqlTemplateXmlNotFoundError(Throwable cause) {
        super(cause);
    }
}
