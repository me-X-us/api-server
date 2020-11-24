package com.mexus.homeleisure.api.user.controller;

import com.mexus.homeleisure.api.training.dto.TrainingsDto;
import com.mexus.homeleisure.api.user.data.dto.SubScribesDto;
import com.mexus.homeleisure.api.user.request.UpdateProfileRequest;
import com.mexus.homeleisure.api.user.response.ProfileResponse;
import com.mexus.homeleisure.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 컨트롤러
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ProfileResponse getProfile(){
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getProfile(requestUserId);
    }

    @PutMapping("/profile")
    public void updateProfile(
            @RequestBody UpdateProfileRequest updateProfileRequest
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateProfile(requestUserId, updateProfileRequest.getNickName());
    }

    @PostMapping("/trainer/{trainerId}")
    public void subscribe(
            @PathVariable String trainerId
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.subscribeTrainer(requestUserId, trainerId);
    }

    @DeleteMapping("/trainer/{trainerId}")
    public void unsubscribe(
            @PathVariable String trainerId
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.unsubscribeTrainer(requestUserId, trainerId);
    }

    @GetMapping("/subscribes")
    public List<SubScribesDto> getSubscribeList() {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getSubscribeList(requestUserId);
    }


    @GetMapping("/training/likes")
    public List<TrainingsDto> getLikesList() {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getLikesList(requestUserId);
    }
}
