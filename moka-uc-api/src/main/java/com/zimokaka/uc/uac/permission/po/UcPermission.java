package com.zimokaka.uc.uac.permission.po;

import com.zimokaka.uc.uac.menu.po.UcMenu;
import com.zimokaka.uc.uac.operation.po.UcOperation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 权限操作的Vo类
 * @author Nicky
 * @date 2017年3月6日
 */
@Table(name="uc_permission")
@Entity
public class UcPermission implements Serializable {

    private int id;
    private String permissionDesc;
    private String name;
    private static final long serialVersionUID = 1L;

    private UcMenu menu;

    private Set<UcOperation> operations = new HashSet<UcOperation>();

    public UcPermission() {
        super();
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(length=100)
    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }



    @Column(length=100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(targetEntity=UcMenu.class,cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
    @JoinColumn(name="menuId",referencedColumnName="menuId")
    public UcMenu getMenu() {
        return menu;
    }

    public void setMenu(UcMenu menu) {
        this.menu = menu;
    }

    @ManyToMany(targetEntity=UcOperation.class,cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
    @JoinTable(name="uc_permission_operation",joinColumns=@JoinColumn(name="permissionId",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="operationId",referencedColumnName="id"))
    public Set<UcOperation> getOperations() {
        return operations;
    }

    public void setOperations(Set<UcOperation> operations) {
        this.operations = operations;
    }
}
