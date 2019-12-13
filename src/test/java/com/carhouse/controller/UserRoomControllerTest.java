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
import com.carhouse.provider.CarCharacteristicsProvider;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class UserRoomControllerTest {

    @Mock
    private CarSaleProvider carSaleProvider;
    @Mock
    private CarCharacteristicsProvider carCharacteristicsProvider;
    @InjectMocks
    private UserRoomController userRoomController;

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
        mockMvc = MockMvcBuilders.standaloneSetup(userRoomController)
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
    void carSale() {
    }

    @Test
    void getAddCarSaleForm() throws Exception {
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(post("/carhouse/user/room/carSale/addForm")
                .param("authentication", token))
                .andExpect(status().isOk())
                .andExpect(view().name("addCarSale"))
                .andExpect(model().attribute("carCharacteristics", carCharacteristicsDto));
    }

    @Test
    void getAddCarSaleFormWrongJsonError() throws Exception {
        List<String> errorMassage =
                Collections.singletonList("Sorry, Incorrect JSON obtained from the database, we are working on it");
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenThrow(RestClientException.class);
        mockMvc.perform(post("/carhouse/user/room/carSale/addForm")
                .param("authentication", token))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
    }

    @Test
    void addCarSaleSubmit() throws Exception {
        mockMvc.perform(post("/carhouse/user/room/carSale/add")
                .param("carFeatureList", "1", "2", "3")
                .param("token", token)
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("userRoom"));
        verify(carSaleProvider).addCarSale(any(CarSale.class), any(), eq(new int[]{1, 2, 3}), eq(token));
    }

    @Test
    void addCarSaleSubmitError() throws Exception {
        int[] carFeatures = new int[]{1, 2};
        List<String> errorMassage = Collections.singletonList("there is wrong references in your car sale");
        HttpStatus httpStatus = HttpStatus.FAILED_DEPENDENCY;
        doThrow(createException(httpStatus, errorMassage)).when(carSaleProvider).addCarSale(any(CarSale.class),
                any(), eq(carFeatures), eq(token));
        mockMvc.perform(post("/carhouse/user/room/carSale/add")
                .param("carFeatureList", "1", "2")
                .param("token", token)
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(carSaleProvider).addCarSale(any(CarSale.class), any(), eq(carFeatures), eq(token));
    }

    @Test
    void addCarSaleValidationError() throws Exception {
        CarSale notValidCarSale = carSale;
        notValidCarSale.setPrice(BigDecimal.valueOf(-123)).getCar().setMileage(-3214).setFuelType(null);
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(post("/carhouse/user/room/carSale/add")
                .param("carFeatureList", "1", "2", "3")
                .param("token", token)
                .flashAttr("carSale", notValidCarSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute("carSale", notValidCarSale))
                .andExpect(model().attribute("selectedCarFeatures", new int[]{1, 2, 3}))
                .andExpect(model().attribute("carCharacteristics", carCharacteristicsDto))
                .andExpect(view().name("addCarSale"));
    }

    @Test
    void getUpdateCarSaleForm() throws Exception {
        int carSaleId = 2;
        when(carSaleProvider.getCarSaleAuthorized(carSaleId, token)).thenReturn(carSale);
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(post("/carhouse/user/room/carSale/{carSaleId}/updateForm", carSaleId)
                .param("token", token))
                .andExpect(status().isOk())
                .andExpect(view().name("updateCarSale"))
                .andExpect(model().attribute("selectedCarFeatures", Arrays.asList(0, 1)))
                .andExpect(model().attribute("carCharacteristics", carCharacteristicsDto));
    }

    @Test
    void updateCarSaleSubmit() throws Exception {
        String requestUrl = "/carhouse/user/room/carSale?carMakeId=2";
        mockMvc.perform(post("/carhouse/user/room/carSale/" + 2)
                .param("carFeatureList", "1", "2", "3")
                .param("token", token)
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(view().name("userRoom"));
        verify(carSaleProvider).updateCarSale(any(CarSale.class), any(), eq(new int[]{1, 2, 3}), eq(token));
    }

    @Test
    void updateCarSaleSubmitError() throws Exception {
        int[] carFeatures = new int[]{1, 2, 3};
        List<String> errorMassage = Collections.singletonList("there is wrong references in your car sale");
        HttpStatus httpStatus = HttpStatus.FAILED_DEPENDENCY;
        doThrow(createException(httpStatus, errorMassage)).when(carSaleProvider).updateCarSale(any(CarSale.class),
                any(), eq(carFeatures), eq(token));
        mockMvc.perform(post("/carhouse/user/room/carSale/" + 2)
                .param("carFeatureList", "1", "2", "3")
                .param("token", token)
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(new CarSale())))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(carSaleProvider).updateCarSale(any(CarSale.class), any(), eq(carFeatures), eq(token));
    }

    @Test
    void updateCarSaleValidationError() throws Exception {
        CarSale notValidCarSale = carSale;
        notValidCarSale.setPrice(BigDecimal.valueOf(-123)).getCar().setMileage(-3214).setFuelType(null);
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(post("/carhouse/user/room/carSale/3")
                .param("carFeatureList", "1", "2", "3")
                .param("token", token)
                .flashAttr("carSale", notValidCarSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute("carSale", notValidCarSale))
                .andExpect(model().attribute("selectedCarFeatures", new int[]{1, 2, 3}))
                .andExpect(model().attribute("carCharacteristics", carCharacteristicsDto))
                .andExpect(view().name("updateCarSale"));
    }

    @Test
    void deleteCarSale() throws Exception {
        int carSaleId = 2;
        mockMvc.perform(get("/carhouse/user/room/carSale/{carSaleId}/delete", carSaleId)
                .param("authentication", token))
                .andExpect(status().isOk())
                .andExpect(view().name("userRoom"));
        verify(carSaleProvider).deleteCarSale(carSaleId, token);
    }

    @Test
    void deleteNotExistCarSale() throws Exception {
        int carSaleId = 22;
        List<String> errorMassage = Collections.singletonList("there is not car sale with id = " + carSaleId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        doThrow(createException(httpStatus, errorMassage)).when(carSaleProvider).deleteCarSale(carSaleId, token);
        mockMvc.perform(get("/carhouse/user/room/carSale/{carSaleId}/delete", carSaleId)
                .param("authentication", token))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(carSaleProvider).deleteCarSale(carSaleId, token);
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