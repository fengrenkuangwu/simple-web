package com.leaf.base.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leaf.entity.AdminInfo;

public class SessionUtil {
	
	/*日志*/
	private static final Log log = LogFactory.getLog(SessionUtil.class);
	 /** 存储在session中验证码key */
	public static final String INDENT_CODE_KEY = "codeInd";
	/** 存储在session中验证码生成时间 */
	public static final String INDENT_CODE_DATE = "codeDate";
	/* sessionkey */
	private static final String SESSION_KEY = "admin";
	/**
	 * 登录用户信息放入session
	 * @param key
	 * @param value
	 * @param request
	 * @author leaf
	 * @date 2015年11月29日16:44:54
	 */
	public static void setAdminToSession( Object value, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("admin", value);
	}
	/**
	 * 获取登录用户信息
	 * @param request
	 * @return
	 * @author leaf
	 * @date 2015年11月29日16:44:54
	 */
	public static AdminInfo getAdminFromSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		AdminInfo admin=(AdminInfo) session.getAttribute("admin");
		return admin;
	}
	
	/**
	 * 
	 * 获取session中给定key对应的值
	 * 
	 * @param key
	 *            String session中key
	 * 
	 * @return Object key对应的值;如果session不存在返回null
	 * 
	 */
	private static Object getSessionValue(String key,HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			if (session == null) {
				log.error("获取Session为空");
				return null;
			}
			// 从session中获取用户信息
			return session.getAttribute(key);
		} catch (Exception ex) {
			log.error("获取httpsession对象失败！", ex);
		}
		return null;
	}
	
	/**
	 * 
	 * 清除session中给定key对应的值
	 * 
	 * @param key
	 *            String session中key
	 * 
	 * @return Object key对应的值;如果session不存在返回null
	 * 
	 */
	public static void cancelCheckCodeValue(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			if (session == null) {
				log.error("获取Session为空");
				return;
			}
			// 清除session中的验证码
			session.setAttribute(INDENT_CODE_KEY, null);
		} catch (Exception ex) {
			log.error("获取httpsession对象失败！", ex);
		}
	}
	
	/**
	 * 
	 * 获取session中验证码
	 * 
	 * @return String session存在返回验证码；不存在返回null
	 * 
	 */
	public static String getSessionAuthCode(HttpServletRequest request) {
		Object obj = getSessionValue(INDENT_CODE_KEY, request);
		if (obj != null) {
			return String.valueOf(obj);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * 获取验证码生成时间戳
	 * 
	 * @return long session存在返回时间戳；不存在返回null
	 * 
	 */
	public static long getSessionAuthTimeMis(HttpServletRequest request) {
		Object obj = getSessionValue(INDENT_CODE_DATE, request);
		if (obj != null) {
			return Long.valueOf(obj.toString()).longValue();
		} else {
			return -1;
		}
	}
	/**
	 * 向session中存信息
	 * @param key  键值
	 * @param value 存的值
	 * @param request 
	 * @author leaf
	 * @date 2015年11月29日16:51:02
	 */
	public static void setInfoToSession(String key,String value,HttpServletRequest request){
		HttpSession session = request.getSession();
		
		session.setAttribute(key, value);
	}
	/**
	 * 通过键值从session获取值（只获取String类型值）
	 * @param key
	 * @param request
	 * @return
	 * @author leaf
	 * @date 2015年11月29日16:51:26
	 */
	public static String getInfoFromSession(String key,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		String value=(String) session.getAttribute(key);
		return value;
	}
	/**
	 * 
	 * 清除session用户
	 * 
	 * @param session
	 *            HttpSession
	 * 
	 */
	public static void clearSession(HttpSession session) {
		clearSessionByKey(SESSION_KEY, session);
	}

	/**
	 * 
	 * 清除session中指定key对应数据
	 * 
	 * @param key
	 *            String 指定key
	 * @param session
	 *            HttpSession
	 * 
	 */
	public static void clearSessionByKey(String key, HttpSession session) {
		if (session == null || StringUtil.isEmptyOrNull(key)) {
			log.error("参数为空." + SessionUtil.class.getName());
			throw new IllegalArgumentException("参数为空");
		}
		session.removeAttribute(key);
	}
}
