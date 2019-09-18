package com.atcrowdfunding.service;

import java.util.List;

import com.atcrowdfunding.model.Permission;
import com.atcrowdfunding.model.User;

public interface PermissionService {

	Permission queryRootPermission();

	List<Permission> queryChildPermissions(Integer pid);

	List<Permission> queryAll();

	void insertPermission(Permission permission);

	Permission queryById(Integer id);

	void updatePermission(Permission permission);

	void deletePermission(Permission permission);

	List<Integer> queryPermissionIdsByRoleid(Integer roleid);

	List<Permission> queryPermissionByUser(User dbUser);

}
