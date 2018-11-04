/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.dao;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.User;

public interface OrderDAO extends JpaRepository<Order,Integer>{
    public List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
