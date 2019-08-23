package com.carhouse.controller;

import com.carhouse.model.*;
import com.carhouse.model.stub.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @GetMapping("/homePage")
    public String firstPage(Model model) {
        List<CarMakeStub> listCarMake = new ArrayList<>();
        listCarMake.add(new CarMakeStub(0, "Bentley", 3));
        listCarMake.add(new CarMakeStub(1, "BMW", 15));
        listCarMake.add(new CarMakeStub(2, "Mercedes", 12));
        listCarMake.add(new CarMakeStub(3, "Audi", 9));
        model.addAttribute("searchFilter", new SearchFilter());
        model.addAttribute("listCarMakes", listCarMake);
        model.addAttribute("listDates", getDates());
        return "homePage";
    }

    @PostMapping("/carSale")
    public String carSaleFilter(@ModelAttribute SearchFilter searchFilter, Model model) {
        if (searchFilter.getCarMakeId() != -1) {
            ReturnBack returnBack = new ReturnBack();
            returnBack.setCarMake(new CarMakeStub(searchFilter.getCarMakeId(), "BMW", 15));
            if (searchFilter.getCarModelId() != -1) {
                returnBack.setCarModel(new CarModelStub(searchFilter.getCarModelId(), "M5", 5));
            }
            model.addAttribute("returnBack", returnBack);
        }
        List<CarSaleStub> listCarSale = getListCarSale();
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }

    @GetMapping("/carSale")
    public String secondPage(Model model) {
        List<CarMakeStub> listCarMake = new ArrayList<>() {{
            add(new CarMakeStub(0, "BMW", 13));
            add(new CarMakeStub(1, "Mercedes", 12));
            add(new CarMakeStub(2, "Audi", 9));
            add(new CarMakeStub(3, "Volkswagen", 16));
        }};
        List<CarSaleStub> listCarSale = getListCarSale();
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarMakes", listCarMake);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }

    @GetMapping("/carSale/carMake/{carMakeId}")
    public String secondPageWithCarMake(@PathVariable Integer carMakeId, Model model) {
        List<CarModelStub> listCarModel = new ArrayList<>() {{
            add(new CarModelStub(0, "M2", 3));
            add(new CarModelStub(1, "M4", 2));
            add(new CarModelStub(2, "M5", 5));
            add(new CarModelStub(3, "M6", 1));
        }};
        List<CarSaleStub> listCarSale = getListCarSale();
        ReturnBack returnBack = new ReturnBack();
        returnBack.setCarMake(new CarMakeStub(carMakeId, "BMW", 15));
        model.addAttribute("returnBack", returnBack);
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarModels", listCarModel);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }

    @GetMapping("/carSale/carMake/{carMakeId}/carModel/{carModelId}")
    public String secondPageWithCarModel(@PathVariable Integer carMakeId,
                                         @PathVariable Integer carModelId, Model model) {
        List<CarSaleStub> listCarSale = getListCarSale();
        ReturnBack returnBack = new ReturnBack();
        returnBack.setCarMake(new CarMakeStub(carMakeId, "BMW", 15));
        returnBack.setCarModel(new CarModelStub(carModelId, "M2", 5));
        model.addAttribute("returnBack", returnBack);
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("carModel", carModelId);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }

    @GetMapping("/carSale/add")
    public String addCarSale(Model model) {
        List<CarMakeStub> listCarMake = new ArrayList<>() {{
            add(new CarMakeStub(0, "BMW", 13));
            add(new CarMakeStub(1, "Mercedes", 12));
            add(new CarMakeStub(2, "Audi", 9));
            add(new CarMakeStub(3, "Volkswagen", 16));
        }};
        List<FuelType> listFuelType = new ArrayList<>() {{
            add(new FuelType(0, "Petrol"));
            add(new FuelType(1, "Diesel"));
            add(new FuelType(2, "Electric"));
        }};
        CarSale carSale = new CarSale();
        model.addAttribute("carSale", carSale);
        model.addAttribute("listCarMakes", listCarMake);
        model.addAttribute("listFuelTypes", listFuelType);
        return "addCarSale";
    }

    @PostMapping("/carSale/add")
    public String addCarSaleSubmit(@ModelAttribute CarSale carSale) {

        return "redirect:/homePage";
    }

    private List<Date> getDates() {
        List<Date> listDate = new ArrayList<>() {{
            add(Date.valueOf("2010-01-01"));
            add(Date.valueOf("2011-01-01"));
            add(Date.valueOf("2012-01-01"));
            add(Date.valueOf("2013-01-01"));
            add(Date.valueOf("2014-01-01"));
            add(Date.valueOf("2015-01-01"));
            add(Date.valueOf("2016-01-01"));
            add(Date.valueOf("2017-01-01"));
            add(Date.valueOf("2018-01-01"));
            add(Date.valueOf("2019-01-01"));
        }};
        return listDate;
    }

    private List<CarSaleStub> getListCarSale() {
        List<CarSaleStub> listCarSale = new ArrayList<>() {{
            add(createCarSaleStub(0, "BMW", "M5"));
            add(createCarSaleStub(1, "Mercedes", "C63AMG"));
            add(createCarSaleStub(2, "Audi", "RS6"));
            add(createCarSaleStub(3, "Mercedes", "E220"));
            add(createCarSaleStub(4, "Mercedes", "C63"));
            add(createCarSaleStub(5, "Audi", "RS5"));
            add(createCarSaleStub(6, "Audi", "A8"));
            add(createCarSaleStub(7, "BMW", "M4"));
            add(createCarSaleStub(8, "BMW", "8"));
            add(createCarSaleStub(9, "Mercedes", "GLS"));
        }};
        return listCarSale;
    }

    private CarSaleStub createCarSaleStub(Integer carSaleId, String carMake, String carModel) {
        return new CarSaleStub(carSaleId, new BigDecimal(30200), Date.valueOf("2019-03-02"),
                new CarStub(0, Date.valueOf("2017-01-01"), 140000,
                        new FuelType(1, "Bensin"),
                        new Transmission(0, "Manual"), new CarModel(0,
                        new CarMake(0, carMake), carModel)));
    }
}
