package com.vst.bridge.admin.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.AuthenticatedRestAction;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value="/logos")
@io.swagger.annotations.Api(value = "/Logos")
public class LogoUpload {


	@Context
	UriInfo uriInfo;


	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	@RequestMapping(method=RequestMethod.POST,consumes="multipart/form-data")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> doUpload(HttpServletRequest httpRequest, @CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			@ApiParam(value = "Upload image")@RequestParam(value="file") MultipartFile uploadedInputStream, /*@RequestPart("file") FormDataContentDisposition fileDetail,*/HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.UPLOAD_LOGO_FILE_DETAILS, uploadedInputStream);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_LOGOS, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, params, httpRequest,response, uriInfo);
	}
}
