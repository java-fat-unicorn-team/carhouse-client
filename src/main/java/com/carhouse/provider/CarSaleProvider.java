package com.carhouse.provider;

import com.carhouse.model.CarSale;
import com.carhouse.model.dto.CarSaleDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The interface for car sale data provider.
 */
public interface CarSaleProvider {

    /**
     * Gets list user car sale.
     *
     * @param token token to authenticate user
     * @return the list car sale
     */
    List<CarSaleDto> getListUserCarSale(String token);

    /**
     * Gets list car sale.
     *
     * @param requestParams the request params
     * @return the list car sale
     */
    List<CarSaleDto> getListCarSale(Map<String, String> requestParams);


    /**
     * Gets last five car sales.
     *
     * @return the car sales
     */
    List<CarSaleDto> getListLastFiveCarSales();

    /**
     * Gets car sale.
     * Return car sale advertisement with selected id
     *
     * @param carSaleId the car sale id
     * @return the car sale
     */
    CarSale getCarSale(Integer carSaleId);

    /**
     * Gets car sale.
     * Return car sale advertisement with selected id
     *
     * @param carSaleId the car sale id
     * @param token     the token
     * @return the car sale
     */
    CarSale getCarSaleAuthorized(Integer carSaleId, String token);

    /**
     * Add new car sale advertisement.
     * Take car features id and set them to the car sale object
     * Gets selected image from multipartFile object
     *
     * @param carSale       object from form
     * @param multipartFile the multipart file
     * @param carFeatures   list of selected car feature's id
     * @param token         the token
     * @return the generated car sale id
     * @throws IOException the io exception if can't read bytes from multipartFile object
     */
    Integer addCarSale(CarSale carSale, MultipartFile multipartFile, int[] carFeatures, String token)
            throws IOException;

    /**
     * Update car sale.
     * Take car features id and set them to the car sale object
     * Gets selected image from multipartFile object
     *
     * @param carSale       the car sale
     * @param multipartFile the multipart file
     * @param carFeatures   the car features
     * @param token         the token
     * @throws IOException the io exception if can't read bytes from multipartFile object
     */
    void updateCarSale(CarSale carSale, MultipartFile multipartFile, int[] carFeatures, String token)
            throws IOException;

    /**
     * Delete car sale.
     *
     * @param carSaleId the car sale id
     * @param token     the token
     */
    void deleteCarSale(int carSaleId, String token);
}
