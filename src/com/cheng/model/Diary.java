package com.cheng.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class Diary extends Model<Diary>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5160785670621892414L;
	
	static Log log = Log.getLog(Diary.class);
	
	public static final Diary dao = new Diary();



}
