package com.samples.currencycalculateservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="currency-exchange-service",value="currency-exchange-service", url="localhost:8101")
//@FeignClient(name="currency-exchange-service", value="currency-exchange-service")
@RibbonClient(name="currency-exchange-service",value="currency-exchange-service")
public interface CurrencyExchangeServiceClient {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyCalculationBean retrieveExchangeValue
            (@PathVariable("from") String from, @PathVariable("to") String to);
}
