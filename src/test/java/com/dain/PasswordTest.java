package com.dain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class PasswordTest {

    @Test
    public void 비밀번호암호화테스트(){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encode = encoder.encode("1234");
        System.out.println("==================================");
        System.out.println("encode = " + encode);
        System.out.println("==================================");

    }
}
