package com.carhouse.controller;

import com.carhouse.consumers.CarMakeConsumer;
import com.carhouse.consumers.CarModelConsumer;
import com.carhouse.consumers.CarSaleConsumer;
import com.carhouse.model.stub.CarSaleStub;
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

    private CarSaleConsumer carSaleConsumer;
    private CarMakeConsumer carMakeConsumer;
    private CarModelConsumer carModelConsumer;

    /**
     * Instantiates a new Car sale controller.
     */
    public CarSaleController() {
    }

    /**
     * Instantiates a new Car sale controller.
     *
     * @param carSaleConsumer  the car sale data provider
     * @param carMakeConsumer  the car make data provide
     * @param carModelConsumer the car model data provide
     */
    @Autowired
    public CarSaleController(final CarSaleConsumer carSaleConsumer, final CarMakeConsumer carMakeConsumer,
                             final CarModelConsumer carModelConsumer) {
        this.carSaleConsumer = carSaleConsumer;
        this.carMakeConsumer = carMakeConsumer;
        this.carModelConsumer = carModelConsumer;
    }

    /**
     * Return all car sale advertisement.
     *
     * @param model model
     * @return view
     */
    @GetMapping("/carSale")
    public String carSale(final Model model) {
        List<CarSaleStub> listCarSale = carSaleConsumer.getListCarSale();
        model.addAttribute("listCarMakes", carMakeConsumer.getListCarMakeStub());
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
        List<CarSaleStub> listCarSale = carSaleConsumer.getListCarSale();
        model.addAttribute("carMake", carMakeConsumer.getCarMakeStub(carMakeId));
        model.addAttribute("listCarModels", carModelConsumer.getListCarModelStub(carMakeId));
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
        List<CarSaleStub> listCarSale = carSaleConsumer.getListCarSale();
        model.addAttribute("carMake", carMakeConsumer.getCarMakeStub(carMakeId));
        model.addAttribute("carModel", carModelConsumer.getCarModelStub(carModelId));
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }
}
