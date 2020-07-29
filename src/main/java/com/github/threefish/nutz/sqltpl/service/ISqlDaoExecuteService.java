package com.github.threefish.nutz.sqltpl.service;

import com.github.threefish.nutz.sqltpl.SqlsTplHolder;
import com.github.threefish.nutz.sqltpl.dto.PageDataDTO;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.dao.util.Daos;
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
     * 取得Sql
     *
     * @param id       sqlxml中的唯一ID
     * @param param    查询参数
     * @param cnd      为 SQL 增加条件，SQL 必须有 '$condition' 变量
     * @param callback 自定义callback
     * @return sql
     */
    default Sql getSql(String id, NutMap param, Cnd cnd, SqlCallback callback) {
        Sql sql = Sqls.create(getSqlsTplHolder().getSql(id, param));
        param.forEach((k, v) -> sql.setParam(k, v));
        sql.setCallback(callback);
        if (cnd != null) {
            sql.setCondition(cnd);
        }
        return sql;
    }


    /**
     * 分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param pager 分页参数
     * @return 列表NutMap类型
     */
    default PageDataDTO queryMapBySql(String id, NutMap param, Pager pager) {
        return queryMapBySql(id, param, null, pager);
    }

    /**
     * 分页查询列表实体
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param pager 分页参数
     * @return 列表实体类型
     */
    default PageDataDTO queryEntityBySql(String id, NutMap param, Pager pager) {
        return queryEntityBySql(id, param, null, pager, getEntityClass());
    }

    /**
     * 分页查询列表实体
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param pager 分页参数
     * @return 列表实体类型
     */
    default PageDataDTO queryEntityBySql(String id, NutMap param, Pager pager, Class entityClass) {
        Sql sql = getSql(id, param, null, Sqls.callback.entities());
        sql.setEntity(getDao().getEntity(entityClass));
        sql.setPager(pager);
        getDao().execute(sql);
        return queryEntityBySql(id, param, null, pager);
    }

    /**
     * 不分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 列表NutMap类型
     */
    default List<NutMap> queryMapBySql(String id, NutMap param) {
        return queryMapBySql(id, param, (Cnd) null);
    }

    /**
     * 不分页查询列表实体
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 列表实体类型
     */
    default List<T> queryEntityBySql(String id, NutMap param) {
        return queryEntityBySql(id, param, (Cnd) null);
    }

    /**
     * 获取单个
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 单个 NutMap
     */
    default NutMap fetchMapBySql(String id, NutMap param) {
        return fetchMapBySql(id, param, null);
    }

    /**
     * 获取单个
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param <T>   实体类泛型
     * @return 单个实体类
     */
    default <T> T fetchEntityBySql(String id, NutMap param) {
        return fetchEntityBySql(id, param, null);
    }

    /**
     * 更新
     *
     * @param id    sqlxml中的唯一ID
     * @param param 参数
     * @return 更新个数
     */
    default int updateBySql(String id, NutMap param) {
        return updateBySql(id, param, null);
    }

    /**
     * 删除
     *
     * @param id    sqlxml中的唯一ID
     * @param param 参数
     * @return 删除个数
     */
    default int delectBySql(String id, NutMap param) {
        return updateBySql(id, param);
    }


    /**
     * 不分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 列表类型
     */
    default List<String> queryStrsBySql(String id, NutMap param) {
        return queryStrsBySql(id, param, null);
    }

    /**
     * 不分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 列表类型
     */
    default List<Integer> queryIntsBySql(String id, NutMap param) {
        return queryIntsBySql(id, param, null);
    }

    /**
     * 计数查询
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 数字
     */
    default long queryLongBySql(String id, NutMap param) {
        return queryLongBySql(id, param, null);
    }

    /**
     * 分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @param pager 分页参数
     * @return 列表NutMap类型
     */
    default PageDataDTO queryMapBySql(String id, NutMap param, Cnd cnd, Pager pager) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.maps());
        long count = Daos.queryCount(getDao(), sql);
        sql.setPager(pager);
        getDao().execute(sql);
        return new PageDataDTO(count, sql.getList(NutMap.class));
    }

    /**
     * 分页查询列表实体
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @param pager 分页参数
     * @return 列表实体类型
     */
    default PageDataDTO queryEntityBySql(String id, NutMap param, Cnd cnd, Pager pager) {
        return queryEntityBySql(id, param, cnd, pager, getEntityClass());
    }

    /**
     * 分页查询列表实体
     *
     * @param id          sqlxml中的唯一ID
     * @param param       查询参数
     * @param cnd         cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @param pager       分页参数
     * @param entityClass 实体类型
     * @return 列表实体类型
     */
    default PageDataDTO queryEntityBySql(String id, NutMap param, Cnd cnd, Pager pager, Class entityClass) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.entities());
        long count = Daos.queryCount(getDao(), sql);
        sql.setEntity(getDao().getEntity(entityClass));
        sql.setPager(pager);
        getDao().execute(sql);
        return new PageDataDTO(count, sql.getList(getEntityClass()));
    }

    /**
     * 不分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 列表NutMap类型
     */
    default List<NutMap> queryMapBySql(String id, NutMap param, Cnd cnd) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.maps());
        getDao().execute(sql);
        return sql.getList(NutMap.class);
    }

    /**
     * 不分页查询列表实体
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 列表实体类型
     */
    default List<T> queryEntityBySql(String id, NutMap param, Cnd cnd) {
        return queryEntityBySql(id, param, cnd, getEntityClass());
    }

    /**
     * 不分页查询列表实体
     *
     * @param id          sqlxml中的唯一ID
     * @param param       查询参数
     * @param entityClass 指定实体类型
     * @return 列表实体类型
     */
    default List<T> queryEntityBySql(String id, NutMap param, Class entityClass) {
        return queryEntityBySql(id, param, (Cnd) null, entityClass);
    }

    /**
     * 不分页查询列表实体
     *
     * @param id          sqlxml中的唯一ID
     * @param param       查询参数
     * @param cnd         cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @param entityClass 指定实体类型
     * @return 列表实体类型
     */
    default List<T> queryEntityBySql(String id, NutMap param, Cnd cnd, Class entityClass) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.entities());
        sql.setEntity(getDao().getEntity(entityClass));
        getDao().execute(sql);
        return sql.getList(entityClass);
    }

    /**
     * 获取单个
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 单个 NutMap
     */
    default NutMap fetchMapBySql(String id, NutMap param, Cnd cnd) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.map());
        getDao().execute(sql);
        return sql.getObject(NutMap.class);
    }

    /**
     * 获取单个
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @param <T>   实体类泛型
     * @return 单个实体类
     */
    default <T> T fetchEntityBySql(String id, NutMap param, Cnd cnd) {
        return fetchEntityBySql(id, param, cnd, getEntityClass());
    }

    /**
     * 获取单个
     *
     * @param id          sqlxml中的唯一ID
     * @param param       查询参数
     * @param cnd         cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @param entityClass 指定实体类型
     * @param <T>         实体类泛型
     * @return 单个实体类
     */
    default <T> T fetchEntityBySql(String id, NutMap param, Cnd cnd, Class entityClass) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.entity());
        sql.setEntity(getDao().getEntity(entityClass));
        getDao().execute(sql);
        return (T) sql.getObject(entityClass);
    }

    /**
     * 更新
     *
     * @param id    sqlxml中的唯一ID
     * @param param 参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 更新个数
     */
    default int updateBySql(String id, NutMap param, Cnd cnd) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.integer());
        getDao().execute(sql);
        return sql.getUpdateCount();
    }

    /**
     * 删除
     *
     * @param id    sqlxml中的唯一ID
     * @param param 参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 删除个数
     */
    default int delectBySql(String id, NutMap param, Cnd cnd) {
        return updateBySql(id, param, cnd);
    }


    /**
     * 不分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 列表类型
     */
    default List<String> queryStrsBySql(String id, NutMap param, Cnd cnd) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.strs());
        getDao().execute(sql);
        return sql.getList(String.class);
    }

    /**
     * 不分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 列表类型
     */
    default List<Integer> queryIntsBySql(String id, NutMap param, Cnd cnd) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.ints());
        getDao().execute(sql);
        return sql.getList(Integer.class);
    }

    /**
     * 计数查询
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param cnd   cnd 不为Null时 SQL 必须有 '$condition' 变量
     * @return 数字
     */
    default long queryLongBySql(String id, NutMap param, Cnd cnd) {
        Sql sql = getSql(id, param, cnd, Sqls.callback.integer());
        getDao().execute(sql);
        return sql.getLong(0);
    }


}
