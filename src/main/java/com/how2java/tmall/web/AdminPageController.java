/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//后台所有不用数据的简单跳转
@Controller
public class AdminPageController {
	@GetMapping(value="/admin")
	public String admin(){
		return "redirect:admin_category_list";
	}

	@GetMapping(value="/admin_category_list")
	public String listCategory(){
		return "admin/listCategory";
	}

	@GetMapping(value="/admin_category_edit")
	public String editCategory(){
		return "admin/editCategory";

	}

	@GetMapping(value="/admin_order_list")
	public String listOrder(){
		return "admin/listOrder";

	}

	@GetMapping(value="/admin_product_list")
	public String listProduct(){
		return "admin/listProduct";

	}

	@GetMapping(value="/admin_product_edit")
	public String editProduct(){
		return "admin/editProduct";

	}
	@GetMapping(value="/admin_productImage_list")
	public String listProductImage(){
		return "admin/listProductImage";

	}

	@GetMapping(value="/admin_property_list")
	public String listProperty(){
		return "admin/listProperty";

	}

	@GetMapping(value="/admin_property_edit")
	public String editProperty(){
		return "admin/editProperty";

	}

	@GetMapping(value="/admin_propertyValue_edit")
	public String editPropertyValue(){
		return "admin/editPropertyValue";

	}

	@GetMapping(value="/admin_user_list")
	public String listUser(){
		return "admin/listUser";

	}




}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
