package dev.tudos.quizbuilder.common.api.controller.handler;

import dev.tudos.quizbuilder.common.api.dto.response.ApiErrorResponse;
import dev.tudos.quizbuilder.common.api.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Controller advice to handle response body and wrap it with ApiResponse or ApiErrorResponse.
 */
@RestControllerAdvice
public class ResponseBodyAdviceController implements ResponseBodyAdvice<Object> {
    private static final String OPEN_API_CONFIG_URL = "/v3/api-docs";

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        final Class<?> parameterType = methodParameter.getParameterType();
        return !ApiResponse.class.isAssignableFrom(parameterType)
                && !ResponseEntity.class.isAssignableFrom(parameterType)
                && !String.class.equals(parameterType);
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        final HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        if (request.getServletPath().startsWith(OPEN_API_CONFIG_URL)) {
            return object;
        }

        if (object instanceof ApiErrorResponse) {
            return object;
        }

        return new ApiResponse<>(object);
    }
}
