/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.how2java.tmall.interceptor.LoginInterceptor;
import com.how2java.tmall.interceptor.OtherInterceptor;

@Configuration
class WebMvcConfigurer extends WebMvcConfigurerAdapter{

	@Bean
	public OtherInterceptor getOtherIntercepter() {
		return new OtherInterceptor();
	}
	@Bean
	public LoginInterceptor getLoginIntercepter() {
		return new LoginInterceptor();
	}

	//注册拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry){

		//拦截所有
		registry.addInterceptor(getOtherIntercepter())
				.addPathPatterns("/**");

		//拦截所有
		registry.addInterceptor(getLoginIntercepter())
				.addPathPatterns("/**");
	}
}


/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
