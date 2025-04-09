package com.RaccoonGG.test.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@Builder
public class GenericResponse <T>{
    private Boolean success;
    private int status;
    private String message;
    private T data;

    public GenericResponse() {
    }

}
