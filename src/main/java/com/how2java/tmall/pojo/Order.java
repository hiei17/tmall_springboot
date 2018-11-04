/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.how2java.tmall.service.OrderService;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "order_")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="uid")
	private User user;
    
	
	private String orderCode;
	private String address;
	private String post;
	private String receiver;
	private String mobile;
	private String userMessage;
	private Date createDate;
	private Date payDate;
	private Date deliveryDate;
	private Date confirmDate;
	private String status;
	
	@Transient
	private List<OrderItem> orderItems;
	@Transient
	private float total;
	@Transient
	private int totalNumber;
	@Transient
	private String statusDesc;
	

	

	
	public String getStatusDesc(){

		if(null==statusDesc) {

			statusDesc=covertFromStatus();
		}

		return statusDesc;
	}

	private String covertFromStatus() {
		String desc ="未知";
		switch(status){
			case OrderService.waitPay:
				desc="待付";
				break;
			case OrderService.waitDelivery:
				desc="待发";
				break;
			case OrderService.waitConfirm:
				desc="待收";
				break;
			case OrderService.waitReview:
				desc="等评";
				break;
			case OrderService.finish:
				desc="完成";
				break;
			case OrderService.delete:
				desc="刪除";
				break;
			default:
		}
		return desc;
	}


}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
