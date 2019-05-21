package com.vst.bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/view/", method = { RequestMethod.POST, RequestMethod.GET })
public class ViewController {

	
	public ViewController() {
		System.out.println("View Called !");
	}
	
	@RequestMapping(value="migrate.do",method=RequestMethod.GET)
	public String getMigrationPage(){
		return "migrate";
	}
	
	@RequestMapping(value="time.do",method=RequestMethod.GET)
	public String getTimePage(){
		return "time";
	}
}
