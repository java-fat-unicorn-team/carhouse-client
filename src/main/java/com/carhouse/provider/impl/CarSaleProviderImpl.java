package com.carhouse.provider.impl;

import com.carhouse.model.CarFeature;
import com.carhouse.model.CarSale;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarSaleProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The car sale data provider.
 */
@Component
public class CarSaleProviderImpl implements CarSaleProvider {

    private final Logger LOGGER = LogManager.getLogger(CarSaleProviderImpl.class);

    @Value("${carSale.url.host}:${carSale.url.port}")
    private String URL;

    @Value("${carSale.car.sale.all}")
    private String CAR_SALE_ALL;

    @Value("${carSale.car.sale.last.five}")
    private String CAR_SALE_LAST_FIVE;

    @Value("${carSale.car.sale.byId}")
    private String CAR_SALE_BY_ID;

    @Value("${carSale.car.sale.byId.authorized}")
    private String CAR_SALE_BY_ID_AUTHORIZED;

    @Value("${carSale.car.sale.add}")
    private String CAR_SALE_ADD;

    @Value("${carSale.car.sale.update}")
    private String CAR_SALE_UPDATE;

    @Value("${carSale.car.sale.delete}")
    private String CAR_SALE_DELETE;

    @Value("${default.image.url}")
    private String DEFAULT_IMAGE_URL;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets list user car sale.
     *
     * @param token token to authenticate user
     * @return the list car sale
     */
    @Override
    public List<CarSaleDto> getListUserCarSale(final String token) {
        LOGGER.debug("method getListUserCarSale with parameter {}", token);
        ResponseEntity<List<CarSaleDto>> response = restTemplate.exchange(URL + "/authorized/carSale",
                HttpMethod.GET, getEntity(token), new ParameterizedTypeReference<List<CarSaleDto>>() {
                });
        return response.getBody();
    }

    /**
     * Gets list car sale.
     *
     * @param requestParams the request params
     * @return the list car sale
     */
    public List<CarSaleDto> getListCarSale(final Map<String, String> requestParams) {
        LOGGER.debug("method getListCarSale with parameters {}", requestParams);
        ResponseEntity<List<CarSaleDto>> response = restTemplate.exchange(buildUrl(URL + CAR_SALE_ALL,
                requestParams), HttpMethod.GET, null, new ParameterizedTypeReference<List<CarSaleDto>>() {
        });
        return response.getBody();
    }

    /**
     * Gets last five car sales.
     *
     * @return the car sales
     */
    @Override
    public List<CarSaleDto> getListLastFiveCarSales() {
        ResponseEntity<List<CarSaleDto>> response = restTemplate.exchange(URL + CAR_SALE_LAST_FIVE, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<CarSaleDto>>() {
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
        LOGGER.debug("method getCarSale with parameter carSaleId = {}", carSaleId);
        return restTemplate.getForObject(URL + CAR_SALE_BY_ID, CarSale.class, carSaleId);
    }

    /**
     * Gets car sale.
     * Return car sale advertisement with selected id
     *
     * @param carSaleId the car sale id
     * @return the car sale
     */
    @Override
    public CarSale getCarSaleAuthorized(final Integer carSaleId, final String token) {
        LOGGER.debug("method getCarSale with parameter carSaleId = {}", carSaleId);
        ResponseEntity<CarSale> response = restTemplate.exchange(URL + CAR_SALE_BY_ID_AUTHORIZED,
                HttpMethod.GET, getEntity(token), CarSale.class, carSaleId);
        return response.getBody();
    }

    /**
     * Add new car sale advertisement.
     * Take car features id and set them to the car sale object
     * Create multipart/form-data request which contains carSale object and image
     *
     * @param carSale       object from form
     * @param multipartFile the multipart file
     * @param carFeatures   list of selected car feature's id
     */
    @Override
    public Integer addCarSale(final CarSale carSale, final MultipartFile multipartFile, final int[] carFeatures,
                              final String token) {
        LOGGER.debug("method addCarSale with parameter carSale = [{}]", carSale);
        carSale.getCar().setCarFeatureList(createCarFeatureList(carFeatures));
        return restTemplate.exchange(URL + CAR_SALE_ADD, HttpMethod.POST,
                createMultipartRequest(carSale, token, multipartFile), Integer.class).getBody();
    }

    /**
     * Update car sale.
     * Take car features id and set them to the car sale object
     * Create multipart/form-data request which contains carSale object and image
     *
     * @param carSale       the car sale
     * @param multipartFile the multipart file
     * @param carFeatures   the car features
     */
    @Override
    public void updateCarSale(final CarSale carSale, final MultipartFile multipartFile, final int[] carFeatures,
                              final String token) {
        LOGGER.debug("method updateCarSale with parameter carSale = [{}]", carSale);
        carSale.getCar().setCarFeatureList(createCarFeatureList(carFeatures));
        restTemplate.exchange(URL + CAR_SALE_UPDATE, HttpMethod.POST,
                createMultipartRequest(carSale, token, multipartFile), Void.class, carSale.getCarSaleId());
    }

    /**
     * Delete car sale by id.
     *
     * @param carSaleId the car sale id
     */
    @Override
    public void deleteCarSale(final int carSaleId, final String token) {
        LOGGER.debug("method deleteCarSale with parameter carSaleId = {}", carSaleId);
        restTemplate.exchange(URL + CAR_SALE_DELETE, HttpMethod.DELETE, getEntity(token), Void.class, carSaleId);
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
        LOGGER.debug("method buildUrl with parameters [{}]", requestParams);
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
        LOGGER.debug("method createCarFeatureList with car features id [{}]", carFeatures);
        List<CarFeature> carFeatureList = new ArrayList<>();
        if (Objects.nonNull(carFeatures)) {
            for (int carFeatureId : carFeatures) {
                carFeatureList.add(new CarFeature(carFeatureId, ""));
            }
        }
        return carFeatureList;
    }

    /**
     * Create entity of multipart/form-data request which contains carSale object and image.
     *
     * @param carSale the car sale
     * @param file    the file
     * @param token   the to authenticate user
     * @return the http entity
     */
    private HttpEntity<MultiValueMap<String, Object>> createMultipartRequest(final CarSale carSale, final String token,
                                                                             final MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", token);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("carSale", carSale);
        body.add("file", file.getResource());
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<String> getEntity(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        return new HttpEntity<String>("parameters", headers);
    }
}
