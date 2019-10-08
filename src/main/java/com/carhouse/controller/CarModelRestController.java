package com.carhouse.controller;

import com.carhouse.model.CarModel;
import com.carhouse.provider.CarModelProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CarModelRestController is used to get car model list with ajax.
 *
 * @author Katuranau Maksimilyan
 */
@RestController
public class CarModelRestController {

    @Autowired
    private CarModelProvider carModelProvider;

    /**
     * Gets car make id and returns list car models of this car make.
     *
     * @param carMakeId id of selected car make
     * @return list car models
     */
    @GetMapping("/carModels/{carMakeId}")
    public List<CarModel> getCarModels(@PathVariable final String carMakeId) {
        return carModelProvider.getCarModels(carMakeId);
    }
}