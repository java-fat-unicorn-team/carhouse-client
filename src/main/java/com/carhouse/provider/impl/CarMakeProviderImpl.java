package com.carhouse.provider.impl;

import com.carhouse.model.CarMake;
import com.carhouse.provider.CarMakeProvider;
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

import java.util.List;

/**
 * The car make data provider.
 */
@Component
public class CarMakeProviderImpl implements CarMakeProvider {

    private final Logger LOGGER = LogManager.getLogger(CarMakeProviderImpl.class);

    @Value("${carSale.url.host}:${carSale.url.port}")
    private String URL;

    @Value("${carSale.car.make.all}")
    private String CAR_MAKE_ALL;

    @Value("${carSale.car.make.byId}")
    private String CAR_MAKE_BY_ID;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets list car make.
     *
     * @return the list car make
     */
    public List<CarMake> getCarMakes() {
        LOGGER.debug("method getCarMakes was invoked");
        ResponseEntity<List<CarMake>> response = restTemplate.exchange(URL + CAR_MAKE_ALL,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CarMake>>() {
                });
        return response.getBody();
    }

    /**
     * Gets car make.
     *
     * @param carMakeId the car make id
     * @return the car make
     */
    public CarMake getCarMake(final String carMakeId) {
        LOGGER.debug("method getCarMake with parameter carMakeId = {}", carMakeId);
        if (StringUtils.isNumeric(carMakeId)) {
            return restTemplate.getForObject(URL + CAR_MAKE_BY_ID, CarMake.class, carMakeId);
        } else {
            return null;
        }
    }
}
