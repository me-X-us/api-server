package com.mexus.homeleisure.api.user.service;

import com.mexus.homeleisure.api.training.data.Likes;
import com.mexus.homeleisure.api.training.data.LikesRepository;
import com.mexus.homeleisure.api.training.dto.TrainingsDto;
import com.mexus.homeleisure.api.user.data.Subscribe;
import com.mexus.homeleisure.api.user.data.SubscribeRepository;
import com.mexus.homeleisure.api.user.data.Users;
import com.mexus.homeleisure.api.user.data.UsersRepository;
import com.mexus.homeleisure.api.user.data.dto.SubScribesDto;
import com.mexus.homeleisure.api.user.exception.InvalidUserException;
import com.mexus.homeleisure.api.user.response.ProfileResponse;
import com.mexus.homeleisure.security.data.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final SubscribeRepository subscribeRepository;
    private final LikesRepository likesRepository;
    private final UsersRepository usersRepository;

    public void subscribeTrainer(String requestUserId, String trainerId) {
        subscribeRepository.save(new Subscribe(
                usersRepository.findByUserIdAndState(trainerId, UserStatus.NORMAL, Users.class)
                        .orElseThrow(InvalidUserException::new),
                usersRepository.findByUserIdAndState(requestUserId, UserStatus.NORMAL, Users.class)
                        .orElseThrow(InvalidUserException::new)
        ));
    }

    public void unsubscribeTrainer(String requestUserId, String trainerId) {
        subscribeRepository.deleteByTrainer_UserIdAndUser_UserId(trainerId, requestUserId);
    }

    public List<SubScribesDto> getSubscribeList(String requestUserId) {
        List<Subscribe> subscribeList = subscribeRepository.findAllByUser_UserId(requestUserId);
        List<SubScribesDto> subScribesDtos = new ArrayList<SubScribesDto>();
        for (Subscribe subscribe : subscribeList) {
            subScribesDtos.add(new SubScribesDto(subscribe.getTrainer().getUserId(), subscribe.getTrainer().getName()));
        }
        return subScribesDtos;
    }

    public List<TrainingsDto> getLikesList(String requestUserId) {
        List<Likes> likesList = likesRepository.findAllByUser_UserId(requestUserId);
        List<TrainingsDto> trainingsDtos = new ArrayList<TrainingsDto>();
        for (Likes likes : likesList) {
            trainingsDtos.add(new TrainingsDto(likes.getTraining()));
        }
        return trainingsDtos;
    }

    @Transactional
    public void updateProfile(String requestUserId, String nickName) {
        Users users = usersRepository.findByUserIdAndState(requestUserId,UserStatus.NORMAL,Users.class)
                .orElseThrow(InvalidUserException::new);
        users.updateProfile(nickName);
    }

    public ProfileResponse getProfile(String requestUserId) {
        return usersRepository.findByUserIdAndState(requestUserId,UserStatus.NORMAL,ProfileResponse.class)
            .orElseThrow(UnknownError::new);
    }
}
