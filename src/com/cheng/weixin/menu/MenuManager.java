/**
 * Copyright (c) 2015-2016, Javen Zhou  (javen205@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.cheng.weixin.menu;


import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;

/**
 * 菜单管理器类 
 * @author Javen
 * 2016年5月30日
 */
public class MenuManager  {
	 public static void main(String[] args) { 
 
			//将菜单对象转换成json字符串
			//有问题：主菜单项多了一个type
			String jsonMenu = JsonKit.toJson(getTestMenu()).toString();
			System.out.println(jsonMenu);
			ApiConfig ac = new ApiConfig();
				
			// 配置微信 API 相关常量
			PropKit.use("weiriji_config.txt");
			ac.setAppId(PropKit.get("appId"));
			System.out.println(PropKit.get("appId"));
			ac.setAppSecret(PropKit.get("appSecret"));
			ApiConfigKit.setThreadLocalApiConfig(ac);
			   
			//创建菜单
			ApiResult apiResult=MenuApi.createMenu(jsonMenu);
			System.out.println(apiResult.getJson());
	 }  
	 
	 
	  
	    /** 
	     * 组装菜单数据 
	     *  
	     * @return 
	     */  
	    private static Menu getTestMenu() {
	    	
	    	ClickButton btn11 = new ClickButton();  
	        btn11.setName("短日记");  
	        btn11.setType("click");  
	        btn11.setKey("rselfmenu_1_1");
	  	  
	        ViewButton btn21 = new ViewButton();  
	        btn21.setName("长日记");  
	        btn21.setType("view");  
	        btn21.setUrl("http://www.baidu.com"); 
	        
	        ViewButton btn31 = new ViewButton();  
	        btn31.setName("留言墙");  
	        btn31.setType("view");  
	        btn31.setUrl("http://www.baidu.com");  
	          
	        ClickButton btn32 = new ClickButton();  
	        btn32.setName("发送位置");  
	        btn32.setType("location_select");  
	        btn32.setKey("rselfmenu_3_2"); 
	  
	            
	        ComButton mainBtn3 = new ComButton();  
	        mainBtn3.setName("更多");  
	        mainBtn3.setSub_button(new Button[] { btn31, btn32});  
	  
	        /** 
	         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
	         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
	         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
	         */
	        Menu menu = new Menu();  
	        menu.setButton(new Button[] { btn11, btn21, mainBtn3 });  
	        return menu;  
	    }
}
