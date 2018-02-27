package net.myscloud.open.yuna.controller;

import net.myscloud.open.yuna.common.kits.CollectionKits;
import net.myscloud.open.yuna.consts.YunaConsts;
import net.myscloud.open.yuna.model.Permission;
import net.myscloud.open.yuna.service.BusinessSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by genesis on 2016/12/30.
 */
@Controller
public class LocationController {

    @Autowired
    private BusinessSystemService businessSystemService;

    /**
     * 定位页面渲染Iframe
     *
     * @param x x坐标(required)
     * @return ModelAndView
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "location/{x}", method = RequestMethod.GET)
    public ModelAndView businessSystem(HttpSession session, @PathVariable String x) {
        session.setAttribute(YunaConsts.SESSION_CURRENT_BUSINESS_SYSTEM, x);
        Map<String, List<Permission>> businessSystemCodeMap = (Map<String, List<Permission>>) session.getAttribute(YunaConsts.SESSION_BUSINESS_SYSTEM_CODE_MAP);
        Collection<Permission> permissions = businessSystemCodeMap.get(x);
        if (CollectionKits.isEmpty(permissions)) {
            return new ModelAndView("index");
        }

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject(YunaConsts.CURRENT_PERMISSIONS, permissions.parallelStream()
                .filter(permission -> permission.getType() != null
                        && permission.getType() == Permission.Type.Page.getCode())
                .map(permission -> {
                    StringBuilder value = new StringBuilder();
                    businessSystemService.get(permission.getSid()).ifPresent(businessSystem -> {
                        if (!permission.getValue().startsWith("http")) {
                            value.append(businessSystem.getDomain());
                        }
                    });
                    value.append(permission.getValue());
                    permission.setValue(value.toString());
                    return permission;
                })
                .collect(Collectors.groupingBy(permission -> permission.getPid().toString())));
        return modelAndView;
    }
}
