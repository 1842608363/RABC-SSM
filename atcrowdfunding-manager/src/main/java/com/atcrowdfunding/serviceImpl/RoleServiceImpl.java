package com.atcrowdfunding.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.dao.RoleDao;
import com.atcrowdfunding.model.Role;
import com.atcrowdfunding.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> pageQueryData(Map<String, Object> map) {
		return roleDao.pageQueryData(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		return roleDao.pageQueryCount(map);
	}

	@Override
	public void deleteRole(Integer id) {
		roleDao.deleteRole(id);
	}

	@Override
	public void deleteRoles(Map<String, Object> map) {
		roleDao.deleteRoles(map);
	}

	@Override
	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	@Override
	public void insertRolePermission(Map<String, Object> map) {
		roleDao.deleteRolePermission(map);
		roleDao.insertRolePermission(map);
	}

}
