package com.workshopLab.workshopLab.controller;



import com.workshopLab.workshopLab.model.Prices;
import com.workshopLab.workshopLab.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
public class PricesController {
    @Autowired
    PricesRepository pricesRepository;

    @GetMapping("/prices")
    public String viewTasksPage(Model model) {
        Iterable<Prices> price = pricesRepository.findAll();
        model.addAttribute("prices", price);;
        return "price_show";
    }
    @PostMapping("/prices/add")
    public String addPrice(@RequestBody @Valid PriceRequest priceRequest)
    {
        try {
            Prices prices = new Prices();
            prices.setName(priceRequest.getName());
            prices.setPrice(priceRequest.getPrice());
            prices.setDescription(priceRequest.getDescription());
            pricesRepository.save(prices);
            return " save price";
        }
        catch (Exception E)

        {
            return E.getMessage();
        }

    }
    @PostMapping("/prices/update/{id}")
    public String updatePrice(@RequestBody @Valid PriceRequest priceRequest,@PathVariable(value = "id") long id)
    {
        try {
            Prices prices = pricesRepository.findById(id).orElseThrow();
            prices.setName(priceRequest.getName());
            prices.setPrice(priceRequest.getPrice());
            prices.setDescription(priceRequest.getDescription());
            pricesRepository.save(prices);
            return " update price";
        }
        catch (Exception E)

        {
            return E.getMessage();
        }

    }
    @PostMapping("/prices/delete/{id}")
    public String deletePrice(@PathVariable(value = "id") long id)
    {
        try {
            Prices prices = pricesRepository.findById(id).orElseThrow();
            pricesRepository.deleteById(id);
            return " delete price";
        }
        catch (Exception E)

        {
            return E.getMessage();
        }

    }
}
