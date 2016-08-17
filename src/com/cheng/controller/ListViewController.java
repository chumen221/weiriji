package com.cheng.controller;

import com.jfinal.core.Controller;

public class ListViewController extends Controller{
	
	public void mylist() {
		
		
		render("my_list.html");
	}
	
	public void showdetail() {
		
		render("detail.html");
	}

}
