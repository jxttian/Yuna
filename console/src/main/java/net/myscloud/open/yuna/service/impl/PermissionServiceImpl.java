package net.myscloud.open.yuna.service.impl;

import net.myscloud.open.yuna.common.framework.mybatis.AbstractGenericService;
import net.myscloud.open.yuna.consts.CacheName;
import net.myscloud.open.yuna.mapper.PermissionMapper;
import net.myscloud.open.yuna.model.Permission;
import net.myscloud.open.yuna.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by genesis on 2016/12/29.
 */
@Service
public class PermissionServiceImpl extends AbstractGenericService<Permission, Integer, PermissionMapper> implements PermissionService {

    @Autowired
    private PermissionMapper mapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    protected PermissionMapper getMapper() {
        return mapper;
    }

    @Override
    @Cacheable(value = CacheName.NAME_YUNA_PERMISSION, key = "#id")
    public Optional<Permission> get(Integer id) {
        return Optional.ofNullable(getMapper().get(id));
    }

    @Override
    @Cacheable(value = CacheName.NAME_YUNA_PERMISSION, key = "'code:'+#code")
    public Optional<Permission> get(String code) {
        return Optional.ofNullable(mapper.getByCode(code));
    }

    @Override
    public void beforeUpdateData() {
        cacheManager.getCache(CacheName.NAME_YUNA_PERMISSION).clear();
    }
}
