package com.carhouse.controller;

import com.carhouse.model.CarFeature;
import com.carhouse.model.CarSale;
import com.carhouse.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Car Sale controller.
 * Works with page what contains list of car sale advertisement.
 */
@Controller
public class CarSaleController {

    private CarSaleProvider carSaleProvider;
    private CarMakeProvider carMakeProvider;
    private CarModelProvider carModelProvider;
    private FuelTypeProvider fuelTypeProvider;
    private CarFeatureProvider carFeatureProvider;

    /**
     * Instantiates a new Car sale controller.
     *
     * @param carSaleProvider    the car sale data provider
     * @param carMakeProvider    the car make data provide
     * @param carModelProvider   the car model data provide
     * @param fuelTypeProvider   the fuel type provider
     * @param carFeatureProvider the car feature provider
     */
    @Autowired
    public CarSaleController(final CarSaleProvider carSaleProvider, final CarMakeProvider carMakeProvider,
                             final CarModelProvider carModelProvider, final FuelTypeProvider fuelTypeProvider,
                             final CarFeatureProvider carFeatureProvider) {
        this.carSaleProvider = carSaleProvider;
        this.carMakeProvider = carMakeProvider;
        this.carModelProvider = carModelProvider;
        this.fuelTypeProvider = fuelTypeProvider;
        this.carFeatureProvider = carFeatureProvider;
    }

    /**
     * Return page with car sale advertisement.
     * Get request parameters to filter car sale advertisements.
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
        if (carMakeId == null || carMakeId.isEmpty()) {
            model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        } else if (carModelId == null || carModelId.isEmpty()) {
            model.addAttribute("listCarModels", carModelProvider.getCarModels(carMakeId));
        }
        model.addAttribute("carMake", carMakeProvider.getCarMake(carMakeId));
        model.addAttribute("carModel", carModelProvider.getCarModel(carModelId));
        model.addAttribute("listCarSales", carSaleProvider.getListCarSale(requestParams));
        return "carSales";
    }

    /**
     * Return page to add new car sale advertisement.
     *
     * @param model model
     * @return view
     */
    @GetMapping("/carSale/addForm")
    public String getAddCarSaleForm(final Model model) {
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
     * Return page with list car sales
     *
     * @param carSale     object is used to get entered data.
     * @param featureList the feature list
     * @return view string
     */
    @PostMapping("/carSale/add")
    public String addCarSaleSubmit(@ModelAttribute final CarSale carSale,
                                   @RequestParam(required = false, value = "carFeatureList") final int[] featureList) {
        carSaleProvider.addCarSale(carSale, featureList);
        return "redirect:/carSale";
    }

    /**
     * Return page to update new car sale advertisement.
     * Create list of selected car feature's id to select car feature
     * Gets the request url to return to the same page
     *
     * @param carSaleId  the car sale id
     * @param requestUrl the request url
     * @param model      model
     * @return view string
     */
    @GetMapping("/carSale/{carSaleId}/updateForm")
    public String getUpdateCarSaleForm(@PathVariable final int carSaleId,
                                       @RequestParam("requestUrl") final String requestUrl, final Model model) {
        CarSale carSale = carSaleProvider.getCarSale(carSaleId);
        model.addAttribute("carSale", carSale);
        model.addAttribute("requestUrl", requestUrl);
        model.addAttribute("selectedCarFeatures",
                createSelectedCarFeatureList(carSale.getCar().getCarFeatureList()));
        model.addAttribute("listCarMakes", carMakeProvider.getCarMakes());
        model.addAttribute("listFuelTypes", fuelTypeProvider.getFuelTypes());
        model.addAttribute("listCarFeatures", carFeatureProvider.getCarFeatures());
        return "updateCarSale";
    }

    /**
     * Submit update car sale advertisement.
     * Take list of selected car feature's id as request param
     * Return page with list car sales
     *
     * @param carSale     the car sale
     * @param carSaleId   the car sale id
     * @param requestUrl  the request url
     * @param featureList the feature list
     * @return the string
     */
    @PostMapping("/carSale/{carSaleId}")
    public String updateCarSaleSubmit(
            @ModelAttribute final CarSale carSale,
            @PathVariable final int carSaleId,
            @RequestParam("requestUrl") final String requestUrl,
            @RequestParam(required = false, value = "carFeatureList") final int[] featureList) {
        carSale.setCarSaleId(carSaleId);
        carSaleProvider.updateCarSale(carSale, featureList);
        return "redirect:" + requestUrl;
    }

    /**
     * Delete car sale string.
     * Gets the request url to return to the same page
     *
     * @param carSaleId  the car sale id
     * @param requestUrl the request url
     * @return the string
     */
    @GetMapping("/carSale/{carSaleId}/delete")
    public String deleteCarSale(@PathVariable final Integer carSaleId,
                                @RequestParam("requestUrl") final String requestUrl) {
        String redirectUrl = requestUrl.replaceAll("\\*\\*\\*", "&")
                .replaceFirst("http://[a-zA-Z0-9/.]*:[0-9]*", "redirect:");
        carSaleProvider.deleteCarSale(carSaleId);
        return redirectUrl;
    }

    /**
     * Create array of selected car features.
     * It is used to set selected car features when updating car sale advertisement
     *
     * @param carFeatureList the car feature list
     * @return id list
     */
    private List<Integer> createSelectedCarFeatureList(final List<CarFeature> carFeatureList) {
        return Optional.ofNullable(carFeatureList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .map(CarFeature::getCarFeatureId)
                .collect(Collectors.toList());
    }
}
