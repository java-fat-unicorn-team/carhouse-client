package com.carhouse.controller;

import com.carhouse.consumers.CarMakeConsumer;
import com.carhouse.consumers.FuelTypeConsumer;
import com.carhouse.model.CarSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Add car sale advertisement controller.
 */
@Controller
public class AddCarSaleController {

    private CarMakeConsumer carMakeConsumer;
    private FuelTypeConsumer fuelTypeConsumer;

    /**
     * Instantiates a new Add car sale controller.
     */
    public AddCarSaleController() {
    }

    /**
     * Instantiates a new Add car sale controller.
     *
     * @param carMakeConsumer  the car make data provide
     * @param fuelTypeConsumer the fuel type data provide
     */
    @Autowired
    public AddCarSaleController(final CarMakeConsumer carMakeConsumer, final FuelTypeConsumer fuelTypeConsumer) {
        this.carMakeConsumer = carMakeConsumer;
        this.fuelTypeConsumer = fuelTypeConsumer;
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
        model.addAttribute("listCarMakes", carMakeConsumer.getListCarMakeStub());
        model.addAttribute("listFuelTypes", fuelTypeConsumer.getListFuelTypes());
        return "addCarSale";
    }

    /**
     * Submit add car sale advertisement.
     * Return home page
     *
     * @param carSale object is used to get entered data.
     * @return view
     */
    @PostMapping("/carSale/add")
    public String addCarSaleSubmit(@ModelAttribute final CarSale carSale) {
        return "redirect:/homePage";
    }
}
