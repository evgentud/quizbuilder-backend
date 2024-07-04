package dev.tudos.quizbuilder.common.api.config;

import dev.tudos.quizbuilder.common.api.controller.handler.ApiExceptionControllerAdvice;
import dev.tudos.quizbuilder.common.api.controller.handler.ResponseBodyAdviceController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Common web configuration for base API.
 */
@Configuration(proxyBeanMethods = false)
@Import(ObjectMapperConfiguration.class)
public class CommonWebAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ApiExceptionControllerAdvice apiExceptionControllerAdvice(MessageSource messageSource) {
        return new ApiExceptionControllerAdvice(messageSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public ResponseBodyAdviceController responseBodyAdviceController() {
        return new ResponseBodyAdviceController();
    }
}
