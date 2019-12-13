package com.carhouse.provider.impl;

import com.carhouse.model.User;
import com.carhouse.model.dto.AuthenticationDto;
import com.carhouse.model.dto.UserLoginDto;
import com.carhouse.provider.UserProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserProviderImpl implements UserProvider {

    private final Logger LOGGER = LogManager.getLogger(UserProviderImpl.class);

    @Value("${carSale.url.host}:${carSale.url.port}")
    private String URL;

    @Value("${user.login}")
    private String USER_LOGIN;

    @Value("${user.register}")
    private String USER_REGISTER;

    private RestTemplate restTemplate;

    @Autowired
    public UserProviderImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AuthenticationDto loginUser(final UserLoginDto userLoginDto) {
        LOGGER.debug("method loginUser");
        return restTemplate.postForObject(URL + USER_LOGIN, userLoginDto, AuthenticationDto.class);
    }

    @Override
    public AuthenticationDto registerUser(final User user) {
        LOGGER.debug("method registerUser");
        return restTemplate.postForObject(URL + USER_REGISTER, user, AuthenticationDto.class);
    }
}
