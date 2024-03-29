package com.carhouse.provider.impl;

import com.carhouse.model.dto.CarCharacteristicsDto;
import com.carhouse.provider.CarCharacteristicsProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The car characteristics dto data provider.
 */
@Component
public class CarCharacteristicsProviderImpl implements CarCharacteristicsProvider {

    private final Logger LOGGER = LogManager.getLogger(CarCharacteristicsProviderImpl.class);

    @Value("${carSale.url.host}:${carSale.url.port}")
    private String URL;

    @Value("${carSale.car.characteristics.dto}")
    private String CAR_CHARACTERISTICS_DTO;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Return CarCharacteristicsDto which is used to reduce the count of request to the server.
     * Contains list car makes, fuel types, transmissions and car features
     *
     * @return CarCharacteristicsDto
     */
    @Override
    public CarCharacteristicsDto getCarCharacteristicsDto() {
        LOGGER.debug("method getCarCharacteristicsDto was invoked");
        return restTemplate.getForObject(URL + CAR_CHARACTERISTICS_DTO, CarCharacteristicsDto.class);
    }
}
