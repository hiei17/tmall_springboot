/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

    	HttpSession session = httpServletRequest.getSession();
        String contextPath=session.getServletContext().getContextPath();

        String[] requireAuthPages = new String[]{
        		"buy",
        		"alipay",
        		"payed",
        		"cart",
        		"bought",
        		"confirmPay",
        		"orderConfirmed",
        		
        		"forebuyone",
        		"forebuy",
        		"foreaddCart",
        		"forecart",
        		"forechangeOrderItem",
        		"foredeleteOrderItem",
        		"forecreateOrder",
        		"forepayed",
        		"forebought",
        		"foreconfirmPay",
        		"foreorderConfirmed",
        		"foredeleteOrder",
        		"forereview",
        		"foredoreview"
        		
        };
 
        
        String uri = httpServletRequest.getRequestURI();

        uri = StringUtils.remove(uri, contextPath+"/");
        String page = uri;



		if(begingWith(page, requireAuthPages)){
			Subject subject = SecurityUtils.getSubject();

			if(!subject.isAuthenticated()) {
				httpServletResponse.sendRedirect("login");
				return false;
			}
		}
        return true;   
    }

    private boolean begingWith(String page, String[] requiredAuthPages) {
    	boolean result = false;
    	for (String requiredAuthPage : requiredAuthPages) {
			if(StringUtils.startsWith(page, requiredAuthPage)) {
				result = true;	
				break;
			}
		}
    	return result;
	}

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
