package com.task.demo.controllers;

import com.task.demo.models.Exrate;
import com.task.demo.services.MyCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/exrates")
public class MyCurrencyController {

    @Autowired
    private final MyCurrencyService myCurrencyService;

    public MyCurrencyController(MyCurrencyService myCurrencyService) {
        this.myCurrencyService = myCurrencyService;
    }




    @GetMapping("/{curr_from}")
    public List<Exrate> getConvertationByCurrencyFrom(@PathVariable("curr_from") String curr_from) {
        return myCurrencyService.getConvertationByCurrencyFrom(curr_from.toUpperCase());

    }

    @GetMapping("/{curr_from}/to/{curr_to}")
    public Exrate getConvertationByCurrencyFrom(@PathVariable("curr_from") String curr_from, @PathVariable("curr_to") String curr_to) {
        return myCurrencyService.getConvertationByCurrencyFromAndCurrencyTo(curr_from.toUpperCase(),curr_to.toUpperCase());

    }
}
