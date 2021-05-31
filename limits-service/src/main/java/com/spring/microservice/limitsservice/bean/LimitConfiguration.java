package com.spring.microservice.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LimitConfiguration {

	private int maximum;
	private int minimum;

}
