package com.github.threefish.nutz.sqls;

import com.github.threefish.nutz.sqltpl.SqlsTplHolder;
import com.github.threefish.nutz.sqltpl.annotation.SqlsXml;
import com.github.threefish.nutz.sqltpl.service.ISqlTpl;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
@SqlsXml("Bean1.xml")
public class Bean1 implements ISqlTpl {

    private SqlsTplHolder sqlsTplHolder;

    @Override
    public SqlsTplHolder getSqlTplHolder() {
        return this.sqlsTplHolder;
    }

    @Override
    public void setSqlTpl(SqlsTplHolder sqlsTplHolder) {
        this.sqlsTplHolder = sqlsTplHolder;
    }
}
