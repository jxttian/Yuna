package net.myscloud.open.yuna.model;

import lombok.*;
import net.myscloud.open.yuna.common.framework.base.BaseModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessSystem extends BaseModel {
    private Integer id;

    /**
     * 编码，定位中的x坐标
     */
    private String code;

    /**
     * 名称，显示用
     */
    private String name;

    private String domain;

    /**
     * 等级分值 0为不显示，分值越高越靠前
     */
    private Integer rank;
}