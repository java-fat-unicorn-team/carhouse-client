package com.carhouse.provider;

import com.carhouse.model.User;
import com.carhouse.model.dto.AuthenticationDto;
import com.carhouse.model.dto.UserLoginDto;

public interface UserProvider {

    AuthenticationDto loginUser(UserLoginDto userLoginDto);

    AuthenticationDto registerUser(User user);
}
