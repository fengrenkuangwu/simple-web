package com.leaf.publicbusiness.login.service;

import com.leaf.entity.AdminInfo;
/**
 * 登录服务层
 * @author Leaf
 * @date 2015年11月30日23:41:52
 */
public interface LoginService {

	AdminInfo adminLogin(String userName,String userPwd);
}
