package com.carhouse.controller;

import com.carhouse.handler.RestTemplateResponseErrorHandler;
import com.carhouse.model.Car;
import com.carhouse.model.CarFeature;
import com.carhouse.model.CarMake;
import com.carhouse.model.CarModel;
import com.carhouse.model.CarSale;
import com.carhouse.model.FuelType;
import com.carhouse.model.Transmission;
import com.carhouse.model.dto.CarCharacteristicsDto;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarModelProvider;
import com.carhouse.provider.CarSaleProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class CarSaleControllerTest {

    @Mock
    private CarSaleProvider carSaleProvider;
    @Mock
    private CarMakeProvider carMakeProvider;
    @Mock
    private CarModelProvider carModelProvider;
    @InjectMocks
    private CarSaleController carSaleController;

    private static List<CarSaleDto> listCarSales;
    private static List<CarMake> listCarMakes;
    private static CarCharacteristicsDto carCharacteristicsDto;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static String token;
    private CarSale carSale;
    private MockMvc mockMvc;

    @BeforeAll
    static void before() {
        listCarSales = new ArrayList<>() {{
            add(new CarSaleDto().setCarSaleId(0).setCarMake("BMW").setCarModel("M5"));
            add(new CarSaleDto().setCarSaleId(1).setCarMake("Mercedes").setCarModel("C63AMG"));
        }};
        listCarMakes = new ArrayList<>() {{
            add(new CarMake(0, "Bentley"));
            add(new CarMake(1, "BMW"));
        }};
        List<FuelType> listFuelTypes = new ArrayList<>() {{
            add(new FuelType(0, "Petrol"));
            add(new FuelType(1, "Diesel"));
        }};
        List<Transmission> listTransmission = new ArrayList<>() {{
            add(new Transmission(0, "Manual"));
            add(new Transmission(1, "Automatic"));
        }};
        List<CarFeature> listCarFeatures = new ArrayList<>() {{
            add(new CarFeature(0, "Winter tire"));
            add(new CarFeature(1, "Air condition"));
        }};
        carCharacteristicsDto = new CarCharacteristicsDto()
                .setCarMakeList(listCarMakes)
                .setFuelTypeList(listFuelTypes)
                .setTransmissionList(listTransmission)
                .setCarFeatureList(listCarFeatures);
        token = "there should be generated token";
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carSaleController)
                .setControllerAdvice(new RestTemplateResponseErrorHandler(objectMapper))
                .build();
        carSale = new CarSale(3)
                .setDate(new Date(56346346))
                .setPrice(new BigDecimal("23000"))
                .setCar(new Car(2)
                        .setTransmission(new Transmission(1))
                        .setFuelType(new FuelType(2))
                        .setYear(new Date(56346346))
                        .setCarModel(new CarModel(12).setCarMake(new CarMake(4)))
                        .setCarFeatureList(carCharacteristicsDto.getCarFeatureList()));
    }

    @Test
    void carSale() throws Exception {
        when(carSaleProvider.getListCarSale(any())).thenReturn(listCarSales);
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMakes);
        mockMvc.perform(get("/carhouse/carSale"))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attribute("listCarMakes", listCarMakes))
                .andExpect(model().attribute("listCarSales", listCarSales));
    }

    @Test
    void carSaleWithCarMake() throws Exception {
        String carMakeId = "2";
        List<CarModel> listCarModels = new ArrayList<>() {{
            add(new CarModel().setCarModelId(0).setCarModel("M2"));
            add(new CarModel().setCarModelId(1).setCarModel("M4"));
        }};
        CarMake carMake = new CarMake(Integer.parseInt(carMakeId), "Bentley");
        when(carSaleProvider.getListCarSale(any())).thenReturn(listCarSales);
        when(carModelProvider.getCarModels(carMakeId)).thenReturn(listCarModels);
        when(carMakeProvider.getCarMake(carMakeId)).thenReturn(carMake);
        mockMvc.perform(get("/carhouse/carSale/?carMakeId={carMakeId}", carMakeId))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attribute("carMake", carMake))
                .andExpect(model().attribute("listCarModels", listCarModels))
                .andExpect(model().attribute("listCarSales", listCarSales));
    }

    @Test
    void carSaleWithCarModel() throws Exception {
        String carMakeId = "2";
        String carModelId = "4";
        CarMake carMake = new CarMake(Integer.parseInt(carMakeId), "Bentley");
        CarModel carModel = new CarModel().setCarModelId(Integer.parseInt(carModelId)).setCarModel("Continental GT");
        when(carSaleProvider.getListCarSale(any())).thenReturn(listCarSales);
        when(carMakeProvider.getCarMake(carMakeId)).thenReturn(carMake);
        when(carModelProvider.getCarModel(carModelId)).thenReturn(carModel);
        mockMvc.perform(get("/carhouse/carSale/?carMakeId={carMakeId}&carModelId={carModelId}",
                carMakeId, carModelId))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attribute("carMake", carMake))
                .andExpect(model().attribute("carModel", carModel))
                .andExpect(model().attribute("listCarSales", listCarSales));
    }

    @Test
    void carSaleWithWrongCarMake() throws Exception {
        String carMakeId = "123";
        List<String> errorMassage = Collections.singletonList("there is not car make with id = " + carMakeId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        when(carModelProvider.getCarModels(carMakeId)).thenThrow(createException(httpStatus, errorMassage));
        mockMvc.perform(get("/carhouse/carSale/?carMakeId={carMakeId}", carMakeId))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
    }

    private HttpClientErrorException createException(HttpStatus httpStatus, List<String> errorMassage)
            throws JsonProcessingException {
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(httpStatus.value());
        exceptionJSONResponse.setMessages(errorMassage);
        exceptionJSONResponse.setPath("");
        HttpClientErrorException exception = HttpClientErrorException.create(httpStatus,
                String.valueOf(httpStatus.value()), null, objectMapper.writeValueAsBytes(exceptionJSONResponse),
                null);
        return exception;
    }
}