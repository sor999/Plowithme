package com.example.Plowithme.exception.error;

import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;


}