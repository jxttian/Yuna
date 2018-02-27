package net.myscloud.open.yuna.model;

import lombok.*;
import net.myscloud.open.yuna.common.framework.base.BaseModel;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel {

    private Integer id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * MD5后的密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户标签
     */
    private String tags;

    /**
     * 用户绑定的角色，逗号分隔
     */
    private String roles;

    /**
     * 二进制标签
     */
    private Integer flags;

    //Ext
    private String oldPassword;

    //用户登录时间
    private Long loginTime;

    //用户登录地址
    private String loginHost;

    //用户最后一次操作
    private String lastOperate;

    //用户最后一次操作时间
    private Long lastOperateTime;

    //用户最后一次操作地址
    private String lastOperateHost;

    //Session Id
    private String sessionId;

    //拓展信息
    private Map<String, Object> exts;
}