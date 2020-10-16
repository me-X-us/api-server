package com.mexus.homeleisure.testfactory;

import com.mexus.homeleisure.security.data.UserStatus;
import com.mexus.homeleisure.security.response.SignInResponse;
import com.mexus.homeleisure.security.service.AuthService;
import com.mexus.homeleisure.api.user.data.Users;
import com.mexus.homeleisure.api.user.data.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountFactory {

    @Autowired
    private AuthService authService;
    @Autowired
    protected UsersRepository usersRepository;

    @Transactional
    public SignInResponse generateUser(int i) {
         return authService.signUp(
                 "TestUser" + i,
                "password",
                "테스트 유저 " + i
        );
    }
    @Transactional
    public Users generateUserAndGetUser(int i) {
        authService.signUp(
                "TestUser" + i,
                "password",
                "테스트 유저 " + i
        );
        return usersRepository.findByUserIdAndState("TestUser"+ i, UserStatus.NORMAL, Users.class)
                .get();
    }
}
