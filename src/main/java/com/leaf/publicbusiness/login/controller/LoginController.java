package com.leaf.publicbusiness.login.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leaf.base.utils.JsonUtil;
import com.leaf.base.utils.SessionUtil;
import com.leaf.base.utils.StringUtil;
import com.leaf.entity.AdminInfo;
import com.leaf.publicbusiness.login.service.LoginService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/free")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping(value = "/adminLogin")
	public void adminLogin(String userName,String userPassword,HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		if(StringUtil.isEmptyOrNull(userName)||StringUtil.isEmptyOrNull(userPassword)){
			log.error("用户名或密码为空！");
			json.element("status", "4000");
			json.element("retMsg", "用户名或密码为空！");
			JsonUtil.outJSONObject(json, null, response);
		}
		if(userName.length()>20||userPassword.length()>20){
			log.error("用户名或密码过长！");
			json.element("status", "4000");
			json.element("retMsg", "用户名或密码过长！");
			JsonUtil.outJSONObject(json, null, response);
		}
		AdminInfo adminInfo = loginService.adminLogin(userName, userPassword);
		if(adminInfo==null){
			log.error("用户名或密码正确！");
			json.element("status", "4000");
			json.element("retMsg", "用户名或密码不正确！");
			JsonUtil.outJSONObject(json, null, response);
		}
		SessionUtil.setAdminToSession(adminInfo, request);
		json.element("status", "0000");
		JsonUtil.outJSONObject(json, null, response);
	}
}
