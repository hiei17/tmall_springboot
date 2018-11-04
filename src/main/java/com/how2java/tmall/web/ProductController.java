/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
 
@RestController
public class ProductController {
	@Autowired ProductService productService;
	@Autowired CategoryService categoryService;
	@Autowired ProductImageService productImageService;

    @GetMapping("/categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
    	start = start<0?0:start;
    	Page4Navigator<Product> page =productService.list(cid, start, size,5 );

        productImageService.setFirstProdutImages(page.getContent());

    	return page;
    }
    
    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) throws Exception {
    	Product bean=productService.get(id);
    	return bean;
    }
    
    
    @PostMapping("/products")
    public Object add(@RequestBody Product bean) throws Exception {
    	bean.setCreateDate(new Date());
        productService.add(bean);
        return bean;
    }
    
    
    
    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        productService.delete(id);
        return null;
    }
    
    @PutMapping("/products")
    public Object update(@RequestBody Product bean) throws Exception {
        productService.update(bean);
        return bean;
    }
}




/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
