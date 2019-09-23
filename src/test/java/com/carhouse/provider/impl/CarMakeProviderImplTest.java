package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.CarMake;
import com.carhouse.provider.CarMakeProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CarMakeProviderImplTest {

    @Value("${carSale.car.make.all}")
    private String CAR_MAKE_LIST_GET;
    private String CAR_MAKE_GET = "/carSale/car/carModel/carMake/";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarMakeProvider carMakeProvider;

    @Test
    void getCarMakes() throws JsonProcessingException {
        List<CarMake> carMakeList = new ArrayList<>() {{
            add(new CarMake(1, "Mercedes"));
            add(new CarMake(2, "Audi"));
        }};
        givenThat(get(urlPathEqualTo(CAR_MAKE_LIST_GET))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carMakeList)))
        );
        List<CarMake> result = carMakeProvider.getCarMakes();
        assertEquals(carMakeList.size(), result.size());
        assertEquals(carMakeList.get(0).getCarMakeId(), result.get(0).getCarMakeId());
        assertEquals(carMakeList.get(1).getCarMake(), result.get(1).getCarMake());
    }

    @Test
    void getCarMake() throws JsonProcessingException {
        int carMakeId = 2;
        CarMake carMake = new CarMake(carMakeId, "BMW");
        givenThat(get(urlPathEqualTo(CAR_MAKE_GET + carMakeId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carMake)))
        );
        CarMake result = carMakeProvider.getCarMake(String.valueOf(carMakeId));
        assertEquals(carMake.getCarMakeId(), result.getCarMakeId());
        assertEquals(carMake.getCarMake(), result.getCarMake());
    }

    @Test
    void getNotExistCarMake() {
        int carMakeId = 123;
        int errorStatus = 404;
        String errorMassage = "there is not car make with id = " + carMakeId;
        String errorResponse = "\"date\":\"Thu Sep 26 11:13:01 MSK 2019\", \"status\":\"" + errorStatus + "\", "
                + "\"message\":\"" + errorMassage + "\", \"path\":\"/carSale/123\"";
        givenThat(get(urlPathEqualTo(CAR_MAKE_GET + carMakeId))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(errorResponse))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carMakeProvider.getCarMake(String.valueOf(carMakeId)));
        assertEquals(errorStatus, exception.getStatusCode().value());
        assertTrue(exception.getResponseBodyAsString().contains(errorMassage));
    }
}