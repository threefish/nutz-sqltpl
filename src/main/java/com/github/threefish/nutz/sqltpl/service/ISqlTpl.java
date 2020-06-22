package com.github.threefish.nutz.sqltpl.service;

import com.github.threefish.nutz.sqltpl.SqlsTplHolder;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date 2020/3/11
 */
public interface ISqlTpl {
    /**
     * @return 取得sql模版持有对象
     */
    SqlsTplHolder getSqlTplHolder();

    /**
     * 设置sql模版持有对象
     *
     * @param sqlsTplHolder
     */
    void setSqlTpl(SqlsTplHolder sqlsTplHolder);
}
