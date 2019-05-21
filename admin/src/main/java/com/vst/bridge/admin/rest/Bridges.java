package com.vst.bridge.admin.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.AuthenticatedRestAction;
import com.vst.bridge.rest.config.PortalPermissionType;
import com.vst.bridge.rest.config.UnAuthenticatedRestAction;
import com.vst.bridge.rest.input.vo.AllowanceRequestVO;
import com.vst.bridge.rest.input.vo.BookConcurrencyLimitVO;
import com.vst.bridge.rest.input.vo.BridgeGroupBookVO;
import com.vst.bridge.rest.input.vo.BridgeGroupUserVO;
import com.vst.bridge.rest.input.vo.BridgeGroupVO;
import com.vst.bridge.rest.input.vo.KeyBatchesVO;
import com.vst.bridge.rest.input.vo.KeyGenerateRequestVO;
import com.vst.bridge.rest.input.vo.PurchaseRequestVO;
import com.vst.bridge.rest.input.vo.TokenRequestVO;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.rest.response.vo.ancillary.AncillaryAdminVO;
import com.vst.bridge.rest.response.vo.ancillary.AncillaryVO;
import com.vst.bridge.rest.response.vo.books.BridgeBookVO;
import com.vst.bridge.rest.response.vo.bridge.BridgeBooksVO;
import com.vst.bridge.rest.response.vo.bridge.BridgeInfoWithFavoriteVO;
import com.vst.bridge.rest.response.vo.bridge.IntigrationVO;
import com.vst.bridge.rest.response.vo.group.BridgeGroupAssetUserVO;
import com.vst.bridge.rest.response.vo.page.BridgePaginationVo;
import com.vst.bridge.rest.response.vo.page.PaginationVO;
import com.vst.bridge.rest.response.vo.user.CreateKeysVO;
import com.vst.bridge.util.constant.ApplicationConstants;
import com.vst.bridge.util.csv.CSVFileType;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value="/bridges")
@io.swagger.annotations.Api(value = "/Bridges")
public class Bridges {

	
	private UriInfo uriInfo;
		
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	/**
	 * Get list of bridges.
	 * 
	 * Authenticated: yes
	 * @response.representation.200.doc  Returns a summary of all available portals
	 * @response.representation.200.mediaType application/json
	 * @response.representation.200.example {
	 * 
	 * }
	 */
	@RequestMapping(method={RequestMethod.GET})
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBridges(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletRequest request,HttpServletResponse response,
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
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BRIDGES_LIST, PortalPermissionType.admin, sessionId, bridgePaginationVo, request,response, uriInfo);
	}
	
	@RequestMapping(value="{"+ApplicationConstants.REST_PARAM_ID+"}",method=RequestMethod.GET,produces="application/json")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBridgeForId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,  HttpServletRequest request,HttpServletResponse response, 
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BRIDGES_FOR_ID, PortalPermissionType.admin, sessionId, bridgeId, request, response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> createBridge(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody BridgeInfoWithFavoriteVO bridgeInfoWithFavoriteVO,HttpServletRequest request,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_BRIDGE, PortalPermissionType.admin, sessionId, bridgeInfoWithFavoriteVO, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridge(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody BridgeInfoWithFavoriteVO bridgeInfoWithFavoriteVO,HttpServletResponse response,
			 HttpServletRequest request, @PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId) {
		
		Map<String, Object> putMap = new HashMap<String, Object>();
		putMap.put(ApplicationConstants.PUT_REQUEST_ID, bridgeId);
		putMap.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgeInfoWithFavoriteVO);
		
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BRIDGE, PortalPermissionType.admin, sessionId,putMap, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> deleteBridge(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletResponse response,
			 HttpServletRequest request, @PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId) {
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_BRIDGE, PortalPermissionType.admin, sessionId, bridgeId, request,response,  uriInfo);
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/books")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBooks(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_PAGE,required=false) Integer page,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_REFRESH,required=false) Boolean refreshCatche, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_LIMIT,required=false) Integer limit,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDERBY,required=false) String orderby, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDER,required=false) String order, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH,required=false) String search,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.IS_GROUPED,required=false) Boolean isGrouped,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.CATEGORY_FILTER,required=false) String category) {
		if(search!=null && StringUtils.isNotEmpty(search)){
 			try { 				
 				search = new String(search.getBytes("ISO-8859-1"), "UTF-8"); 				
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
		
		refreshCatche= null!=refreshCatche ?refreshCatche:false;
		BridgePaginationVo bridgePaginationVo = new BridgePaginationVo(bridgeId,refreshCatche,page,limit,null,orderby,order,search,null,null);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.IS_GROUPED, isGrouped);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgePaginationVo);
		params.put(ApplicationConstants.CATEGORY_FILTER, category);
		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BOOKS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, httpRequest,response,  uriInfo);
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/books/{"+ApplicationConstants.BRIDGE_BOOK_VBID_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBooksForVbid(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, HttpServletRequest httpRequest,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@PathVariable(ApplicationConstants.BRIDGE_BOOK_VBID_ID) String vbid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_BOOK_VBID_ID, vbid);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BOOKS_FOR_VBID, PortalPermissionType.admin, sessionId, params, httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/books/{"+ApplicationConstants.BRIDGE_BOOK_VBID_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Edit asset information",notes = "Edit asset information, currently only ancillary assets")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBookForVbid(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, HttpServletRequest httpRequest,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@PathVariable(ApplicationConstants.BRIDGE_BOOK_VBID_ID) String vbid,@RequestBody BridgeBooksVO bridgeBookVO){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_BOOK_VBID_ID, vbid);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgeBookVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.EDIT_BOOK_FOR_VBID, PortalPermissionType.admin, sessionId, params, httpRequest,response,uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/books")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgeBooks(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody List<BridgeBookVO> bridgeBooksVO,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.SELECT_ALL_BOOKS,required=false) Boolean selectAllBooks,
			 HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.SELECT_ALL_BOOKS, selectAllBooks);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgeBooksVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BOOKS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/config")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBridgeConfig(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BRIDGE_CONFIG, PortalPermissionType.admin, sessionId, bridgeId, httpRequest,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/config")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgeConfig(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@RequestBody Map<String,String> requestMap,@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, requestMap);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BRIDGE_CONFIG, PortalPermissionType.admin, sessionId, params, httpRequest,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/keybatches")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getKeyBatches(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BRIDGE_KEY_BATCHES, PortalPermissionType.admin, sessionId, bridgeId, httpRequest,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="{"+ApplicationConstants.REST_PARAM_ID+"}/keybatches")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> createKeyBatches(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody KeyBatchesVO keyBatchesVO,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, keyBatchesVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_BRIDGE_KEY_BATCHES, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/integrations")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgeIntigration(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@RequestBody IntigrationVO intigrationVO,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, intigrationVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BRIDGE_INTIGRATION, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/integrations")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBridgeIntigration(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BRIDGE_INTIGRATION, PortalPermissionType.admin, sessionId, bridgeId, httpRequest,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/keybatches/{"+ApplicationConstants.BATCH_ID+"}",produces="application/plain")
	@io.swagger.annotations.ApiOperation(value = "Get Keybatches file ",notes = "To get Keybatches file with entitlements")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public void getKeyBatch(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			 HttpServletRequest httpRequest, HttpServletResponse response,@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, 
			 @PathVariable(ApplicationConstants.GET_BRIDGE_BATCH_ID) int batchId) {
		Map<String, Integer> requestparams = new HashMap<String, Integer>();
		requestparams.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestparams.put(ApplicationConstants.GET_BRIDGE_BATCH_ID, batchId);
		applicationServiceHandler.process(AuthenticatedRestAction.GET_BATCHES_FILE,PortalPermissionType.admin, sessionId, requestparams,httpRequest,response,  uriInfo);
	
			
	
	}
	@RequestMapping(method={RequestMethod.POST},value="/refresh")
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getKeyBatch(@RequestBody CreateKeysVO createKeysVO, HttpServletRequest httpRequest,HttpServletResponse response) {
		return applicationServiceHandler.process(UnAuthenticatedRestAction.REFRESH_BOOK_CACHE,PortalPermissionType.admin,null,httpRequest,response,uriInfo, createKeysVO);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups")
	@io.swagger.annotations.ApiOperation(value = "createBridgeGroup",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> createBridgeGroup(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@RequestBody BridgeGroupVO bridgeGroupVO,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgeGroupVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_BRIDGE_GROUP, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "updateBridgeGroup",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgeGroup(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, @PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,
			@RequestBody BridgeGroupVO bridgeGroupVO,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgeGroupVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BRIDGE_GROUP, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}/duplicate")
	@io.swagger.annotations.ApiOperation(value = "duplicateBridgeGroup",notes = "To create duplicate of group")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> duplicateBridgeGroup(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, @PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,
			@RequestBody String duplicateGroupName,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, duplicateGroupName);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_DUPLICATE_GROUP, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups")
	@io.swagger.annotations.ApiOperation(value = "Get bridge groups",notes = "Get list of groups for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getGroups(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_REFRESH,required=false) Boolean refreshGroups,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PAGINATION_REFRESH, refreshGroups);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_GROUPS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}/users")
	@io.swagger.annotations.ApiOperation(value = "updateGroupUsers",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgeGroupUsers(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, @PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,
			@RequestBody BridgeGroupUserVO bridgeGroupUserVO,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgeGroupUserVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BRIDGE_GROUP_USERS, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}/books")
	@io.swagger.annotations.ApiOperation(value = "updateGroupBooks",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgeGroupBooks(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, @PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,
			@RequestBody BridgeGroupBookVO bridgeGroupBookVO,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bridgeGroupBookVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BRIDGE_GROUP_BOOKS, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}/users")
	@io.swagger.annotations.ApiOperation(value = "Get bridge groups users",notes = "Get list of users from groups for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getGroupUsers(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId, HttpServletRequest httpRequest,
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
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, paginationVo);
		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_GROUP_UESRS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, httpRequest, response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/users")
	@io.swagger.annotations.ApiOperation(value = "getBridgeUsers",notes = "Get list of users for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBridgeUsers(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,HttpServletRequest httpRequest,
			@ApiParam(value = "For BC refresh", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_REFRESH,required=false) Boolean refresh,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_PAGE,required=false) Integer page,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_LIMIT,required=false) Integer limit,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDERBY,required=false) String orderby, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDER,required=false) String order, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH,required=false) String search,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.IS_GROUPED,required=false) Boolean isGrouped) {
		if(search!=null && StringUtils.isNotEmpty(search)){
 			try { 				
 				search = new String(search.getBytes("ISO-8859-1"), "UTF-8"); 				
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
		
		PaginationVO paginationVo = new PaginationVO(page,limit,null,orderby,order,search,null,null);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, paginationVo);
		params.put(ApplicationConstants.IS_GROUPED, isGrouped);
		params.put(ApplicationConstants.PAGINATION_REFRESH, refresh);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_USERS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, httpRequest, response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}/books")
	@io.swagger.annotations.ApiOperation(value = "Get bridge groups assets / books",notes = "Get list of assets / books from groups for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getGroupBook(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId, HttpServletRequest httpRequest,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_PAGE,required=false) Integer page,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_LIMIT,required=false) Integer limit,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDERBY,required=false) String orderby, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDER,required=false) String order, 
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH,required=false) String search,
			@ApiParam(value = "Unique project token", required = false)@RequestParam(value=ApplicationConstants.CATEGORY_FILTER,required=false) String category) {
		if(search!=null && StringUtils.isNotEmpty(search)){
 			try { 				
 				search = new String(search.getBytes("ISO-8859-1"), "UTF-8"); 				
 			} catch (UnsupportedEncodingException e) {
 				e.printStackTrace();
 			}
 		}
		
		BridgePaginationVo paginationVo = new BridgePaginationVo(null, null, page, limit, null, orderby, order, search, null,null);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, paginationVo);
		params.put(ApplicationConstants.CATEGORY_FILTER, category);
		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_GROUP_BOOKS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, httpRequest, response, uriInfo);		
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/bulk")
	@io.swagger.annotations.ApiOperation(value = "createBridgeGroup",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> addUsersOrAssetsToGroup(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@RequestBody BridgeGroupAssetUserVO bridgeGroupVO,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.GROUP_ASSET_USERS, bridgeGroupVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.BULK_INSERT_GROUP_ASSETS_USERS, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}/users")
	@io.swagger.annotations.ApiOperation(value = "deleteGroupUsers",notes = "Delete users from gourp for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> deleteGroupUsers(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@RequestParam(required=false) List<Integer> users,
			@RequestParam(required=false) Boolean deleteAll,
			HttpServletResponse response,HttpServletRequest request, 
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,
			@RequestParam(required=false) String search,
			@RequestParam(required=false) Boolean isGrouped) {
		if(search!=null && StringUtils.isNotEmpty(search)){
 			try { 				
 				search = new String(search.getBytes("ISO-8859-1"), "UTF-8"); 				
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.DELETE_ALL, deleteAll);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, users);
		params.put(ApplicationConstants.PAGINATION_SEARCH,search);
		params.put(ApplicationConstants.IS_GROUPED, isGrouped);
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_BRIDGE_GROUP_USERS, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}/books")
	@io.swagger.annotations.ApiOperation(value = "deleteBridgeGroupBooks",notes = "Delete books from gourp for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> deleteBridgeGroupBooks(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, @PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,
			@RequestParam(required=false) List<String> books,@RequestParam(required=false) Boolean deleteAll,@RequestParam(required=false) String search,@RequestParam(required=false) Boolean isGrouped, @RequestParam(required=false) String category,
			HttpServletRequest request,HttpServletResponse response) {
		if(search!=null && StringUtils.isNotEmpty(search)){
 			try { 				
 				search = new String(search.getBytes("ISO-8859-1"), "UTF-8"); 				
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, books);
		params.put(ApplicationConstants.DELETE_ALL, deleteAll);
		params.put(ApplicationConstants.PAGINATION_SEARCH, search);
		params.put(ApplicationConstants.IS_GROUPED, isGrouped);
		params.put(ApplicationConstants.CATEGORY_FILTER, category);
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_BRIDGE_GROUP_BOOKS, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}")

	@io.swagger.annotations.ApiOperation(value = "Delete group",notes = "Delete group from bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> deleteBridgeGroup(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletResponse response,
			 HttpServletRequest request, @PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_BRIDGE_GROUP, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{"+ApplicationConstants.REST_PARAM_ID+"}/users")

	@io.swagger.annotations.ApiOperation(value = "Delete users",notes = "Delete users from bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> deleteBridgeUsers(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletResponse response,			
			@RequestParam(required=false) List<Integer> users,@RequestParam(required=false) Boolean deleteAll,@RequestParam(required=false)String search,@RequestParam(required=false)Boolean isungrouped,
			 HttpServletRequest request, @PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId) {
		if(search!=null && StringUtils.isNotEmpty(search)){
 			try { 				
 				search = new String(search.getBytes("ISO-8859-1"), "UTF-8"); 				
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);

		params.put(ApplicationConstants.GROUP_ASSET_USERS, users);
		params.put(ApplicationConstants.DELETE_ALL, deleteAll);
		params.put(ApplicationConstants.PAGINATION_SEARCH, search);
		params.put(ApplicationConstants.IS_UNGROUPED, isungrouped);
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_BRIDGE_USERS, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/books/{"+ApplicationConstants.PARAM_VBID+"}/groups")
	@io.swagger.annotations.ApiOperation(value = "Get asset / book groups list",notes = "Get list of  groups for asset / book for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBookGroups(HttpServletResponse response, HttpServletRequest httpRequest,
			@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,			
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@PathVariable(ApplicationConstants.PARAM_VBID) String vbid) {				
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_BOOK_VBID_ID, vbid);		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BOOK_GROUPS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, httpRequest, response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/users/{"+ApplicationConstants.USER_ID+"}/groups")
	@io.swagger.annotations.ApiOperation(value = "Get user groups list",notes = "Get list of  groups for user for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getUserGroups(HttpServletResponse response, HttpServletRequest httpRequest,
			@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,			
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@PathVariable(ApplicationConstants.USER_ID) int userId) {				
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.USER_ID, userId);		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_USER_GROUPS_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId, params, httpRequest, response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="{"+ApplicationConstants.REST_PARAM_ID+"}/roster",consumes="multipart/form-data")
	@io.swagger.annotations.ApiOperation(value = "upload Roster",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> uploadCSVRoster(HttpServletRequest httpRequest, @CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@ApiParam(value = "Upload Roster")@RequestParam(value="file") MultipartFile uploadedInputStream, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.UPLOAD_INPUT_STREAM, uploadedInputStream);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_CSV_ROSTER, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, params, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/rosterHistory")
	@io.swagger.annotations.ApiOperation(value = "rosterHistory",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getRosteredHistory(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest,HttpServletResponse response) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BRIDGE_ROSTER_HISTORY, PortalPermissionType.admin, sessionId, bridgeId, httpRequest,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/rosterHistory/{"+ApplicationConstants.ROSTER_ID+"}",produces="application/plain")
	@io.swagger.annotations.ApiOperation(value = "getBridgeRosterFile",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public void getBridgeRosterFile(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			 HttpServletRequest httpRequest, HttpServletResponse response,@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, 
			 @PathVariable(ApplicationConstants.ROSTER_ID) int rosterId) {
		Map<String, Integer> requestparams = new HashMap<String, Integer>();
		requestparams.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		requestparams.put(ApplicationConstants.REST_PARAM_ID,rosterId);
		applicationServiceHandler.process(AuthenticatedRestAction.GET_ROSTER_FILE,PortalPermissionType.admin, sessionId, requestparams,httpRequest,response,  uriInfo);
	
			
	
	}

	@RequestMapping(method=RequestMethod.POST,value="/roster/template",consumes="multipart/form-data")
	@io.swagger.annotations.ApiOperation(value = "upload Roster Template",notes = "Upload Roster Template")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> uploadCSVRosterTemplate(HttpServletRequest httpRequest,HttpServletResponse response,
			@CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@ApiParam(value = "Upload Roster Template", required = true) @RequestParam(value="file", required=true) MultipartFile uploadedInputStream,
			@ApiParam(value = "Roster Template Type", required=true) @RequestParam(value="type", required=true) CSVFileType fileType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.TYPE, fileType);
		params.put(ApplicationConstants.UPLOAD_INPUT_STREAM, uploadedInputStream);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_CSV_ROSTER_TEMPLATE, PortalPermissionType.admin, sessionId, params, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/roster/template", produces="application/json")
	@io.swagger.annotations.ApiOperation(value = "Download Roster Template",notes = "Download Roster Template for Given Type")
	public ResponseEntity<RestResponse> downloadCSVRosterTemplate(HttpServletRequest httpRequest,HttpServletResponse response,
			@CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@ApiParam(value = "Roster Template Type", required = true) @RequestParam(value="type", required = true) CSVFileType fileType) {
			return  applicationServiceHandler.process(AuthenticatedRestAction.GET_CSV_ROSTER_TEMPLATE, PortalPermissionType.admin, sessionId, fileType, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/assetsConcurrency")
	@io.swagger.annotations.ApiOperation(value = "updateConcurrencyLimitForAssets",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateConcurrencyLimitForAssets(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@RequestBody BookConcurrencyLimitVO bookConcurrencyLimitVO,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, bookConcurrencyLimitVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_CONCURRENCY_LIMIT_FOR_BOOKS, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groups/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get bridge group by Id",notes = "Get group for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBridgeGroupById(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,
			@ApiParam(value = "For BC refresh", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_REFRESH,required=false) Boolean refresh,HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		params.put(ApplicationConstants.PAGINATION_REFRESH, refresh);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_BRIDGE_FOR_GROUP_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/tenants/{"+ApplicationConstants.TENANT_ID+"}/courses")
	@io.swagger.annotations.ApiOperation(value = "Get courses/groups from BC",notes = "Get courses/groups from BC")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBcGroupsForBridge(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@PathVariable(ApplicationConstants.TENANT_ID) int tenantId,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.TENANT_ID, tenantId);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_COURSES_FOR_TENANT_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/tenants/{"+ApplicationConstants.TENANT_ID+"}/users")
	@io.swagger.annotations.ApiOperation(value = "Get users from BC",notes = "Get users from BC")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBcUsersForBridge(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@PathVariable(ApplicationConstants.TENANT_ID) int tenantId,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.TENANT_ID, tenantId);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_USERS_FOR_TENANT_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/course/{"+ApplicationConstants.BRIDGE_GROUP_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get course from BC using groupId",notes = "Get course from BC")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getBcGroupsForGroupId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@PathVariable(ApplicationConstants.BRIDGE_GROUP_ID) int groupId,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.BRIDGE_GROUP_ID, groupId);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_COURSE_FOR_GROUP_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/allowance")
	@io.swagger.annotations.ApiOperation(value = "Set Allowance(Entitlements options)",notes = "Set allowance")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgeAllowance(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@RequestBody AllowanceRequestVO allowanceRequestVO,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, allowanceRequestVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_ALLOWANCE_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/allowance")
	@io.swagger.annotations.ApiOperation(value = "Get Bridge allowance",notes = "Get Bridge Allowance")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getAllowanceForBridgeId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_ALLOWANCE_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/accessType")
	@io.swagger.annotations.ApiOperation(value = "Get Bridge AccessType",notes = "Get Bridge AccessType")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getAccessTypeForBridgeId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_ACCESS_TYPE_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/errorFile/{"+ApplicationConstants.ROSTER_LOG_ID+"}",produces="application/plain")
	@io.swagger.annotations.ApiOperation(value = "Get roster error file",notes = "To get roster error file")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public void getErrorsFile(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId, 
			 HttpServletRequest httpRequest, HttpServletResponse response, 
			 @PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			 @PathVariable (ApplicationConstants.ROSTER_LOG_ID)Integer rosterLogId ) {
		Map<String, Integer> requestparams = new HashMap<String, Integer>();
		requestparams.put(ApplicationConstants.BRIDGE_ID, bridgeId);	
		requestparams.put((ApplicationConstants.ROSTER_LOG_ID), rosterLogId);
		applicationServiceHandler.process(AuthenticatedRestAction.GET_CSV_ERROR_FILE,PortalPermissionType.admin, sessionId, requestparams,httpRequest,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/purchase")
	@io.swagger.annotations.ApiOperation(value = "Set Purchasing options",notes = "Set purchase")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateBridgePurchase(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,@RequestBody PurchaseRequestVO purchaseRequestVO,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, purchaseRequestVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_PURCHASE_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/purchase")
	@io.swagger.annotations.ApiOperation(value = "Get Bridge purchase",notes = "Get Bridge purchase")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getPurchasesForBridgeId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);		
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_PURCHASE_FOR_BRIDGE_ID, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="{"+ApplicationConstants.REST_PARAM_ID+"}/generateKeys")
	@io.swagger.annotations.ApiOperation(value = "Generate Keys",notes = "To generate Keys")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> generateKeys(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,@RequestBody KeyGenerateRequestVO keyGenerateRequestVO,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, keyGenerateRequestVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_BRIDGE_KEY_GENERATE, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{"+ApplicationConstants.REST_PARAM_ID+"}/generateKeys/{"+ApplicationConstants.KEY_BATCH_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Edit generated Keys",notes = "To edit generated Keys")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> updateGeneratedKeys(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@RequestBody KeyGenerateRequestVO keyGenerateRequestVO,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@PathVariable(ApplicationConstants.KEY_BATCH_ID) int keyBatchId, 
			HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.KEY_BATCH_ID, keyBatchId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, keyGenerateRequestVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.PUT_BRIDGE_KEY_GENERATE, PortalPermissionType.admin, sessionId, params, request,response,  uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{"+ApplicationConstants.REST_PARAM_ID+"}/keybatchentitlement/{"+ApplicationConstants.KEY_BATCH_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get Keybatch Entitlements",notes = "Get Keybatch Entitlements")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> getEntitlementForKeybatchId(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="")  String sessionId,HttpServletResponse response,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId, @PathVariable(ApplicationConstants.KEY_BATCH_ID)int keyBatchId,  HttpServletRequest httpRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.KEY_BATCH_ID, keyBatchId);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_ENTITLEMENT_FOR_KEYBATCH, PortalPermissionType.admin, sessionId,params,httpRequest,response, uriInfo);		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="{"+ApplicationConstants.BRIDGE_ID+"}/files/upload")
	@io.swagger.annotations.ApiOperation(value = "Get upload token", notes = "retrieves upload token for ancillaries")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> getAncillaryUploadLink(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@RequestBody TokenRequestVO tokenRequestVO,
			HttpServletResponse httpServletResponse,HttpServletRequest httpRequest){
				
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, tokenRequestVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_UPLOAD_TOKEN, PortalPermissionType.admin, sessionId,params, httpRequest, httpServletResponse, uriInfo);		
	}
	@RequestMapping(method=RequestMethod.GET, value="{"+ApplicationConstants.BRIDGE_ID+"}/files")
	@io.swagger.annotations.ApiOperation(value = "Get upload token", notes = "retrieves list of available ancillaries")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> getAllAncillariesForBridge(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@ApiParam(value = "Page number", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_PAGE,required=false) Integer page,
			@ApiParam(value = "Pagination limit", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_LIMIT,required=false) Integer limit,
			@ApiParam(value = "Order by", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDERBY,required=false) String orderby, 
			@ApiParam(value = "Pagination order", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_ORDER,required=false) String order, 
			@ApiParam(value = "Search parameter", required = false)@RequestParam(value=ApplicationConstants.PAGINATION_SEARCH,required=false) String search,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		if(search!=null && StringUtils.isNotEmpty(search)){
 			try { 				
 				search = new String(search.getBytes("ISO-8859-1"), "UTF-8"); 				
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
		PaginationVO paginationVO = new PaginationVO(page,limit,null,orderby,order,search,null,null);
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PAGINATION_VO, paginationVO);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_ANCILLARIES_FOR_BRIDGE, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}
	
	@RequestMapping(method=RequestMethod.GET, value="{"+ApplicationConstants.BRIDGE_ID+"}/files/{"+ApplicationConstants.REST_PARAM_ID+"}/status")
	@io.swagger.annotations.ApiOperation(value = "Get upload token", notes = "retrieves status for an ancillary")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> getAllAncillaryStatus(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID)Integer id,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.REST_PARAM_ID, id);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_ANCILLARY_STATUS, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}
	
	@RequestMapping(method=RequestMethod.GET, value="{"+ApplicationConstants.BRIDGE_ID+"}/files/{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Get upload token", notes = "retrieves status for an ancillary")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> getAncillary(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID)int id,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.REST_PARAM_ID, id);
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_ANCILLARY, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}

	
	@RequestMapping(method=RequestMethod.POST,value="{"+ApplicationConstants.REST_PARAM_ID+"}/groupassetmap",consumes="multipart/form-data")
	@io.swagger.annotations.ApiOperation(value = "upload Group to asset mapping",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> uploadGroupToAssetMappingCSV(HttpServletRequest httpRequest, @CookieValue(value=ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID) int bridgeId,
			@ApiParam(value = "Upload Csv")@RequestParam(value="file") MultipartFile uploadedInputStream, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.UPLOAD_INPUT_STREAM, uploadedInputStream);
		return applicationServiceHandler.process(AuthenticatedRestAction.POST_CSV_GROUP_TO_ASSET_MAPPING, com.vst.bridge.rest.config.PortalPermissionType.admin, sessionId, params, httpRequest,response, uriInfo);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="{"+ApplicationConstants.BRIDGE_ID+"}/files")
	@io.swagger.annotations.ApiOperation(value = "Get upload token", notes = "creates ancillary infomation for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> createAncillaryCallback(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@RequestBody AncillaryVO ancillaryVO,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, ancillaryVO);
		return applicationServiceHandler.process(UnAuthenticatedRestAction.CREATE_ANCILLARY, PortalPermissionType.admin,sessionId, httpRequest, httpResponse, uriInfo, params);

	}
	
	@RequestMapping(method=RequestMethod.PUT, value="{"+ApplicationConstants.BRIDGE_ID+"}/files")
	@io.swagger.annotations.ApiOperation(value = "Update ancillary", notes = "edits ancillary infomation for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> updateAncillaries(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@RequestBody List<AncillaryAdminVO> ancillaryVOList,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, ancillaryVOList);
		return applicationServiceHandler.process(AuthenticatedRestAction.UPDATE_ANCILLARY, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}
	
	@RequestMapping(method=RequestMethod.PUT, value="{"+ApplicationConstants.BRIDGE_ID+"}/files/"+"{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Update ancillary", notes = "edits ancillary infomation for bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> updateSingleAncillary(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID)int id,
			@RequestBody AncillaryAdminVO ancillaryVO,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, ancillaryVO);
		params.put(ApplicationConstants.REST_PARAM_ID, id);
		return applicationServiceHandler.process(AuthenticatedRestAction.UPDATE_ANCILLARY, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}
	@RequestMapping(method=RequestMethod.DELETE, value="{"+ApplicationConstants.BRIDGE_ID+"}/files")
	@io.swagger.annotations.ApiOperation(value = "Delete the ancillary", notes = "deletes ancillary from bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> deleteAncillaries(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@RequestBody List<AncillaryAdminVO> ancillaryVOList,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, ancillaryVOList);
	
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_ANCILLARY, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="{"+ApplicationConstants.BRIDGE_ID+"}/files/"+"{"+ApplicationConstants.REST_PARAM_ID+"}")
	@io.swagger.annotations.ApiOperation(value = "Delete the ancillary", notes = "deletes single ancillary from bridge")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> deleteSingleAncillary(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@PathVariable(ApplicationConstants.REST_PARAM_ID)int id,
			@RequestBody AncillaryAdminVO ancillaryAdminVO,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, ancillaryAdminVO);
		params.put(ApplicationConstants.REST_PARAM_ID, id);
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_ANCILLARY, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="{"+ApplicationConstants.BRIDGE_ID+"}/books")
	@io.swagger.annotations.ApiOperation(value = "Delete the ancillary", notes = "deletes ancillary from bridge bridge book cache and ancillaries")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<RestResponse> deleteBridgeBookCacheVbidList(@CookieValue(value = ApplicationConstants.BRIDGE_SESSIONID, defaultValue="") String sessionId,
			@PathVariable(ApplicationConstants.BRIDGE_ID)int bridgeId,
			@RequestBody List<String> vbidList,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, Object> params = new HashMap<>();
		params.put(ApplicationConstants.BRIDGE_ID, bridgeId);
		params.put(ApplicationConstants.PUT_REQUEST_OBJECT, vbidList);
	
		return applicationServiceHandler.process(AuthenticatedRestAction.DELETE_ANCILLARY_BOOK_CACHE, PortalPermissionType.admin, sessionId,params, httpRequest, httpResponse, uriInfo);

	}
}
