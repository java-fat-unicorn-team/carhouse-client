package com.carhouse.controller;

import com.carhouse.handler.RestTemplateResponseErrorHandler;
import com.carhouse.model.*;
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

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private CarCharacteristicsProvider carCharacteristicsProvider;
    @InjectMocks
    private CarSaleController carSaleController;

    private static List<CarSaleDto> listCarSales;
    private static List<CarMake> listCarMakes;
    private static CarCharacteristicsDto carCharacteristicsDto;
    private ObjectMapper objectMapper = new ObjectMapper();
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
        List<String> errorMassage = Collections.singletonList("there is not car make with id = " + carMakeId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        when(carModelProvider.getCarModels(carMakeId)).thenThrow(createException(httpStatus, errorMassage));
        mockMvc.perform(get("/carSale/?carMakeId={carMakeId}", carMakeId))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
    }

    @Test
    void getAddCarSaleForm() throws Exception {
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(get("/carSale/addForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCarSale"))
                .andExpect(model().attribute("carCharacteristics", carCharacteristicsDto));
    }

    @Test
    void addCarSaleSubmit() throws Exception {
        mockMvc.perform(post("/carSale/add")
                .param("carFeatureList", "1", "2", "3")
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/carSale"));
        verify(carSaleProvider).addCarSale(any(CarSale.class), any(), eq(new int[]{1, 2, 3}));
    }

    @Test
    void addCarSaleSubmitError() throws Exception {
        int[] carFeatures = new int[]{1, 2};
        List<String> errorMassage = Collections.singletonList("there is wrong references in your car sale");
        HttpStatus httpStatus = HttpStatus.FAILED_DEPENDENCY;
        doThrow(createException(httpStatus, errorMassage)).when(carSaleProvider).addCarSale(any(CarSale.class),
                any(), eq(carFeatures));
        mockMvc.perform(post("/carSale/add")
                .param("carFeatureList", "1", "2")
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(carSaleProvider).addCarSale(any(CarSale.class), any(), eq(carFeatures));
    }

    @Test
    void addCarSaleValidationError() throws Exception {
        CarSale notValidCarSale = carSale;
        notValidCarSale.setPrice(BigDecimal.valueOf(-123)).getCar().setMileage(-3214).setFuelType(null);
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(post("/carSale/add")
                .param("carFeatureList", "1", "2", "3")
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
        String requestUrl = "/carSale/carMakeId=2";
        when(carSaleProvider.getCarSale(carSaleId)).thenReturn(carSale);
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(get("/carSale/{carSaleId}/updateForm", carSaleId)
                .param("requestUrl", requestUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("updateCarSale"))
                .andExpect(model().attribute("requestUrl", requestUrl))
                .andExpect(model().attribute("selectedCarFeatures", Arrays.asList(0, 1)))
                .andExpect(model().attribute("carCharacteristics", carCharacteristicsDto));
    }

    @Test
    void updateCarSaleSubmit() throws Exception {
        String requestUrl = "/carSale?carMakeId=2";
        mockMvc.perform(post("/carSale/" + 2)
                .param("requestUrl", requestUrl)
                .param("carFeatureList", "1", "2", "3")
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(requestUrl));
        verify(carSaleProvider).updateCarSale(any(CarSale.class), any(), eq(new int[]{1, 2, 3}));
    }

    @Test
    void updateCarSaleSubmitError() throws Exception {
        int[] carFeatures = new int[]{1, 2, 3};
        List<String> errorMassage = Collections.singletonList("there is wrong references in your car sale");
        HttpStatus httpStatus = HttpStatus.FAILED_DEPENDENCY;
        doThrow(createException(httpStatus, errorMassage)).when(carSaleProvider).updateCarSale(any(CarSale.class),
                any(), eq(carFeatures));
        mockMvc.perform(post("/carSale/" + 2)
                .param("requestUrl", "/carSale")
                .param("carFeatureList", "1", "2", "3")
                .flashAttr("carSale", carSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(new CarSale())))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(carSaleProvider).updateCarSale(any(CarSale.class), any(), eq(carFeatures));
    }

    @Test
    void updateCarSaleValidationError() throws Exception {
        CarSale notValidCarSale = carSale;
        notValidCarSale.setPrice(BigDecimal.valueOf(-123)).getCar().setMileage(-3214).setFuelType(null);
        when(carCharacteristicsProvider.getCarCharacteristicsDto()).thenReturn(carCharacteristicsDto);
        mockMvc.perform(post("/carSale/3")
                .param("carFeatureList", "1", "2", "3")
                .param("requestUrl", "carSale?carMakeId=2")
                .flashAttr("carSale", notValidCarSale)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute("carSale", notValidCarSale))
                .andExpect(model().attribute("requestUrl", "carSale?carMakeId=2"))
                .andExpect(model().attribute("selectedCarFeatures", new int[]{1, 2, 3}))
                .andExpect(model().attribute("carCharacteristics", carCharacteristicsDto))
                .andExpect(view().name("updateCarSale"));
    }

    @Test
    void deleteCarSale() throws Exception {
        int carSaleId = 2;
        String requestUrl = "/carSale?carMakeId=1&carModelId=1";
        mockMvc.perform(get("/carSale/{carSaleId}/delete", carSaleId)
                .param("requestUrl", requestUrl))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(requestUrl));
        verify(carSaleProvider).deleteCarSale(carSaleId);
    }

    @Test
    void deleteNotExistCarSale() throws Exception {
        int carSaleId = 22;
        String requestUrl = "/carSale?carMakeId=1&carModelId=1";
        List<String> errorMassage = Collections.singletonList("there is not car sale with id = " + carSaleId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        doThrow(createException(httpStatus, errorMassage)).when(carSaleProvider).deleteCarSale(carSaleId);
        mockMvc.perform(get("/carSale/{carSaleId}/delete", carSaleId)
                .param("requestUrl", requestUrl))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(carSaleProvider).deleteCarSale(carSaleId);
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