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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

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

    @Value("${carSale.car.sale.byId}")
    private String CAR_SALE_BY_ID;

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
     * Gets list car sale.
     * Generate imageUrl(string in Base64 format) for each car sale advertisement to display image on the UI
     *
     * @param requestParams the request params
     * @return the list car sale
     */
    public List<CarSaleDto> getListCarSale(final Map<String, String> requestParams) {
        LOGGER.debug("method getListCarSale with parameters {}", requestParams);
        ResponseEntity<List<CarSaleDto>> response = restTemplate.exchange(buildUrl(URL + CAR_SALE_ALL,
                requestParams), HttpMethod.GET, null, new ParameterizedTypeReference<List<CarSaleDto>>() {
        });
        List<CarSaleDto> carSaleList = response.getBody();
        carSaleList.forEach(carSale -> carSale.setImageUrl(getImageUrl(carSale.getImage())));
        return carSaleList;
    }

    /**
     * Gets car sale.
     * Return car sale advertisement with selected id
     * Generate imageUrl(string in Base64 format) to display image on the UI
     *
     * @param carSaleId the car sale id
     * @return the car sale
     */
    @Override
    public CarSale getCarSale(final Integer carSaleId) {
        LOGGER.debug("method getCarSale with parameter carSaleId = {}", carSaleId);
        CarSale carSale = restTemplate.getForObject(URL + CAR_SALE_BY_ID, CarSale.class, carSaleId);
        carSale.setImageUrl(getImageUrl(carSale.getImage()));
        return carSale;
    }

    /**
     * Add new car sale advertisement.
     * Take car features id and set them to the car sale object
     * If multipartFile object contains image then gets bytes from multipartFile object and sets to image field
     *
     * @param carSale       object from form
     * @param multipartFile the multipart file
     * @param carFeatures   list of selected car feature's id
     * @throws IOException the io exception if can't read bytes from multipartFile object
     */
    @Override
    public Integer addCarSale(final CarSale carSale, final MultipartFile multipartFile, final int[] carFeatures)
            throws IOException {
        LOGGER.debug("method addCarSale with parameter carSale = [{}]", carSale);
        carSale.getCar().setCarFeatureList(createCarFeatureList(carFeatures));
        if (!multipartFile.isEmpty()) {
            carSale.setImage(multipartFile.getBytes());
        }
        return restTemplate.postForObject(URL + CAR_SALE_ADD, carSale, Integer.class);
    }

    /**
     * Update car sale.
     * Take car features id and set them to the car sale object
     * If multipartFile object contains image then gets bytes from multipartFile object and sets to image field
     *
     * @param carSale       the car sale
     * @param multipartFile the multipart file
     * @param carFeatures   the car features
     * @throws IOException the io exception if can't read bytes from multipartFile object
     */
    @Override
    public void updateCarSale(final CarSale carSale, final MultipartFile multipartFile, final int[] carFeatures)
            throws IOException {
        LOGGER.debug("method updateCarSale with parameter carSale = [{}]", carSale);
        carSale.getCar().setCarFeatureList(createCarFeatureList(carFeatures));
        if (!multipartFile.isEmpty()) {
            carSale.setImage(multipartFile.getBytes());
        }
        restTemplate.put(URL + CAR_SALE_UPDATE, carSale);
    }

    /**
     * Delete car sale by id.
     *
     * @param carSaleId the car sale id
     */
    @Override
    public void deleteCarSale(final int carSaleId) {
        LOGGER.debug("method deleteCarSale with parameter carSaleId = {}", carSaleId);
        restTemplate.delete(URL + CAR_SALE_DELETE, carSaleId);
    }

    /**
     * Gets image url.
     * Convert byte array of image to Base64 String format to display the image on the UI
     * If there is not image then set image url to default image from the internet
     *
     * @param image the image
     * @return the image url
     */
    private String getImageUrl(final byte[] image) {
        String imageUrl;
        if (Objects.nonNull(image)) {
            imageUrl = "data:image;base64,";
            imageUrl = imageUrl.concat(new String(Base64.getEncoder().encode(image)));
        } else {
            imageUrl = DEFAULT_IMAGE_URL;
        }
        return imageUrl;
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
}
