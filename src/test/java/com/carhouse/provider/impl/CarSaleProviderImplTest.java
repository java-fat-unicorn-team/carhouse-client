package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.Car;
import com.carhouse.model.CarFeature;
import com.carhouse.model.CarSale;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.CarSaleProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CarSaleProviderImplTest {

    @Value("${carSale.car.sale.all}")
    private String CAR_SALE_LIST_GET;
    @Value("${carSale.car.sale.add}")
    private String CAR_SALE_ADD;
    @Value("${carSale.car.sale.update}")
    private String CAR_SALE_UPDATE;
    @Value("${carSale.car.sale.delete}")
    private String CAR_SALE_DELETE;
    private String CAR_SALE_GET = "/carSale/";

    private static int[] carFeatures;
    private static List<CarFeature> carFeatureList;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarSaleProvider carSaleProvider;

    @BeforeAll
    static void setup() {
        carFeatures = new int[]{1, 2, 3};
        carFeatureList = new ArrayList<>();
        for (int carFeatureId : carFeatures) {
            carFeatureList.add(new CarFeature(carFeatureId, ""));
        }
    }

    @Test
    void getListCarSale() throws JsonProcessingException {
        List<CarSale> carSaleList = new ArrayList<>() {{
            add(new CarSale().setCarSaleId(1).setDate(new Date()).setPrice(new BigDecimal(20000)));
            add(new CarSale().setCarSaleId(2).setDate(new Date()).setPrice(new BigDecimal(30000)));
        }};
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("carMakeId", "1");
        queryParams.put("yearFrom", "2015-01-01");
        stubFor(get(urlPathEqualTo(CAR_SALE_LIST_GET))
                .withQueryParam("carMakeId", equalTo(queryParams.get("carMakeId")))
                .withQueryParam("yearFrom", equalTo(queryParams.get("yearFrom")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carSaleList)))
        );
        carSaleProvider.getListCarSale(queryParams);
    }

    @Test
    void getCarSale() throws JsonProcessingException {
        int carSaleId = 1;
        CarSale carSale = new CarSale().setCarSaleId(carSaleId).setDate(new Date()).setPrice(new BigDecimal(20000));
        stubFor(get(urlPathEqualTo(CAR_SALE_GET + carSaleId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carSale)))
        );
        CarSale result = carSaleProvider.getCarSale(carSaleId);
        assertEquals(carSale.getCarSaleId(), result.getCarSaleId());
        assertEquals(carSale.getDate(), result.getDate());
        assertEquals(carSale.getPrice(), result.getPrice());
    }

    @Test
    void getNotExistCarSale() throws JsonProcessingException {
        int carSaleId = 123;
        int responseStatus = 404;
        String errorMsg = "there is not car sale with id = " + carSaleId;
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(responseStatus);
        exceptionJSONResponse.setMessage(errorMsg);
        stubFor(get(urlPathEqualTo(CAR_SALE_GET + carSaleId))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(exceptionJSONResponse)))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.getCarSale(carSaleId));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessage());
    }

    @Test
    void addCarSale() throws JsonProcessingException {
        Integer carSaleId = 7;
        CarSale carSaleWithCarFeatures = new CarSale().setCar(new Car());
        carSaleWithCarFeatures.getCar().setCarFeatureList(carFeatureList);
        stubFor(post(urlPathEqualTo(CAR_SALE_ADD))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(carSaleWithCarFeatures)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(String.valueOf(carSaleId))));
        assertEquals(carSaleId, carSaleProvider.addCarSale(new CarSale().setCar(new Car()), carFeatures));
    }

    @Test
    void addCarSaleWithoutCarFeatures() throws JsonProcessingException {
        Integer carSaleId = 7;
        CarSale carSale = new CarSale().setCar(new Car());
        stubFor(post(urlPathEqualTo(CAR_SALE_ADD))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(carSale)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(String.valueOf(carSaleId))));
        assertEquals(carSaleId, carSaleProvider.addCarSale(carSale, null));
    }

    @Test
    void addCarSaleWithWrongReferences() throws JsonProcessingException {
        int responseStatus = 424;
        String errorMsg = "there is wrong references in your car sale";
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(responseStatus);
        exceptionJSONResponse.setMessage(errorMsg);
        stubFor(post(urlPathEqualTo(CAR_SALE_ADD))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(new CarSale().setCar(new Car()))))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(exceptionJSONResponse))));
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.addCarSale(new CarSale().setCar(new Car()), null));
        ExceptionJSONResponse response = new ObjectMapper().readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessage());
    }

    @Test
    void updateCarSale() throws JsonProcessingException {
        Integer carSaleId = 7;
        CarSale carSale = new CarSale(carSaleId).setCar(new Car());
        carSale.getCar().setCarFeatureList(carFeatureList);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_UPDATE)
                .buildAndExpand(carSaleId);
        stubFor(post(urlPathEqualTo(uriComponents.toString()))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(carSale)))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    @Test
    void updateCarSaleWithWrongReferences() throws JsonProcessingException {
        CarSale carSale = new CarSale(2);
        carSale.setCar(new Car());
        int responseStatus = 424;
        String errorMsg = "there is wrong references in your car sale";
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(responseStatus);
        exceptionJSONResponse.setMessage(errorMsg);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_UPDATE)
                .buildAndExpand(2);
        stubFor(put(urlPathEqualTo(uriComponents.toString()))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(carSale)))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(exceptionJSONResponse))));
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.updateCarSale(carSale, null));
        ExceptionJSONResponse response = new ObjectMapper().readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessage());
    }

    @Test
    void deleteCarSale() {
        int carSaleId = 3;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_DELETE)
                .buildAndExpand(carSaleId);
        stubFor(delete(urlPathEqualTo(uriComponents.toString()))
                .willReturn(aResponse()
                        .withStatus(200))
        );
        carSaleProvider.deleteCarSale(carSaleId);
    }

    @Test
    void deleteNotExistCarSale() throws JsonProcessingException {
        int carSaleId = 30;
        int responseStatus = 424;
        String errorMsg = "there is not car sale with id = " + carSaleId;
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(responseStatus);
        exceptionJSONResponse.setMessage(errorMsg);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_DELETE)
                .buildAndExpand(carSaleId);
        stubFor(delete(urlPathEqualTo(uriComponents.toString()))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(exceptionJSONResponse)))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.deleteCarSale(carSaleId));
        ExceptionJSONResponse response = new ObjectMapper().readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessage());
    }
}