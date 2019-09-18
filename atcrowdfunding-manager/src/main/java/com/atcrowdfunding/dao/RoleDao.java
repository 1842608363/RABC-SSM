package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.model.Role;

public interface RoleDao {

	List<Role> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void deleteRoles(Map<String, Object> map);

	void deleteRole(Integer id);

	List<Role> queryAll();

	void insertRolePermission(Map<String, Object> map);

	void deleteRolePermission(Map<String, Object> map);

}
