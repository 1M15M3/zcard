package com.ftsafe.iccd.huipan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;

@Controller
public class BaseController {

	private final static LogWrapper LOG = Log.get();
	
	private final static String VIEW_INDEX = "index";
	private final static String VIEW_WELCOME = "welcome";
	private final static String VIEW_WARN = "warning";
	
	@SuppressWarnings("unused")
	private HttpServletRequest request;
    @SuppressWarnings("unused")
	private HttpServletResponse response;
    @SuppressWarnings("unused")
	private HttpSession session;
    
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }
    @RequestMapping(value="/")
	public String index(){
		LOG.info("Hello Zcard");
		return VIEW_INDEX;
	}
	@RequestMapping(value="/welcome")
	public String welcome(){
		return VIEW_WELCOME;
	}
	@RequestMapping(value="/warning", method=RequestMethod.GET)
	public String warning() {
		return VIEW_WARN;
	}
}
