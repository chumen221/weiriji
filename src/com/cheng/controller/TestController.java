package com.cheng.controller;


import com.cheng.model.Sex;
import com.jfinal.core.Controller;

public class TestController extends Controller{
	
	public void index(){
		
		Sex sex=Sex.dao.findBySexName(1);
		
		String sexname=null;
		
		sexname=sex.getStr("SEX_NAME");
		
		System.out.println("打印在这里："+sex);
		
		System.out.println("打印在这里："+sexname);
		
		
		renderText(sexname);
	}

}
