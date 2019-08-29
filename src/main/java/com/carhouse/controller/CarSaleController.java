package com.carhouse.controller;

import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarModelProvider;
import com.carhouse.provider.CarSaleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Car Sale controller.
 * Works with page what contains list of car sale advertisement.
 */
@Controller
public class CarSaleController {

    private CarSaleProvider carSaleProvider;
    private CarMakeProvider carMakeProvider;
    private CarModelProvider carModelProvider;

    /**
     * Instantiates a new Car sale controller.
     *
     * @param carSaleProvider  the car sale data provider
     * @param carMakeProvider  the car make data provide
     * @param carModelProvider the car model data provide
     */
    @Autowired
    public CarSaleController(final CarSaleProvider carSaleProvider, final CarMakeProvider carMakeProvider,
                             final CarModelProvider carModelProvider) {
        this.carSaleProvider = carSaleProvider;
        this.carMakeProvider = carMakeProvider;
        this.carModelProvider = carModelProvider;
    }

    /**
     * Return page with car sale advertisement.
     * Get request parameters to filter car sale advertisements
     * If selected all advertisements then it returns list of car makes in the top of page or
     * if selected car make then it returns list of car models in the top of page
     *
     * @param requestParams the request params
     * @param model         the model
     * @return the string
     */
    @GetMapping("/carSale")
    public String carSale(@RequestParam(required = false) final Map<String, String> requestParams,
                          final Model model) {
        String carMakeId = requestParams.get("carMakeId");
        String carModelId = requestParams.get("carModelId");
        if (carMakeId == null) {
            model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        } else if (carModelId == null) {
            model.addAttribute("listCarModels", carModelProvider.getCarModels(carMakeId));
        }
        model.addAttribute("carMake", carMakeProvider.getCarMake(carMakeId));
        model.addAttribute("carModel", carModelProvider.getCarModel(carModelId));
        model.addAttribute("listCarSales", carSaleProvider.getListCarSale());
        return "carSales";
    }
}
