package com.github.threefish.nutz.sqltpl;

import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
public class SqlsTplHolder {
    /**
     * SQL资源加载器
     */
    SqlTplResourceLoader sqlTplResourceLoader;

    public SqlsTplHolder(SqlTplResourceLoader sqlTplResourceLoader) {
        this.sqlTplResourceLoader = sqlTplResourceLoader;
    }

    /**
     * 渲染Sql
     *
     * @param id    xml中的唯一ID
     * @param param 变量参数
     * @return 渲染后的SQL文本
     */
    public String getSql(String id, Map<String, Object> param) {
        return sqlTplResourceLoader.renderSql(id, param);
    }

    /**
     * 取得原始模版
     *
     * @param id xml中的唯一ID
     * @return 原始模版
     */
    public String getOriginalTemplate(String id) {
        return sqlTplResourceLoader.getSqlTemplate(id);
    }

}
