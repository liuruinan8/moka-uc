package com.zimokaka.uc.uac.permission.repository;


import com.zimokaka.uc.uac.permission.po.UcPermission;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Nicky on 2017/12/3.
 */
public interface UcPermissionPageRepository extends PagingAndSortingRepository<UcPermission,Integer> {

}
