package com.carhouse.controller;

import com.carhouse.model.*;
import com.carhouse.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Add car sale advertisement controller.
 */
@Controller
public class AddCarSaleController {

    private CarSaleProvider carSaleProvider;
    private CarMakeProvider carMakeProvider;
    private FuelTypeProvider fuelTypeProvider;
    private CarFeatureProvider carFeatureProvider;

    /**
     * Instantiates a new Add car sale controller.
     *
     * @param carSaleProvider    the car sale provider
     * @param carMakeProvider    the car make data provide
     * @param fuelTypeProvider   the fuel type data provide
     * @param carFeatureProvider the car feature provider
     */
    @Autowired
    public AddCarSaleController(final CarSaleProvider carSaleProvider, final CarMakeProvider carMakeProvider,
                                final FuelTypeProvider fuelTypeProvider, final CarFeatureProvider carFeatureProvider) {
        this.carSaleProvider = carSaleProvider;
        this.carMakeProvider = carMakeProvider;
        this.fuelTypeProvider = fuelTypeProvider;
        this.carFeatureProvider = carFeatureProvider;
    }

    /**
     * Return page to add new car sale advertisement.
     *
     * @param model model
     * @return view
     */
    @GetMapping("/carSale/add")
    public String addCarSale(final Model model) {
        CarSale carSale = new CarSale();
        model.addAttribute("carSale", carSale);
        model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        model.addAttribute("listFuelTypes", fuelTypeProvider.getFuelTypes());
        model.addAttribute("listCarFeatures", carFeatureProvider.getCarFeatures());
        return "addCarSale";
    }

    /**
     * Submit add car sale advertisement.
     * Take list of selected car feature's id as request param
     * Return home page
     *
     * @param carSale     object is used to get entered data.
     * @param featureList the feature list
     * @return view string
     */
    @PostMapping("/carSale/add")
    public String addCarSaleSubmit(@ModelAttribute final CarSale carSale,
                                   @RequestParam(value = "carFeatureList", required = false) final int[] featureList) {
        carSaleProvider.addCarSale(carSale, featureList);
        return "redirect:/homePage";
    }
}
