/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.UserDAO;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.SpringContextUtil;

@Service
@CacheConfig(cacheNames="users")
public class UserService {
	
	@Autowired UserDAO userDAO;

	
	public boolean isExist(String name) {
		UserService userService = SpringContextUtil.getBean(UserService.class);
		User user = userService.getByName(name);
		return null!=user;
	}

	@Cacheable(key="'users-one-name-'+ #p")
	public User getByName(String name) {
		return userDAO.findByName(name);
	}
	
	

	@Cacheable(key="'users-one-name-'+ #p0 +'-password-'+ #p1")
	public User get(String name, String password) {
		return userDAO.getByNameAndPassword(name,password);
	}

	@Cacheable(key="'users-page-'+#p0+ '-' + #p1")
	public Page4Navigator<User> list(int start, int size, int navigatePages) {
    	Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		Page pageFromJPA =userDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA,navigatePages);
	}

	@CacheEvict(allEntries=true)
	public void add(User user) {
		userDAO.save(user);
	}	

}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
