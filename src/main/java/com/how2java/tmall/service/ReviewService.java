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
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.ReviewDAO;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Review;

@Service
@CacheConfig(cacheNames="reviews")
public class ReviewService {

	@Autowired ReviewDAO reviewDAO;
	@Autowired ProductService productService;

	@CacheEvict(allEntries=true)
    public void add(Review review) {
    	reviewDAO.save(review);
    }

	@Cacheable(key="'reviews-pid-'+ #p0.id")
    public List<Review> list(Product product){
        List<Review> result =  reviewDAO.findByProductOrderByIdDesc(product);
        return result;
    }

	@Cacheable(key="'reviews-count-pid-'+ #p0.id")
    public int getCount(Product product) {
    	return reviewDAO.countByProduct(product);
    }
	
}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
