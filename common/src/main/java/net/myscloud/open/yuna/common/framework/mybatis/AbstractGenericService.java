package net.myscloud.open.yuna.common.framework.mybatis;

import net.myscloud.open.yuna.common.framework.PaginationParam;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 通用Service实现
 *
 * @param <E> 实体类型
 * @param <K> 主键类型
 * @param <M> Mapper接口
 */
public abstract class AbstractGenericService<E, K extends Serializable, M extends IGenericMapper<E, K>> implements IGenericService<E, K> {

    protected abstract M getMapper();

    public int insert(E object) {
        beforeUpdateData();
        return getMapper().insert(object);
    }

    public int insert(List<E> items) {
        beforeUpdateData();
        return getMapper().insertBatch(items);
    }

    public int insertSelective(E object) {
        beforeUpdateData();
        return getMapper().insertSelective(object);
    }

    public int insertSelective(List<E> items) {
        beforeUpdateData();
        return getMapper().insertSelectiveBatch(items);
    }

    public int delete(K id) {
        beforeUpdateData();
        return getMapper().delete(id);
    }

    public int delete(List<K> ids) {
        beforeUpdateData();
        return getMapper().deleteBatch(ids);
    }

    public Optional<E> update(E object) {
        beforeUpdateData();
        return Optional.ofNullable(getMapper().updateSelective(object) > 0 ? object : null);
    }

    public int update(List<E> items) {
        beforeUpdateData();
        return getMapper().updateBatch(items);
    }

    public Optional<E> updateSelective(E object) {
        beforeUpdateData();
        return Optional.ofNullable(getMapper().updateSelective(object) > 0 ? object : null);
    }

    public int updateSelective(List<E> items) {
        beforeUpdateData();
        return getMapper().updateSelectiveBatch(items);
    }

    public Optional<E> get(K id) {
        return Optional.ofNullable(getMapper().get(id));
    }

    public long count(Object condition) {
        return getMapper().count(condition);
    }

    public Optional<List<E>> all() {
        return Optional.ofNullable(getMapper().all());
    }

    public Optional<List<E>> page(PaginationParam paginationParam) {
        return Optional.ofNullable(getMapper().page(paginationParam));
    }

    /**
     * 更新(增删改)数据库数据前置操作，如更新缓存
     */
    public void beforeUpdateData() {

    }
}

