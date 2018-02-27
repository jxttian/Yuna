package net.myscloud.open.yuna.mapper;

import net.myscloud.open.yuna.common.framework.mybatis.IGenericMapper;
import net.myscloud.open.yuna.model.User;

public interface UserMapper extends IGenericMapper<User, Integer> {
    /**
     * 根据邮箱获取用户
     *
     * @param email
     * @return
     */
    User getByEmail(String email);

    /**
     * 根据手机号码获取用户
     *
     * @param phone
     * @return
     */
    User getByPhone(String phone);

}