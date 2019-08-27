package com.carhouse.controller;

import com.carhouse.model.CarMake;
import com.carhouse.model.CarModel;
import com.carhouse.model.FuelType;
import com.carhouse.model.Transmission;
import com.carhouse.model.dto.CarDto;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.model.dto.SearchFilter;
import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarModelProvider;
import com.carhouse.provider.CarSaleProvider;
import com.carhouse.provider.WebProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private WebProvider webProvider;
    @Mock
    private CarSaleProvider carSaleProvider;
    @Mock
    private CarMakeProvider carMakeProvider;
    @Mock
    private CarModelProvider carModelProvider;

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
        List<Date> listDates = new ArrayList<>() {{
            add(Date.valueOf("2010-01-01"));
            add(Date.valueOf("2011-01-01"));
        }};
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMake);
        when(webProvider.getDates()).thenReturn(listDates);
        mockMvc.perform(get("/homePage"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"))
                .andExpect(model().attribute("listCarMakes", listCarMake))
                .andExpect(model().attribute("listDates", listDates));
    }

    @Test
    void carSaleFilter() throws Exception {
        int carMakeId = 3;
        int carModelId = 4;
        List<CarSaleDto> listCarSales = new ArrayList<>() {{
            add(createCarSaleDto(0, "BMW", "M5"));
            add(createCarSaleDto(1, "Mercedes", "C63AMG"));
        }};
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setCarMakeId(carMakeId);
        searchFilter.setCarModelId(carModelId);
        CarMake carMake = new CarMake(carMakeId, "BMW");
        CarModel carModel = new CarModel(carModelId, new CarMake(), "M4");
        when(carSaleProvider.getListCarSale()).thenReturn(listCarSales);
        when(carMakeProvider.getCarMake(carMakeId)).thenReturn(carMake);
        when(carModelProvider.getCarModel(carModelId)).thenReturn(carModel);
        mockMvc.perform(post("/homePage")
                .flashAttr("searchFilter", searchFilter))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attribute("carMake", carMake))
                .andExpect(model().attribute("carModel", carModel))
                .andExpect(model().attribute("listCarSales", listCarSales))
                .andExpect(model().attribute("listCarSaleSize", listCarSales.size()));
    }

    @Test
    void carSaleFilterWithoutCarMakeAndCarModel() throws Exception {
        List<CarSaleDto> listCarSales = new ArrayList<>() {{
            add(createCarSaleDto(0, "BMW", "M5"));
            add(createCarSaleDto(1, "Mercedes", "C63AMG"));
        }};
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setCarMakeId(-1);
        searchFilter.setCarModelId(-1);
        when(carSaleProvider.getListCarSale()).thenReturn(listCarSales);
        mockMvc.perform(post("/homePage")
                .flashAttr("searchFilter", searchFilter))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attributeDoesNotExist("carMake"))
                .andExpect(model().attributeDoesNotExist("carModel"))
                .andExpect(model().attribute("listCarSales", listCarSales))
                .andExpect(model().attribute("listCarSaleSize", listCarSales.size()));
    }

    private CarSaleDto createCarSaleDto(final Integer carSaleId, final String carMake, final String carModel) {
        return new CarSaleDto(carSaleId, new BigDecimal(30200), Date.valueOf("2019-03-02"),
                new CarDto(0, Date.valueOf("2017-01-01"), 140000,
                        new FuelType(1, "Bensin"),
                        new Transmission(0, "Manual"), new CarModel(0,
                        new CarMake(0, carMake), carModel)));
    }
}