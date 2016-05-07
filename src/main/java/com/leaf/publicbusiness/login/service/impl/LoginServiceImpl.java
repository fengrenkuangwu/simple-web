package com.leaf.publicbusiness.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaf.entity.AdminInfo;
import com.leaf.mapper.AdminInfoMapper;
import com.leaf.publicbusiness.login.service.LoginService;

/**
 * 登录服务层
 * @author Leaf
 * @date 2015年11月30日23:45:32
 */
@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private AdminInfoMapper adminInfoMapper;

	@Override
	public AdminInfo adminLogin(String userName, String userPwd) {
		// TODO Auto-generated method stub
		AdminInfo adminInfo = new AdminInfo();
		adminInfo.setAdminName(userName);
		adminInfo.setAdminPwd(userPwd);
		adminInfo = adminInfoMapper.selectForLogin(adminInfo);
		return adminInfo;
	}

}
