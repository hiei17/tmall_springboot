/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.util;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
//判断某个端口是否启动。 因为部分同学常常忘记启动 redis服务器，而导致系统无法运行， 这个工具的作用，是帮助检查是否启动了
public class PortUtil {

	public static boolean testPort(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			ss.close();
			return false;
		} catch (java.net.BindException e) {
			return true;
		} catch (IOException e) {
			return true;
		}
	}


	public static void checkPort(int port, String server, boolean shutdown) {
		if(!testPort(port)) {
			if(shutdown) {
				String message =String.format("在端口 %d 未检查得到 %s 启动%n",port,server);
				JOptionPane.showMessageDialog(null, message);
				System.exit(1);
			}
			else {
				String message =String.format("在端口 %d 未检查得到 %s 启动%n,是否继续?",port,server);
			    if(JOptionPane.OK_OPTION != 	JOptionPane.showConfirmDialog(null, message)) 
					System.exit(1);
			    
				
			}
		}
	}

}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
