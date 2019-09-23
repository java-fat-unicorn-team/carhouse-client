package com.carhouse.handler;

import com.carhouse.model.dto.ExceptionJSONResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;

/**
 * The RestTemplate response error handler.
 */
@ControllerAdvice
public class RestTemplateResponseErrorHandler {

    /**
     * Handle client error and return error page.
     *
     * @param ex the exception
     * @return the model and view
     * @throws JsonProcessingException if can't convert json to object
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleClientError(final HttpClientErrorException ex) throws JsonProcessingException {
        ExceptionJSONResponse response = new ObjectMapper().readValue(ex.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        ModelAndView model = new ModelAndView();
        model.addObject("errorCode", response.getStatus());
        model.addObject("errorMsg", response.getMessage());
        model.setViewName("errorPage");
        return model;
    }

    /**
     * Handle server not available error.
     *
     * @return the model and view
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ModelAndView handleServerNotAvailableError() {
        ModelAndView model = new ModelAndView();
        model.addObject("errorCode", 503);
        model.addObject("errorMsg", "Sorry, the server is not available");
        model.setViewName("errorPage");
        return model;
    }
}
