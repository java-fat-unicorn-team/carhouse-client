package com.carhouse.controller;

import com.carhouse.model.CarMake;
import com.carhouse.model.CarSale;
import com.carhouse.model.FuelType;
import com.carhouse.provider.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AddCarSaleControllerTest {

    @Mock
    private CarSaleProvider carSaleProvider;
    @Mock
    private CarMakeProvider carMakeProvider;
    @Mock
    private FuelTypeProvider fuelTypeProvider;
    @InjectMocks
    private AddCarSaleController addCarSaleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(addCarSaleController).build();
    }

    @Test
    void addCarSale() throws Exception {
        List<CarMake> listCarMake = new ArrayList<>() {{
            add(new CarMake(0, "Bentley"));
            add(new CarMake(1, "BMW"));
        }};
        List<FuelType> listFuelType = new ArrayList<>() {{
            add(new FuelType(0, "Petrol"));
            add(new FuelType(1, "Diesel"));
        }};
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMake);
        when(fuelTypeProvider.getFuelTypes()).thenReturn(listFuelType);
        mockMvc.perform(get("/carSale/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addCarSale"))
                .andExpect(model().attribute("listCarMakes", listCarMake))
                .andExpect(model().attribute("listFuelTypes", listFuelType));
    }

    @Test
    void addCarSaleSubmit() throws Exception {
        mockMvc.perform(post("/carSale/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/homePage"));
    }
}