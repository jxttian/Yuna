package net.myscloud.open.yuna.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.bean.search.UserSearch;
import net.myscloud.open.yuna.common.framework.Pagination;
import net.myscloud.open.yuna.common.framework.Response;
import net.myscloud.open.yuna.common.kits.SecretKits;
import net.myscloud.open.yuna.common.kits.StringKits;
import net.myscloud.open.yuna.consts.YunaConsts;
import net.myscloud.open.yuna.model.User;
import net.myscloud.open.yuna.service.RoleService;
import net.myscloud.open.yuna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by genesis on 2016/12/29.
 */
@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @Autowired
    @Qualifier("sessionRedisTemplate")
    private RedisTemplate sessionRedisTemplate;

    @Value("${session.redis.namespace}")
    private String redisSessionNamespace;

    @RequestMapping({"/", "", "index", "index.html"})
    public ModelAndView index() {
        return new ModelAndView("user/index", "roles", roleService.all().orElse(Lists.newArrayList()));
    }

    @RequestMapping("info.html")
    public ModelAndView info(HttpSession session) {
        return new ModelAndView("user/info", "user", session.getAttribute(YunaConsts.SESSION_USER));
    }

    @RequestMapping("info/save")
    @ResponseBody
    public Response infoSave(HttpSession session, @RequestBody User model) {
        User currentUser = (User) session.getAttribute(YunaConsts.SESSION_USER);
        if (currentUser.getId().equals(model.getId())) {
            return save(session, model);
        }
        return Response.result(Response.Status.SYSTEM_ERROR.getCode(), "没有权限");
    }

    @RequestMapping("list.json")
    @ResponseBody
    public Pagination<User> list(UserSearch search) {
        long count = service.count(search);
        return Pagination.build(count > 0 ? service.page(search).orElse(Lists.newArrayList()) : Lists.newArrayList(), count);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Response save(HttpSession session, @RequestBody User model) {
        boolean success;
        User currentUser = (User) session.getAttribute(YunaConsts.SESSION_USER);
        if (model.getId() == null) {
            //新增
            if (StringKits.isNotEmpty(model.getPassword()))
                model.setPassword(SecretKits.md5(model.getPassword()));
            model.setCreator(currentUser.getId());
            model.setCreationTime(new Date());
            success = service.insertSelective(model) > 0;
        } else {
            if (StringKits.isNotEmpty(model.getPassword())) {
                if (StringKits.isEmpty(model.getOldPassword())) {
                    return Response.result(Response.Status.SYSTEM_ERROR.getCode(), "请输入原始密码");
                }
                Optional<User> oldUser = service.get(model.getId());
                if (oldUser.isPresent() && oldUser.get().getPassword().equals(SecretKits.md5(model.getOldPassword()))) {
                    model.setPassword(SecretKits.md5(model.getPassword()));
                } else {
                    return Response.result(Response.Status.SYSTEM_ERROR.getCode(), "密码不正确");
                }
            }
            model.setModificationTime(new Date());
            model.setModifier(currentUser.getId());
            success = service.updateSelective(model).isPresent();
        }
        return success ? Response.success() : Response.result(Response.Status.SYSTEM_ERROR.getCode(), "用户保存失败");
    }

    @RequestMapping("online.html")
    public ModelAndView onlineUser() {
        return new ModelAndView("user/online");
    }

    @RequestMapping("online.json")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Response listOnlineUsers() {
        String prefix = "spring:session:" + redisSessionNamespace + ":sessions:";
        return Response.success(sessionRedisTemplate.keys(prefix + "expires:*").stream().map(sessionKey -> {
            String sessionId = sessionKey.toString().replace(prefix + "expires:", "");
            User user = (User) sessionRedisTemplate.boundHashOps(prefix + sessionId).get("sessionAttr:session_user");
            if (user != null)
                user.setSessionId(sessionId);
            return user;
        }).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    @RequestMapping(value = "online/{sessionId}", method = RequestMethod.DELETE)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Response removeOnlineUser(@PathVariable String sessionId, HttpServletRequest request) {
        if (request.getRequestedSessionId().equals(sessionId)) {
            return Response.result(Response.Status.SYSTEM_ERROR.getCode(), "不得剔除自己");
        }
        String prefix = "spring:session:" + redisSessionNamespace + ":sessions:";
        sessionRedisTemplate.delete(Lists.newArrayList(prefix + sessionId));
        return Response.success();
    }
}
