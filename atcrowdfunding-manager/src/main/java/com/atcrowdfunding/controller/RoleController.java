package com.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.model.AJAXResult;
import com.atcrowdfunding.model.Page;
import com.atcrowdfunding.model.Role;
import com.atcrowdfunding.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String index() {
		return "role/index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "role/add";
	}
	
	@RequestMapping("/assign")
	public String assign() {
		return "role/assign";
	}
	
	@RequestMapping("/edit")
	public String edit() {
		return "role/edit";
	}
	
	/**
	 * 分页查询+搜索
	 * @param queryText 搜索关键字
	 * @param pageNum 当前页码
	 * @param pageSize 页面容量
	 * @return
	 */
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
			
			List<Role> list = roleService.pageQueryData(map);
			
			//当前页
			//数据总条数
			int totalSize = roleService.pageQueryCount(map);
			//最后一页(总页码)
			int totalNum = 0;
			if (totalSize % pageSize ==0) {
				totalNum = totalSize / pageSize;
			} else {
				totalNum = totalSize / pageSize + 1;
			}
			
			Page<Role> rolepage = new Page<Role>();
			rolepage.setList(list);
			rolepage.setPageNum(pageNum);
			rolepage.setTotalNum(totalNum);
			rolepage.setTotalSize(totalSize);
			
			ajaxResult.setData(rolepage);
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		try {
			roleService.deleteRole(id);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Integer[] roleid) {
		AJAXResult result = new AJAXResult();
		try {
			for (Integer integer : roleid) {
				System.out.println(integer);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleids", roleid);
			roleService.deleteRoles(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAsssign(Integer roleid,Integer[] permissionids){
		AJAXResult result = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleid",roleid);
			map.put("permissionids",permissionids);
			roleService.insertRolePermission(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

}
