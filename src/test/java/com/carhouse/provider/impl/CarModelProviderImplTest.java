package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.CarModel;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.CarModelProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        int carModelId = 1;
        CarModel carModel = new CarModel().setCarModelId(carModelId).setCarModel("M5");
        givenThat(get(urlPathEqualTo(CAR_MODEL_GET + carModelId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carModel)))
        );
        CarModel result = carModelProvider.getCarModel(String.valueOf(carModelId));
        assertEquals(carModel.getCarModelId(), result.getCarModelId());
        assertEquals(carModel.getCarModel(), result.getCarModel());
    }

    @Test
    void getNotExistCarModel() throws JsonProcessingException {
        int carModelId = 123;
        String errorMsg = "there is not car model with id = " + carModelId;
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(404);
        exceptionJSONResponse.setMessage(errorMsg);
        givenThat(get(urlPathEqualTo(CAR_MODEL_GET + carModelId))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(objectMapper.writeValueAsString(exceptionJSONResponse)))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carModelProvider.getCarModel(String.valueOf(carModelId)));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(errorMsg, response.getMessage());
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
