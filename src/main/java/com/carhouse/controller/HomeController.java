package com.carhouse.controller;

import com.carhouse.consumers.CarMakeConsumer;
import com.carhouse.consumers.CarModelConsumer;
import com.carhouse.consumers.CarSaleConsumer;
import com.carhouse.consumers.WebConsumer;
import com.carhouse.model.stub.CarSaleStub;
import com.carhouse.model.stub.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Home controller.
 */
@Controller
public class HomeController {

    private WebConsumer webConsumer;
    private CarSaleConsumer carSaleConsumer;
    private CarMakeConsumer carMakeConsumer;
    private CarModelConsumer carModelConsumer;

    /**
     * Instantiates a new Home controller.
     */
    public HomeController() {
    }

    /**
     * Instantiates a new Home controller.
     *
     * @param webConsumer      the web consumer
     * @param carSaleConsumer  the car sale data provide
     * @param carMakeConsumer  the car make data provide
     * @param carModelConsumer the car model data provide
     */
    @Autowired
    public HomeController(final WebConsumer webConsumer, final CarSaleConsumer carSaleConsumer,
                          final CarMakeConsumer carMakeConsumer, final CarModelConsumer carModelConsumer) {
        this.webConsumer = webConsumer;
        this.carSaleConsumer = carSaleConsumer;
        this.carMakeConsumer = carMakeConsumer;
        this.carModelConsumer = carModelConsumer;
    }

    /**
     * Home page.
     * Search filter object is used to get selected data.
     * ListCarMakes provides list car makes to choose.
     * ListDates provides dates to choose period from-to
     *
     * @param model model
     * @return view
     */
    @GetMapping("/homePage")
    public String firstPage(final Model model) {
        model.addAttribute("searchFilter", new SearchFilter());
        model.addAttribute("listCarMakes", carMakeConsumer.getListCarMakeStub());
        model.addAttribute("listDates", webConsumer.getDates());
        return "homePage";
    }

    /**
     * Submit filter form from home page.
     * CarMake and CarModel are used to create a search tree
     *
     * @param searchFilter object is used to get selected data.
     * @param model        model
     * @return view
     */
    @PostMapping("/homePage")
    public String carSaleFilter(@ModelAttribute final SearchFilter searchFilter, final Model model) {
        if (searchFilter.getCarMakeId() >= 0) {
            model.addAttribute("carMake", carMakeConsumer.getCarMakeStub(searchFilter.getCarMakeId()));
            if (searchFilter.getCarModelId() >= 0) {
                model.addAttribute("carModel",
                        carModelConsumer.getCarModelStub(searchFilter.getCarModelId()));
            }
        }
        List<CarSaleStub> listCarSale = carSaleConsumer.getListCarSale();
        model.addAttribute("listCarSales", listCarSale);
        model.addAttribute("listCarSaleSize", listCarSale.size());
        return "carSales";
    }
}

