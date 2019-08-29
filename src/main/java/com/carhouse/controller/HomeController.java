package com.carhouse.controller;

import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.model.dto.SearchFilter;
import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarModelProvider;
import com.carhouse.provider.CarSaleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Home controller.
 */
@Controller
public class HomeController {

    private CarSaleProvider carSaleProvider;
    private CarMakeProvider carMakeProvider;
    private CarModelProvider carModelProvider;

    /**
     * Instantiates a new Home controller.
     *
     * @param carSaleProvider  the car sale data provide
     * @param carMakeProvider  the car make data provide
     * @param carModelProvider the car model data provide
     */
    @Autowired
    public HomeController(final CarSaleProvider carSaleProvider, final CarMakeProvider carMakeProvider,
                          final CarModelProvider carModelProvider) {
        this.carSaleProvider = carSaleProvider;
        this.carMakeProvider = carMakeProvider;
        this.carModelProvider = carModelProvider;
    }

    /**
     * Home page.
     * Search filter object is used to get selected data.
     * ListCarMakes provides list car makes to choose.
     * ListDates provides dates to choose period from-to
     *
     * @param model model
     * @return view
     */
    @GetMapping("/homePage")
    public String firstPage(final Model model) {
        model.addAttribute("searchFilter", new SearchFilter());
        model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        return "homepage";
    }

    /**
     * Submit filter form from home page.
     * CarMake and CarModel are used to create a search tree
     *
     * @param searchFilter object is used to get selected data.
     * @param model        model
     * @return view
     */
    @PostMapping("/homePage")
    public String carSaleFilter(@ModelAttribute final SearchFilter searchFilter, final Model model) {
        if (searchFilter.getCarMakeId() >= 0) {
            model.addAttribute("carMake", carMakeProvider.getCarMake(searchFilter.getCarMakeId()));
            if (searchFilter.getCarModelId() >= 0) {
                model.addAttribute("carModel",
                        carModelProvider.getCarModel(searchFilter.getCarModelId()));
            }
        }
        List<CarSaleDto> listCarSale = carSaleProvider.getListCarSale();
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }
}

