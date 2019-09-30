package com.carhouse.controller;

import com.carhouse.handler.RestTemplateResponseErrorHandler;
import com.carhouse.model.*;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarSaleControllerTest {

    @Mock
    private CarSaleProvider carSaleProvider;
    @Mock
    private CarMakeProvider carMakeProvider;
    @Mock
    private CarModelProvider carModelProvider;
    @Mock
    private FuelTypeProvider fuelTypeProvider;
    @Mock
    private CarFeatureProvider carFeatureProvider;
    @InjectMocks
    private CarSaleController carSaleController;

    private static List<CarSaleDto> listCarSales;
    private static List<CarMake> listCarMakes;
    private static List<FuelType> listFuelTypes;
    private static List<CarFeature> listCarFeatures;
    private ObjectMapper objectMapper = new ObjectMapper();
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
        listFuelTypes = new ArrayList<>() {{
            add(new FuelType(0, "Petrol"));
            add(new FuelType(1, "Diesel"));
        }};
        listCarFeatures = new ArrayList<>() {{
           add(new CarFeature(0, "Winter tire"));
           add(new CarFeature(1, "Air condition"));
        }};
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carSaleController)
                .setControllerAdvice(new RestTemplateResponseErrorHandler(objectMapper))
                .build();
    }

    @Test
    void carSale() throws Exception {
        when(carSaleProvider.getListCarSale(any())).thenReturn(listCarSales);
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMakes);
        mockMvc.perform(get("/carSale"))
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
        mockMvc.perform(get("/carSale/?carMakeId={carMakeId}", carMakeId))
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
        mockMvc.perform(get("/carSale/?carMakeId={carMakeId}&carModelId={carModelId}",
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
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(404);
        exceptionJSONResponse.setMessage("there is not car make with id = " + carMakeId);
        HttpClientErrorException exception = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "404",
                null, objectMapper.writeValueAsBytes(exceptionJSONResponse), null);
        when(carModelProvider.getCarModels(carMakeId)).thenThrow(exception);
        mockMvc.perform(get("/carSale/?carMakeId={carMakeId}", carMakeId))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", exceptionJSONResponse.getStatus()))
                .andExpect(model().attribute("errorMsg", exceptionJSONResponse.getMessage()));
    }

    @Test
    void addCarSale() throws Exception {
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMakes);
        when(fuelTypeProvider.getFuelTypes()).thenReturn(listFuelTypes);
        when(carFeatureProvider.getCarFeatures()).thenReturn(listCarFeatures);
        mockMvc.perform(get("/carSale/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCarSale"))
                .andExpect(model().attribute("listCarMakes", listCarMakes))
                .andExpect(model().attribute("listFuelTypes", listFuelTypes));
    }

    @Test
    void addCarSaleSubmit() throws Exception {
        mockMvc.perform(post("/carSale/add")
                .param("carFeatureList", "1", "2", "3")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new CarSale())))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/carSale"));
        verify(carSaleProvider).addCarSale(any(CarSale.class), eq(new int[] {1, 2, 3}));
    }

    @Test
    void addCarSaleSubmitError() throws Exception {
        int[] carFeatures = new int[] {1, 2};
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(424);
        exceptionJSONResponse.setMessage("there is wrong references in your car sale");
        HttpClientErrorException exception = HttpClientErrorException.create(HttpStatus.FAILED_DEPENDENCY,
                "424", null, objectMapper.writeValueAsBytes(exceptionJSONResponse), null);
        doThrow(exception).when(carSaleProvider).addCarSale(any(CarSale.class), eq(carFeatures));
        mockMvc.perform(post("/carSale/add")
                .param("carFeatureList", "1", "2")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new CarSale())))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", exceptionJSONResponse.getStatus()))
                .andExpect(model().attribute("errorMsg", exceptionJSONResponse.getMessage()));
        verify(carSaleProvider).addCarSale(any(CarSale.class), eq(carFeatures));
    }

    @Test
    void updateCarSale() throws Exception {
        int carSaleId = 2;
        String requestUrl = "/carSale/carMakeId=2";
        CarSale carSale = new CarSale(carSaleId);
        carSale.setCar(new Car()).getCar().setCarFeatureList(listCarFeatures);
        when(carSaleProvider.getCarSale(carSaleId)).thenReturn(carSale);
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMakes);
        when(fuelTypeProvider.getFuelTypes()).thenReturn(listFuelTypes);
        when(carFeatureProvider.getCarFeatures()).thenReturn(listCarFeatures);
        mockMvc.perform(get("/carSale/{carSaleId}/update", carSaleId)
                .param("requestUrl", requestUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("updateCarSale"))
                .andExpect(model().attribute("listCarMakes", listCarMakes))
                .andExpect(model().attribute("listFuelTypes", listFuelTypes))
                .andExpect(model().attribute("requestUrl", requestUrl))
                .andExpect(model().attribute("selectedCarFeatures", Arrays.asList(0, 1)));
    }

    @Test
    void updateCarSaleSubmit() throws Exception {
        String requestUrl = "/carSale/carMakeId=2";
        mockMvc.perform(post("/carSale/update")
                .param("requestUrl", requestUrl)
                .param("carFeatureList", "1", "2", "3")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new CarSale())))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(requestUrl));
        verify(carSaleProvider).updateCarSale(any(CarSale.class), eq(new int[] {1, 2, 3}));
    }

    @Test
    void updateCarSaleSubmitError() throws Exception {
        int[] carFeatures = new int[] {1, 2, 3};
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(424);
        exceptionJSONResponse.setMessage("there is wrong references in your car sale");
        HttpClientErrorException exception = HttpClientErrorException.create(HttpStatus.FAILED_DEPENDENCY,
                "424", null, objectMapper.writeValueAsBytes(exceptionJSONResponse), null);
        doThrow(exception).when(carSaleProvider).updateCarSale(any(CarSale.class), eq(carFeatures));
        mockMvc.perform(post("/carSale/update")
                        .param("requestUrl", "/carSale")
                        .param("carFeatureList", "1", "2", "3")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new CarSale())))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", exceptionJSONResponse.getStatus()))
                .andExpect(model().attribute("errorMsg", exceptionJSONResponse.getMessage()));
        verify(carSaleProvider).updateCarSale(any(CarSale.class), eq(carFeatures));
    }

    @Test
    void deleteCarSale() throws Exception {
        int carSaleId = 2;
        mockMvc.perform(get("/carSale/{carSaleId}/delete", carSaleId)
                .param("requestUrl", "http://localhost:8099/carSale?carMakeId=1***carModelId=1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/carSale?carMakeId=1&carModelId=1"));
        verify(carSaleProvider).deleteCarSale(carSaleId);
    }

    @Test
    void deleteNotExistCarSale() throws Exception {
        int carSaleId = 22;
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(404);
        exceptionJSONResponse.setMessage("there is not car sale with id = " + carSaleId);
        HttpClientErrorException exception = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "404",
                null, objectMapper.writeValueAsBytes(exceptionJSONResponse), null);
        doThrow(exception).when(carSaleProvider).deleteCarSale(carSaleId);
        mockMvc.perform(get("/carSale/{carSaleId}/delete", carSaleId)
                .param("requestUrl", "http://localhost:8099/carSale?carMakeId=1***carModelId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", exceptionJSONResponse.getStatus()))
                .andExpect(model().attribute("errorMsg", exceptionJSONResponse.getMessage()));
        verify(carSaleProvider).deleteCarSale(carSaleId);
    }
}