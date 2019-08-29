package com.carhouse.controller;

import com.carhouse.provider.CarMakeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller.
 */
@Controller
public class HomeController {

    private CarMakeProvider carMakeProvider;

    /**
     * Instantiates a new Home controller.
     *
     * @param carMakeProvider the car make data provide
     */
    @Autowired
    public HomeController(final CarMakeProvider carMakeProvider) {
        this.carMakeProvider = carMakeProvider;
    }

    /**
     * Home page.
     * Contains form with filters to find car sale advertisement
     * ListCarMakes provides list car makes to choose.
     *
     * @param model model
     * @return view
     */
    @GetMapping("/homePage")
    public String firstPage(final Model model) {
        model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        return "homepage";
    }
}

