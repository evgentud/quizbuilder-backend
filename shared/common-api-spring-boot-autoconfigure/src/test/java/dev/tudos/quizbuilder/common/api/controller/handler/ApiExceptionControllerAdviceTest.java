package dev.tudos.quizbuilder.common.api.controller.handler;

import dev.tudos.quizbuilder.common.api.config.CommonWebAutoConfiguration;
import dev.tudos.quizbuilder.common.api.config.ObjectMapperConfiguration;
import dev.tudos.quizbuilder.common.api.controller.TestApiExceptionController;
import dev.tudos.quizbuilder.common.api.controller.TestController;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CommonWebAutoConfiguration.class, ObjectMapperConfiguration.class, ApiExceptionControllerAdvice.class,
        ResponseBodyAdviceController.class,
        TestApiExceptionController.class, TestController.class})
@Import({CommonWebAutoConfiguration.class})
public class ApiExceptionControllerAdviceTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class ApiExceptionTest {
        @Test
        public void whenResourceIsNotFound_thenNotFoundShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/no-resource",
                    status().isNotFound(),
                    404,
                    "NOT_FOUND",
                    "Resource file is not found"
            );
        }

        @Test
        public void whenAccessIsDenied_thenForbiddenShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/access-denied",
                    status().isForbidden(),
                    403,
                    "FORBIDDEN",
                    "The authenticated user does not have access to the resource"
            );
        }

        @Test
        public void whenResourceAlreadyExists_thenBadRequestShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/api-already-exists",
                    status().isBadRequest(),
                    400,
                    "ALREADY_EXISTS",
                    "Resource already exists"
            );
        }

        @Test
        public void whenValidationFails_thenBadRequestShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/validation-exception",
                    status().isBadRequest(),
                    400,
                    "BAD_REQUEST",
                    "Data validation error"
            );
        }

        @Test
        public void whenValidationFailsWithParameter_thenBadRequestWithDescriptionShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/validation-exception-with-parameter",
                    status().isBadRequest(),
                    400,
                    "BAD_REQUEST",
                    "Input parameter \"parameter\" has invalid value \"value\""
            );
        }

        @Test
        public void whenRuntimeEceptionOccurs_thenInternalServerErrorShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/runtime-exception",
                    status().isInternalServerError(),
                    500,
                    "INTERNAL_SERVER_ERROR",
                    "Something went wrong. Please contact the administrator for details on this error"
            );
        }
    }
    
    @Nested
    class ApiBadRequestTest {
        @Test
        public void whenRequestParameterIsRequiredAndMissing_thenBadRequestShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/with-required-param",
                    status().isBadRequest(),
                    400,
                    "BAD_REQUEST",
                    "Required request parameter \"param\" is missing"
            );
        }

        @Test
        public void whenNoHandlerFound_thenNotFoundShouldBeReturned() throws Exception {
            performAndAssertHttpResponse(
                    "/test/invalid_url",
                    status().isNotFound(),
                    404,
                    "NOT_FOUND",
                    "Handler \"/test/invalid_url\" is not found"
            );
        }
    }

    @Nested
    class ApiValidRequestTest {
        @Test
        public void whenGetRequestWithQueryParametersIsValid_thenOkShouldBeReturnedWithBody() throws Exception {
            mockMvc.perform(get("/test/sample-object")
                            .contentType(MediaType.APPLICATION_JSON)
                            .queryParam("int", "42")
                            .queryParam("string", "string_value"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.int_variable").value(42))
                    .andExpect(jsonPath("$.data.string_variable").value("string_value"));
        }

        @Test
        public void whenPostRequestWithValidObject_thenOkShouldBeReturnedWithBody() throws Exception {
            mockMvc.perform(post("/test/post-sample-object")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"int_variable\":42,\"string_variable\":\"string_value\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.int_variable").value(43))
                    .andExpect(jsonPath("$.data.string_variable").value("string_value_updated"));
        }
    }

    private void performAndAssertHttpResponse(String urlTemplate, ResultMatcher resultMatcher, int expectedStatusCode,
                                              String expectedErrorCode, String expectedMessage) throws Exception {
        ResultActions performed = mvcPerform(urlTemplate);
        assertHttpResponse(performed, resultMatcher, expectedStatusCode, expectedErrorCode, expectedMessage);
    }

    private static void assertHttpResponse(ResultActions performed, ResultMatcher resultMatcher, int expectedStatusCode,
                                           String expectedErrorCode, String expectedMessage) throws Exception {
        performed
                .andExpect(resultMatcher)
                .andExpect(jsonPath("$.status_code").value(expectedStatusCode))
                .andExpect(jsonPath("$.error_code").value(expectedErrorCode))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    private ResultActions mvcPerform(String urlTemplate) throws Exception {
        return mockMvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON));
    }
}
