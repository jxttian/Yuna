package net.myscloud.open.yuna.common.framework.mybatis;


import net.myscloud.open.yuna.common.framework.PaginationParam;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGenericService<E, K extends Serializable> {

    int insert(E object);

    int insert(List<E> items);

    int insertSelective(E object);

    int insertSelective(List<E> items);

    int delete(K id);

    int delete(List<K> ids);

    Optional<E> update(E object);

    int update(List<E> items);

    Optional<E> updateSelective(E object);

    int updateSelective(List<E> items);

    Optional<E> get(K id);

    long count(Object condition);

    Optional<List<E>> all();

    Optional<List<E>> page(PaginationParam paginationParam);
}
