package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.CarModel;
import com.carhouse.provider.CarModelProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CarModelProviderImplTest {

    private String CAR_MODEL_LIST_GET = "/carSale/car/carModel/list/";
    private String CAR_MODEL_GET = "/carSale/car/carModel/";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarModelProvider carModelProvider;

    @Test
    void getCarModel() throws JsonProcessingException {
        CarModel carModel = new CarModel().setCarModelId(1).setCarModel("M5");
        givenThat(get(urlPathEqualTo(CAR_MODEL_GET + 1))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carModel)))
        );
        CarModel result = carModelProvider.getCarModel("1");
        assertEquals(carModel.getCarModelId(), result.getCarModelId());
        assertEquals(carModel.getCarModel(), result.getCarModel());
    }

    @Test
    void getCarModels() throws JsonProcessingException {
        List<CarModel> carModelList = new ArrayList<>() {{
           add(new CarModel().setCarModelId(1).setCarModel("RS6"));
           add(new CarModel().setCarModelId(2).setCarModel("RS7"));
        }};
        givenThat(get(urlPathEqualTo(CAR_MODEL_LIST_GET + 2))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carModelList)))
        );
        List<CarModel> result = carModelProvider.getCarModels("2");
        assertEquals(carModelList.size(), result.size());
        assertEquals(carModelList.get(0).getCarModelId(), result.get(0).getCarModelId());
        assertEquals(carModelList.get(1).getCarModel(), result.get(1).getCarModel());
    }
}
