package com.carhouse.provider.impl;

import com.carhouse.model.CarModel;
import com.carhouse.provider.CarModelProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * The car model data provider.
 */
@Component
public class CarModelProviderImpl implements CarModelProvider {

    private final Logger LOGGER = LogManager.getLogger(CarModelProviderImpl.class);

    @Value("${carSale.url.host}:${carSale.url.port}")
    private String URL;

    @Value("${carSale.car.make.model.all}")
    private String CAR_MAKE_MODEL_ALL;

    @Value("${carSale.car.make.model.byId}")
    private String CAR_MODEL_BY_ID;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets car model.
     *
     * @param carModelId the car model id
     * @return the car model
     */
    public CarModel getCarModel(final String carModelId) {
        LOGGER.debug("method getCarModel with parameter carModelId = {}", carModelId);
        if (StringUtils.isNumeric(carModelId)) {
            return restTemplate.getForObject(URL + CAR_MODEL_BY_ID, CarModel.class, carModelId);
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
        LOGGER.debug("method getCarModels with parameter carMakeId = {}", carMakeId);
        if (StringUtils.isNumeric(carMakeId)) {
            ResponseEntity<List<CarModel>> response = restTemplate.exchange(URL + CAR_MAKE_MODEL_ALL,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<CarModel>>() {
                    }, carMakeId);
            return response.getBody();
        } else {
            return new ArrayList<>();
        }
    }
}
