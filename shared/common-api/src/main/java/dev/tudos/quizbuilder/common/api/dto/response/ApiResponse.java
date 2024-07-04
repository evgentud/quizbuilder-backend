package dev.tudos.quizbuilder.common.api.dto.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {
    private final T data;

    public ApiResponse(T data) {
        this.data = data;
    }
}