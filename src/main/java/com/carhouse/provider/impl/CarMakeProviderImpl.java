package com.carhouse.provider.impl;

import com.carhouse.model.CarMake;
import com.carhouse.provider.CarMakeProvider;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${carSale.url.host}")
    private String URL_HOST;

    @Value("${carSale.url.port}")
    private String URL_PORT;

    @Value("${car.make.list.get}")
    private String CAR_MAKE_LIST_GET;

    @Value("${car.make.get}")
    private String CAR_MAKE_GET;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets list car make.
     *
     * @return the list car make
     */
    public List<CarMake> getCarMakes() {
        ResponseEntity<List<CarMake>> response = restTemplate.exchange(URL_HOST + URL_PORT + CAR_MAKE_LIST_GET,
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
        if (StringUtils.isNumeric(carMakeId)) {
            return restTemplate.getForObject(URL_HOST + URL_PORT + CAR_MAKE_GET, CarMake.class, carMakeId);
        } else {
            return null;
        }
    }
}
