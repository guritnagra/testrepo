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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.AuthenticatedRestAction;
import com.vst.bridge.rest.config.PortalPermissionType;
import com.vst.bridge.rest.response.vo.GroupVO;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiResponse;

/**
 * UNUSED. REST web-services endpoints for /groups/* URLs.
 **/
@RestController
@RequestMapping(value="/groups")
@io.swagger.annotations.Api(value = "/Groups")
public class Groups {
	@Context
	UriInfo uriInfo;

	
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	

	
	/**
	 * Creates a new Group.
	 * 
	 * Authenticated: yes
	 *
	 * @response.representation.200.mediaType application/json
	 * @response.representation.200.doc  Returns whether successful
	 * @response.representation.200.example {
			"code" : "OK",
			"text" : "Login successful"
		}
		
		Request Example:
		<pre>
		{
			"name":"groupName"
		}
		</pre>
	 * @response.representation.500.doc  An unexpected error occurred
	 * @response.representation.500.mediaType application/json
	 * @param sessionId session id
	 * @param request http request
	 */
	@RequestMapping(method=RequestMethod.POST)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> createGroup(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody GroupVO groupVO, HttpServletRequest httpRequest,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_GROUPS, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, groupVO, httpRequest,response, uriInfo);
	}
	
	/**
	 * Get all Groups.
	 * 
	 * Authenticated: yes
	 *
	 * @response.representation.200.doc  Respond with bridge configuration elements
	 * @response.representation.200.mediaType application/json
	 * @response.representation.200.example {
			"code" : "OK",
			"data" : {
				{	"id":7,
					"name":"groupName"
				},
				{	"id":8,
					"name":"group8Name"
				}
			},
			"text" : "OK"
		}
	 * @response.representation.500.doc  An unexpected error occurred
	 * @response.representation.500.mediaType application/json
	 **/
	@RequestMapping(method=RequestMethod.GET)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getGroups(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, HttpServletRequest httpRequest,HttpServletResponse response) {
		return  applicationServiceHandler.process(AuthenticatedRestAction.GET_GROUPS, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, null, httpRequest,response,uriInfo);
	}
	
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateGroup(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody GroupVO groupVO, 
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int groupId,HttpServletRequest httpRequest,HttpServletResponse response) {
		Map<String, Object> putMap = new HashMap<String, Object>();
		putMap.put(ApplicationConstants.PUT_REQUEST_ID, groupId);
		putMap.put(ApplicationConstants.PUT_REQUEST_OBJECT, groupVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_GROUPS, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, putMap, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/companies")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getCompaniesForGroupId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			HttpServletRequest httpRequest,@PathVariable(ApplicationConstants.REST_PARAM_ID) Integer groupId,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_COMPANIES_FOR_GROUP_ID, PortalPermissionType.admin, sessionId, groupId, httpRequest,response,uriInfo);
	}
}
