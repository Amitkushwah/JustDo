package com.cognizant.retailbank.apigateway.filters;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.retailbank.apigateway.service.VerificationService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

/*
 * Zuul API Filter Class for JWT verification and forwarding request to respective service
 */

@Component
@Slf4j
public class ZuulAPIFilter extends ZuulFilter{

	@Autowired
	private VerificationService verificationService;
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request =ctx.getRequest();
		String uri=request.getRequestURI();
		if(uri.equals("/auth-ms/auth-ms/login")) {
			return false;
		}
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		log.debug("Filter Started");
		RequestContext requestContext=RequestContext.getCurrentContext();
		HttpServletRequest request =requestContext.getRequest();
		String authorizationHeader=request.getHeader("Authorization");
		String response="";
		if(authorizationHeader==null) {
			log.info("No auth");

			response="{\"status\":false,\"message\":\"Token Not Provided\","
					+ "\"path\":\""+request.getRequestURI()+"\"}";
			requestContext.setSendZuulResponse(false);
			requestContext.setResponseStatusCode(HttpStatus.SC_BAD_REQUEST);
			requestContext.setResponseBody(response);
			requestContext.getResponse().setContentType("application/json");
		}
		else {
			boolean isVerified=verificationService.verifyService(authorizationHeader, request.getRequestURI());
			if(!isVerified) {
				response="{\"status\":false,\"message\":\"Invalid Token or Token Expired or Unauthorized User\","
						+ "\"path\":\""+request.getRequestURI()+"\"}";
				requestContext.setSendZuulResponse(false);
				requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
				requestContext.setResponseBody(response);
				requestContext.getResponse().setContentType("application/json");
			}
		}
		
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
