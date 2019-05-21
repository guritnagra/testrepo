package com.vst.bridge.admin.rest;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vst.bridge.rest.central.IApplicationServiceHandler;
import com.vst.bridge.rest.config.AuthenticatedRestAction;
import com.vst.bridge.rest.config.PortalPermissionType;
import com.vst.bridge.rest.config.UnAuthenticatedRestAction;
import com.vst.bridge.rest.input.vo.LoginInfoVO;
import com.vst.bridge.rest.response.vo.RestResponse;
import com.vst.bridge.util.constant.ApplicationConstants;

import io.swagger.annotations.ApiResponse;

/**
 * Extended WADL tags:
 * Fails:
     * @request.representation.mediaType application/json
     * @request.representation.example {@link #createKeyBatch}
     * @request.representation.qname { http://www.example.com }request
	 * @response.representation.Response.Status.OK.doc fails
	Works:
	   @response.representation.200.doc works
     * @response.representation.200.mediaType application/json    works
     * 
     * see: https://wikis.oracle.com/display/Jersey/SupportedJavadocTagsForExtendedWADL

 * REST web-services endpoints for pre-logged-ed in user.
 **/

@RestController
@RequestMapping(value="/session")
@io.swagger.annotations.Api(value = "/Session")
public class Session {
	

	
	@Context
	UriInfo uriInfo;

	
	@Autowired
	private IApplicationServiceHandler applicationServiceHandler;
	
	/**
	 * Authenticate Session
	 * 
	 * Authenticated: yes
	 *  
	 * @param vsbsession	The users session cookie
	 * 
	 * @response.representation.200.mediaType application/json
	 * @response.representation.200.doc Authentication successful
	 * @response.representation.200.example	{
			 "code":Response.Status.OK,
			 "data":
			 {
			  "email": "smurphy@vpg.com",
			  "firstName": "Sean",
			  "fullName": "Sean Murphy",
			  "lastName": "Murphy",
			  "role": "admin",
			  "userId": 1
			 },
			 "message":"session.authorized"
			}
	 * @response.representation.401.doc A valid session was not supplied
	 * @response.representation.401.mediaType application/json
	 * @response.representation.401.example {
		 "code":401,
		 "data":null,
		 "errors":
		 [
		  {
		   "data":{"message":"session.expired","field":"stacksessionid"},
		   "type":"fielderror"
		  }
		 ],
		 "message":"session.expired"
		}
	 * @response.representation.500.doc An error occurred
	 * @response.representation.500.mediaType application/json
	 * @response.representation.500.example	{
		 "code":401,
		 "data":null,
		 "errors":[],
		 "message":"session.unauthorized"
		}
 * @param sessionId session id
 * @param request http request
	 **/
	@RequestMapping(method={RequestMethod.GET})
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> isLoggedIn(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletRequest request,HttpServletResponse httpResponse) {
		return applicationServiceHandler.process(AuthenticatedRestAction.GET_USER, PortalPermissionType.admin, sessionId, null,request,httpResponse,uriInfo);
	}
		
	/**
	 * Login
	 * 
	 * Authenticated: no
	 * 
		@request.representation.example
		{
		 "email":"ftaylor@mailinator.com",
		 "password":"password"
		}
	 * @response.representation.200.mediaType application/json  
	 * @response.representation.200.doc	Login successful fdiubhjvbjvbjvb bjjvbjnvcbnvcbvcb
	 * @response.representation.200.example	
	 * {
		    "code": 200,
		    "data": {
		        "companies": [
		            {
		                "id": 19,
		                "name": "Sobey MBA (CPA Stream) Digital Library",
		                "type": "INSTITUTIONAL"
		            },
		            {
		                "id": 26,
		                "name": "Bridge Institution",
		                "type": "INSTITUTIONAL"
		            },
		            {
		                "id": 165,
		                "name": "MIT McGraw Hill Portal Library",
		                "type": "INSTITUTIONAL"
		            },
		            {
		                "id": 183,
		                "name": "demo u Library",
		                "type": "INSTITUTIONAL"
		            }
		        ],
		        "created": 1427376305000,
		        "createdBy": "Clay1115 SuperAdmin",
		        "createdDate": "26 Mar, 2015",
		        "email": "smurphy@vpg.com",
		        "firstName": "Sean",
		        "groups": [
		            {
		                "id": 1,
		                "name": "testGroup"
		            },
		            {
		                "id": 2,
		                "name": "StackGroup"
		            }
		        ],
		        "id": 99,
		        "lastName": "Murphy",
		        "type": "admin"
		    },
		    "error": [],
		    "message": "OK",
		    "messageid": "response.ok",
		    "metadata": {},
		    "version": 1
	}
	 * @response.representation.400.doc	JSON Parsing issue OR Email or password is missing
	 * @response.representation.400.mediaType application/json
	 * @response.representation.400.example	{
		 "code":Response.Status.BAD_REQUEST,
		 "data":null,
		 "errors":
		 [
		  {
		   "data":{"message":"login.error.password","field":"password"},
		   "type":"fielderror"
		  }
		 ],
		 "message":"message.data_validation_error"
		}		 * 
				 * OR
				 * 
				 * {
		 "code":Response.Status.BAD_REQUEST,
		 "data":null,
		 "errors":
		 [
		  {
		   "data":{"message":"login.error.email","field":"email"},
		   "type":"fielderror"
		  }
		 ],
		 "message":"message.data_validation_error"
		}
	 * @response.representation.401.doc	Unable to login
	 * @response.representation.401.mediaType application/json
	 * @response.representation.401.example	{
			 "code":401,
			 "data":null,
			 "errors":[],
			 "message":"login.failed"
			}
	 * @response.representation.500.doc	An error occurred
	 * @response.representation.500.mediaType application/json
	 * @response.representation.500.example	{
		 "code":Response.Status.INTERNAL_SERVER_ERROR,
		 "data":null,
		 "errors":[],
		 "message":"message.unexpected_error"
		}
	 * @param request http request
	 **/
	
	@RequestMapping(method={RequestMethod.POST})
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> login (@RequestBody LoginInfoVO loginInfoVO,HttpServletRequest httpRequest,HttpServletResponse httpResponse) {
		return applicationServiceHandler.process(UnAuthenticatedRestAction.POST_SESSION, PortalPermissionType.admin, null, httpRequest,httpResponse,uriInfo,loginInfoVO);
	}
	
	/**
	 * Logout
	 * 
	 * Authenticated: yes
	 * 
	 * @response.representation.200.doc Logout successful
	 * @response.representation.200.mediaType application/json
	 * @response.representation.200.example {"code":Response.Status.OK,"data":null,"errors":[],"message":"Logout successful"}
	 * @response.representation.401.doc A valid session was not supplied
	 * @response.representation.401.mediaType application/json
	 * @response.representation.401.example	{"code":401,"data":null,"errors":[],"message":"You are not currently logged in"}
	 * @response.representation.500.doc An unexpected error occurred
	 * @response.representation.500.mediaType application/json
	 * @response.representation.500.example	{"code":Response.Status.INTERNAL_SERVER_ERROR,"data":null,"errors":[],"message":"An unexpected error occurred"} 
	 *  
	 *	{
		 "code":Response.Status.OK,
		 "data":null,
		 "message":"session.logout"
		}
	 * @param sessionId session id
	 * @param request servlet request
	 * @return response
	 **/
	@RequestMapping(method={RequestMethod.DELETE})
	@io.swagger.annotations.ApiOperation(value = "Get user",notes = "To get logged in user information")
	@io.swagger.annotations.ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<RestResponse> logout(@CookieValue(value =ApplicationConstants.BRIDGE_SESSIONID,defaultValue="") String sessionId,HttpServletRequest httpRequest,HttpServletResponse httpResponse) { 
		return applicationServiceHandler.process(UnAuthenticatedRestAction.DELETE_SESSION, PortalPermissionType.admin, sessionId, httpRequest,httpResponse,uriInfo,null);
	}
}
