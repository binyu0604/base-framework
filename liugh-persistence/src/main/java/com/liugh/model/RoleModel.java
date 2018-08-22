package com.liugh.model;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liugh
 * @since 2018-06-30
 */
public class RoleModel {
    private String roleName;
    private Long roleCode;
    private List<Long> menuCodes;

    public RoleModel() {
    }

    public RoleModel(String roleName, Long roleCode, List<Long> menuCodes) {
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.menuCodes = menuCodes;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Long> getMenuCodes() {
        return menuCodes;
    }

    public void setMenuCodes(List<Long> menuCodes) {
        this.menuCodes = menuCodes;
    }

    public Long getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Long roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public String toString() {
        return "RoleModel{" +
                ", roleName='" + roleName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", menuCodes=" + menuCodes +
                '}';
    }


}
