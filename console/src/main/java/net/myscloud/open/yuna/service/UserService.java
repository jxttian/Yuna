package net.myscloud.open.yuna.service;

import net.myscloud.open.yuna.common.framework.mybatis.IGenericService;
import net.myscloud.open.yuna.model.User;

import java.util.Optional;

/**
 * Created by genesis on 2016/12/29.
 */
public interface UserService extends IGenericService<User, Integer> {

    /**
     * 根据邮箱获取用户
     *
     * @param email
     * @return
     */
    Optional<User> getByEmail(String email);

    /**
     * 根据手机号码获取用户
     *
     * @param phone
     * @return
     */
    Optional<User> getByPhone(String phone);
}
