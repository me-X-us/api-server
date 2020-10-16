package com.mexus.homeleisure.api.training.controller;

import com.mexus.homeleisure.api.common.response.ErrorResponse;
import com.mexus.homeleisure.api.training.exception.TrainingNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * 트레이닝 서비스에서 발생하는 Exception Handler
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TrainingExceptionHandler {
    /**
     * 없는 트레이닝 예외 발생
     *
     * @param exception 없는 트레이닝 예외
     * @return NOT_FOUND
     */
    @ExceptionHandler(TrainingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleCommentNotFound(TrainingNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "1101", exception.getMessage());
    }
}
