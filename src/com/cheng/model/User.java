package com.cheng.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5539087322235844211L;
	 
	static Log log = Log.getLog(User.class);
	
	public static final User dao = new User();

	
	
}
