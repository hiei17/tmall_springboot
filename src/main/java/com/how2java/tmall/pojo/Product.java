/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})//json忽略这2个字段
@Document(indexName = "tmall_springboot",type = "product")//es 对应索引
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	//外键
	@ManyToOne
	@JoinColumn(name="cid")
	private Category category;

	//如果既没有指明 关联到哪个Column,又没有明确要用@Transient忽略，那么就会自动关联到表对应的同名字段
	private String name;
	private String subTitle;
	private float originalPrice;
	private float promotePrice;
	private int stock;
	private Date createDate;

	@Transient
	private ProductImage firstProductImage;
	@Transient
	private List<ProductImage> productSingleImages;
	@Transient
	private List<ProductImage> productDetailImages;
	@Transient
	private int reviewCount;
	@Transient
	private int saleCount;


	@Override
	public String toString() {
		return "Product [id=" + id + ", category=" + category + ", name=" + name + ", subTitle=" + subTitle
				+ ", originalPrice=" + originalPrice + ", promotePrice=" + promotePrice + ", stock=" + stock
				+ ", createDate=" + createDate + ", firstProductImage=" + firstProductImage + ", reviewCount="
				+ reviewCount + ", saleCount=" + saleCount + "]";
	}



}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
