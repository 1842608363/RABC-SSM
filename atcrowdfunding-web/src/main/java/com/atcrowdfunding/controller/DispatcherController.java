package com.atcrowdfunding.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.model.AJAXResult;
import com.atcrowdfunding.model.Permission;
import com.atcrowdfunding.model.User;
import com.atcrowdfunding.service.PermissionService;
import com.atcrowdfunding.service.UserService;

@Controller
public class DispatcherController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	
	/*
	 * 跳转至登陆页面
	 * */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/error")
	public String error() {
		return "error";
	}
	
	@RequestMapping("/main")
	public String main() {
		return "main";
	}
	
	/*
	 * 退出系统请求
	 * */
	@RequestMapping("/Logout")
	public String logout(HttpSession session) {
		//session.removeAttribute("loginUser");
		session.invalidate();
		return "redirect:login";
	}
	
	/*
	 * AJAX登陆请求
	 */
	@ResponseBody
	@RequestMapping("/doAJAXLogin")
	public Object doAJAXLogin(User user,HttpSession session) {
		
		AJAXResult result = new AJAXResult();
		
		User dbUser = userService.queryLogin(user);
		if (dbUser != null) {
			session.setAttribute("loginUser", dbUser);
			//获取用户权限信息
			List<Permission> permissions = permissionService.queryPermissionByUser(dbUser);
			Map<Integer,Permission> permissionMap = new HashMap<Integer, Permission>();
			Permission root = null;
			Set<String> uriSet = new HashSet<String>();
			for (Permission permission : permissions) {
				permissionMap.put(permission.getId(), permission);
				if (permission.getUrl() != null && !"".equals(permission.getUrl())) {
					uriSet.add(session.getServletContext().getContextPath() + permission.getUrl());
				}
			}
			session.setAttribute("authUriSet", uriSet);
			for (Permission permission : permissions) {
				Permission childPermission = permission;
				if (childPermission.getPid() == 0) {
					root = permission;
				}else {
					Permission parent = permissionMap.get(childPermission.getPid());
					parent.getChildren().add(childPermission);
				}
			}
			session.setAttribute("rootPermission", root);
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return result;
	}
	
	/*
	 * 执行登陆操作
	 * */
	@RequestMapping("doLogin")
	public String doLogin(User user,Model model) throws UnsupportedEncodingException {
		// 1.获取表单数据
		// 1-1.HttpServletRequest
		// 1-2.在方法参数列表中增加表单对应的参数，名称相同
		// 1-3.将表单数据封装为实体类对象
		// 2.查询用户信息
		User dbUser = userService.queryLogin(user);
		// 3.判断用户信息是否存在
		if (dbUser != null) {
			//登陆成功
			return "main";
		}else {
			//登陆失败
			String errorMsg = "登陆账号或密码不正确，请重新输入。";
			model.addAttribute("errorMsg" ,errorMsg);
			return "redirect:login";
		}
	}
}
