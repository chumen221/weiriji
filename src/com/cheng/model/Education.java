package com.cheng.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class Education extends Model<Education>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5003146172682044901L;
	
	static Log log = Log.getLog(Education.class);
	
	public static final Education dao = new Education();



}
