package net.myscloud.open.yuna.common.framework.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseModel implements Serializable {
    /**
     * 是否可用
     */
    private Integer enable;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 修改时间
     */
    private Date modificationTime;

    /**
     * 修改
     */
    private Integer modifier;

    /**
     * 备注
     */
    private String memo;

    /**
     * 是否可用枚举
     */
    @AllArgsConstructor
    public enum Enable {
        Yes(1), No(0);

        @Getter
        private int code;
    }
}
