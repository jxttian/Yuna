package net.myscloud.open.yuna.common.framework;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Response<T> implements Serializable {

    /**
     * 返回码
     */
    private int code = Status.SUCCESS.getCode();

    /**
     * 返回信息
     */
    private String msg = Status.SUCCESS.getMsg();

    private String desc;

    /**
     * 数据总量
     */
    private Long count;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 请求成功
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> success() {
        return Response.result(Status.SUCCESS, null, null);
    }

    /**
     * 请求成功，并返回数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T data) {
        return Response.result(Status.SUCCESS, null, data);
    }

    /**
     * 请求成功并返回数据总量，用于分页
     *
     * @param count
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(long count, T data) {
        return Response.result(Status.SUCCESS, count, data);
    }

    /**
     * 返回结果码及信息
     *
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(int code, String msg) {
        return Response.result(code, msg, null, null);
    }

    /**
     * 返回结果码及信息
     *
     * @param status
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(Status status) {
        return Response.result(status.getCode(), status.getMsg(), null, null);
    }

    /**
     * 返回结果码，信息，数据
     *
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(int code, String msg, T data) {
        return Response.result(code, msg, null, data);
    }

    /**
     * 返回结果码，信息，数据
     *
     * @param status
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(Status status, T data) {
        return Response.result(status.getCode(), status.getMsg(), null, data);
    }

    /**
     * 返回结果码，信息，用于分页数据总量，数据
     *
     * @param status
     * @param count
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(Status status, Long count, T data) {
        return Response.result(status.getCode(), status.getMsg(), count, data);
    }

    public static <T> Response<T> result(int code, String msg, Long count, T data) {
        return Response.result(code, msg, null, count, data);
    }

    /**
     * 返回结果码，信息，用于分页数据总量，数据
     *
     * @param code
     * @param msg
     * @param desc
     * @param count
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(int code, String msg, String desc, Long count, T data) {
        return new Response<>(code, msg, desc, count, data);
    }

    public static <T> Response<T> builder(Status status) {
        return Response.result(status);
    }

    public Response<T> msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public Response<T> desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public Response<T> data(T data) {
        this.setData(data);
        return this;
    }

    public Response<T> build() {
        return this;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Status {
        SYSTEM_ERROR(500, "系统错误"),
        NOT_FOUND_HANDLER(501, "请求未找到相应执行者"),
        MYSQL_DUPLICATE_KEY_ERROR(502, "数据已存在，请勿重复插入"),

        BUSINESS_ERROR(300, "业务错误"),

        ILLEGAL_ARGUMENT(400, "参数不正确"),
        ILLEGAL_SIGN(401, "签名不正确"),

        SUCCESS(200, "请求成功"),;
        private static Map<Integer, Status> map;

        static {
            map = Maps.newHashMap();
            for (Status status : Status.values()) {
                if (!map.containsKey(status.code)) {
                    map.put(status.code, status);
                }
            }
        }

        @Getter
        private int code;
        @Getter
        private String msg;

        public static Status getStatus(int code) {
            Preconditions.checkArgument(Status.map.containsKey(code), "不存在相应Code的状态");
            return Status.map.get(code);
        }

        public static String getStatusMsg(int code) {
            Preconditions.checkArgument(Status.map.containsKey(code), "不存在相应Code的状态");
            return Status.map.get(code).getMsg();
        }
    }
}
