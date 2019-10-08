package com.carhouse.controller;

import com.carhouse.model.CarSale;
import com.carhouse.provider.CarSaleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Car sale advertisement controller.
 */
@Controller
public class AdvertisementController {

    private CarSaleProvider carSaleProvider;

    /**
     * Instantiates a new Advertisement controller.
     *
     * @param carSaleProvider the car sale provider
     */
    @Autowired
    public AdvertisementController(final CarSaleProvider carSaleProvider) {
        this.carSaleProvider = carSaleProvider;
    }

    /**
     * Gets advertisement by id.
     * Convert byte array to base64 string format and set to imageUrl attribute to display image on the UI
     *
     * @param advertisementId the advertisement id
     * @param model           the model
     * @return the advertisement
     */
    @GetMapping("/advertisement/{advertisementId}")
    public String getAdvertisement(@PathVariable final Integer advertisementId, final Model model) {
        CarSale carSale = carSaleProvider.getCarSale(advertisementId);
        model.addAttribute("carSale", carSale);
        model.addAttribute("imageUrl", carSale.getImageUrl());
        return "advertisement";
    }
}
