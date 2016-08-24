package com.cheng.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class DiaryCategory extends Model<DiaryCategory>{
	
	/**
	 * 日记种类
	 */
	private static final long serialVersionUID = -7054415026782531571L;

	static Log log = Log.getLog(DiaryCategory.class);
	
	public static final DiaryCategory dao = new DiaryCategory();
	
}
