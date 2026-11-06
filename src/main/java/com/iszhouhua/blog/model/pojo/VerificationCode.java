package com.iszhouhua.blog.model.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VerificationCode {
    private final String value;
    private int failedAttempts;
    private long lastSentAt;
}
