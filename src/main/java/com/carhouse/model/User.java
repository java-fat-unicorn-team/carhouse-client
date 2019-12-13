package com.carhouse.model;

import javax.validation.constraints.NotBlank;

/**
 * The User model.
 * The model includes user name, login etc.
 * Each user can have more then one car sale announcements.
 *
 * @author Katuranau Maksimilyan
 */
public class User {
    private int userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param userId the user id
     */
    public User(final int userId) {
        this.userId = userId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     * @return the user object
     */
    public User setUserId(final int userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     * @return the user object
     */
    public User setUserName(final String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     * @return the user object
     */
    public User setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

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
     * @return the user object
     */
    public User setLogin(final String login) {
        this.login = login;
        return this;
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
     * @return the user object
     */
    public User setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override
    public final String toString() {
        return "userId=" + userId
                + ", userName='" + userName + '\'';
    }
}
