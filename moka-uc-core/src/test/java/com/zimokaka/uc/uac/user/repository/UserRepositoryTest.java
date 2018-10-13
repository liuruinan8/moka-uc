package com.zimokaka.uc.uac.user.repository;

import com.zimokaka.uc.uac.user.po.UcUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Test
    public void testInsert() {
        UcUser user = new UcUser();
        user.setName("管理员");
        user.setUid("1");
        UcUser result = this.userRepository.save(user);
        System.out.println(result);
    }

    @Test
    public void testFindOne() {
        UcUser user = this.userRepository.findOne("1");
        System.out.println(user);
    }

    @Test
    public void testUpdate() {
        UcUser user = new UcUser();
        user.setUid("1");
        user.setName("管理员");
        user.setPassword("控制权限");
        UcUser result = this.userRepository.save(user);
        System.out.println(result);
    }

    @Test
    public void testDelete() {
        this.userRepository.delete("1");
    }
}
