/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
 
@RestController
public class PropertyController {
	@Autowired PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
    	start = start<0?0:start;
    	Page4Navigator<Property> page =propertyService.list(cid, start, size,5); 
        return page;
    }
    
    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id) throws Exception {
    	Property bean=propertyService.get(id);
    	return bean;
    }
    
    
    @PostMapping("/properties")
    public Object add(@RequestBody Property bean) throws Exception {
        propertyService.add(bean);
        return bean;
    }
    
    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        propertyService.delete(id);
        return null;
    }
    
    @PutMapping("/properties")
    public Object update(@RequestBody Property bean) throws Exception {
        propertyService.update(bean);
        return bean;
    }



}


/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
