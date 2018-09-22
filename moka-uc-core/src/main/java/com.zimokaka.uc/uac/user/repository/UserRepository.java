package com.zimokaka.uc.uac.user.repository;

import com.zimokaka.uc.uac.user.po.UcUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<UcUser, Integer> {
}
