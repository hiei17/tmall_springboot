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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String password;
	private String name;
	private String salt;
	
	@Transient
	private String anonymousName;



	public String getAnonymousName(){

		if(null!=anonymousName) {
			return anonymousName;
		}

		anonymousName=	covertName(name);
		return anonymousName;
	}

	private String covertName(String name) {
		if(name.length()<=1) {
			return "*";
		}
		if(name.length()==2) {
			return name.substring(0,1) +"*";
		}

		char[] cs = name.toCharArray();
		for (int i = 1; i < cs.length-1; i++) {
			cs[i]='*';
		}
		return new String(cs);

	}


}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
