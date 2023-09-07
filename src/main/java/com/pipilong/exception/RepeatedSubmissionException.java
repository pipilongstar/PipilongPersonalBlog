package com.pipilong.exception;

/**
 * @author pipilong
 * @createTime 2023/8/7
 * @description
 */
public class RepeatedSubmissionException extends Exception{

    public RepeatedSubmissionException() {
    }

    public RepeatedSubmissionException(String message) {
        super(message);
    }
}
