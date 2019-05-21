package com.vst.bridge.admin.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.AuthenticatedRestAction;
import com.vst.bridge.rest.config.PortalPermissionType;
import com.vst.bridge.rest.input.vo.AdminUserProfileVO;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.rest.response.vo.page.BridgePaginationVo;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * REST web-services end points for /users/* URLs.
 **/
@RestController
@RequestMapping(value="/users")
@io.swagger.annotations.Api(value = "/Users")
public class Users {

	
	UriInfo uriInfo;
	
	Request request;
	
//	private static Logger log = LogManager.getLogger(User.class);
		
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	@RequestMapping(method=RequestMethod.GET)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")}) 
	public ResponseEntity<RestResponse> getUsersList(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, HttpServletRequest httpRequest,HttpServletResponse response,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_PAGE,required=false) Integer page,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_LIMIT,required=false) Integer limit,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDERBY,required=false) String orderby, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDER,required=false) String order, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH,required=false) String search) {
		if(search!=null && StringUtils.isNotEmpty(search)){
			try {				
				search = new String(search.getBytes("ISO-8859-1"), "UTF-8");				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BridgePaginationVo bridgePaginationVo = new BridgePaginationVo(null,null,page,limit,null,orderby,order,search,null,null);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_USERS, PortalPermissionType.admin, sessionId,bridgePaginationVo ,httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")}) 
	public ResponseEntity<RestResponse> getUsersForId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@PathVariable(ApplicationConstants.REST_PARAM_ID) Integer adminId, HttpServletRequest httpRequest,HttpServletResponse response) {
		Map<String,Object> putMap = new HashMap<String,Object>();
		putMap.put(ApplicationConstants.PUT_REQUEST_ID, adminId);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_USER, PortalPermissionType.admin, sessionId,putMap,httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")}) 
	public ResponseEntity<RestResponse> createNewUser(@RequestBody AdminUserProfileVO adminUserProfileVO, HttpServletRequest httpRequest, HttpServletResponse response,
			@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId){
		return applicationServiceHandler.process(AuthenticatedRestAction.ADD_USER, PortalPermissionType.admin, sessionId, adminUserProfileVO, httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")}) 
	public ResponseEntity<RestResponse> updateUser(@RequestBody AdminUserProfileVO adminUserProfileVO, HttpServletRequest httpRequest, HttpServletResponse response,
			@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@PathVariable(ApplicationConstants.REST_PARAM_ID) Integer adminId){
		Map<String,Object> putMap = new HashMap<String,Object>();
		putMap.put(ApplicationConstants.PUT_REQUEST_ID, adminId);
		putMap.put(ApplicationConstants.PUT_REQUEST_OBJECT, adminUserProfileVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.UPDATE_USER, PortalPermissionType.admin, sessionId, putMap, httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> deleteUser(HttpServletRequest httpRequest,HttpServletResponse response,@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  @PathVariable(ApplicationConstants.REST_PARAM_ID) int adminId) {
		Map<String,Object> putMap = new HashMap<String,Object>();
		putMap.put(ApplicationConstants.PUT_REQUEST_ID, adminId);		
	 	return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_USER, PortalPermissionType.admin, sessionId, putMap, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/labels")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getLabels( HttpServletRequest request,HttpServletResponse response,@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_ADMINS_LABELS, PortalPermissionType.admin, sessionId,null,request,response,uriInfo);
	}
	
}
