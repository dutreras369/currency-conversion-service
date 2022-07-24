package cl.paseodelasdelicias.microservices.currencyconversion.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cl.paseodelasdelicias.microservices.currencyconversion.model.CurrencyConversion;
import cl.paseodelasdelicias.microservices.currencyconversion.service.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion converterCurrency(@PathVariable String from,
			@PathVariable String to, @PathVariable BigDecimal quantity){
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVariables);
		
		CurrencyConversion response = responseEntity.getBody();
		
		return new CurrencyConversion(response.getId(), from, to, response.getConversionMultiple(),
				quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion converterCurrencyFeign(@PathVariable String from,
			@PathVariable String to, @PathVariable BigDecimal quantity){
	
		CurrencyConversion response = proxy.retireveExchangeValue(from, to);
		
		return new CurrencyConversion(response.getId(), from, to, response.getConversionMultiple(),
				quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
}
