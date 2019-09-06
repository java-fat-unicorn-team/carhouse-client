package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.CarFeature;
import com.carhouse.provider.CarFeatureProvider;
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
class CarFeatureProviderImplTest {

    @Value("${protocol.host.port}")
    private String URL;
    @Value("${car.feature.list.get}")
    private String CAR_FEATURE_LIST_GET;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarFeatureProvider carFeatureProvider;

    @Test
    void getCarFeatures() throws JsonProcessingException {
        List<CarFeature> carFeatureList = new ArrayList<>() {{
            add(new CarFeature(1, "Winter tire"));
            add(new CarFeature(2, "Air condition"));
        }};
        stubFor(get(urlPathEqualTo(CAR_FEATURE_LIST_GET))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsBytes(carFeatureList)))
        );
        List<CarFeature> result = carFeatureProvider.getCarFeatures();
        assertEquals(carFeatureList.size(), result.size());
        assertEquals(carFeatureList.get(0).getCarFeature(), result.get(0).getCarFeature());
        assertEquals(carFeatureList.get(1).getCarFeatureId(), result.get(1).getCarFeatureId());
    }
}