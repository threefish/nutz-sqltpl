package com.github.threefish.nutz.sqltpl;

import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
public interface ISqlTemplteEngine {
    /**
     * 解析模版
     *
     * @param source   源
     * @param bindData 绑定参数
     * @return 渲染后的SQL
     */
    String render(String source, Map bindData);
}
