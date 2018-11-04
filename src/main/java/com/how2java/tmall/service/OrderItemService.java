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

import com.how2java.tmall.dao.OrderItemDAO;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.SpringContextUtil;

@Service
@CacheConfig(cacheNames="orderItems")
public class OrderItemService {
	@Autowired OrderItemDAO orderItemDAO;
	@Autowired ProductImageService productImageService;

	public void fill(List<Order> orders) {
		for (Order order : orders) 
			fill(order);
	}
	@CacheEvict(allEntries=true)
	public void update(OrderItem orderItem) {
		orderItemDAO.save(orderItem);
	}

	
	public void fill(Order order) {
		OrderItemService orderItemService = SpringContextUtil.getBean(OrderItemService.class);
		List<OrderItem> orderItems = orderItemService.listByOrder(order);
		float total = 0;
		int totalNumber = 0;			
		for (OrderItem oi :orderItems) {
			total+=oi.getNumber()*oi.getProduct().getPromotePrice();
			totalNumber+=oi.getNumber();
			productImageService.setFirstProdutImage(oi.getProduct());
		}
		order.setTotal(total);
		order.setOrderItems(orderItems);
		order.setTotalNumber(totalNumber);		
		order.setOrderItems(orderItems);
	}
	
	@CacheEvict(allEntries=true)
    public void add(OrderItem orderItem) {
    	orderItemDAO.save(orderItem);
    }
	@Cacheable(key="'orderItems-one-'+ #p0")
    public OrderItem get(int id) {
    	return orderItemDAO.findOne(id);
    }

	@CacheEvict(allEntries=true)
    public void delete(int id) {
        orderItemDAO.delete(id);
    }

	
	
	
    public int getSaleCount(Product product) {
		OrderItemService orderItemService = SpringContextUtil.getBean(OrderItemService.class);
        List<OrderItem> ois =orderItemService.listByProduct(product);
        int result =0;
        for (OrderItem oi : ois) {
        	if(null!=oi.getOrder())
        	if(null!= oi.getOrder() && null!=oi.getOrder().getPayDate())
        		result+=oi.getNumber();
        }
        return result;
    }

    @Cacheable(key="'orderItems-uid-'+ #p0.id")
    public List<OrderItem> listByUser(User user) {
    	return orderItemDAO.findByUserAndOrderIsNull(user);
    }
    
    @Cacheable(key="'orderItems-pid-'+ #p0.id")
    public List<OrderItem> listByProduct(Product product) {
    	return orderItemDAO.findByProduct(product);
    }
    @Cacheable(key="'orderItems-oid-'+ #p0.id")
    public List<OrderItem> listByOrder(Order order) {
    	return orderItemDAO.findByOrderOrderByIdDesc(order);
    }
	
	
}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
