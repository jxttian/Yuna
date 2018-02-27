package net.myscloud.open.yuna.service;

import net.myscloud.open.yuna.common.framework.mybatis.IGenericService;
import net.myscloud.open.yuna.model.Permission;

import java.util.List;
import java.util.Optional;

/**
 * Created by genesis on 2016/12/29.
 */
public interface PermissionService extends IGenericService<Permission, Integer> {

    Optional<Permission> get(String code);
}
