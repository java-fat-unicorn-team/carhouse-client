package com.carhouse.controller;

import com.carhouse.model.CarMake;
import com.carhouse.model.CarModel;
import com.carhouse.model.FuelType;
import com.carhouse.model.Transmission;
import com.carhouse.model.dto.CarDto;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarMakeProvider;
import com.carhouse.provider.CarModelProvider;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carSaleController).build();
    }

    @Test
    void carSale() throws Exception {
        List<CarSaleDto> listCarSales = new ArrayList<>() {{
            add(createCarSaleDto(0, "BMW", "M5"));
            add(createCarSaleDto(1, "Mercedes", "C63AMG"));
        }};
        List<CarMake> listCarMakes = new ArrayList<>() {{
            add(new CarMake(0, "Bentley"));
            add(new CarMake(1, "BMW"));
        }};
        when(carSaleProvider.getListCarSale()).thenReturn(listCarSales);
        when(carMakeProvider.getCarMakes()).thenReturn(listCarMakes);
        mockMvc.perform(get("/carSale"))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attribute("listCarMakes", listCarMakes))
                .andExpect(model().attribute("listCarSales", listCarSales))
                .andExpect(model().attribute("listCarSaleSize", listCarSales.size()));
    }

    @Test
    void carSaleWithCarMake() throws Exception {
        int carMakeId = 2;
        List<CarSaleDto> listCarSales = new ArrayList<>() {{
            add(createCarSaleDto(0, "BMW", "M5"));
            add(createCarSaleDto(1, "Mercedes", "C63AMG"));
        }};
        List<CarModel> listCarModels = new ArrayList<>() {{
            add(new CarModel().setCarModelId(0).setCarModel("M2"));
            add(new CarModel().setCarModelId(1).setCarModel("M4"));
        }};
        CarMake carMake = new CarMake(carMakeId, "Bentley");
        when(carSaleProvider.getListCarSale()).thenReturn(listCarSales);
        when(carModelProvider.getCarModels(carMakeId)).thenReturn(listCarModels);
        when(carMakeProvider.getCarMake(carMakeId)).thenReturn(carMake);
        mockMvc.perform(get("/carSale/carMake/{carMakeId}", carMakeId))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attribute("carMake", carMake))
                .andExpect(model().attribute("listCarModels", listCarModels))
                .andExpect(model().attribute("listCarSales", listCarSales))
                .andExpect(model().attribute("listCarSaleSize", listCarSales.size()));
    }

    @Test
    void carSaleWithCarModel() throws Exception {
        int carMakeId = 2;
        int carModelId = 4;
        List<CarSaleDto> listCarSales = new ArrayList<>() {{
            add(createCarSaleDto(0, "BMW", "M5"));
            add(createCarSaleDto(1, "Mercedes", "C63AMG"));
        }};
        CarMake carMake = new CarMake(carMakeId, "Bentley");
        CarModel carModel = new CarModel().setCarModelId(carModelId).setCarModel("Continental GT");
        when(carSaleProvider.getListCarSale()).thenReturn(listCarSales);
        when(carMakeProvider.getCarMake(carMakeId)).thenReturn(carMake);
        when(carModelProvider.getCarModel(carModelId)).thenReturn(carModel);
        mockMvc.perform(get("/carSale/carMake/{carMakeId}/carModel/{carModelId}", carMakeId, carModelId))
                .andExpect(status().isOk())
                .andExpect(view().name("carSales"))
                .andExpect(model().attribute("carMake", carMake))
                .andExpect(model().attribute("carModel", carModel))
                .andExpect(model().attribute("listCarSales", listCarSales))
                .andExpect(model().attribute("listCarSaleSize", listCarSales.size()));
    }

    private CarSaleDto createCarSaleDto(final Integer carSaleId, final String carMake, final String carModel) {
        return new CarSaleDto()
                .setCarSaleId(carSaleId)
                .setPrice(new BigDecimal(30200))
                .setDate(Date.valueOf("2019-03-02"))
                .setCar(new CarDto()
                        .setYear(Date.valueOf("2017-01-01"))
                        .setMileage(140000)
                        .setFuelType(new FuelType(1, "Bensin"))
                        .setTransmission(new Transmission(0, "Manual"))
                        .setCarModel(new CarModel()
                                .setCarModel(carModel)
                                .setCarMake(new CarMake(0, carMake))
                        )
                );
    }
}