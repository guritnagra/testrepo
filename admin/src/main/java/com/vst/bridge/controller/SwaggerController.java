package com.vst.bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/serviceclient")
public class SwaggerController {

	
	public SwaggerController() {
		System.out.println("instrance created !");
	}
	
	@RequestMapping(method={RequestMethod.GET})
	public String getSwaggerPage(){
		return "document";
	}
}
