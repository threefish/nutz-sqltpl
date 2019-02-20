package com.github.threefish.nutz.sqltpl;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.util.NutMap;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
public interface ISqlDaoExecuteService<T> {
    /**
     * SQL信息持有者
     *
     * @return SQL信息持有者
     */
    SqlsTplHolder getSqlsTplHolder();

    /**
     * 取得dao
     *
     * @return 取得dao
     */
    Dao getDao();

    /**
     * 获取实体的Entity
     *
     * @return 实体的Entity
     */
    Entity getEntity();

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    Class getEntityClass();

    /**
     * 分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param pager 分页参数
     * @return 列表NutMap类型
     */
    default List<NutMap> queryMapBySql(String id, NutMap param, Pager pager) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.maps());
        sql.setPager(pager);
        getDao().execute(sql);
        return sql.getList(NutMap.class);
    }

    /**
     * 分页查询列表实体
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param pager 分页参数
     * @return 列表实体类型
     */
    default List<T> queryEntityBySql(String id, NutMap param, Pager pager) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(getEntity());
        sql.setPager(pager);
        getDao().execute(sql);
        return sql.getList(getEntityClass());
    }

    /**
     * 不分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 列表NutMap类型
     */
    default List<NutMap> queryMapBySql(String id, NutMap param) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.maps());
        getDao().execute(sql);
        return sql.getList(NutMap.class);
    }

    /**
     * 不分页查询列表实体
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 列表实体类型
     */
    default List<T> queryEntityBySql(String id, NutMap param) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(getEntity());
        getDao().execute(sql);
        return sql.getList(getEntityClass());
    }

    /**
     * 获取单个
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 单个 NutMap
     */
    default NutMap fetchMapBySql(String id, NutMap param) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.map());
        getDao().execute(sql);
        return sql.getObject(NutMap.class);
    }

    /**
     * 获取单个
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 单个实体类
     */
    default T fetchEntityBySql(String id, NutMap param) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.entity());
        sql.setEntity(getEntity());
        getDao().execute(sql);
        return (T) sql.getObject(getEntityClass());
    }

}
