package com.carhouse.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * The class is used to create response on the exceptions.
 */
public class ExceptionJSONResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;
    private int status;
    private String message;
    private String path;

    /**
     * Instantiates a new Exception json info.
     */
    public ExceptionJSONResponse() {
    }

    /**
     * Gets message.
     *
     * @return the message describing the exception
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message describing the exception
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets url.
     *
     * @return the url of the request at which the exception was thrown
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets url.
     *
     * @param path the url of the request at which the exception was thrown
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(final Date date) {
        this.date = date;
    }

    /**
     * Gets response status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets response status.
     *
     * @param status the status
     */
    public void setStatus(final int status) {
        this.status = status;
    }
}
