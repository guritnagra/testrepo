package com.vst.bridge.admin.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.CookieParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.AuthenticatedRestAction;
import com.vst.bridge.rest.config.PortalPermissionType;
import com.vst.bridge.rest.config.UnAuthenticatedRestAction;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.rest.response.vo.page.BridgePaginationVo;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value="/report")
@io.swagger.annotations.Api(value = "/Report")
public class Reports {


	
	UriInfo uriInfo;

	
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/books")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")}) 
	public ResponseEntity<RestResponse> getBooksForBridgeId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathVariable(ApplicationConstants.BRIDGE_ID) int bridgeId,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_PAGE,required=false) Integer page,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_LIMIT,required=false) Integer limit,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH,required=false) String search,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH,required=false) String category) {
		
	//	PaginationVO paginationVO = new PaginationVO(page, limit, null, null, null, null, null);
		BridgePaginationVo bridgePaginationVo = new BridgePaginationVo(bridgeId, null, page, limit, null, null, null, search, null,null);
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.PAGINATION_VO, bridgePaginationVo);
		params.put(ApplicationConstants.CATEGORY_FILTER, category);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_GET_BOOK_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, request, response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/books/{"+ApplicationConstants.BRIDGE_BOOK_VBID_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBridgeForId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathVariable(ApplicationConstants.BRIDGE_ID) int bridgeId,@PathVariable(ApplicationConstants.BRIDGE_BOOK_VBID_ID) String vbid) {
		Map<String, Object> requestParms = new HashMap<String, Object>();
		requestParms.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestParms.put(ApplicationConstants.BRIDGE_BOOK_VBID_ID, vbid);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_GET_BOOK_FOR_VBID, PortalPermissionType.admin, sessionId, requestParms, request, response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/keys")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getKeysForBridgeId(@CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathVariable(ApplicationConstants.BRIDGE_ID) int bridgeId,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_PAGE,required=false) Integer page,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_LIMIT,required=false) Integer limit,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH, required=false) String search) {
		BridgePaginationVo bridgePaginationVo = new BridgePaginationVo(bridgeId, null, page, limit, null, null, null, search, null,null);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_GET_KEYS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, bridgePaginationVo, request,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/keys/{"+ApplicationConstants.KEYCODE+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getKeyForCode(@CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathVariable(ApplicationConstants.BRIDGE_ID) int bridgeId,@PathVariable(ApplicationConstants.KEYCODE) String keyCode) {
		Map<String, Object> requestParms = new HashMap<String, Object>();
		requestParms.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestParms.put(ApplicationConstants.KEYCODE, keyCode);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_GET_KEY_FOR_CODE, PortalPermissionType.admin, sessionId, requestParms, request,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/summary/logins")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getLoginSummary(@CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathVariable(ApplicationConstants.BRIDGE_ID) int bridgeId,
			@ApiParam(value="Unique project token", required = false)@RequestParam(value=ApplicationConstants.REPORT_PARAM_START_DATE,required=false) Long startDate,
			@ApiParam(value="Unique project token", required = false)@RequestParam(value=ApplicationConstants.REPORT_PARAM_END_DATE,required=false) Long endDate) {
		Map<String, Object> requestParms = new HashMap<String, Object>();
		requestParms.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestParms.put(ApplicationConstants.REPORT_PARAM_START_DATE, startDate);
		requestParms.put(ApplicationConstants.REPORT_PARAM_END_DATE, endDate);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_SUMMARY_LOGIN, PortalPermissionType.admin, sessionId, requestParms, request, response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/summary/keys")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getKeysSummary(@CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathVariable(ApplicationConstants.BRIDGE_ID) int bridgeId,
			@ApiParam(value="Unique project token", required = false)@RequestParam(value=ApplicationConstants.REPORT_PARAM_START_DATE,required=false) Long startDate,
			@ApiParam(value="Unique project token", required = false)@RequestParam(value=ApplicationConstants.REPORT_PARAM_END_DATE,required=false) Long endDate) {
		Map<String, Object> requestParms = new HashMap<String, Object>();
		requestParms.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestParms.put(ApplicationConstants.REPORT_PARAM_START_DATE, startDate);
		requestParms.put(ApplicationConstants.REPORT_PARAM_END_DATE, endDate);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_SUMMARY_KEYS, PortalPermissionType.admin, sessionId, requestParms, request,response, uriInfo);
	}
	

	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/summary/credits")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getCreditsSummary(@CookieParam(ApplicationConstants.BRIDGE_SESSIONID) String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathParam(ApplicationConstants.BRIDGE_ID) int bridgeId,
			@QueryParam(ApplicationConstants.REPORT_PARAM_START_DATE) Long startDate,
			@QueryParam(ApplicationConstants.REPORT_PARAM_END_DATE) Long endDate) {
		Map<String, Object> requestParms = new HashMap<String, Object>();
		requestParms.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestParms.put(ApplicationConstants.REPORT_PARAM_START_DATE, startDate);
		requestParms.put(ApplicationConstants.REPORT_PARAM_END_DATE, endDate);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_SUMMARY_CREDITS, PortalPermissionType.admin, sessionId, requestParms, request, response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.BRIDGE_ID+"}/summary/launch")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBookLaunchSummary(@CookieParam(ApplicationConstants.BRIDGE_SESSIONID) String sessionId,  HttpServletRequest request, HttpServletResponse response,
			@PathParam(ApplicationConstants.BRIDGE_ID) int bridgeId,
			@QueryParam(ApplicationConstants.REPORT_PARAM_START_DATE) Long startDate,
			@QueryParam(ApplicationConstants.REPORT_PARAM_END_DATE) Long endDate,
			@QueryParam(ApplicationConstants.PAGINATION_LIMIT) Integer limit) {
		Map<String, Object> requestParms = new HashMap<String, Object>();
		requestParms.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestParms.put(ApplicationConstants.REPORT_PARAM_START_DATE, startDate);
		requestParms.put(ApplicationConstants.REPORT_PARAM_END_DATE, endDate);
		requestParms.put(ApplicationConstants.PAGINATION_LIMIT, limit);
		return applicationServiceHandler.process(AuthenticatedRestAction.REPORTS_SUMMARY_LAUNCH, PortalPermissionType.admin, sessionId, requestParms, request, response,uriInfo);
	}
	@RequestMapping(method=RequestMethod.GET, value="/kpidata/json")
	public ResponseEntity<RestResponse> getKpiDataJson(@CookieParam(ApplicationConstants.BRIDGE_SESSIONID) String sessionId,HttpServletRequest request, HttpServletResponse response){
		return applicationServiceHandler.process(UnAuthenticatedRestAction.GET_KPI_DATA_JSON, PortalPermissionType.admin, sessionId, request, response, uriInfo, new HashMap<>());
	}
}
