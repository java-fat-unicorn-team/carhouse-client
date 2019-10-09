package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.CarFeature;
import com.carhouse.model.CarMake;
import com.carhouse.model.dto.CarCharacteristicsDto;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.CarCharacteristicsProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CarCharacteristicsProviderImplTest {

    @Value("${carSale.car.characteristics.dto}")
    private String CAR_CHARACTERISTICS_DTO;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarCharacteristicsProvider carCharacteristicsProvider;

    @Test
    void getCarCharacteristicsDto() throws JsonProcessingException {
        List<CarMake> listCarMakes = new ArrayList<>() {{
            add(new CarMake(0, "Bentley"));
            add(new CarMake(1, "BMW"));
        }};
        List<CarFeature> listCarFeatures = new ArrayList<>() {{
            add(new CarFeature(0, "Winter tire"));
            add(new CarFeature(1, "Air condition"));
        }};
        CarCharacteristicsDto carCharacteristicsDto = new CarCharacteristicsDto()
                .setCarMakeList(listCarMakes)
                .setCarFeatureList(listCarFeatures);
        givenThat(get(urlPathEqualTo(CAR_CHARACTERISTICS_DTO))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carCharacteristicsDto)))
        );
        CarCharacteristicsDto result = carCharacteristicsProvider.getCarCharacteristicsDto();
        assertEquals(carCharacteristicsDto.getCarMakeList().size(), result.getCarMakeList().size());
        assertEquals(carCharacteristicsDto.getCarFeatureList().size(), result.getCarFeatureList().size());
        assertEquals(carCharacteristicsDto.getCarMakeList().get(0).getCarMake(),
                result.getCarMakeList().get(0).getCarMake());
        assertEquals(carCharacteristicsDto.getCarFeatureList().get(0).getCarFeature(),
                result.getCarFeatureList().get(0).getCarFeature());
    }

    @Test
    void getCarCharacteristicsDtoError() throws JsonProcessingException {
        givenThat(get(urlPathEqualTo(CAR_CHARACTERISTICS_DTO))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsString("wrong json")))
        );
        assertThrows(RestClientException.class,
                () -> carCharacteristicsProvider.getCarCharacteristicsDto());
    }
}
