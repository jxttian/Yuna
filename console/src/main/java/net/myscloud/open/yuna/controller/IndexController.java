package net.myscloud.open.yuna.controller;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.common.kits.SecretKits;
import net.myscloud.open.yuna.common.kits.StringKits;
import net.myscloud.open.yuna.consts.YunaConsts;
import net.myscloud.open.yuna.model.BusinessSystem;
import net.myscloud.open.yuna.model.Permission;
import net.myscloud.open.yuna.model.User;
import net.myscloud.open.yuna.service.BusinessSystemService;
import net.myscloud.open.yuna.service.PermissionService;
import net.myscloud.open.yuna.service.RoleService;
import net.myscloud.open.yuna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by genesis on 2016/12/29.
 */
@Slf4j
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private BusinessSystemService businessSystemService;

    @RequestMapping({"/", "", "index", "index.html"})
    public String index() {
        return "index";
    }

    @RequestMapping("login.html")
    public String login() {
        return "login";
    }

    /**
     * 执行登录
     *
     * @param session
     * @param verification   验证码
     * @param identification 身份认证 （手机或邮箱）
     * @param password       密码
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest request, HttpSession session, String verification, String identification, String password) {
        String verificationInSession = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (StringKits.isNotEmpty(verificationInSession) && verificationInSession.equalsIgnoreCase(verification)) {
            Optional<User> user = StringKits.isEmail(identification) ? userService.getByEmail(identification)
                    : userService.getByPhone(identification);
            if (user.isPresent() && user.get().getEnable() == User.Enable.Yes.getCode()
                    && SecretKits.md5(password).equals(user.get().getPassword())) {
                //登录成功
                user.get().setPassword(null);
                user.get().setLoginTime(System.currentTimeMillis());
                user.get().setLoginHost(request.getRemoteHost());
                Map<String, Object> exts = Maps.newHashMap();
                exts.put("referer", request.getHeader("Referer"));
                exts.put("userAgent", request.getHeader("User-Agent"));
                user.get().setExts(exts);
                session.setAttribute(YunaConsts.SESSION_USER, user.get());
                Set<Permission> permissions = Sets.newHashSet();
                //load role and permission
                if (StringKits.isNotEmpty(user.get().getRoles())) {
                    Splitter.on(",").splitToList(user.get().getRoles())
                            .parallelStream()
                            .filter(StringKits::isNotEmpty)
                            .filter(StringKits::isNumeric)
                            .forEach(roleId -> roleService.get(Integer.parseInt(roleId)).ifPresent(role -> {
                                if (StringKits.isNotEmpty(role.getPermissions())) {
                                    permissions.addAll(Splitter.on(",").splitToList(role.getPermissions())
                                            .parallelStream()
                                            .filter(StringKits::isNotEmpty)
                                            .filter(StringKits::isNumeric)
                                            .map(permissionId -> permissionService.get(Integer.parseInt(permissionId)).orElse(null))
                                            .collect(Collectors.toSet()));
                                }
                            }));
                }

                Map<String, List<Permission>> businessSystemCodeMap = permissions.parallelStream().filter(Objects::nonNull)
                        .sorted(Comparator.comparing(Permission::getRank).reversed())
                        .map(permission -> {
                            businessSystemService.get(permission.getSid()).ifPresent(businessSystem -> {
                                if (businessSystem.getEnable() == BusinessSystem.Enable.Yes.getCode()) {
                                    permission.setSystemCode(businessSystem.getCode());
                                }
                            });
                            return permission;
                        })
                        .filter(permission -> StringKits.isNotEmpty(permission.getSystemCode()))
                        .collect(Collectors.groupingBy(Permission::getSystemCode));
                session.setAttribute(YunaConsts.SESSION_BUSINESS_SYSTEM_CODE_MAP, businessSystemCodeMap);

                //初始化有权限的业务系统列表
                List<BusinessSystem> businessSystems = businessSystemService.all()
                        .orElse(Lists.newArrayList()).parallelStream()
                        .filter(Objects::nonNull)
                        .filter(businessSystem -> businessSystemCodeMap.containsKey(businessSystem.getCode()))
                        .sorted(Comparator.comparing(BusinessSystem::getRank).reversed()).collect(Collectors.toList());

                session.setAttribute(YunaConsts.SESSION_BUSINESS_SYSTEMS, businessSystems);
                session.setAttribute(YunaConsts.SESSION_CURRENT_BUSINESS_SYSTEM, businessSystems.get(0).getCode());

                return new ModelAndView("redirect:/index.html");
            } else {
                return new ModelAndView("login", "errorMsg", "用户名或密码错误");
            }
        } else {
            return new ModelAndView("login", "errorMsg", "验证码错误");
        }
    }

    @RequestMapping(value = "logout")
    public ModelAndView doLogout(HttpSession session) {
        session.removeAttribute(YunaConsts.SESSION_USER);
        session.removeAttribute(YunaConsts.SESSION_CURRENT_BUSINESS_SYSTEM);
        session.removeAttribute(YunaConsts.SESSION_BUSINESS_SYSTEM_CODE_MAP);
        session.removeAttribute(YunaConsts.SESSION_BUSINESS_SYSTEMS);
        return new ModelAndView("redirect:/login.html");
    }
}
