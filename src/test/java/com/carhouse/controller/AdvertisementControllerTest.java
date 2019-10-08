package com.carhouse.controller;

import com.carhouse.handler.RestTemplateResponseErrorHandler;
import com.carhouse.model.Car;
import com.carhouse.model.CarSale;
import com.carhouse.model.Comment;
import com.carhouse.model.User;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.CarSaleProvider;
import com.carhouse.provider.CommentProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementControllerTest {

    @Mock
    private CarSaleProvider carSaleProvider;
    @Mock
    private CommentProvider commentProvider;

    @InjectMocks
    private AdvertisementController advertisementController;

    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(advertisementController)
                .setControllerAdvice(new RestTemplateResponseErrorHandler(objectMapper))
                .build();
    }

    @Test
    void getAdvertisement() throws Exception {
        int carSaleId = 1;
        CarSale carSale = new CarSale()
                .setCarSaleId(carSaleId)
                .setPrice(new BigDecimal(23300))
                .setCar(new Car(2))
                .setUser(new User(1));
        List<Comment> commentList = new ArrayList<>() {{
            add(new Comment().setCommentId(1).setUserName("Vadim").setComment("Great"));
            add(new Comment().setCommentId(2).setUserName("Petya").setComment("Cool"));
        }};
        when(carSaleProvider.getCarSale(carSaleId)).thenReturn(carSale);
        when(commentProvider.getComments(carSaleId)).thenReturn(commentList);
        mockMvc.perform(get("/advertisement/{advertisementId}", carSaleId))
                .andExpect(status().isOk())
                .andExpect(view().name("advertisement"))
                .andExpect(model().attribute("carSale", carSale))
                .andExpect(model().attribute("comments", commentList));
        verify(carSaleProvider).getCarSale(carSaleId);
        verify(commentProvider).getComments(carSaleId);
    }

    @Test
    void getNotExistAdvertisement() throws Exception {
        int carSaleId = 21;
        String errorMassage = "there is not car sale with id = " + carSaleId;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(httpStatus.value());
        exceptionJSONResponse.setMessage(errorMassage);
        exceptionJSONResponse.setPath("/advertisement/" + carSaleId);
        HttpClientErrorException exception = HttpClientErrorException.create(httpStatus,
                String.valueOf(httpStatus.value()), null, objectMapper.writeValueAsBytes(exceptionJSONResponse),
                null);
        when(carSaleProvider.getCarSale(carSaleId)).thenThrow(exception);
        mockMvc.perform(get("/advertisement/{advertisementId}", carSaleId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new Comment())))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsg", errorMassage));
        verify(carSaleProvider).getCarSale(carSaleId);
    }
}
