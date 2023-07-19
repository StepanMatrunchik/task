package com.task.demo.controllers;

import com.task.demo.models.ExchangeOperation;
import com.task.demo.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class ExchangeController {

    @Autowired
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/api/exchange")
    public Map<String,Double> doExchange(@RequestBody ExchangeOperation exchangeOperation){
        return exchangeService.doExchange(exchangeOperation);
    }

    @GetMapping("/api/analytics/exchange-report")
    public List<ExchangeOperation> getExchangeOperationsFromDateToDate(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate, @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate){
        return exchangeService.getExchangeOperationsFromDateToDate(fromDate, toDate);
    }


}
