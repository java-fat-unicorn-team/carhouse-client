package com.carhouse.controller;

import com.carhouse.consumers.CarModelConsumer;
import com.carhouse.model.stub.CarModelStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Ajax Rest Controller is used to perform queries to server with ajax without updating page.
 *
 * @author Katuranau Maksimilyan
 */
@RestController
public class AjaxRestController {

    @Autowired
    private CarModelConsumer carModelConsumer;

    /**
     * Gets car make id and returns list car models of this car make.
     *
     * @param carMakeId id of selected car make
     * @return list car models
     */
    @GetMapping("/carMake/{carMakeId}/carModels")
    public List<CarModelStub> getCarModels(@PathVariable final Integer carMakeId) {
        List<CarModelStub> listCarModel;
        if (carMakeId >= 0) {
            listCarModel = carModelConsumer.getListCarModelStub(carMakeId);
        } else {
            listCarModel = new ArrayList<>();
        }
        return listCarModel;
    }
}
