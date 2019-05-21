package com.vst.bridge.admin.swagger.config;

import org.springframework.web.util.UriComponentsBuilder;

import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.vst.bridge.util.constant.ApplicationConstants;
 
public class ApiPathProvider extends SwaggerPathProvider {


//    private ServletContext servletContext;

	@Override
	protected String applicationPath() {
		return null;
	}

	@Override
	protected String getDocumentationPath() {
		 return UriComponentsBuilder
	                .fromHttpUrl(getAppBasePath())
	                .pathSegment("api-docs/")
	                .build()
	                .toString();
	}
	
	public String getAppBasePath() {
        return UriComponentsBuilder
                .fromHttpUrl(ApplicationConstants.SWAGGER_DOCUMENT_APPLICATION_BASE_URL)
                .path(getApplicationContextPath())
                .build()
                .toString();
    }
	
	 private String getApplicationContextPath(){
	    	return "/api";
	    }
}
