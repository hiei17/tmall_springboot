/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.test;
  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
  
public class TestTmall {
  
    public static void main(String args[]){
        //准备分类测试数据：
  
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
  
        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall_springboot?useUnicode=true&characterEncoding=utf8",
                        "root", "admin");
                Statement s = c.createStatement();
        )
        {
            for (int i = 1; i <=10 ; i++) {
                String sqlFormat = "insert into category values (null, '测试分类%d')";
                String sql = String.format(sqlFormat, i);
                s.execute(sql);
            }
             
            System.out.println("已经成功创建10条分类测试数据");
  
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
    }
  
}
/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
