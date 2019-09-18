package com.atcrowdfunding.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.model.AJAXResult;
import com.atcrowdfunding.model.Page;
import com.atcrowdfunding.model.Role;
import com.atcrowdfunding.model.User;
import com.atcrowdfunding.service.RoleService;
import com.atcrowdfunding.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String index() {
		return "user/index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "user/add";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object deleteUser(Integer id) {
		AJAXResult result = new AJAXResult();
		try {
			userService.deleteUser(id);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Integer[] userid) {
		AJAXResult result = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userids", userid);
			userService.deleteUsers(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	@RequestMapping("/assign")
	public String assign(Integer id,Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		
		List<Role> roles = roleService.queryAll();
		
		List<Role> assignRoles = new ArrayList<Role>();
		List<Role> unassignRoles = new ArrayList<Role>();
		//获取关系表的数据
		List<Integer> roleids = userService.queryRoleidsByUserid(id);
		for (Role role : roles) {
			if (roleids.contains(role.getId())) {
				assignRoles.add(role);
			} else {
				unassignRoles.add(role);
			}
		}
		model.addAttribute("assignRoles",assignRoles);
		model.addAttribute("unassignRoles",unassignRoles);
		return "user/assign";
	}
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer userid,Integer[] unassingroleids) {
		AJAXResult result = new AJAXResult();
		try {
			//增加关系表数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("roleids", unassingroleids);
			userService.insertUserRoles(map);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/dounAssign")
	public Object dounAssign(Integer userid,Integer[] assignroleids) {
		AJAXResult result = new AJAXResult();
		try {
			//删除关系表数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("roleids", assignroleids);
			userService.deleteUserRoles(map);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("update")
	public Object update(User user) {
		AJAXResult result = new AJAXResult();
		try {
			userService.updateUser(user);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(User user) {
		AJAXResult result = new AJAXResult();
		try {
			// 管理员设置默认密码
			SimpleDateFormat sfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setCreatetime(sfFormat.format(new Date()));
			user.setPassword("123456");
			userService.insertUser(user);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText,Integer pageNum,Integer pageSize) {
		AJAXResult ajaxResult = new AJAXResult();
		try {
			//分页查询
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (pageNum-1)*pageSize);
			map.put("size", pageSize);
			map.put("queryText", queryText);
			
			List<User> list = userService.pageQueryData(map);
			
			//当前页
			//数据总条数
			int totalSize = userService.pageQueryCount(map);
			//最后一页(总页码)
			int totalNum = 0;
			if (totalSize % pageSize ==0) {
				totalNum = totalSize / pageSize;
			} else {
				totalNum = totalSize / pageSize + 1;
			}
			
			Page<User> userpage = new Page<User>();
			userpage.setList(list);
			userpage.setPageNum(pageNum);
			userpage.setTotalNum(totalNum);
			userpage.setTotalSize(totalSize);
			
			ajaxResult.setData(userpage);
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	/**
	 * 用户首页
	 * @return
	 */
	@RequestMapping("/index1")
	public String index(@RequestParam(required=false,defaultValue = "1")Integer pageNum,@RequestParam(required=false,defaultValue = "2")Integer pageSize,Model model) {
		
		//分页查询
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageNum-1)*pageSize);
		map.put("size", pageSize);
		
		List<User> list = userService.pageQueryData(map);
		model.addAttribute("users", list);
		
		//当前页
		model.addAttribute("pageNum", pageNum);
		//数据总条数
		int totalSize = userService.pageQueryCount(map);
		//最后一页(总页码)
		int totalNum = 0;
		if (totalSize % pageSize ==0) {
			totalNum = totalSize / pageSize;
		} else {
			totalNum = totalSize / pageSize + 1;
		}
		model.addAttribute("totalNum", totalNum);
		
		return "user/index";
	}

}
