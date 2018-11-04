/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.how2java.tmall.service.OrderService;

@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	private SpringContextUtil() {
		
	}
	
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }
    
    public static <T> T getBean(Class<T> clazz) {
    	return applicationContext.getBean(clazz);
    }

}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
