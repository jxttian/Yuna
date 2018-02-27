package net.myscloud.open.yuna.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.bean.search.PermissionSearch;
import net.myscloud.open.yuna.common.framework.Pagination;
import net.myscloud.open.yuna.common.framework.Response;
import net.myscloud.open.yuna.consts.YunaConsts;
import net.myscloud.open.yuna.model.Permission;
import net.myscloud.open.yuna.model.User;
import net.myscloud.open.yuna.service.BusinessSystemService;
import net.myscloud.open.yuna.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by genesis on 2016/12/29.
 */
@Slf4j
@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @Autowired
    private BusinessSystemService businessSystemService;

    @RequestMapping({"/", "", "index", "index.html"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("permission/index");
        modelAndView.addObject("businessSystems", businessSystemService.all().orElse(Lists.newArrayList()));
        return modelAndView;
    }

    @RequestMapping("list.json")
    @ResponseBody
    public Pagination<Permission> list(PermissionSearch search) {
        long count = service.count(search);
        return Pagination.build(count > 0 ? service.page(search).orElse(Lists.newArrayList()).stream().map(permission -> {
            businessSystemService.get(permission.getSid())
                    .ifPresent(businessSystem -> {
                        permission.setSystemCode(businessSystem.getCode());
                        permission.setSystemName(businessSystem.getName());
                    });
            return permission;
        }).collect(Collectors.toList()) : Lists.newArrayList(), count);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Response save(HttpSession session, @RequestBody Permission model) {
        boolean success;
        User currentUser = (User) session.getAttribute(YunaConsts.SESSION_USER);
        if (model.getId() == null) {
            //新增
            model.setCreationTime(new Date());
            model.setCreator(currentUser.getId());
            success = service.insertSelective(model) > 0;
        } else {
            model.setModificationTime(new Date());
            model.setModifier(currentUser.getId());
            success = service.updateSelective(model).isPresent();
        }
        return success ? Response.success() : Response.result(Response.Status.SYSTEM_ERROR.getCode(), "权限保存失败");
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(@RequestBody List<Integer> ids) {
        //
        ids.forEach(id -> {
            // TODO: 2017/04/12 有子权限不可删除 or 同时删除所有子权限
            // TODO: 2017/04/12 删除所有含该权限的角色的权限
            service.delete(id);
        });
        return Response.success();
    }
}
