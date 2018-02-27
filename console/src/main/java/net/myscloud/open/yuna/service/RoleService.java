package net.myscloud.open.yuna.service;

import net.myscloud.open.yuna.common.framework.mybatis.IGenericService;
import net.myscloud.open.yuna.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by genesis on 2016/12/29.
 */
@Service
public interface RoleService extends IGenericService<Role, Integer> {

}
