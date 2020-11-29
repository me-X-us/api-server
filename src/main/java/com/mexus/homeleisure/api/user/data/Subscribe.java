package com.mexus.homeleisure.api.user.data;

import com.mexus.homeleisure.api.user.data.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscribeId;

    @OneToOne
    @JoinColumn(name = "trainerId")
    private Users trainer;

    @OneToOne
    @JoinColumn(name = "userId")
    private Users user;

    public Subscribe(Users trainer, Users user){
        this.trainer = trainer;
        this.user = user;
    }
}
