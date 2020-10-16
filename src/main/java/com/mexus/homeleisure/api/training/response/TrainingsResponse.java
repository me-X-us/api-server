package com.mexus.homeleisure.api.training.response;

import com.mexus.homeleisure.api.training.controller.TrainingController;
import com.mexus.homeleisure.api.training.dto.TrainingsDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * 트레이닝 리스트 응답
 *
 * @author always0ne
 * @version 1.0
 */
public class TrainingsResponse extends EntityModel<TrainingsDto> {
    /**
     * 트레이닝 리스트 응답
     * self Link, APIDocs Link
     *
     * @param Training  트레이닝 데이터
     * @param links 추가 링크
     */
    public TrainingsResponse(TrainingsDto Training, Link... links) {
        super(Training, links);
        add(linkTo(TrainingController.class).slash(Training.getTrainingId()).withSelfRel());
    }
}