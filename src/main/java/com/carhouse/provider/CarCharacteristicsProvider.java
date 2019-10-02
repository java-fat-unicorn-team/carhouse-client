package com.carhouse.provider;

import com.carhouse.model.dto.CarCharacteristicsDto;

/**
 * The interface for car feature data provider.
 */
public interface CarCharacteristicsProvider {

    /**
     * Return CarCharacteristicsDto which is used to reduce the count of request to the server.
     * Contains list car makes, fuel types, transmissions and car features
     * @return CarCharacteristicsDto
     */
    CarCharacteristicsDto getCarCharacteristicsDto();
}
