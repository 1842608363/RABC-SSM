package com.atcrowdfunding.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.dao.PermissionDao;
import com.atcrowdfunding.model.Permission;
import com.atcrowdfunding.model.User;
import com.atcrowdfunding.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Permission queryRootPermission() {
		return permissionDao.queryRootPermission();
	}

	@Override
	public List<Permission> queryChildPermissions(Integer pid) {
		return permissionDao.queryChildPermissions(pid);
	}

	@Override
	public List<Permission> queryAll() {
		return permissionDao.queryAll();
	}

	@Override
	public void insertPermission(Permission permission) {
		permissionDao.insertPermission(permission);
	}

	@Override
	public Permission queryById(Integer id) {
		return permissionDao.queryById(id);
	}

	@Override
	public void updatePermission(Permission permission) {
		permissionDao.updatePermission(permission);
	}

	@Override
	public void deletePermission(Permission permission) {
		permissionDao.deletePermission(permission);
	}

	@Override
	public List<Integer> queryPermissionIdsByRoleid(Integer roleid) {
		return permissionDao.queryPermissionIdsByRoleid(roleid);
	}

	@Override
	public List<Permission> queryPermissionByUser(User dbUser) {
		return permissionDao.queryPermissionByUser(dbUser);
	}
}
