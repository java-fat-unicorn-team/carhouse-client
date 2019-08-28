package com.carhouse.provider.impl;

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
    public CarModel getCarModel(final String carModelId) {
        if (carModelId != null && !carModelId.isEmpty()) {
            try {
                return new CarModel().setCarModelId(Integer.parseInt(carModelId)).setCarModel("M2");
            } catch (NumberFormatException ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Gets list car model.
     *
     * @param carMakeId the car make id
     * @return the list car model
     */
    public List<CarModel> getCarModels(final String carMakeId) {
        if (carMakeId != null && !carMakeId.isEmpty()) {
            try {
            return new ArrayList<>() {{
                add(new CarModel().setCarModelId(0).setCarModel("M2"));
                add(new CarModel().setCarModelId(1).setCarModel("M4"));
                add(new CarModel().setCarModelId(2).setCarModel("M5"));
                add(new CarModel().setCarModelId(3).setCarModel("M6"));
            }};
            } catch (NumberFormatException ex) {
                return null;
            }
        } else {
            return new ArrayList<>();
        }

    }
}
