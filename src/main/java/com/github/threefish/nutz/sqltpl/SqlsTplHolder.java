package com.github.threefish.nutz.sqltpl;

import com.github.threefish.nutz.utils.XmlUtils;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.resource.impl.FileResource;
import org.w3c.dom.Document;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
public class SqlsTplHolder {

    private static final Log LOG = Logs.get();
    /**
     * 是否开发者模式，可以动态加载XML中的SQL
     */
    public static boolean DEVELOPER_MODE = false;
    /**
     * 文件修改时间
     */
    private static Long updatetime = System.currentTimeMillis();
    /**
     * 模版中可以进行引用的变量 如${cols}
     */
    private HashMap<String, String> vars = new HashMap<>();
    /**
     * 模版ID与未渲染前SQL内容的缓存
     */
    private HashMap<String, String> sqlTemplateCache = new HashMap<>();
    /**
     * 非jar情况下的文件资源
     */
    private FileResource fileResource;

    /**
     * jar情况下的文件资源
     */
    private JarResource jarResource;
    /**
     * 在jar中
     */
    private boolean inJar;

    /**
     * 可以自定义的模版引擎
     */
    private ISqlTemplteEngine sqlTemplteEngine;

    /**
     * 普通文件情况下
     *
     * @param fileResource         xml文件资源
     * @param sqlTemplteEngine SQL模版引擎
     */
    public SqlsTplHolder(FileResource fileResource, ISqlTemplteEngine sqlTemplteEngine) {
        this.fileResource = fileResource;
        this.sqlTemplteEngine = sqlTemplteEngine;
        this.load();
    }

    /**
     * jar 情况下
     *
     * @param jarResource      JAR中的xml文件资源信息
     * @param sqlTemplteEngine SQL模版引擎
     */
    public SqlsTplHolder(JarResource jarResource, ISqlTemplteEngine sqlTemplteEngine) {
        this.jarResource = jarResource;
        this.sqlTemplteEngine = sqlTemplteEngine;
        this.inJar = true;
        this.load();
    }


    /**
     * 获取Sql
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
     * 获取Sql
     *
     * @param id    xml中的唯一ID
     * @param param 变量参数
     * @return 渲染后可供执行的Sql对象
     */
    public Sql getSql(String id, Map<String, Object> param) {
        String sqlTemplate = getSqlTemplate(id);
        HashMap bindData = new HashMap(param);
        bindData.putAll(vars);
        String sqlStr = sqlTemplteEngine.render(sqlTemplate, bindData);
        Sql sql = Sqls.create(sqlStr);
        vars.forEach((key, value) -> sql.setVar(key, value));
        param.forEach((key, value) -> sql.setParam(key, value));
        return sql;
    }


    private void load() {
        try {
            vars.clear();
            sqlTemplateCache.clear();
            Document document;
            if (inJar) {
                document = XmlUtils.loadDocument(jarResource.getInputStream());
            } else {
                document = XmlUtils.loadDocument(fileResource.getInputStream());
            }
            XmlUtils.setCache(document, "sql", "id", sqlTemplateCache);
            XmlUtils.setCache(document, "var", "name", vars);
        } catch (Exception e) {
            LOG.error(e);
        }
    }


    /**
     * 取得未经过beetl模版引擎的sql语句
     *
     * @param id
     * @return
     */
    private String getSqlTemplate(String id) {
        if (DEVELOPER_MODE) {
            File file = inJar ? jarResource.getJarFile() : fileResource.getFile();
            if (updatetime != file.lastModified()) {
                updatetime = file.lastModified();
                this.load();
            }
        }
        return sqlTemplateCache.get(id);
    }


}
