package com.carhouse.controller;

import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarSaleProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home controller.
 */
@Controller
@RequestMapping("/carhouse")
public class HomeController {

    private final Logger LOGGER = LogManager.getLogger(HomeController.class);

    private CarMakeProvider carMakeProvider;
    private CarSaleProvider carSaleProvider;

    /**
     * Instantiates a new Home controller.
     *
     * @param carMakeProvider the car make data provide
     * @param carSaleProvider the car sale provider to get last five advertisement
     */
    @Autowired
    public HomeController(final CarMakeProvider carMakeProvider, final CarSaleProvider carSaleProvider) {
        this.carMakeProvider = carMakeProvider;
        this.carSaleProvider = carSaleProvider;
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
    public String homePage(final Model model) {
        LOGGER.debug("method homePage was invoked");
        model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        model.addAttribute("lastFiveCarSales", carSaleProvider.getListLastFiveCarSales());
        return "homepage";
    }
}

