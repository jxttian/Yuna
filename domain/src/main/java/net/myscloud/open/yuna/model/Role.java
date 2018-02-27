package net.myscloud.open.yuna.model;

import lombok.*;
import net.myscloud.open.yuna.common.framework.base.BaseModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseModel {

    private Integer id;

    private String code;

    private String name;

    private String permissions;
}