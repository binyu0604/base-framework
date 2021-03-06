package com.liugh.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author liugh123
 * @since 2018-06-25
 */
@Data
@TableName("tb_user_to_role")
public class UserToRole extends Model<UserToRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_to_role_id", type = IdType.AUTO)
    private Integer userToRoleId;
    /**
     * 用户编号
     */
    @TableField("user_no")
    private Long userNo;
    /**
     * 角色代号
     */
    @TableField("role_code")
    private Long roleCode;


//    public Integer getUserToRoleId() {
//        return userToRoleId;
//    }
//
//    public void setUserToRoleId(Integer userToRoleId) {
//        this.userToRoleId = userToRoleId;
//    }
//
//    public Long getUserNo() {
//        return userNo;
//    }
//
//    public void setUserNo(Long userNo) {
//        this.userNo = userNo;
//    }
//
//    public String getRoleCode() {
//        return roleCode;
//    }
//
//    public void setRoleCode(String roleCode) {
//        this.roleCode = roleCode;
//    }

    @Override
    protected Serializable pkVal() {
        return this.userToRoleId;
    }

    @Override
    public String toString() {
        return "UserToRole{" +
                "userToRoleId=" + userToRoleId +
                ", userNo=" + userNo +
                ", roleCode=" + roleCode +
                "}";
    }
}
