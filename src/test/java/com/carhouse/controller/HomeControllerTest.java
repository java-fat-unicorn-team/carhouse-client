package com.carhouse.controller;

import com.carhouse.handler.RestTemplateResponseErrorHandler;
import com.carhouse.model.CarMake;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarSaleProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private CarMakeProvider carMakeProvider;
    @Mock
    private CarSaleProvider carSaleProvider;

    @InjectMocks
    private HomeController homeController;

    private static List<CarSaleDto> listCarSales;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .setControllerAdvice(new RestTemplateResponseErrorHandler(new ObjectMapper()))
                .build();
        listCarSales = new ArrayList<>() {{
            add(new CarSaleDto().setCarSaleId(0).setCarMake("BMW").setCarModel("M5"));
            add(new CarSaleDto().setCarSaleId(1).setCarMake("Mercedes").setCarModel("C63AMG"));
        }};
    }

    @Test
    void firstPage() throws Exception {
        List<CarMake> listCarMake = new ArrayList<>() {{
            add(new CarMake(0, "Bentley"));
            add(new CarMake(1, "BMW"));
        }};
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMake);
        when(carSaleProvider.getListLastFiveCarSales()).thenReturn(listCarSales);
        mockMvc.perform(get("/carhouse/homePage"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"))
                .andExpect(model().attribute("listCarMakes", listCarMake))
                .andExpect(model().attribute("lastFiveCarSales", listCarSales));
    }

    @Test
    void firstPageServerIsNotAvailable() throws Exception {
        int statusCode = 503;
        when(carMakeProvider.getCarMakes()).thenThrow(ResourceAccessException.class);
        mockMvc.perform(get("/carhouse/homePage"))
                .andExpect(status().is(statusCode))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", statusCode))
                .andExpect(model().attribute("errorMsgList",
                        Collections.singletonList("Sorry, the server is not available")));
    }
}