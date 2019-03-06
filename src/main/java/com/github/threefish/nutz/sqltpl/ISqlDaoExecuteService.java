package com.github.threefish.nutz.sqltpl;

import com.github.threefish.nutz.dto.PageDataDTO;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
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
     * 分页查询列表
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @param pager 分页参数
     * @return 列表NutMap类型
     */
    default PageDataDTO queryMapBySql(String id, NutMap param, Pager pager) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.maps());
        sql.setPager(pager);
        getDao().execute(sql);
        return new PageDataDTO(Daos.queryCount(getDao(), sql), sql.getList(NutMap.class));
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
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(getEntity());
        sql.setPager(pager);
        getDao().execute(sql);
        return new PageDataDTO(Daos.queryCount(getDao(), sql), sql.getList(getEntityClass()));
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
     * @param <T>   实体类泛型
     * @return 单个实体类
     */
    default <T> T fetchEntityBySql(String id, NutMap param) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.entity());
        sql.setEntity(getEntity());
        getDao().execute(sql);
        return (T) sql.getObject(getEntityClass());
    }

    /**
     * 更新
     *
     * @param id    sqlxml中的唯一ID
     * @param param 参数
     * @return 更新个数
     */
    default int updateBySql(String id, NutMap param) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.integer());
        getDao().execute(sql);
        return sql.getInt();
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
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.strs());
        getDao().execute(sql);
        return sql.getList(String.class);
    }

    /**
     * 计数查询
     *
     * @param id    sqlxml中的唯一ID
     * @param param 查询参数
     * @return 数字
     */
    default long queryLongBySql(String id, NutMap param) {
        Sql sql = getSqlsTplHolder().getSql(id, param);
        sql.setCallback(Sqls.callback.integer());
        getDao().execute(sql);
        return sql.getLong();
    }

}
