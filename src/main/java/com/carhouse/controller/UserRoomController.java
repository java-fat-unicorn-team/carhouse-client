package com.carhouse.controller;

import com.carhouse.model.CarFeature;
import com.carhouse.model.CarSale;
import com.carhouse.provider.CarCharacteristicsProvider;
import com.carhouse.provider.CarSaleProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/carhouse/user/room")
public class UserRoomController {

    private final Logger LOGGER = LogManager.getLogger(UserRoomController.class);

    private CarSaleProvider carSaleProvider;
    private CarCharacteristicsProvider carCharacteristicsProvider;

    /**
     * Instantiates a new User room controller.
     *
     * @param carSaleProvider            the car sale data provider
     * @param carCharacteristicsProvider the car characteristics provider
     */
    @Autowired
    public UserRoomController(final CarSaleProvider carSaleProvider,
                              final CarCharacteristicsProvider carCharacteristicsProvider) {
        this.carSaleProvider = carSaleProvider;
        this.carCharacteristicsProvider = carCharacteristicsProvider;
    }

    /**
     * Return page with car sale advertisement.
     * Get request parameters to filter car sale advertisements.
     * If selected all advertisements then it returns list of car makes in the top of page or
     * if selected car make then it returns list of car models in the top of page
     *
     * @param authentication the authentication
     * @param model          the model
     * @return the string
     */
    @PostMapping
    public String carSale(@RequestParam final String authentication, final Model model) {
        LOGGER.debug("method carSale with parameters");
        model.addAttribute("listCarSales", carSaleProvider.getListUserCarSale(authentication));
        return "userRoom";
    }

    /**
     * Return page to add new car sale advertisement.
     *
     * @param authentication the authentication
     * @param model          model
     * @return view add car sale form
     */
    @PostMapping("/carSale/addForm")
    public String getAddCarSaleForm(@RequestParam("authentication") final String authentication, final Model model) {
        LOGGER.debug("method getAddCarSaleForm was invoked");
        CarSale carSale = new CarSale();
        if (authentication.isEmpty()) {
            return "redirect:/carhouse/login";
        }
        model.addAttribute("carSale", carSale);
        model.addAttribute("selectedCarFeatures", new ArrayList<>());
        model.addAttribute("carCharacteristics", carCharacteristicsProvider.getCarCharacteristicsDto());
        return "addCarSale";
    }

    /**
     * Submit add car sale advertisement.
     * Take list of selected car feature's id as request param
     * Return page with list car sales
     * Gets selected file to upload as request param
     * If not file selected return empty object
     *
     * @param carSale       object is used to get entered data.
     * @param bindingResult the binding result to get form errors
     * @param featureList   the feature list
     * @param file          the file
     * @param token         the token
     * @param model         the model
     * @return view string
     * @throws IOException the io exception
     */
    @PostMapping("/carSale/add")
    public String addCarSaleSubmit(@ModelAttribute @Valid final CarSale carSale, final BindingResult bindingResult,
                                   @RequestParam(required = false, value = "carFeatureList") final int[] featureList,
                                   @RequestParam(required = false, value = "multipartFile") final MultipartFile file,
                                   @RequestParam("token") final String token, final Model model)
            throws IOException {
        LOGGER.debug("method addCarSaleSubmit with body [{}]", carSale);
        if (bindingResult.hasErrors()) {
            LOGGER.warn("add car sale form has errors");
            model.addAttribute("carCharacteristics",
                    carCharacteristicsProvider.getCarCharacteristicsDto());
            model.addAttribute("selectedCarFeatures",
                    (Objects.isNull(featureList)) ? new ArrayList<>() : featureList);
            return "addCarSale";
        }
        carSaleProvider.addCarSale(carSale, file, featureList, token);
        model.addAttribute("listCarSales", carSaleProvider.getListUserCarSale(token));
        return "userRoom";
    }

    /**
     * Return page to update new car sale advertisement.
     * Create list of selected car feature's id to select car feature
     * Gets the request url to return to the same page
     *
     * @param carSaleId the car sale id
     * @param token     the token
     * @param model     model
     * @return view string
     */
    @PostMapping("/carSale/{carSaleId}/updateForm")
    public String getUpdateCarSaleForm(@PathVariable final int carSaleId,
                                       @RequestParam("token") final String token, final Model model) {
        LOGGER.debug("method getUpdateCarSaleForm with parameter carSaleId = {}", carSaleId);
        CarSale carSale = carSaleProvider.getCarSaleAuthorized(carSaleId, token);
        model.addAttribute("carCharacteristics",
                carCharacteristicsProvider.getCarCharacteristicsDto());
        model.addAttribute("carSale", carSale);
        model.addAttribute("selectedCarFeatures",
                createSelectedCarFeatureList(carSale.getCar().getCarFeatureList()));
        return "updateCarSale";
    }

    /**
     * Submit update car sale advertisement.
     * Take list of selected car feature's id as request param
     * Return page with list car sales
     * Gets selected file to upload as request param
     * If not file selected return empty object
     *
     * @param carSale       the car sale
     * @param bindingResult the binding result to get form errors
     * @param carSaleId     the car sale id
     * @param token         the token
     * @param featureList   the feature list
     * @param file          the file
     * @param model         the model
     * @return the string
     * @throws IOException the io exception
     */
    @PostMapping("/carSale/{carSaleId}")
    public String updateCarSaleSubmit(
            @ModelAttribute @Valid final CarSale carSale, final BindingResult bindingResult,
            @PathVariable(name = "carSaleId") final int carSaleId, @RequestParam("token") final String token,
            @RequestParam(required = false, value = "carFeatureList") final int[] featureList,
            @RequestParam(required = false, value = "multipartFile") final MultipartFile file,
            final Model model) throws IOException {
        LOGGER.debug("method updateCarSaleSubmit with body [{}]", carSale);
        if (bindingResult.hasErrors()) {
            LOGGER.warn("update car sale form has errors");
            model.addAttribute("carCharacteristics",
                    carCharacteristicsProvider.getCarCharacteristicsDto());
            model.addAttribute("selectedCarFeatures",
                    (Objects.isNull(featureList)) ? new ArrayList<>() : featureList);
            return "updateCarSale";
        }
        carSale.setCarSaleId(carSaleId);
        carSaleProvider.updateCarSale(carSale, file, featureList, token);
        model.addAttribute("listCarSales", carSaleProvider.getListUserCarSale(token));
        return "userRoom";
    }

    /**
     * Delete car sale string.
     * Gets the request url to return to the same page
     *
     * @param carSaleId      the car sale id
     * @param authentication the authentication
     * @param model          the model
     * @return the string
     */
    @GetMapping("/carSale/{carSaleId}/delete")
    public String deleteCarSale(@PathVariable final Integer carSaleId,
                                @RequestParam("authentication") final String authentication, final Model model) {
        LOGGER.debug("method deleteCarSale with parameter carSaleId = {}", carSaleId);
        carSaleProvider.deleteCarSale(carSaleId, authentication);
        model.addAttribute("listCarSales", carSaleProvider.getListUserCarSale(authentication));
        return "userRoom";
    }

    /**
     * Create array of selected car features.
     * It is used to set selected car features when updating car sale advertisement
     *
     * @param carFeatureList the car feature list
     * @return id list
     */
    private List<Integer> createSelectedCarFeatureList(final List<CarFeature> carFeatureList) {
        return Optional.ofNullable(carFeatureList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .map(CarFeature::getCarFeatureId)
                .collect(Collectors.toList());
    }
}
