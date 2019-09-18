package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.model.Role;

public interface RoleService {

	List<Role> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void deleteRole(Integer id);

	void deleteRoles(Map<String, Object> map);

	List<Role> queryAll();

	void insertRolePermission(Map<String, Object> map);

}
