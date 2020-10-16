package com.mexus.homeleisure.api.training.response;

import com.mexus.homeleisure.api.common.DocsController;
import com.mexus.homeleisure.api.training.dto.TrainingDetailDto;
import com.mexus.homeleisure.api.comment.controller.CommentController;
import com.mexus.homeleisure.api.training.controller.TrainingController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * 트레이닝 상세 응답
 *
 * @author always0ne
 * @version 1.0
 */
public class TrainingResponse extends EntityModel<TrainingDetailDto> {
    /**
     * 트레이닝 상세 응답
     * self Link, APIDocs Link
     *
     * @param training   트레이닝 데이터
     * @param trainingId 트레이닝 ID
     * @param links  추가 링크
     */
    public TrainingResponse(TrainingDetailDto training, Long trainingId, Link... links) {
        super(training, links);
        add(linkTo(TrainingController.class).slash(trainingId).withSelfRel());
        add(linkTo(CommentController.class, trainingId).withRel("sendComment"));
        add(linkTo(DocsController.class).slash("#getTraining").withRel("profile"));
    }
}
