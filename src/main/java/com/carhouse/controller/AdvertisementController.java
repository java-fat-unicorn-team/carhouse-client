package com.carhouse.controller;

import com.carhouse.provider.CarSaleProvider;
import com.carhouse.provider.CommentProvider;
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
    private CommentProvider commentProvider;

    /**
     * Instantiates a new Advertisement controller.
     *
     * @param carSaleProvider the car sale provider
     * @param commentProvider the comment provider
     */
    @Autowired
    public AdvertisementController(final CarSaleProvider carSaleProvider,
                                   final CommentProvider commentProvider) {
        this.carSaleProvider = carSaleProvider;
        this.commentProvider = commentProvider;
    }

    /**
     * Gets advertisement by id.
     *
     * @param advertisementId the advertisement id
     * @param model           the model
     * @return the advertisement
     */
    @GetMapping("/advertisement/{advertisementId}")
    public String getAdvertisement(@PathVariable final Integer advertisementId, final Model model) {
        model.addAttribute("carSale", carSaleProvider.getCarSale(advertisementId));
        model.addAttribute("comments", commentProvider.getComments(advertisementId));
        return "advertisement";
    }
}
