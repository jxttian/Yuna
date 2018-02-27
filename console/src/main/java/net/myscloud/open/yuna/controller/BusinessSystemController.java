package net.myscloud.open.yuna.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.bean.search.BusinessSystemSearch;
import net.myscloud.open.yuna.common.framework.Pagination;
import net.myscloud.open.yuna.common.framework.Response;
import net.myscloud.open.yuna.model.BusinessSystem;
import net.myscloud.open.yuna.service.BusinessSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by genesis on 2016/12/29.
 */
@Slf4j
@Controller
@RequestMapping("system")
public class BusinessSystemController {

    @Autowired
    private BusinessSystemService service;

    @RequestMapping({"/", "", "index", "index.html"})
    public ModelAndView index() {
        return new ModelAndView("system/index");
    }

    @RequestMapping("list.json")
    @ResponseBody
    public Pagination<BusinessSystem> list(BusinessSystemSearch search) {
        long count = service.count(search);
        return Pagination.build(count > 0 ? service.page(search).orElse(Lists.newArrayList()) : Lists.newArrayList(), count);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Response save(@RequestBody BusinessSystem model) {
        boolean success;
        if (model.getId() == null) {
            //新增
            success = service.insertSelective(model) > 0;
        } else {
            success = service.updateSelective(model).isPresent();
        }
        return success ? Response.success() : Response.result(Response.Status.SYSTEM_ERROR.getCode(), "业务系统保存失败");
    }
}
