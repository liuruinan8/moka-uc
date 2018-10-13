package com.zimokaka.uc.uac.user.repository;

import com.zimokaka.uc.uac.user.po.UcUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository(value="ucUserRepository")
public interface  UserRepository extends JpaRepository<UcUser, String> {

    UcUser findByUsername(String username);

    @Query(value = "select u from UcUser u where u.username=:username and u.password=:password")
    UcUser findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query( value = "select u from UcUser u where u.id=:id")
    UcUser findById(@Param("id") int id);

    @Query(value = "select u from UcUser u where u.lastLogin BETWEEN :startDate and :endDate")
    Page<UcUser> searchU(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update UcUser u set u.loginIp =?1 where u.username = ?2")
    int updateLoginIpById( String loginIp,  String username);
}
