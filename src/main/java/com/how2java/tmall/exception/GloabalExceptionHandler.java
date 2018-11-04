/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
public class GloabalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

    	e.printStackTrace();

    	Class constraintViolationException = Class.forName("org.hibernate.exception.ConstraintViolationException");
    	if(null!=e.getCause()  && constraintViolationException==e.getCause().getClass()) {
    		return "违反了约束，多半是外键约束";
    	}
        return e.getMessage();
    }

}


/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
