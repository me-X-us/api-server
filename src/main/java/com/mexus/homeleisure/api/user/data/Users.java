package com.mexus.homeleisure.api.user.data;

import com.mexus.homeleisure.security.data.Account;
import com.mexus.homeleisure.security.data.UserRole;
import com.mexus.homeleisure.security.data.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Users extends Account {

    public Users(String userId, String password, String name, UserStatus state, List<UserRole> roles, String refreshToken) {
        super(userId, password, name, state, roles, refreshToken);
    }
}
