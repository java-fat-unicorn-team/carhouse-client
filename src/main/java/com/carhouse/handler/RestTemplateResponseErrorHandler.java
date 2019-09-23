package com.carhouse.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleClientError(final HttpClientErrorException ex) {
        Map<String, String> responseValues = parseResponseString(ex.getResponseBodyAsString());
        ModelAndView model = new ModelAndView();
        model.addObject("errorCode", responseValues.get("status"));
        model.addObject("errorMsg", responseValues.get("message"));
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

    /**
     * Parse response string and create map of response values.
     *
     * @param response the response
     * @return the map of response values
     */
    private Map<String, String> parseResponseString(final String response) {
        String[] mas = response.substring(1, response.length() - 1).split("\",\\s\"");
        Map<String, String> map = new HashMap<>();
        for (String item : mas) {
            List<String> list = Arrays.asList(item.split("\":\""));
            map.put(list.get(0), list.get(1));
        }
        return map;
    }
}
