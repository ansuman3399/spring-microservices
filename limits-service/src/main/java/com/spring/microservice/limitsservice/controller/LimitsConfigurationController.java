package com.spring.microservice.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.spring.microservice.limitsservice.config.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservice.limitsservice.bean.LimitConfiguration;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfiguration() {
		return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
}
