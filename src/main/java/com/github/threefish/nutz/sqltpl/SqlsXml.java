package com.github.threefish.nutz.sqltpl;

import java.lang.annotation.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date 2019/2/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SqlsXml {

    /**
     * 文件相对与当前service类路径
     *
     * @return xml文件路径
     */
    String value();

    /**
     * 模版类 (模版类需要放到IOC中进行管理)
     *
     * @return 提供渲染模版的实现类
     */
    Class<? extends ISqlTemplteEngine> klass() default BeetlSqlTemplteEngineImpl.class;

}
