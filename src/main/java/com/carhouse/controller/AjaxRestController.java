package com.carhouse.controller;

import com.carhouse.model.stub.CarModelFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxRestController {

    @GetMapping("/carMake/{carMakeId}/carModels")
    public List<CarModelFilter> getCarModels(@PathVariable Integer carMakeId) {
        List<CarModelFilter> listCarModel;
        if (carMakeId == -1) {
            listCarModel =new ArrayList<>();
        } else {
            listCarModel = new ArrayList<>() {{
                add(new CarModelFilter(0, "M2"));
                add(new CarModelFilter(1, "M4"));
                add(new CarModelFilter(2, "M5"));
                add(new CarModelFilter(3, "M6"));
            }};
        }
        return listCarModel;
    }
}
