package com.zimokaka.uc.uac.role.repository;

import com.zimokaka.uc.uac.role.po.UcRole;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Nicky on 2017/7/30.
 */
public interface UcRolePageRepository extends PagingAndSortingRepository<UcRole, Integer> {

//    @Query("from Role r where r.roleId=:id")
//    Role findByRoleId(@Param("id") int id);



}
