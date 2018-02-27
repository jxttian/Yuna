package net.myscloud.open.yuna.controller;

import net.myscloud.open.yuna.common.framework.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jxtti on 2017/04/13.
 */
@Controller
public class ErrorController {

    @CrossOrigin(origins = "*")
    @RequestMapping({"500", "500.html"})
    public Object serviceError(HttpServletRequest request, @RequestParam(defaultValue = "系统内部错误") String msg) {
        if (YunaExceptionHandler.needJsonResponse(request)) {
            return ResponseEntity.ok(Response.result(Response.Status.SYSTEM_ERROR.getCode(), msg));
        }
        return new ModelAndView("error/500", "msg", msg);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping({"404", "404.html"})
    public ModelAndView notFound(HttpServletRequest request) {
        return new ModelAndView("error/404");
    }
}
