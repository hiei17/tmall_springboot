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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.how2java.tmall.dao.OrderDAO;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.SpringContextUtil;

@Service
@CacheConfig(cacheNames="orders")
public class OrderService {
	public static final String waitPay = "waitPay";
	public static final String waitDelivery = "waitDelivery";
	public static final String waitConfirm = "waitConfirm";
	public static final String waitReview = "waitReview";
	public static final String finish = "finish";
	public static final String delete = "delete";	
	
	@Autowired OrderDAO orderDAO;
	
	@Autowired OrderItemService orderItemService;


	
	public List<Order> listByUserWithoutDelete(User user) {
		OrderService orderService = SpringContextUtil.getBean(OrderService.class);
		List<Order> orders = orderService.listByUserAndNotDeleted(user);
		orderItemService.fill(orders);
		return orders;
	}


	@Cacheable(key="'orders-uid-'+ #p0.id")
	public List<Order> listByUserAndNotDeleted(User user) {
		return orderDAO.findByUserAndStatusNotOrderByIdDesc(user, OrderService.delete);
	}
	
	

	@CacheEvict(allEntries=true)
	public void update(Order bean) {
		orderDAO.save(bean);
	}

	@Cacheable(key="'orders-page-'+#p0+ '-' + #p1")
	public Page4Navigator<Order> list(int start, int size, int navigatePages) {
    	Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		Page pageFromJPA =orderDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA,navigatePages);
	}

	@CacheEvict(allEntries=true)
	public void add(Order order) {
		orderDAO.save(order);
	}
	
	@CacheEvict(allEntries=true)
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order order, List<OrderItem> ois) {
        float total = 0;
        add(order);

        if(false)
            throw new RuntimeException();

        for (OrderItem oi: ois) {
            oi.setOrder(order);
            orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

	@Cacheable(key="'orders-one-'+ #p0")
	public Order get(int oid) {
		return orderDAO.findOne(oid);
	}


	public void cacl(Order o) {
		List<OrderItem> orderItems = o.getOrderItems();
		float total = 0;
		for (OrderItem orderItem : orderItems) {
			total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
		}
		o.setTotal(total);
	}
	
	public void removeOrderFromOrderItem(List<Order> orders) {
		for (Order order : orders) {
			removeOrderFromOrderItem(order);
		}
	}

	public void removeOrderFromOrderItem(Order order) {
		List<OrderItem> orderItems= order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrder(null);
		}
	}

}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
