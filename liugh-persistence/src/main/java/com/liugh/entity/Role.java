package com.liugh.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author liugh123
 * @since 2018-06-25
 */
@TableName("tb_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色代号主键
     */
    @TableId("role_code")
    private Long roleCode;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

//    public Role() {
//    }
//
    public Role(String roleName, Long roleCode) {
        this.roleName = roleName;
        this.roleCode = roleCode;
    }
//
//    public String getRoleCode() {
//        return roleCode;
//    }
//
//    public void setRoleCode(String roleCode) {
//        this.roleCode = roleCode;
//    }
//
//    public String getRoleName() {
//        return roleName;
//    }
//
//    public void setRoleName(String roleName) {
//        this.roleName = roleName;
//    }
//
    @Override
    protected Serializable pkVal() {
        return this.roleCode;
    }
//
//    @Override
//    public String toString() {
//        return "Role{" +
//                "roleCode=" + roleCode +
//                ", roleName=" + roleName +
//                "}";
//    }
}
