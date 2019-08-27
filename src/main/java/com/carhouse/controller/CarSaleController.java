package com.carhouse.controller;

import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarModelProvider;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarSaleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
     * Return all car sale advertisement.
     *
     * @param model model
     * @return view
     */
    @GetMapping("/carSale")
    public String carSale(final Model model) {
        List<CarSaleDto> listCarSale = carSaleProvider.getListCarSale();
        model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }

    /**
     * Return all car sale advertisement of selected car make.
     *
     * @param carMakeId selected car make id
     * @param model model
     * @return view
     */
    @GetMapping("/carSale/carMake/{carMakeId}")
    public String carSaleWithCarMake(@PathVariable final Integer carMakeId, final Model model) {
        List<CarSaleDto> listCarSale = carSaleProvider.getListCarSale();
        model.addAttribute("carMake", carMakeProvider.getCarMake(carMakeId));
        model.addAttribute("listCarModels", carModelProvider.getCarModels(carMakeId));
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }

    /**
     * Return all car sale advertisement of selected car model.
     *
     * @param carMakeId selected car make id
     * @param carModelId selected car model id
     * @param model model
     * @return view
     */
    @GetMapping("/carSale/carMake/{carMakeId}/carModel/{carModelId}")
    public String carSaleWithCarModel(@PathVariable final Integer carMakeId,
                                      @PathVariable final Integer carModelId, final Model model) {
        List<CarSaleDto> listCarSale = carSaleProvider.getListCarSale();
        model.addAttribute("carMake", carMakeProvider.getCarMake(carMakeId));
        model.addAttribute("carModel", carModelProvider.getCarModel(carModelId));
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }
}
