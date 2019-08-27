package com.carhouse.provider.impl;

import com.carhouse.model.CarMake;
import com.carhouse.model.CarModel;
import com.carhouse.provider.CarModelProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The car model data provider.
 */
@Component
public class CarModelProviderImpl implements CarModelProvider {

    /**
     * Gets car model.
     *
     * @param carModelId the car model id
     * @return the car model
     */
    public CarModel getCarModel(final Integer carModelId) {
        return new CarModel(carModelId, new CarMake(), "M2");
    }

    /**
     * Gets list car model.
     *
     * @param carMakeId the car make id
     * @return the list car model
     */
    public List<CarModel> getCarModels(final Integer carMakeId) {
        if (carMakeId >= 0) {
            return new ArrayList<>() {{
                add(new CarModel(0, new CarMake(), "M2"));
                add(new CarModel(1, new CarMake(), "M4"));
                add(new CarModel(2, new CarMake(), "M5"));
                add(new CarModel(3, new CarMake(), "M6"));
            }};
        } else {
            return new ArrayList<>();
        }

    }
}
