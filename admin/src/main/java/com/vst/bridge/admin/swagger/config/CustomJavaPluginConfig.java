package com.vst.bridge.admin.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class CustomJavaPluginConfig {
		
	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.vst.bridge.admin.rest"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .pathMapping("/");
    }	
		
	 private ApiInfo apiInfo() {
		 ApiInfo apiInfo = new ApiInfo(
	              "Bridge Application admin side API",
	              "Bridge Application admin sideAPI Documentation",
	              "1.2",
	              "",
	             new Contact("", "", ""),
	              "",
	              ""
	        );
	      return apiInfo;
	    }
}