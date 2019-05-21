package com.vst.bridge.admin.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
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
import com.vst.bridge.rest.response.vo.CompanyVO;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.rest.response.vo.page.PaginationVO;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * REST web-services endpoints for /companies/* URLs.
 **/

@RestController
@RequestMapping(value="/companies")
@io.swagger.annotations.Api(value = "/Companies")
public class Companies {

	@Context
	UriInfo uriInfo;
	
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	/**
	 *  Get all Companies.
	 * 
	 * Authenticated: yes
	 * 
	 * @param sessionId
	 * @param httpRequest
	 * @return
	 */
	
	@RequestMapping(method=RequestMethod.GET)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getCompanies(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest httpRequest,HttpServletResponse response,
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
		PaginationVO paginationVo = new PaginationVO(page,limit,null,orderby,order,search,null,null);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_COMPANIES, PortalPermissionType.admin, sessionId, paginationVo, httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getCompaniesForId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			 HttpServletRequest httpRequest,@PathVariable(ApplicationConstants.REST_PARAM_ID) Integer companyId,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_COMPANIES, PortalPermissionType.admin, sessionId, companyId, httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> createCompany(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody CompanyVO companyVO,  HttpServletRequest httpRequest,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_COMPANIES, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, companyVO, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateCompany(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody CompanyVO companyVO,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int groupId, HttpServletRequest httpRequest,HttpServletResponse response) {
		HashMap<String, Object> putMap = new HashMap<String, Object>();
		putMap.put(ApplicationConstants.PUT_REQUEST_ID, groupId);
		putMap.put(ApplicationConstants.PUT_REQUEST_OBJECT, companyVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_COMPANIES, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId,putMap, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/apikey")
	@io.swagger.annotations.ApiOperation(value = "Check Company",notes = "Check Company")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> checkCompany(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			 HttpServletRequest httpRequest,@RequestBody String apiKey,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.CHECK_COMPANY, PortalPermissionType.admin, sessionId, apiKey, httpRequest,response,uriInfo);
	}
}
