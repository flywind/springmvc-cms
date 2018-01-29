package com.uxdpen.cms.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.sys.Organization;
import com.uxdpen.cms.business.entities.sys.Role;
import com.uxdpen.cms.business.entities.sys.User;
import com.uxdpen.cms.business.services.sys.OrganizationService;
import com.uxdpen.cms.component.DataCallback;
import com.uxdpen.cms.component.DataGrid;
import com.uxdpen.cms.util.BaseResult;
import com.uxdpen.cms.web.bind.annotation.CurrentUser;

@Controller
public class UserController extends BaseController {
	
	@Autowired
	private OrganizationService organizationService;

    @RequiresPermissions("user:view")
    @RequestMapping(value = "admin/sys/userList", method = RequestMethod.GET)
    public String list(Model model, @CurrentUser User curuser) {
    	setCommonData(model,curuser);
        return "admin/sys/userList";
    }
    
    @RequiresPermissions("user:view")
    @RequestMapping(value = "admin/sys/userCreate", method = RequestMethod.GET)
    public String create(Model model, @CurrentUser User curuser) {
    	setCommonData(model,curuser);
        return "admin/sys/userCreate";
    }
    
    @ResponseBody
    @RequestMapping(value = "admin/sys/user/list", method = RequestMethod.POST)
    public void getUserList(@CurrentUser User curuser, @RequestParam int page, @RequestParam int rows, @RequestParam String sort,@RequestParam String order,
    		@RequestParam String username, @RequestParam String name, HttpServletRequest request, HttpServletResponse response){
    	DataGrid result = new DataGrid();
    	FPage pager = new FPage();
    	pager.setPageNumber(page);
    	pager.setPageSize(rows);
    	pager.setSortName(sort);
    	pager.setSortOrder(order);
    	
    	if(null==username&&name==null){
    		List<User> list = userService.findAll(curuser, pager);
        	result.setRows(list);
        	result.setTotal(Long.valueOf(pager.getRowCount()));
        	BaseResult.writeJson(request, response, result, false);
    	}else{
	    	
	    	List<User> list = userService.findByUserNameAndReadName(curuser,username, name, null, curuser.getCustomerCode(),pager);
	    	result.setRows(list);
	    	result.setTotal(Long.valueOf(pager.getRowCount()));
	    	BaseResult.writeJson(request, response, result, false);
    	}
    }
    
    @ResponseBody
    @RequestMapping(value = "admin/sys/organizationTree", method = RequestMethod.POST)
    public void getCompanyTree(@CurrentUser User curuser, HttpServletRequest request, HttpServletResponse response){
    	List<Organization> organization = organizationService.findAllOrganizationByUser(curuser);
    	BaseResult.writeJson(request, response, organization, false);
    }
    
    @ResponseBody
    @RequestMapping(value = "admin/sys/roleTree", method = RequestMethod.POST)
    public void getRoleTree(@CurrentUser User curuser, HttpServletRequest request, HttpServletResponse response){
    	List<Role> roles = userService.findAllRoles(curuser);
    	BaseResult.writeJson(request, response, roles, false);
    }
    
    @ResponseBody
    @RequestMapping(value = "admin/sys/user/check", method = RequestMethod.POST)
    public void checkUsername(@CurrentUser User curuser,String username, HttpServletResponse response){
    	boolean result = userService.checkUserExist(username, curuser.getCustomerCode());
    	BaseResult.writeJson(request, response, !result, false);
    }
    
    @ResponseBody
    @RequestMapping(value = "admin/sys/user/create", method = RequestMethod.POST)
    public void createUser(@CurrentUser User curuser, @RequestBody Map<String,String> map, HttpServletResponse response){
    	User user = new User();
    	user.initBaseInfo(curuser.getUsername(), curuser.getCustomerCode());
    	user.setPassword("123456");
    	if(map.containsKey("username")){
    		String username = map.get("username").toString();
    		user.setUsername(username);
    	}
    	if(map.containsKey("name")){
    		String name = map.get("name").toString();
    		user.setName(name);
    	}
    	if(map.containsKey("mobile")){
    		String mobile = map.get("mobile").toString();
    		user.setMobile(mobile);
    	}
    	if(map.containsKey("email")){
    		String email = map.get("email").toString();
    		user.setEmail(email);
    	}
    	/*Integer message = 0;
    	if(map.containsKey("message")){
    		message = Integer.parseInt(map.get("message"));
    	}*/
    	String roleIds = "";
    	if(map.containsKey("roleIds")){
    		roleIds = map.get("roleIds").toString();
    	}
    	if(map.containsKey("companyId")){
    		Long companyId = Long.parseLong(map.get("companyId"));
    		user.setCompanyId(companyId);
    	}
    	
    	
    	//发送初始密码到邮箱或手机
		/*String title = user.getUsername() + "用户的初始密码，请查收";
		String content = "您的初始密码为: " + user.getPassword();
		if (message == 2) {
			new FMessageThread(content, FMessageUtil.F_SYSTEM, user.getMobile(), curuser.getCustomerCode()).start();
		} else {
			new FEmailThread(user.getEmail(), title, content).start();
		}*/
		
		//拼接父对象的id
		String parentId = curuser.getParentId() + curuser.getId() + FBaseConstants.SLASH;
		user.setParentId(parentId);
		
		DataCallback callback = new DataCallback();
		
		boolean check = userService.checkUserExist(user.getUsername(), curuser.getCustomerCode());
		if(!check){
			Long userId = userService.createUser(user);
			if (!"".equals(roleIds) && null != roleIds) {
				userService.createUserAndRole(userId, roleIds);
			}
			
			callback.setMessageCode(0);
			callback.setMessage("用户添加成功");
			BaseResult.writeJson(request, response, callback, false);
		}else{
			callback.setMessageCode(1);
			callback.setMessage("用户添加失败,用户名已经存在");
			BaseResult.writeJson(request, response, callback, false);
		}
		
		
    }

}
