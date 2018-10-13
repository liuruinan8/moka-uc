package com.zimokaka.uc.uac.user.repository;

import com.zimokaka.uc.uac.role.po.UcRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nicky on 2017/12/2.
 */
public interface UserRoleRepository extends JpaRepository<UcRolePermission,String> {
}
