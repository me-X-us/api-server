package com.mexus.homeleisure.api.user.data;

import com.mexus.homeleisure.security.data.Account;
import com.mexus.homeleisure.security.data.UserRole;
import com.mexus.homeleisure.security.data.UserStatus;
import java.util.List;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Users extends Account {

  public Users(String userId, String password, String name, String email, UserStatus state,
               List<UserRole> roles, String refreshToken) {
    super(userId, password, name, email, state, roles, refreshToken);
  }
}
