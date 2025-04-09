package com.RaccoonGG.test.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final String timestamp = LocalDateTime.now().toString();
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private List<String> errors;
}
