package net.myscloud.open.yuna.mapper;

import net.myscloud.open.yuna.common.framework.mybatis.IGenericMapper;
import net.myscloud.open.yuna.model.Permission;

import java.util.List;

public interface PermissionMapper extends IGenericMapper<Permission, Integer> {
    /**
     * 根据权限Code获取权限
     *
     * @param code
     * @return
     */
    Permission getByCode(String code);

    /**
     * 根据角色ID获取权限ID
     *
     * @param rid
     * @return
     */
    List<Integer> getPermissionIdsByRid(Integer rid);

    /**
     * 根据UID获取用户所拥有的权限ID
     *
     * @param uid
     * @return
     */
    List<Integer> getPermissionIdsByUid(Integer uid);
}