package com.op_solutions.sunsmart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class SunSmart {
	
	@Value("${sunsmart.dev.url}")
	private String sunsmart;

	
	public String callSunSmart()
	{
		System.out.println("sunsmart url " + sunsmart);
		return sunsmart;
	
	}
	
}
