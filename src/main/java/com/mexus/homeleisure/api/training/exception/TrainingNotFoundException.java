package com.mexus.homeleisure.api.training.exception;

/**
 * 없는 트레이닝 예외
 * 존재하지 않는 트레이닝입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class TrainingNotFoundException extends RuntimeException {
    /**
     * 존재하지 않는 트레이닝입니다.
     */
    public TrainingNotFoundException() {
        super("존재하지 않는 트레이닝입니다.");
    }
}
