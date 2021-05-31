package com.spring.microservice.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.microservice.currencyconversionservice.CurrencyExchangeServiceProxy;
import com.spring.microservice.currencyconversionservice.bean.CurrencyConversionBean;

@RestController
public class CurrencyConversionController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

	@Autowired
	private Environment environment;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);

		CurrencyConversionBean response = responseEntity.getBody();

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				response.getConversionMultiple().multiply(quantity),
				Integer.parseInt(environment.getProperty("local.server.port")));

	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
		
		logger.info("{}",response);
		
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				response.getConversionMultiple().multiply(quantity),
				Integer.parseInt(environment.getProperty("local.server.port")));

	}
}
