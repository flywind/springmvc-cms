package com.uxdpen.cms.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.uxdpen.cms.business.common.utils.PropertiesUtils;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.User;
import com.uxdpen.cms.business.services.sys.ResourceService;
import com.uxdpen.cms.business.services.sys.SystemSetingService;
import com.uxdpen.cms.business.services.sys.UserService;
import com.uxdpen.cms.util.ExceptionUtil;

@Controller
public class BaseController {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected SystemSetingService systemSetingService;
    
    @Autowired
    protected ResourceService resourceService;
    
    @Autowired
    protected UserService userService;
    
    @Autowired
    protected HttpServletRequest request;
    

    /*系统公共基本：如菜单、系统设置等等*/
    protected void setCommonData(Model model, User currentUser) {
		// 初始化系统基本信息
		model.addAttribute("systemSeting",
				systemSetingService.querySysSetingByCustomerCode(currentUser.getCustomerCode()));

		// 当前用户
		model.addAttribute("currentUser", currentUser);

		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getServletPath();
		if (request.getQueryString() != null) {
			url += "?" + request.getQueryString();
		}
		
		logger.info("当前页面URL: " + url);

		// 获得当前用户的菜单
		Set<String> permissions = userService.findPermissions(currentUser.getUsername(), currentUser.getCustomerCode());
		Resource r = resourceService.findAllForTree(currentUser.getCustomerCode(), permissions);
		List<Resource> menus = r.getChildResource();
		for (Resource rs : menus) {

			if (!"".equals(rs.getChildResource())) {
				if (null != rs.getChildResource()) {
					for (Resource k : rs.getChildResource()) {
						String page = k.getUrl().toLowerCase();
						if (page.equals(request.getServletPath().toLowerCase())) {
							k.setActiveStyle("active-link");
							rs.setActiveStyle("active-sub active");
						}
					}
				}
			}
		}
		model.addAttribute("menus", menus);

	}

	/* 全局异常处理 */
	@ExceptionHandler
	public void exp(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		request.setAttribute("ex", ex);

		try {
			String errMsg = "";
			errMsg += "程序执行过程发生错误：" + ex.getMessage() + "\n" + ExceptionUtil.getStackMsg(ex);
			logger.error(errMsg);

			if (isAjax(request)) {
				if (getIsDebug()) {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("{\"jsonParams\":{},\"mapParams\":{},\"msg\":\"" + ex.getMessage()
							+ "\",\"msgKey\":\"\",\"success\":0}");
				} else {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(
							"{\"jsonParams\":{},\"mapParams\":{},\"msg\":\"操作失败!!\",\"msgKey\":\"dealFail\",\"success\":0}");
				}
			} else {
				if (getIsDebug()) {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("<h1>发生错误，详情如下!</h1><br>");
					response.getWriter()
							.write(ex.toString() + "\n" + ex.getMessage() + "\n" + ExceptionUtil.getStackMsg(ex));

				} else {
					ex.printStackTrace();
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("<h1>发生未知异常，详情请联系系统管理员！</h1>");

				}
			}
		} catch (Exception myEx) {
			myEx.printStackTrace();
		}
		logger.error("MonitorListController execute error", ex);
	}

	private static boolean getIsDebug() {
		try {
			String isDebug = PropertiesUtils.getProperties().getProperty("isDebug");
			if (StringUtils.isBlank(isDebug)) {
				isDebug = "0";
			}
			if (isDebug.trim().equalsIgnoreCase("1")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private static boolean isAjax(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if (StringUtils.isNoneBlank(requestType) && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		return false;
	}
}
