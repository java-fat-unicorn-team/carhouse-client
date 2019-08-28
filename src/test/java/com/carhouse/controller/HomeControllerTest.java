package com.carhouse.controller;

import com.carhouse.model.CarMake;
import com.carhouse.provider.CarMakeProvider;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private CarMakeProvider carMakeProvider;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    void firstPage() throws Exception {
        List<CarMake> listCarMake = new ArrayList<>() {{
            add(new CarMake(0, "Bentley"));
            add(new CarMake(1, "BMW"));
        }};
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMake);
        mockMvc.perform(get("/homePage"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"))
                .andExpect(model().attribute("listCarMakes", listCarMake));
    }
}