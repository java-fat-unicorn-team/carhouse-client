package com.carhouse.provider.impl;

import com.carhouse.model.FuelType;
import com.carhouse.provider.FuelTypeProvider;
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
 * The fuel type data provider.
 */
@Component
public class FuelTypeProviderImpl implements FuelTypeProvider {

    @Value("${protocol.host.port}")
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
        ResponseEntity<List<FuelType>> response = restTemplate.exchange(URL + FUEL_TYPE_LIST_GET,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<FuelType>>() {
                });
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }
}
