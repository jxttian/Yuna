package net.myscloud.open.yuna.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CacheName {

    YUNA_DEFAULT("default", false, null, null),
    YUNA_BUSINESS_SYSTEM("yuna_business_system", false, 60 * 60L, 1000),
    YUNA_USER("yuna_user", false, 60 * 60L, 1000),
    YUNA_ROLE("yuna_role", false, 60L, 1000),
    YUNA_PERMISSION("yuna_permission", false, 60 * 60L, 1000);

    public static final String NAME_YUNA_DEFAULT = "default";
    public static final String NAME_YUNA_BUSINESS_SYSTEM = "yuna_business_system";
    public static final String NAME_YUNA_USER = "yuna_user";
    public static final String NAME_YUNA_ROLE = "yuna_role";
    public static final String NAME_YUNA_PERMISSION = "yuna_permission";

    /**
     * 缓存名称
     */
    @Getter
    private String name;

    /**
     * 是否一直不失效
     */
    @Getter
    private boolean forever;

    /**
     * 失效时间 单位:s
     */
    @Getter
    private Long expire;

    /**
     * 最大大小
     */
    @Getter
    private Integer maxsize;

    public static CacheName get(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }
        for (CacheName cacheName : CacheName.values()) {
            if (cacheName.name.equals(name)) {
                return cacheName;
            }
        }
        return null;
    }

    public static boolean exist(String name) {
        if (name == null || "".equals(name)) {
            return false;
        }
        for (CacheName cacheName : CacheName.values()) {
            if (cacheName.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
