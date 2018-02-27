package net.myscloud.open.yuna.service.impl;

import net.myscloud.open.yuna.common.framework.mybatis.AbstractGenericService;
import net.myscloud.open.yuna.consts.CacheName;
import net.myscloud.open.yuna.mapper.BusinessSystemMapper;
import net.myscloud.open.yuna.model.BusinessSystem;
import net.myscloud.open.yuna.service.BusinessSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by genesis on 2016/12/29.
 */
@Service
public class BusinessSystemServiceImpl extends AbstractGenericService<BusinessSystem, Integer, BusinessSystemMapper> implements BusinessSystemService {

    @Autowired
    private BusinessSystemMapper mapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    protected BusinessSystemMapper getMapper() {
        return mapper;
    }

    @Override
    @Cacheable(value = CacheName.NAME_YUNA_BUSINESS_SYSTEM, key = "#id")
    public Optional<BusinessSystem> get(Integer id) {
        return Optional.ofNullable(getMapper().get(id));
    }

    @Override
    @Cacheable(value = CacheName.NAME_YUNA_BUSINESS_SYSTEM, keyGenerator = "keyBuilder")
    public Optional<List<BusinessSystem>> all() {
        return Optional.ofNullable(getMapper().all());
    }

    @Override
    public void beforeUpdateData() {
        cacheManager.getCache(CacheName.NAME_YUNA_BUSINESS_SYSTEM).clear();
    }
}
