package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.FuelType;
import com.carhouse.provider.FuelTypeProvider;
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
class FuelTypeProviderImplTest {

    @Value("${carSale.fuel.type.all}")
    private String FUEL_TYPE_LIST_GET;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FuelTypeProvider fuelTypeProvider;

    @Test
    void getFuelTypes() throws JsonProcessingException {
        List<FuelType> fuelTypeList = new ArrayList<>() {{
            add(new FuelType(1, "Petrol"));
            add(new FuelType(1, "Diesel"));
        }};
        givenThat(get(urlPathEqualTo(FUEL_TYPE_LIST_GET))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(fuelTypeList)))
        );
        List<FuelType> result = fuelTypeProvider.getFuelTypes();
        assertEquals(fuelTypeList.size(), result.size());
        assertEquals(fuelTypeList.get(0).getFuelTypeId(), result.get(0).getFuelTypeId());
        assertEquals(fuelTypeList.get(1).getFuelType(), result.get(1).getFuelType());
    }
}
