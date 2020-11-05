package com.mexus.homeleisure.api.image.exception;

public class FileNameException extends RuntimeException {
    public FileNameException(String fileName) {
        super("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
    }
}
