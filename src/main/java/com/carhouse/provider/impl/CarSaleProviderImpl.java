package com.carhouse.provider.impl;

import com.carhouse.model.CarFeature;
import com.carhouse.model.CarSale;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarSaleProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

/**
 * The car sale data provider.
 */
@Component
public class CarSaleProviderImpl implements CarSaleProvider {

    @Value("${carSale.url.host}:${carSale.url.port}")
    private String URL;

    @Value("${carSale.car.sale.all}")
    private String CAR_SALE_ALL;

    @Value("${carSale.car.sale.byId}")
    private String CAR_SALE_BY_ID;

    @Value("${carSale.car.sale.add}")
    private String CAR_SALE_ADD;

    @Value("${carSale.car.sale.update}")
    private String CAR_SALE_UPDATE;

    @Value("${carSale.car.sale.delete}")
    private String CAR_SALE_DELETE;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets list car sale.
     *
     * @param requestParams the request params
     * @return the list car sale
     */
    public List<CarSaleDto> getListCarSale(final Map<String, String> requestParams) {
        ResponseEntity<List<CarSaleDto>> response = restTemplate.exchange(buildUrl(URL + CAR_SALE_ALL,
                requestParams), HttpMethod.GET, null, new ParameterizedTypeReference<List<CarSaleDto>>() {
        });
        return response.getBody();
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
        return restTemplate.getForObject(URL + CAR_SALE_BY_ID, CarSale.class, carSaleId);
    }

    /**
     * Add new car sale advertisement.
     * Take car features id and set them to the car sale object
     *
     * @param carSale     object from form
     * @param carFeatures list of selected car feature's id
     */
    @Override
    public Integer addCarSale(final CarSale carSale, final int[] carFeatures) {
        carSale.getCar().setCarFeatureList(createCarFeatureList(carFeatures));
        return restTemplate.postForObject(URL + CAR_SALE_ADD, carSale, Integer.class);
    }

    /**
     * Update car sale.
     * Take car features id and set them to the car sale object
     *
     * @param carSale     the car sale
     * @param carFeatures the car features
     */
    @Override
    public void updateCarSale(final CarSale carSale, final int[] carFeatures) {
        carSale.getCar().setCarFeatureList(createCarFeatureList(carFeatures));
        restTemplate.put(URL + CAR_SALE_UPDATE, carSale);
    }

    /**
     * Delete car sale by id.
     *
     * @param carSaleId the car sale id
     */
    @Override
    public void deleteCarSale(final int carSaleId) {
        restTemplate.delete(URL + CAR_SALE_DELETE, carSaleId);
    }

    /**
     * Build url with query parameters.
     * Validates incoming parameters and adds only valid
     *
     * @param baseUrl       is url without query parameters
     * @param requestParams request parameters
     * @return url with query parameters
     */
    private String buildUrl(final String baseUrl, final Map<String, String> requestParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        for (Map.Entry<String, String> entry : new HashMap<>(requestParams).entrySet()) {
            if (StringUtils.isNumeric(entry.getValue())
                    || GenericValidator.isDate(entry.getValue(), "yyyy-MM-dd", true)) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return builder.toUriString();
    }

    /**
     * Create car feature list from car feature ids.
     *
     * @param carFeatures the car features
     * @return the list
     */
    private List<CarFeature> createCarFeatureList(final int[] carFeatures) {
        List<CarFeature> carFeatureList = new ArrayList<>();
        if (Objects.nonNull(carFeatures)) {
            for (int carFeatureId : carFeatures) {
                carFeatureList.add(new CarFeature(carFeatureId, ""));
            }
        }
        return carFeatureList;
    }
}
