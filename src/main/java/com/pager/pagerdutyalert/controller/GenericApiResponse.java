package com.pager.pagerdutyalert.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

enum ResponseStatus {
    SUCCESS, FAILURE
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@RequiredArgsConstructor
public class GenericApiResponse<T> {

    private final ResponseStatus status;
    private String message;
    private T data;

    public int getStatus() {
        return status.equals(ResponseStatus.SUCCESS) ? 1 : 0;
    }

    public static <U> GenericApiResponse<U> withData(U data) {
        GenericApiResponse<U> response = new GenericApiResponse<>(ResponseStatus.SUCCESS);
        response.setData(data);
        return response;
    }

    public static GenericApiResponse withError(String errorMessage) {
        GenericApiResponse response = new GenericApiResponse(ResponseStatus.FAILURE);
        response.setMessage(errorMessage);
        return response;
    }

    public static GenericApiResponse success() {
        return new GenericApiResponse(ResponseStatus.SUCCESS);
    }
}
