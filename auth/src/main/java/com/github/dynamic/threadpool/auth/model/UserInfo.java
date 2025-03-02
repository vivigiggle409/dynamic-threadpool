package com.github.dynamic.threadpool.auth.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * User info.
 *
 * @author chen.ma
 * @date 2021/10/30 21:37
 */
@Data
@TableName("user")
public class UserInfo {

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * userName
     */
    private String userName;

    /**
     * password
     */
    private String password;

    /**
     * gmtCreate
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * gmtModified
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * delFlag
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

}
