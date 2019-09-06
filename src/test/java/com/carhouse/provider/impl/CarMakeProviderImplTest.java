package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.CarMake;
import com.carhouse.provider.CarMakeProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CarMakeProviderImplTest {

    @Value("${protocol.host.port}")
    private String URL;
    @Value("${car.make.list.get}")
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
        CarMake carMake = new CarMake(2, "BMW");
        givenThat(get(urlPathEqualTo(CAR_MAKE_GET + 2))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carMake)))
        );
        CarMake result = carMakeProvider.getCarMake("2");
        assertEquals(carMake.getCarMakeId(), result.getCarMakeId());
        assertEquals(carMake.getCarMake(), result.getCarMake());
    }

}