package com.workshopLab.workshopLab.controller;


import com.workshopLab.workshopLab.model.Prices;
import com.workshopLab.workshopLab.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PricesController {
    @Autowired
    PricesRepository pricesRepository;

    @GetMapping
    public List<Prices> getClients() {
        return pricesRepository.findAll();
    }

    @GetMapping("/{id}")
    public Prices getPrice(@PathVariable Long id) {
        return pricesRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity createPrice(@RequestBody @Valid PriceRequest priceRequest) throws URISyntaxException {
        Prices prices = new Prices();
        prices.setName(priceRequest.getName());
        prices.setPrice(priceRequest.getPrice());
        prices.setDescription(priceRequest.getDescription());
        Prices savedPrice = pricesRepository.save(prices);
        return ResponseEntity.created(new URI("/clients/" + savedPrice.getId())).body(savedPrice);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePrice(@PathVariable Long id,@RequestBody @Valid PriceRequest priceRequest) {
        Prices prices = pricesRepository.findById(id).orElseThrow(RuntimeException::new);
        prices.setName(priceRequest.getName());
        prices.setPrice(priceRequest.getPrice());
        prices.setDescription(priceRequest.getDescription());
        Prices currentPrice= pricesRepository.save(prices);

        return ResponseEntity.ok(currentPrice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        pricesRepository.findById(id).orElseThrow();
        pricesRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }




}
