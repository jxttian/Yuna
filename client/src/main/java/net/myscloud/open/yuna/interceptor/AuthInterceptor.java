package net.myscloud.open.yuna.interceptor;

import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.common.kits.StringKits;
import net.myscloud.open.yuna.consts.YunaConsts;
import net.myscloud.open.yuna.model.BusinessSystem;
import net.myscloud.open.yuna.model.Permission;
import net.myscloud.open.yuna.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by jxtti on 2016/11/27.
 */
@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Setter
    private String contextPath;

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        User currentUser;
        if (session == null || (currentUser = (User) session.getAttribute(YunaConsts.SESSION_USER)) == null) {
            log.warn("用户未登录，跳转登录页面");
            response.sendRedirect(contextPath + "/login.html");
            return false;
        }
        if (request.getRequestURL().toString().equals(contextPath + "/index.html")
                || request.getRequestURL().toString().equals(contextPath + "/index.htm")
                || request.getRequestURL().toString().equals(contextPath + "/index")
                || request.getRequestURL().toString().equals(contextPath + "/")
                || request.getRequestURL().toString().equals(contextPath + "/user/info.html")
                || request.getRequestURL().toString().equals(contextPath + "/user/info/save")
                || request.getRequestURL().toString().startsWith(contextPath + "/location/")) {
            return true;
        }
        Map<String, List<Permission>> businessSystemCodePermissionsMap = (Map<String, List<Permission>>) session.getAttribute(YunaConsts.SESSION_BUSINESS_SYSTEM_CODE_MAP);
        List<BusinessSystem> businessSystems = (List<BusinessSystem>) session.getAttribute(YunaConsts.SESSION_BUSINESS_SYSTEMS);
        Map<String, BusinessSystem> businessSystemMap = Maps.newHashMap();
        businessSystems.forEach(businessSystem -> businessSystemMap.put(businessSystem.getCode(), businessSystem));
        for (Map.Entry<String, List<Permission>> entry : businessSystemCodePermissionsMap.entrySet()) {
            for (Permission permission : entry.getValue()) {
                if (permission.getEnable() == Permission.Enable.No.getCode()) {
                    continue;
                }
                //判断value
                if (StringKits.isNotEmpty(permission.getValue())) {
                    String value = permission.getValue();
                    if (!value.startsWith("http")) {
                        value = businessSystemMap.get(entry.getKey()).getDomain() + value;
                    }
                    if (value.equals(request.getRequestURL().toString())) {
                        //访问链接与配置的链接一致
                        session.setAttribute(YunaConsts.SESSION_USER, fillUserOperateInfo(currentUser, request));
                        return true;
                    }
                }
                //正则判断
                if (StringKits.isNotEmpty(permission.getRegular())) {
                    String regular = permission.getRegular();
                    if (!regular.startsWith("http")) {
                        regular = businessSystemMap.get(entry.getKey()).getDomain() + regular;
                    }
                    if (request.getRequestURL().toString().startsWith(regular)
                            || request.getRequestURL().toString().matches(regular)) {
                        //访问链接符合配置的正则表达式规则
                        session.setAttribute(YunaConsts.SESSION_USER, fillUserOperateInfo(currentUser, request));
                        return true;
                    }
                }
            }
        }
        response.sendRedirect(contextPath + "/500.html?msg=" + StringKits.urlEncode("没有操作权限,请联系管理员"));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object o, ModelAndView mav)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object o, Exception excptn)
            throws Exception {
    }

    private User fillUserOperateInfo(User user, HttpServletRequest request) {
        user.setLastOperateTime(System.currentTimeMillis());
        user.setLastOperateHost(request.getRemoteHost());
        user.setLastOperate(request.getRequestURL().toString());
        return user;
    }
}
