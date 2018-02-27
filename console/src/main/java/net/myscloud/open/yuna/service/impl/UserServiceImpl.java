package net.myscloud.open.yuna.service.impl;

import net.myscloud.open.yuna.common.framework.mybatis.AbstractGenericService;
import net.myscloud.open.yuna.consts.CacheName;
import net.myscloud.open.yuna.mapper.UserMapper;
import net.myscloud.open.yuna.model.User;
import net.myscloud.open.yuna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by genesis on 2016/12/29.
 */
@Service
public class UserServiceImpl extends AbstractGenericService<User, Integer, UserMapper> implements UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    protected UserMapper getMapper() {
        return mapper;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.ofNullable(mapper.getByEmail(email));
    }

    @Override
    public Optional<User> getByPhone(String phone) {
        return Optional.ofNullable(mapper.getByPhone(phone));
    }

    @Override
    public void beforeUpdateData() {
        cacheManager.getCache(CacheName.NAME_YUNA_USER).clear();
    }
}
