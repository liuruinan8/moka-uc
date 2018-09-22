package com.zimokaka.uc.uac.role.po;

import com.zimokaka.uc.uac.permission.po.UcPermission;
import com.zimokaka.uc.uac.user.po.UcUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class UcRole implements Serializable {
    @Id
    private String id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UcRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<UcPermission> permissions;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name = "UcUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<UcUser> userInfos;// 一个角色对应多个用户

    public List<UcPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UcPermission> permissions) {
        this.permissions = permissions;
    }

    public List<UcUser> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UcUser> userInfos) {
        this.userInfos = userInfos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
