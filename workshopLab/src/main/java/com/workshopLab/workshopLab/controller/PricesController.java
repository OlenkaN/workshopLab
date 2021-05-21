package com.workshopLab.workshopLab.controller;


import com.workshopLab.workshopLab.model.Prices;
import com.workshopLab.workshopLab.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PricesController {
    @Autowired
    PricesRepository pricesRepository;

    @GetMapping("/prices")
    public Iterable<Prices> viewTasksPage(Model model) {
        Iterable<Prices> price = pricesRepository.findAll();
        model.addAttribute("prices", price);
        ;
        return price;
    }

    @PostMapping("/prices/add")
    public ResponseEntity<Prices> addPrice(@RequestBody @Valid PriceRequest priceRequest) {

        Prices prices = new Prices();
        prices.setName(priceRequest.getName());
        prices.setPrice(priceRequest.getPrice());
        prices.setDescription(priceRequest.getDescription());
        pricesRepository.save(prices);
        return ResponseEntity.ok(prices);


    }

    @PostMapping("/prices/update/{id}")
    public ResponseEntity<Prices> updatePrice(@RequestBody @Valid PriceRequest priceRequest, @PathVariable(value = "id") long id) {
        Prices prices = pricesRepository.findById(id).orElseThrow();
        prices.setName(priceRequest.getName());
        prices.setPrice(priceRequest.getPrice());
        prices.setDescription(priceRequest.getDescription());
        pricesRepository.save(prices);
        return ResponseEntity.ok(prices);
    }

    @PostMapping("/prices/delete/{id}")
    public ResponseEntity<Iterable<Prices>> deletePrice(@PathVariable(value = "id") long id,Model model) {

        Prices prices = pricesRepository.findById(id).orElseThrow();
        pricesRepository.deleteById(id);
        Iterable<Prices> price = pricesRepository.findAll();
        model.addAttribute("prices", price);
        return ResponseEntity.ok(price);

    }
}
