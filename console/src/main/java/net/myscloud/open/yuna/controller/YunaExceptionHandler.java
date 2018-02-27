package net.myscloud.open.yuna.controller;

import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.common.framework.Response;
import net.myscloud.open.yuna.common.kits.StringKits;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jxtti on 2016/11/18.
 */
@Slf4j
@ControllerAdvice
public class YunaExceptionHandler {

    /**
     * 404
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object noHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        if (needJsonResponse(request)) {
            return ResponseEntity.ok(Response.result(Response.Status.NOT_FOUND_HANDLER));
        }
        return "error/404";
    }

    /**
     * 数据库键冲突
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object duplicateKeyException(Exception e, HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        if (needJsonResponse(request)) {
            return ResponseEntity.ok(Response.result(Response.Status.MYSQL_DUPLICATE_KEY_ERROR));
        }
        return "error/500";
    }

    /**
     * 数据库键冲突
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RedisConnectionFailureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object redisConnectionFailureException(Exception e, HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        if (needJsonResponse(request)) {
            return ResponseEntity.ok(Response.result(Response.Status.SYSTEM_ERROR));
        }
        return "error/500";
    }

    /**
     * 统一错误提醒
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exception(Exception e, HttpServletRequest request) {
        log.warn(e.getMessage(), e);
        if (needJsonResponse(request)) {
            return ResponseEntity.ok(Response.result(Response.Status.SYSTEM_ERROR));
        }
        return "error/500";
    }

    static boolean needJsonResponse(HttpServletRequest request) {
        return request == null || StringKits.isEmpty(request.getHeader("accept")) || request.getHeader("accept").contains("application/json");
    }
}
