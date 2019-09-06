package com.carhouse.provider.impl;

import com.carhouse.model.CarFeature;
import com.carhouse.provider.CarFeatureProvider;
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
 * The car feature data provider.
 */
@Component
public class CarFeatureProviderImpl implements CarFeatureProvider {

    @Value("${protocol.host.port}")
    private String URL;

    @Value("${car.feature.list.get}")
    private String CAR_FEATURE_LIST_GET;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Return list of all available car features.
     *
     * @return list car features
     */
    public List<CarFeature> getCarFeatures() {
        ResponseEntity<List<CarFeature>> response = restTemplate.exchange(URL + CAR_FEATURE_LIST_GET,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CarFeature>>() {
                });
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }
}
