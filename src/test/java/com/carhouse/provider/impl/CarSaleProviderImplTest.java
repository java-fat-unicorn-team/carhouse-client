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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static String token;
    private static int[] carFeatures;
    private static List<CarFeature> carFeatureList;
    private static MockMultipartFile mockMultipartFile;

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
        mockMultipartFile = new MockMultipartFile("file", "test.txt", "image/*",
                "There should be bytes of image".getBytes());
        token = "there should be generated token";
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
    void getCarSale() throws IOException {
        int carSaleId = 1;
        CarSale carSale = new CarSale().setCarSaleId(carSaleId)
                .setDate(new Date())
                .setPrice(new BigDecimal(20000))
                .setImageName("2ebe34f34fsf53b");
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
        assertEquals(carSale.getImageName(), result.getImageName());
    }

    @Test
    void getNotExistCarSale() throws JsonProcessingException {
        int carSaleId = 123;
        int responseStatus = 404;
        String errorMsg = "there is not car sale with id = " + carSaleId;
        stubFor(get(urlPathEqualTo(CAR_SALE_GET + carSaleId))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg))))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.getCarSale(carSaleId));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    @Test
    void addCarSale() throws IOException {
        Integer carSaleId = 7;
        CarSale carSaleWithCarFeatures = new CarSale().setCar(new Car());
        carSaleWithCarFeatures.getCar().setCarFeatureList(carFeatureList);
        stubFor(post(urlPathEqualTo(CAR_SALE_ADD))
                .withHeader("Content-Type", containing("multipart/form-data"))
                .withHeader("Authorization", containing(token))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("carSale")
                                .withBody(equalToJson(objectMapper.writeValueAsString(carSaleWithCarFeatures)))
                                .withHeader("Content-Type", containing("application/json"))
                )
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("file")
                                .withBody(binaryEqualTo(mockMultipartFile.getBytes()))
                )
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(String.valueOf(carSaleId))));
        assertEquals(carSaleId, carSaleProvider.addCarSale(new CarSale().setCar(new Car()), mockMultipartFile,
                carFeatures, token));
    }

    @Test
    void addCarSaleWithoutCarFeatures() throws IOException {
        Integer carSaleId = 7;
        CarSale carSale = new CarSale().setCar(new Car());
        carSale.setImageName("2ebe34f34fsf53b");
        stubFor(post(urlPathEqualTo(CAR_SALE_ADD))
                .withHeader("Content-Type", containing("multipart/form-data"))
                .withHeader("Authorization", containing(token))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("carSale")
                                .withBody(equalToJson(objectMapper.writeValueAsString(carSale)))
                                .withHeader("Content-Type", containing("application/json"))
                )
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("file")
                                .withBody(binaryEqualTo(mockMultipartFile.getBytes()))
                )
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(String.valueOf(carSaleId))));
        assertEquals(carSaleId, carSaleProvider.addCarSale(carSale, mockMultipartFile, null, token));
    }

    @Test
    void addCarSaleWithWrongReferences() throws IOException {
        int responseStatus = 424;
        String errorMsg = "there is wrong references in your car sale";
        CarSale carSale = new CarSale().setCar(new Car());
        carSale.getCar().setCarFeatureList(carFeatureList);
        stubFor(post(urlPathEqualTo(CAR_SALE_ADD))
                .withHeader("Content-Type", containing("multipart/form-data"))
                .withHeader("Authorization", containing(token))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("carSale")
                                .withBody(equalToJson(objectMapper.writeValueAsString(carSale)))
                                .withHeader("Content-Type", containing("application/json"))
                )
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("file")
                                .withBody(binaryEqualTo(mockMultipartFile.getBytes()))
                )
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg)))));
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.addCarSale(new CarSale().setCar(new Car()),
                        mockMultipartFile, carFeatures, token));
        ExceptionJSONResponse response = new ObjectMapper().readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    @Test
    void updateCarSale() throws IOException {
        int carSaleId = 5;
        CarSale carSale = new CarSale(carSaleId).setCar(new Car());
        carSale.getCar().setCarFeatureList(carFeatureList);
        carSale.setImageName("2ebe34f34fsf53b");
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_UPDATE).buildAndExpand(carSaleId);
        stubFor(post(urlPathEqualTo(uriComponents.toString()))
                .withHeader("Content-Type", containing("multipart/form-data"))
                .withHeader("Authorization", containing(token))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("carSale")
                                .withBody(equalToJson(objectMapper.writeValueAsString(carSale)))
                                .withHeader("Content-Type", containing("application/json"))
                )
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("file")
                                .withBody(binaryEqualTo(mockMultipartFile.getBytes()))
                )
                .willReturn(aResponse()
                        .withStatus(200)));
        carSaleProvider.updateCarSale(carSale, mockMultipartFile, carFeatures, token);
    }

    @Test
    void updateCarSaleWithWrongReferences() throws IOException {
        int responseStatus = 424;
        int carSaleId = 3;
        String errorMsg = "there is wrong references in your car sale";
        CarSale carSale = new CarSale(carSaleId).setCar(new Car());
        carSale.setImageName("2ebe34f34fsf53b");
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_UPDATE).buildAndExpand(carSaleId);
        stubFor(post(urlPathEqualTo(uriComponents.toString()))
                .withHeader("Content-Type", containing("multipart/form-data"))
                .withHeader("Authorization", containing(token))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("carSale")
                                .withBody(equalToJson(objectMapper.writeValueAsString(carSale)))
                                .withHeader("Content-Type", containing("application/json"))
                )
                .withMultipartRequestBody(
                        aMultipart()
                                .withName("file")
                                .withBody(binaryEqualTo(mockMultipartFile.getBytes()))
                )
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg)))));
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.updateCarSale(carSale, mockMultipartFile, null, token));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    @Test
    void deleteCarSale() {
        int carSaleId = 3;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_DELETE)
                .buildAndExpand(carSaleId);
        stubFor(delete(urlPathEqualTo(uriComponents.toString()))
                .withHeader("Authorization", containing(token))
                .willReturn(aResponse()
                        .withStatus(200))
        );
        carSaleProvider.deleteCarSale(carSaleId, token);
    }

    @Test
    void deleteNotExistCarSale() throws JsonProcessingException {
        int carSaleId = 30;
        int responseStatus = 424;
        String errorMsg = "there is not car sale with id = " + carSaleId;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(CAR_SALE_DELETE)
                .buildAndExpand(carSaleId);
        stubFor(delete(urlPathEqualTo(uriComponents.toString()))
                .withHeader("Authorization", containing(token))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg))))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> carSaleProvider.deleteCarSale(carSaleId, token));
        ExceptionJSONResponse response = new ObjectMapper().readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    private ExceptionJSONResponse createExceptionJSONResponse(int responseStatus, String errorMsg) {
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(responseStatus);
        exceptionJSONResponse.setMessages(Collections.singletonList(errorMsg));
        return exceptionJSONResponse;
    }
}