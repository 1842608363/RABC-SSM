package com.atcrowdfunding.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.model.Permission;
import com.atcrowdfunding.model.User;

public interface PermissionDao {

	@Select("select * from t_permission where pid is null")
	Permission queryRootPermission();

	@Select("select * from t_permission where pid = #{pid}")
	List<Permission> queryChildPermissions(Integer pid);

	@Select("select * from t_permission")
	List<Permission> queryAll();

	void insertPermission(Permission permission);

	@Select("select * from t_permission where id = #{id}")
	Permission queryById(Integer id);

	void updatePermission(Permission permission);

	void deletePermission(Permission permission);

	@Select("select permissionid from t_role_permission where roleid = #{roleid}")
	List<Integer> queryPermissionIdsByRoleid(Integer roleid);

	List<Permission> queryPermissionByUser(User dbUser);

}
