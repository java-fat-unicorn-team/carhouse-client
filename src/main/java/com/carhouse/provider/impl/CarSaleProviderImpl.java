package com.carhouse.provider.impl;

import com.carhouse.model.CarSale;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarSaleProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The car sale data provider.
 */
@Component
public class CarSaleProviderImpl implements CarSaleProvider {

    @Value("${protocol.host.port}")
    private String URL;

    @Value("${car.sale.list.get}")
    private String CAR_SALE_LIST_GET;

    @Value("${car.sale.get}")
    private String CAR_SALE_GET;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets list car sale.
     *
     * @param requestParams the request params
     * @return the list car sale
     */
    public List<CarSaleDto> getListCarSale(final Map<String, String> requestParams) {
        HashMap<String, String> map = new HashMap<>(requestParams);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + CAR_SALE_LIST_GET);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNumeric(entry.getValue())
                    || GenericValidator.isDate(entry.getValue(), "yyyy-MM-dd", true)) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        ResponseEntity<List<CarSaleDto>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                null, new ParameterizedTypeReference<List<CarSaleDto>>() {
                });
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }

    /**
     * Gets car sale.
     * Return car sale advertisement with selected id
     *
     * @param carSaleId the car sale id
     * @return the car sale
     */
    @Override
    public CarSale getCarSale(final Integer carSaleId) {
        ResponseEntity<CarSale> response = restTemplate.getForEntity(URL + CAR_SALE_GET, CarSale.class, carSaleId);
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }

    /**
     * Add new car sale advertisement.
     * Take car features id and set them to the car sale object
     *
     * @param carSale     object from form
     * @param carFeatures list of selected car feature's id
     */
    @Override
    public void addCarSale(final CarSale carSale, final int[] carFeatures) {

    }
}
