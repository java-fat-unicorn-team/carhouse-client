package com.carhouse.provider;

import com.carhouse.model.CarModel;

import java.util.List;

/**
 * The interface for car model data provider.
 */
public interface CarModelProvider {

    /**
     * Gets car model.
     *
     * @param carModelId the car model id
     * @return the car model
     */
    CarModel getCarModel(Integer carModelId);

    /**
     * Gets list car model.
     *
     * @param carMakeId the car make id
     * @return the list car model
     */
    List<CarModel> getCarModels(Integer carMakeId);
}
