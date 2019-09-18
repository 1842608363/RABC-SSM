package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.atcrowdfunding.model.User;

@Repository
public interface UserDao {

	List<User> queryAll();

	User queryLogin(User user);

	List<User> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void insertUser(User user);

	User queryById(Integer id);

	void updateUser(User user);

	void deleteUser(Integer id);

	void deleteUsers(Map<String, Object> map);

	void insertUserRoles(Map<String, Object> map);

	void deleteUserRoles(Map<String, Object> map);

	List<Integer> queryRoleidsByUserid(Integer id);

}
