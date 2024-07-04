package dev.tudos.quizbuilder.common.api.dto;

import lombok.Getter;

@Getter
public class FieldErrorData {
    private final String field;
    private final String message;

    public FieldErrorData(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
