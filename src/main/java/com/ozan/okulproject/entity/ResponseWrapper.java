package com.ozan.okulproject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {
    private boolean success;
    private String message;
    private Object data;
    private int code;

    public ResponseWrapper(String message, Object data, HttpStatus status) {
        this.success = status.is2xxSuccessful();
        this.message = message;
        this.data = data;
        this.code = status.value();
    }
    public ResponseWrapper(String msg, HttpStatus status) {
        this.success = status.is2xxSuccessful();
        this.message = msg;
        this.code = status.value();
    }
    public static ResponseWrapper success(String msg, Object data, HttpStatus status) {
        return new ResponseWrapper(msg, data, status);
    }
    public static ResponseWrapper success(String msg, HttpStatus status) {
        return new ResponseWrapper(msg, status);
    }

    public static ResponseWrapper failure(String msg, HttpStatus status) {
        return new ResponseWrapper(msg, status);
    }
}