package com.carhouse.provider.impl;

import com.carhouse.model.FuelType;
import com.carhouse.provider.FuelTypeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * The fuel type data provider.
 */
@Component
public class FuelTypeProviderImpl implements FuelTypeProvider {

    @Value("${carSale.url}")
    private String URL;

    @Value("${fuel.type.list.get}")
    private String FUEL_TYPE_LIST_GET;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets list fuel types.
     *
     * @return the list fuel types
     */
    public List<FuelType> getFuelTypes() {
        FuelType[] listFuelTypes = restTemplate.getForObject(URL + FUEL_TYPE_LIST_GET, FuelType[].class);
        return listFuelTypes != null ? Arrays.asList(listFuelTypes) : null;
    }
}
