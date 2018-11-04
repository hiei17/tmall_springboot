/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.PropertyDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.util.Page4Navigator;

@Service
@CacheConfig(cacheNames="properties")
public class PropertyService {
	
	@Autowired PropertyDAO propertyDAO;
	@Autowired CategoryService categoryService;

	@CacheEvict(allEntries=true)
	public void add(Property bean) {
		propertyDAO.save(bean);
	}

	@CacheEvict(allEntries=true)
	public void delete(int id) {
		propertyDAO.delete(id);
	}

	@Cacheable(key="'properties-one-'+ #p0")
	public Property get(int id) {
		return propertyDAO.findOne(id);
	}

	@CacheEvict(allEntries=true)
	public void update(Property bean) {
		propertyDAO.save(bean);
	}
	@Cacheable(key="'properties-cid-'+ #p0.id")
	public List<Property> listByCategory(Category category){
		return propertyDAO.findByCategory(category);
	}

	
	@Cacheable(key="'properties-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
	public Page4Navigator<Property> list(int cid, int start, int size,int navigatePages) {
    	Category category = categoryService.get(cid);
		
    	Sort sort = new Sort(Sort.Direction.DESC, "id");
    	Pageable pageable = new PageRequest(start, size, sort);    	
    	
    	Page<Property> pageFromJPA =propertyDAO.findByCategory(category,pageable);
    	
    	return new Page4Navigator<>(pageFromJPA,navigatePages);
    	
    	
	}	

}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
