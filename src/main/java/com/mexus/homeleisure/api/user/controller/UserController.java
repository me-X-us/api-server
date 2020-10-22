package com.mexus.homeleisure.api.user.controller;

import com.mexus.homeleisure.api.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 컨트롤러
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

  private final TrainingService trainingService;

}
