package com.samples.currencycalculateservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyCalculationController {

    @Autowired
    private CurrencyExchangeServiceClient client;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyCalculationBean convertCurrency(@PathVariable String from, @PathVariable String to,
                                                   @PathVariable BigDecimal quantity) {
// Feign - Problem 1
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyCalculationBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8101/currency-exchange/from/{from}/to/{to}", CurrencyCalculationBean.class,
                uriVariables);

        CurrencyCalculationBean response = responseEntity.getBody();


//        new RestTemplate().getForEntity("http://localhost:8102//currency-converter/from/{from}/to/{to}/quantity/20",CurrencyCalculationBean.class);
//        return new CurrencyCalculationBean(1L, from, to, BigDecimal.ONE, quantity,
//                quantity, 8002);
        return new CurrencyCalculationBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());    }



    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyCalculationBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
                                                       @PathVariable BigDecimal quantity) {

        CurrencyCalculationBean response = client.retrieveExchangeValue(from, to);


        return new CurrencyCalculationBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

}
