package com.github.threefish.nutz.sqltpl;

import com.github.threefish.nutz.sqltpl.resource.Resource;
import com.github.threefish.nutz.sqltpl.service.ISqlTemplteEngine;
import com.github.threefish.nutz.utils.XmlUtils;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.w3c.dom.Document;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/25
 */
public class SqlTplResourceLoader {

    private static final Log LOGGER = Logs.get();
    /**
     * 是否开发者模式，可以动态加载XML中的SQL
     */
    public static boolean DEVELOPER_MODE = false;
    /**
     * 文件修改时间
     */
    private static volatile Long updatetime = System.currentTimeMillis();
    /**
     * 模版中可以进行引用的变量 如${cols}
     */
    private final HashMap<String, String> vars = new HashMap<>();
    /**
     * 模版ID与未渲染前SQL内容的缓存
     */
    private final HashMap<String, String> sqlTemplateCache = new HashMap<>();

    /**
     * jar情况下的文件资源
     */
    private final Resource resource;

    /**
     * 当前使用的模版引擎
     */
    private final ISqlTemplteEngine sqlTemplteEngine;

    /**
     * @param resource         JAR中的xml文件资源信息
     * @param sqlTemplteEngine SQL模版引擎
     */
    public SqlTplResourceLoader(Resource resource, ISqlTemplteEngine sqlTemplteEngine) {
        this.resource = resource;
        this.sqlTemplteEngine = sqlTemplteEngine;
        this.load();
    }

    /**
     * 渲染Sql
     *
     * @param id    xml中的唯一ID
     * @param param 变量参数
     * @return 渲染后的SQL文本
     */
    public String renderSql(String id, Map<String, Object> param) {
        String sqlTemplate = getSqlTemplate(id);
        vars.forEach((key, value) -> param.put(key, value));
        return sqlTemplteEngine.render(sqlTemplate, param);
    }

    /**
     * 取得未经过beetl模版引擎的sql语句
     *
     * @param id
     * @return
     */
    public String getSqlTemplate(String id) {
        if (DEVELOPER_MODE) {
            File file = resource.getFile();
            if (updatetime != file.lastModified()) {
                updatetime = file.lastModified();
                this.load();
            }
        }
        return sqlTemplateCache.get(id);
    }


    /**
     * 加载资源
     */
    private final void load() {
        try {
            vars.clear();
            sqlTemplateCache.clear();
            Document document = XmlUtils.loadDocument(resource.getInputStream());
            XmlUtils.setCache(document, "sql", "id", sqlTemplateCache);
            XmlUtils.setCache(document, "var", "name", vars);
        } catch (Exception e) {
            LOGGER.error("资源加载失败", e);
        }
    }

}
