/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class PropertyValue {//属性值
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	//所属产品
    @ManyToOne
    @JoinColumn(name="pid")	
	private Product product;

    //锁描述的属性
    @ManyToOne
	@JoinColumn(name="ptid")
	private Property property;

    private String value;


	@Override
	public String toString() {
		return "PropertyValue [id=" + id + ", product=" + product + ", property=" + property + ", value=" + value + "]";
	}
	
	
}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
