package com.carhouse.handler;

import com.carhouse.model.dto.ExceptionJSONResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

/**
 * The RestTemplate response error handler.
 */
@ControllerAdvice
public class RestTemplateResponseErrorHandler {

    private ObjectMapper objectMapper;

    /**
     * Instantiates a new Rest template response error handler.
     *
     * @param objectMapper the object mapper
     */
    @Autowired
    public RestTemplateResponseErrorHandler(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Handle client error and return error page.
     *
     * @param ex the exception
     * @return the model and view
     * @throws JsonProcessingException if can't convert json to object
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleClientError(final HttpClientErrorException ex) throws JsonProcessingException {
        ExceptionJSONResponse response = objectMapper.readValue(ex.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        return createModelAndView(response.getStatus(), response.getMessages(), response.getPath());
    }

    /**
     * Handle server incorrect JSON error and return error page.
     *
     * @param ex the exception
     * @return the model and view
     * @throws JsonProcessingException if can't convert json to object
     */
    @ExceptionHandler(RestClientException.class)
    public ModelAndView handleServerIncorrectJsonError(final RestClientException ex) {
        return createModelAndView(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Collections.singletonList("Sorry, Incorrect JSON obtained from the database, we are working on it"),
                "");
    }

    /**
     * Handle server not available error.
     *
     * @return the model and view
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ModelAndView handleServerNotAvailableError() {
        return createModelAndView(HttpStatus.SERVICE_UNAVAILABLE.value(),
                Collections.singletonList("Sorry, the server is not available"), "");
    }

    /**
     * Create model and view.
     * Set error code and message as model attribute
     * Set the view name to 'fragments::error' if the request came from CommentController otherwise 'errorPage'
     *
     * @param errorCode     the error code
     * @param errorMessages the error message list
     * @param requestUrl    the request url
     * @return the model and view
     */
    private ModelAndView createModelAndView(final int errorCode, final List<String> errorMessages,
                                            final String requestUrl) {
        ModelAndView model = new ModelAndView();
        model.setStatus(HttpStatus.valueOf(errorCode));
        model.addObject("errorCode", errorCode);
        model.addObject("errorMsgList", errorMessages);
        if (requestUrl.contains("/comment")) {
            model.setViewName("fragments::error");
        } else {
            model.setViewName("errorPage");
        }
        return model;
    }
}
