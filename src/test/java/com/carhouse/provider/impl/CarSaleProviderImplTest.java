package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.CarSale;
import com.carhouse.provider.CarSaleProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CarSaleProviderImplTest {

    @Value("${carSale.car.sale.all}")
    private String CAR_SALE_LIST_GET;
    private String CAR_SALE_GET = "/carSale/";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarSaleProvider carSaleProvider;

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
    void getNotExistCarSale() {
        int carSaleId = 123;
        stubFor(get(urlPathEqualTo(CAR_SALE_GET + carSaleId))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("there is not car sale with id = " + carSaleId))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.getCarSale(carSaleId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(exception.getResponseBodyAsString(),"there is not car sale with id = " + carSaleId);
    }
}