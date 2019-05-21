package com.vst.bridge.admin.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.PortalPermissionType;
import com.vst.bridge.rest.config.UnAuthenticatedRestAction;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value="/monitor")
@io.swagger.annotations.Api(value = "/Monitor")
public class Monitor {
	
	@Context
	UriInfo uriInfo;

	
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	@RequestMapping(method={RequestMethod.GET})
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> monitorConnectivity(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletRequest request,HttpServletResponse httpResponse) {
		return applicationServiceHandler.process(UnAuthenticatedRestAction.GET_MONITOR_CONNECTIVITY, PortalPermissionType.admin, sessionId, request,httpResponse,uriInfo,null);
	}

}
