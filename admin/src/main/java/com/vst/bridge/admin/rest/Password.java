package com.vst.bridge.admin.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.UnAuthenticatedRestAction;
import com.vst.bridge.rest.input.vo.LoginInfoVO;
import com.vst.bridge.rest.response.vo.AdminResetPasswordVO;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiResponse;

/**
 * REST endpoints for /password/* URLs. REST web-services endpoints for
 * /password/* URLs.
 **/
@RestController
@RequestMapping(value="/password")
@io.swagger.annotations.Api(value = "/Password")
public class Password {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	/**
	 * Send Password Reset Email
	 * 
	 * Authenticated: no
	 * 
	 * Request:
	 * <pre>{ email: "theron.patrick@vpg.com", returnPath : "passwordreset.html?token=${token}" }</pre>
	 * 
	 * param: email
	 *            The new users email address (required)
	 * param: returnPath
	 *            The url sent to the end user. This path must start with / and
	 *            must contain ${token}. ex. /passwordreset.html?token=${token}
	 *            (required)
	 * 
	 * @response.representation.200.doc Request successful
	 * @response.representation.200.mediaType application/json
	 * @response.representation.400.doc Email or return_path is missing or invalid
	 * @response.representation.400.mediaType application/json
	 * @response.representation.404.doc Email not found
	 * @response.representation.404.mediaType application/json
	 * @response.representation.500.doc An unexpected error
	 * @response.representation.500.mediaType application/json
	 * @param request http request
	 **/
	@RequestMapping(method=RequestMethod.POST)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> sendPasswordReset(@RequestBody AdminResetPasswordVO resetPasswordVO, HttpServletRequest request,HttpServletResponse response) {
		return applicationServiceHandler.process(UnAuthenticatedRestAction.POST_PASSWORD, com.vst.bridge.rest.config.PortalPermissionType.admin, null, request,response,uriInfo,resetPasswordVO);
	}

	/**
	 * Reset Password
	 * 
	 * Authenticated: no
	 * 
	 * Request:
	 * <pre>{ email: "theron.patrick@vpg.com", password : "password" }</pre>

	 * 
	 * param: email The users username (required)
	 * param: password The users new password (required)
	 * 
	 * @response.representation.200.doc Request successful
	 * @response.representation.400.doc password is missing or invalid
	 * @response.representation.400.mediaType application/json
	 * @response.representation.404.doc username not found
	 * @response.representation.404.mediaType application/json
	 * @response.representation.500.doc An unexpected error
	 * @response.representation.500.mediaType application/json
	 * @response.representation.200.mediaType application/json
	 * @param sessionId session id
	 * @param request http request
	 * @param token token sent from email
	 **/
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.TOKEN+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> resetPassword(@RequestBody LoginInfoVO loginInfoVO,HttpServletRequest request,HttpServletResponse response,@PathVariable(ApplicationConstants.TOKEN) String token, @CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId) {	//	return com.vst.bridge.rest.Password.resetPassword(request, token, PortalPermissionType.admin, sessionId);
		return applicationServiceHandler.process(UnAuthenticatedRestAction.PUT_PASSWORD, com.vst.bridge.rest.config.PortalPermissionType.admin, token, request, response,uriInfo,loginInfoVO);
	}
}
