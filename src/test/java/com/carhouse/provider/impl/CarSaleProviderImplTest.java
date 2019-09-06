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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CarSaleProviderImplTest {

    @Value("${car.sale.list.get}")
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
        CarSale carSale = new CarSale().setCarSaleId(1).setDate(new Date()).setPrice(new BigDecimal(20000));
        stubFor(get(urlPathEqualTo(CAR_SALE_GET + 1))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carSale)))
        );
        CarSale result = carSaleProvider.getCarSale(1);
        assertEquals(carSale.getCarSaleId(), result.getCarSaleId());
        assertEquals(carSale.getDate(), result.getDate());
        assertEquals(carSale.getPrice(), result.getPrice());
    }
}