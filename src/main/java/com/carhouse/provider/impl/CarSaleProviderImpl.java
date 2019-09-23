package com.carhouse.provider.impl;

import com.carhouse.model.CarSale;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarSaleProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The car sale data provider.
 */
@Component
public class CarSaleProviderImpl implements CarSaleProvider {

    @Value("${carSale.url}")
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
        CarSaleDto[] listCarSales = restTemplate.getForObject(buildUrl(URL + CAR_SALE_LIST_GET, requestParams),
                CarSaleDto[].class);
        return listCarSales != null ? Arrays.asList(listCarSales) : null;
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
        return restTemplate.getForObject(URL + CAR_SALE_GET, CarSale.class, carSaleId);
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

    /**
     * Build url with query parameters.
     * Validates incoming parameters and adds only valid
     *
     * @param baseUrl is url without query parameters
     * @param requestParams request parameters
     * @return url with query parameters
     */
    private String buildUrl(final String baseUrl, final Map<String, String> requestParams) {
        HashMap<String, String> map = new HashMap<>(requestParams);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNumeric(entry.getValue())
                    || GenericValidator.isDate(entry.getValue(), "yyyy-MM-dd", true)) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return builder.toUriString();
    }
}
