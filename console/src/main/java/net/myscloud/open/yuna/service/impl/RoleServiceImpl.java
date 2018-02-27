package net.myscloud.open.yuna.service.impl;

import net.myscloud.open.yuna.common.framework.mybatis.AbstractGenericService;
import net.myscloud.open.yuna.consts.CacheName;
import net.myscloud.open.yuna.mapper.RoleMapper;
import net.myscloud.open.yuna.model.Role;
import net.myscloud.open.yuna.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by genesis on 2016/12/29.
 */
@Service
public class RoleServiceImpl extends AbstractGenericService<Role, Integer, RoleMapper> implements RoleService {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    protected RoleMapper getMapper() {
        return mapper;
    }

    @Override
    @Cacheable(value = CacheName.NAME_YUNA_ROLE, key = "#id")
    public Optional<Role> get(Integer id) {
        return Optional.ofNullable(getMapper().get(id));
    }

    @Override
    @Cacheable(value = CacheName.NAME_YUNA_ROLE, keyGenerator = "keyBuilder")
    public Optional<List<Role>> all() {
        return Optional.ofNullable(getMapper().all());
    }


    @Override
    public void beforeUpdateData() {
        cacheManager.getCache(CacheName.NAME_YUNA_ROLE).clear();
    }

}
