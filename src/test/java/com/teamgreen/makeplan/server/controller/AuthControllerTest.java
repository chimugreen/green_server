package com.teamgreen.makeplan.server.controller;


import com.teamgreen.makeplan.server.dto.auth.SignInReqDto;
import com.teamgreen.makeplan.server.dto.auth.SignInResDto;
import com.teamgreen.makeplan.server.dto.user.SignUpReqDto;
import com.teamgreen.makeplan.server.dto.user.SignUpResDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional // 각각의 테스트 메서드에 대해 트랜젝션을 시작하고 종료되면 롤백
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Test
    void testSignUp() throws Exception {

        SignUpReqDto reqDto = new SignUpReqDto("khgg1234@gmail.com", "1230");
        SignUpResDto signUpResDto = authController.signUp(reqDto);

        Assertions.assertNotNull(signUpResDto.getId());
    }

    @Test
    void  testSignIn() throws Exception {
        testSignUp();
        SignInReqDto dto = new SignInReqDto("khgg1234@gmail.com", "1230");
        SignInResDto signInResDto = authController.signIn(dto);
        Assertions.assertNotNull(signInResDto);
    }
}
