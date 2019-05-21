package com.vst.bridge.admin.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.AuthenticatedRestAction;
import com.vst.bridge.rest.input.vo.AdminUserProfileVO;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiResponse;

/**
 * REST web-services endpoints for /user/* URLs.
 **/
@RestController
@RequestMapping(value="/user")
@io.swagger.annotations.Api(value = "/User")
public class User {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	private static Logger loggger = LogManager.getLogger(User.class);
		
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	

	/**
	 * PUT /user
	 * 
	 * Method to update logged in admin user details.
	 * 
	 * Authenticated: YES
	 * 
	 * Example:
	 * 
	 * Request JSON
	*	 {
	*		    "firstName":"Ignacio",
	*		    "lastName":"Van Gelderen",
	*		    "email":"ignacio.vangelderen@ingramcontent.com",
	*		    "password":"password"
	*		}
	 *
	 * @response.representation.200.doc		Admin User updated successfully  
	 *  
	 */
	@RequestMapping(method=RequestMethod.PUT)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateUser(@RequestBody AdminUserProfileVO adminUserProfileVO, HttpServletRequest httpRequest, @CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletResponse response){//@FormParam("email") String email, @FormParam("password") String password, @FormParam("firstname") String firstname, @FormParam("lastname") String lastname, @FormParam("keycode") String keycode, @FormParam("questionid") String questionid, @FormParam("questionresponse") String questionresponse, @FormParam("promotionsubscription") String promotionsubscription, @FormParam("surveysubscription") String surveysubscription) {
		loggger.info("updateUser : request to update user from sessionId {0}",sessionId);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_USER, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, adminUserProfileVO, httpRequest,response,uriInfo);
	}

	
	/**
	 * GET /user
	 * 
	 * Method to get logged in admin user details.
	 * 
	 * @response.representation.200.doc		Admin User updated successfully  
	 *  
	 */
	
	@RequestMapping(method=RequestMethod.GET)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")}) 
	public ResponseEntity<RestResponse> getUser(HttpServletRequest request, @CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, HttpServletResponse response,
			HttpServletRequest httpRequest){
		loggger.info("getUser : request to get user from sessionId {0}",sessionId);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_USER, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, null,httpRequest,response,uriInfo);
	}
}
