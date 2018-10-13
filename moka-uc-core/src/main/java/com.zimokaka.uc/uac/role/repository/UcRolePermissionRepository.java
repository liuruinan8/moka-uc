package com.zimokaka.uc.uac.role.repository;

import com.zimokaka.uc.uac.role.po.UcRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Nicky on 2017/11/18.
 */
public interface UcRolePermissionRepository extends JpaRepository<UcRolePermission,String> {

    @Query(value = "select rp from UcRolePermission rp where rp.roleId=:id")
    public List<UcRolePermission> findByRoleId(@Param("id") int id);

}
