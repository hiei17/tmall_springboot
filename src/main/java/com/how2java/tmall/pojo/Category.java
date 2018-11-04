/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
//jpa 默认会使用 hibernate, 在 jpa 工作过程中，就会创造代理类来继承 Category ，并添加 handler 和 hibernateLazyInitializer 这两个无须 json 化的属性，
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;


    @Transient  //非数据库字段
    List<Product> products;
    @Transient
    List<List<Product>> productsByRow;


    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
