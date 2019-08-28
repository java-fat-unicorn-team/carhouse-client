package com.carhouse.controller;

import com.carhouse.model.Car;
import com.carhouse.model.CarSale;
import com.carhouse.model.User;
import com.carhouse.provider.CarSaleProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementControllerTest {

    @Mock
    private CarSaleProvider carSaleProvider;

    @InjectMocks
    private AdvertisementController advertisementController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(advertisementController).build();
    }

    @Test
    void getAdvertisement() throws Exception {
        Integer carSaleId = 1;
        CarSale carSale = new CarSale()
                .setCarSaleId(carSaleId)
                .setPrice(new BigDecimal(23300))
                .setCar(new Car(2))
                .setUser(new User(1));
        when(carSaleProvider.getCarSale(carSaleId)).thenReturn(carSale);
        mockMvc.perform(get("/advertisement/{advertisementId}", carSaleId))
                .andExpect(status().isOk())
                .andExpect(view().name("advertisement"))
                .andExpect(model().attribute("carSale", carSale));
    }
}
