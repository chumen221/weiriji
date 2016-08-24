package com.cheng.model;


import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class Sex extends Model<Sex>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6488668641624074759L;
	
	static Log log = Log.getLog(Sex.class);
	
	public static final Sex dao = new Sex();
	
	
	
	public Sex findBySexName(int sexid){
		return this.findFirst("select * from m_sex where sex_id=?", sexid);
	}

	

}
