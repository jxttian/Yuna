package net.myscloud.open.yuna.model;

import lombok.*;
import net.myscloud.open.yuna.common.framework.base.BaseModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseModel {

    private Integer id;

    /**
     * 系统
     */
    private Integer sid;

    /**
     * Ext 系统名称
     */
    private String systemName;

    /**
     * Ext 系统编码
     */
    private String systemCode;

    /**
     * 上级权限ID
     */
    private Integer pid;

    /**
     * 资源编码  定位中y坐标
     */
    private String code;

    /**
     * {@link Type}
     */
    private Integer type;

    private String name;

    private String icon;

    private String value;

    private String regular;

    /**
     * 等级分值 0为不显示，分值越高越靠前
     */
    private Integer rank;

    @AllArgsConstructor
    public enum Type {
        /**
         * 页面
         */
        Page(1),
        /**
         * 链接,不直接显示在页面上
         */
        Url(2),
        /**
         * 按钮
         */
        Button(3),
        /**
         * 标签
         */
        Tag(4);

        @Getter
        private int code;
    }

}