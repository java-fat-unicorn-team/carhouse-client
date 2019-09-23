package com.carhouse.provider.impl;

import com.carhouse.model.CarModel;
import com.carhouse.provider.CarModelProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The car model data provider.
 */
@Component
public class CarModelProviderImpl implements CarModelProvider {

    @Value("${carSale.url}")
    private String URL;

    @Value("${car.model.list.get}")
    private String CAR_MODEL_LIST_GET;

    @Value("${car.model.get}")
    private String CAR_MODEL_GET;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets car model.
     *
     * @param carModelId the car model id
     * @return the car model
     */
    public CarModel getCarModel(final String carModelId) {
        if (StringUtils.isNumeric(carModelId)) {
            return restTemplate.getForObject(URL + CAR_MODEL_GET, CarModel.class, carModelId);
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
        if (StringUtils.isNumeric(carMakeId)) {
            CarModel[] listCarModels = restTemplate.getForObject(URL + CAR_MODEL_LIST_GET,
                    CarModel[].class, carMakeId);
            return listCarModels != null ? Arrays.asList(listCarModels) : null;
        } else {
            return new ArrayList<>();
        }
    }
}
