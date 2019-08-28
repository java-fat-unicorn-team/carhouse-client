package com.carhouse.controller;

import com.carhouse.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Login controller.
 */
@Controller
public class LoginController {

    /**
     * Gets login page.
     *
     * @param model the model
     * @return view
     */
    @GetMapping("/loginPage")
    public String getLoginPage(final Model model) {
        model.addAttribute("user", new User());
        return "loginpage";
    }

    /**
     * Submit login into the site.
     *
     * @param user the user
     * @return view
     */
    @PostMapping("/loginPage")
    public String submitLogin(@ModelAttribute final User user) {
        return "redirect:/homePage";
    }
}
