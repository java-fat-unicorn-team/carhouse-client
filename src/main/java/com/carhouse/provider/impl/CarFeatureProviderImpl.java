package com.carhouse.provider.impl;

import com.carhouse.model.CarFeature;
import com.carhouse.provider.CarFeatureProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * The car feature data provider.
 */
@Component
public class CarFeatureProviderImpl implements CarFeatureProvider {

    @Value("${carSale.url}")
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
        CarFeature[] listCarFeatures = restTemplate.getForObject(URL + CAR_FEATURE_LIST_GET, CarFeature[].class);
        return listCarFeatures != null ? Arrays.asList(listCarFeatures) : null;
    }
}
