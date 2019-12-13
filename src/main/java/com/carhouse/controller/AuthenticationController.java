package com.carhouse.controller;

import com.carhouse.model.User;
import com.carhouse.model.dto.AuthenticationDto;
import com.carhouse.model.dto.UserLoginDto;
import com.carhouse.provider.UserProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

/**
 * The type Authentication controller.
 */
@Controller
@RequestMapping("/carhouse")
public class AuthenticationController {

    private final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private UserProvider userProvider;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param userProvider the user provider
     */
    @Autowired
    public AuthenticationController(final UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    /**
     * Gets login page.
     *
     * @param model       the model
     * @return the login page
     */
    @GetMapping("/login")
    public ModelAndView getLoginPage(final Model model) {
        LOGGER.debug("method getLoginPage");
        ModelAndView modelAndView = new ModelAndView("loginPage");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        modelAndView.addObject(model.addAttribute("userLogin", new UserLoginDto()));
        return modelAndView;
    }

    /**
     * Submit login response entity.
     *
     * @param userLoginDto  the user login dto
     * @param bindingResult the binding result
     * @return the response entity
     */
    @PostMapping("/login/submit")
    public ResponseEntity<AuthenticationDto> submitLogin(@ModelAttribute @Valid final UserLoginDto userLoginDto,
                                                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.warn("submitLogin form has errors");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        LOGGER.debug("method submitLogin");
        AuthenticationDto authenticationDto = userProvider.loginUser(userLoginDto);
        return ResponseEntity.ok(authenticationDto);
    }

    /**
     * Gets register page.
     *
     * @param model the model
     * @return the register page
     */
    @GetMapping("/register")
    public String getRegisterPage(final Model model) {
        LOGGER.debug("method getRegisterPage");
        model.addAttribute("user", new User());
        return "registerPage";
    }

    /**
     * Submit register response entity.
     *
     * @param user          the user
     * @param bindingResult the binding result
     * @return the response entity
     */
    @PostMapping("/register/submit")
    public ResponseEntity<AuthenticationDto> submitRegister(@ModelAttribute @Valid final User user,
                                                            final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.warn("submitRegister form has errors");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        LOGGER.debug("method submitRegister");
        AuthenticationDto authenticationDto = userProvider.registerUser(user);
        return ResponseEntity.ok(authenticationDto);
    }
}
