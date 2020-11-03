package com.mexus.homeleisure.security.exception;

/**
 * 아이디 중복 예외
 * ID: nickName 이미 사용중인 닉네임 입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class NicknameAlreadyExistsException extends RuntimeException {
    /**
     * ID: nickName 이미 사용중인 닉네임 입니다.
     *
     * @param nickName 이미 사용중인 닉네임
     */
    public NicknameAlreadyExistsException(String nickName) {
        super("Nickname: " + nickName + " 이미 사용중인 닉네임입니다.");
    }
}
