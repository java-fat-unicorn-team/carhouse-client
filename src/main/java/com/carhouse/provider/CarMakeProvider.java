package com.carhouse.provider;

import com.carhouse.model.CarMake;

import java.util.List;

/**
 * The interface for car make data provider.
 */
public interface CarMakeProvider {

    /**
     * Gets list car make.
     *
     * @return the list car make
     */
    List<CarMake> getCarMakes();

    /**
     * Gets car make.
     *
     * @param carMakeId the car make id
     * @return the car make
     */
    CarMake getCarMake(Integer carMakeId);
}
