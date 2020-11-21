package com.mexus.homeleisure.api.training.data;

import com.mexus.homeleisure.api.user.data.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @OneToOne
    @JoinColumn(name = "trainingId")
    private Training training;

    @OneToOne
    @JoinColumn(name = "accountId")
    private Users user;

    public Likes(Users user, Training training){
        this.training = training;
        this.user = user;
    }
}
