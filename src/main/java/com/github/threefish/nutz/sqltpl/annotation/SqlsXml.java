package com.github.threefish.nutz.sqltpl.annotation;

import com.github.threefish.nutz.sqltpl.service.ISqlTemplteEngine;
import com.github.threefish.nutz.sqltpl.templte.beetl.BeetlSqlTemplteEngineImpl;

import java.lang.annotation.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SqlsXml {

    /**
     * 文件相对与当前service类路径
     * <p>
     * 默认为java文件名以.xml结尾 如（UserServicesImpl.java 对应 UserServicesImpl.xml）
     *
     * @return xml文件路径
     */
    String value() default "";

    /**
     * 模版类 (模版类需要放到IOC中进行管理)
     *
     * @return 提供渲染模版的实现类
     */
    Class<? extends ISqlTemplteEngine> klass() default BeetlSqlTemplteEngineImpl.class;

}
