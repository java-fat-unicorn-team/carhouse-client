package com.carhouse.provider.impl;

import com.carhouse.model.CarMake;
import com.carhouse.provider.CarMakeProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The car make data provider.
 */
@Component
public class CarMakeProviderImpl implements CarMakeProvider {

    @Value("${protocol.host.port}")
    private String URL;

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
        ResponseEntity<List<CarMake>> response = restTemplate.exchange(URL + CAR_MAKE_LIST_GET,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CarMake>>() {
                });
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }

    /**
     * Gets car make.
     *
     * @param carMakeId the car make id
     * @return the car make
     */
    public CarMake getCarMake(final String carMakeId) {
        if (StringUtils.isNumeric(carMakeId)) {
            ResponseEntity<CarMake> response = restTemplate.getForEntity(URL + CAR_MAKE_GET,
                    CarMake.class, carMakeId);
            return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
        } else {
            return null;
        }
    }
}
