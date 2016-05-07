package com.leaf.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.leaf.base.utils.SessionUtil;
import com.leaf.entity.AdminInfo;

/**
 * 
 * 拦截器
 * 
 *
 */
public class AuthInterceptor implements HandlerInterceptor {
	private static final Logger log = Logger.getLogger(AuthInterceptor.class);
	/** 登录jsp */
	private final String index = "/index.html";
	/** 登录路径 */
	private String LoginPath = null;
	/** 允许通过的url */
	private String[] allowUrls;
	/** 允许通过静态文件 */
	private String[] staticFiles;

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	public void setStaticFiles(String[] staticFiles) {
		this.staticFiles = staticFiles;
	}

	/**
	 * 
	 * 在业务处理器处理请求之前被调用，在该方法中对用户请求request进行处理
	 * 
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		if (log.isInfoEnabled()) {
			log.info("preHandle:" + requestUrl);
		}


		if (null != staticFiles && staticFiles.length >= 1) {// 静态文件不需要拦截
			for (String url : staticFiles) {
				if (requestUrl.startsWith(url)) {
					return true;
				}
			}
		}

		if (null != allowUrls && allowUrls.length >= 1) {
			for (String url : allowUrls) {
				if (requestUrl.startsWith(url)) {
					return true;
				}
			}
		}

		AdminInfo admin = SessionUtil.getAdminFromSession(request);
		if (admin != null) {
			return true; // 返回true，则这个方法调用后会接着调用postHandle(), afterCompletion()
		} else {
			// 未登录 跳转到登录页面
			  if (LoginPath == null) {
					StringBuffer buff = new StringBuffer();
					buff.append("<script type='text/javascript'>var rfy='not logined';  window.parent.location.href = '");
					buff.append(request.getContextPath());
					buff.append(index);
					buff.append("';</script>");
					LoginPath = buff.toString();
				}
			response.getWriter().write(LoginPath);
			
			return false;
		}
	}

	/**
	 * 
	 * 在DispatcherServlet完全处理完请求后被调用，可以在该方法中进行一些资源清理的操作。
	 * 
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// String requestUrl =
		// request.getRequestURI().replace(request.getContextPath(), "");
		// log.info("afterCompletion:" + requestUrl);
	}

	/**
	 * 
	 * 在业务处理器处理完请求后，但是DispatcherServlet向客户端返回请求前被调用，在该方法中对用户请求request进行处理。
	 * 
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// String requestUrl =
		// request.getRequestURI().replace(request.getContextPath(), "");
		// log.info("postHandle:" + requestUrl);
	}
}
