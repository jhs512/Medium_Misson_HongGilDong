package com.ll.medium.global.exceptions;

import com.ll.medium.global.rsData.RsData.RsData;
import com.ll.medium.standard.base.Empty;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final RsData<Empty> rsData;

    public GlobalException(String resultCode, String msg) {
        super("resultCode=" + resultCode + ",msg=" + msg);
        this.rsData = RsData.of(resultCode, msg);
    }
}