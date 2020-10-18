package com.cfm.datasource.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: SysUser
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/18 10:27
 */
@Data
@TableName("t_sys_user")
public class SysUser {

    private String id;

    private String userName;

    private String nickName;

    private String password;

    private Integer enabled;

    private String email;

    private String userface;

    private String salt;

    private String phone;

    private Date lastLoginTime;

    private Date createTime;

    private Date updateTime;
}
