package com.zimokaka.uc.uac.role.po;

import com.zimokaka.uc.uac.permission.po.UcPermission;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 角色信息实体类
 * @author Nicky
 * @date 2017年3月16日
 */
@Table(name="uc_role")
@Entity
public class UcRole implements Serializable{

    /** 角色Id**/
    private int roleId;

    /** 角色描述**/
    private String roleDesc;

    /** 角色名称**/
    private String roleName;

    /** 角色标志**/
    private String role;

    private Set<UcPermission> permissions = new HashSet<UcPermission>();

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(length=100)
    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Column(length=100)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(length=100)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //修改cascade策略为级联关系
    @OneToMany(targetEntity=UcPermission.class,cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
    @JoinTable(name="uc_role_permission", joinColumns=@JoinColumn(name="roleId",referencedColumnName="roleId"), inverseJoinColumns=@JoinColumn(name="permissionId",referencedColumnName="id",unique=true))
    public Set<UcPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<UcPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UcRole) {
            UcRole role = (UcRole) obj;
            return this.roleId==(role.getRoleId())
                    && this.roleName.equals(role.getRoleName())
                    && this.roleDesc.equals(role.getRoleDesc())
                    && this.role.equals(role.getRole());
        }
        return super.equals(obj);
    }
}
