package com.cheng.weixin.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class TDiaryType extends Model<TDiaryType> {

	public static final TDiaryType dao = new TDiaryType();
	
	public List<TDiaryType> getDiaryTypeByUserId(String userId) {
		return dao.find("select * from t_diary_type where USER_ID='*' or USER_ID=?", userId);
	}
	
	public List<TDiaryType> getDiaryTypeByUserIdOrderBySeqDesc(String userId) {
		return dao.find("select * from t_diary_type where USER_ID='*' or USER_ID=? order by seq desc", userId);
	}
	
	public TDiaryType getDiaryTypeByTypeNameAndUserId(String typeName, String userId) {
		return dao.findFirst("select * from t_diary_type where type_name=? and (USER_ID='*' or USER_ID=?)", typeName, userId);
	}
	
	public TDiaryType getDiaryTypeBySeqAndUserId(String seq, String userId) {
		return dao.findFirst("select * from t_diary_type where seq=? and (USER_ID='*' or USER_ID=?)", seq, userId);
	}
}
