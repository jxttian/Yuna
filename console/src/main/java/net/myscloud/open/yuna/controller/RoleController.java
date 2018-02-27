package net.myscloud.open.yuna.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.bean.search.RoleSearch;
import net.myscloud.open.yuna.common.framework.Pagination;
import net.myscloud.open.yuna.common.framework.Response;
import net.myscloud.open.yuna.common.kits.StringKits;
import net.myscloud.open.yuna.consts.YunaConsts;
import net.myscloud.open.yuna.model.Role;
import net.myscloud.open.yuna.model.User;
import net.myscloud.open.yuna.service.PermissionService;
import net.myscloud.open.yuna.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by genesis on 2016/12/29.
 */
@Slf4j
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService service;

    @RequestMapping({"/", "", "index", "index.html"})
    public ModelAndView index() {
        return new ModelAndView("role/index");
    }

    @RequestMapping("list.json")
    @ResponseBody
    public Pagination<Role> list(RoleSearch search) {
        long count = service.count(search);
        return Pagination.build(count > 0 ? service.page(search).orElse(Lists.newArrayList()) : Lists.newArrayList(), count);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Response save(HttpSession session, @RequestBody Role model) {
        boolean success;
        User currentUser = (User) session.getAttribute(YunaConsts.SESSION_USER);
        if (model.getId() == null) {
            //新增
            model.setCreationTime(new Date());
            model.setCreator(currentUser.getId());
            success = service.insertSelective(model) > 0;
        } else {
            model.setModifier(currentUser.getId());
            model.setModificationTime(new Date());
            success = service.updateSelective(model).isPresent();
        }
        return success ? Response.success() : Response.result(Response.Status.SYSTEM_ERROR.getCode(), "角色保存失败");
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(@RequestBody List<Integer> ids) {
        //
        ids.forEach(id -> {
            // TODO: 2017/04/12 删除已赋予该角色的用户的角色
            service.delete(id);
        });
        return Response.success();
    }

    @RequestMapping(value = "{id}/permissions", method = RequestMethod.GET)
    @ResponseBody
    public Response permissions(@PathVariable Integer id) {
        Optional<Role> role = service.get(id);
        if (role.isPresent() && StringKits.isNotEmpty(role.get().getPermissions())) {
            return Response.success(Splitter.on(",").splitToList(role.get().getPermissions())
                    .parallelStream()
                    .filter(StringKits::isNotEmpty)
                    .filter(StringKits::isNumeric)
                    .map(permissionId -> permissionService.get(Integer.parseInt(permissionId)).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        return Response.success();
    }

    @RequestMapping(value = "{id}/save_permissions", method = RequestMethod.POST)
    @ResponseBody
    public Response permissions(@PathVariable Integer id, @RequestBody List<Integer> pids) {
        service.updateSelective(Role.builder().id(id).permissions(Joiner.on(",").skipNulls().join(pids)).build());
        return Response.success();
    }
}
