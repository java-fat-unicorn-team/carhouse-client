package com.carhouse.model.dto;

import javax.validation.constraints.NotBlank;

/**
 * The type User login dto.
 */
public class UserLoginDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
