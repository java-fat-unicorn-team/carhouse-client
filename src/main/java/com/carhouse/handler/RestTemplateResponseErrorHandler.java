package com.carhouse.handler;

import com.carhouse.model.dto.ExceptionJSONResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;

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
        return createModelAndView(response.getStatus(), response.getMessage());
    }

    /**
     * Handle server incorrect JSON error and return error page.
     *
     * @param ex the exception
     * @return the model and view
     * @throws JsonProcessingException if can't convert json to object
     */
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ModelAndView handleServerIncorrectJsonError(final HttpServerErrorException ex)
            throws JsonProcessingException {
        ExceptionJSONResponse response = objectMapper.readValue(ex.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        return createModelAndView(response.getStatus(), response.getMessage());
    }

    /**
     * Handle server not available error.
     *
     * @return the model and view
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ModelAndView handleServerNotAvailableError() {
        return createModelAndView(503, "Sorry, the server is not available");
    }

    /**
     * Create model and view.
     * Set error code and message as model attribute
     * Set view name to 'errorPage'
     *
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @return the model and view
     */
    private ModelAndView createModelAndView(final int errorCode, final String errorMessage) {
        ModelAndView model = new ModelAndView();
        model.addObject("errorCode", errorCode);
        model.addObject("errorMsg", errorMessage);
        model.setViewName("errorPage");
        return model;
    }
}
