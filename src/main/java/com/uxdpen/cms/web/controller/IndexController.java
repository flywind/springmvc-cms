package com.uxdpen.cms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.User;
import com.uxdpen.cms.business.services.sys.ResourceService;
import com.uxdpen.cms.business.services.sys.UserService;
import com.uxdpen.cms.web.bind.annotation.CurrentUser;

import java.util.List;
import java.util.Set;

@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        //List<Resource> menus = resourceService.findMenus(permissions);
        //model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


}
