package com.cheng.weixin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cheng.utils.StringUtils;
import com.cheng.weixin.helper.PublicHelper;
import com.cheng.weixin.model.TDiary;
import com.cheng.weixin.model.TDiaryType;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;

public class ShortDiaryService {

	static Log log = Log.getLog(ShortDiaryService.class);
	
	static TDiaryType defaultDiaryType = null;
	
	static {
		defaultDiaryType = TDiaryType.dao.getDiaryTypeBySeqAndUserId("1", "*");
	}
	
	/**
	 * 记录每个用户最后的记录日记时间
	 * :TODO 数据量大后是否记录在表中？暂时为了省事用map
	 */
	public static Map<String, Integer> diaryRecordTimeMap = new HashMap<String, Integer>();

	public static Map<String, Integer> diaryIdMap = new HashMap<String, Integer>();
	
	public String ADD_DIARY_RET_1 = "{0},你好！你刚才输入的短日记已经记录成功，你可以对该条日记定义标签，回复数字：\n";
	
	/**
	 *  " 1 定义标签为待办"
	 */
	public String ADD_DIARY_RET_2 = " {0} 定义标签为\"{1}\"\n";
	
	public String ADD_DIARY_RET_3 = "输入小于5个文字，自定义标签，一分钟内有效！\n"
			+ "点击【我的记录】菜单查看你的历史记录\n";
	
	public String ADD_TAG_RET = "日记标签已定义生成，回复\"{0}\"可查看";

	public String dealShortDiary(InTextMsg inTextMsg) {
		log.debug("begin dealShortDiary");
		
		//addShortDiary(inTextMsg);
		String fromUser = inTextMsg.getFromUserName();
		// 先判断该次回复是否是设置标签
		String ret = "";
		if (judgeIsSetDiaryTag(inTextMsg)) {
			int typeId = getDiaryType(inTextMsg);
			if (0 == typeId) {
				typeId = addTag(inTextMsg);
			}
				
			// 设置标签
			modDiaryType(typeId, fromUser);
			
			ret = createReturn4AddTag(inTextMsg);
		}
		else {
			int diaryId = addShortDiary(inTextMsg);
			addDiaryRecordMap(inTextMsg, diaryId);
			ret = createReturn4AddDiary(inTextMsg);
		}
		log.debug("end dealShortDiary");
		return ret;
	}

	private void modDiaryType(int typeId, String fromUser) {
		int diaryId = diaryIdMap.get(fromUser);
		TDiary.dao.findById(diaryId).set("TYPE_ID", typeId).update();
		
		// 标签设置完后需要移除
		diaryRecordTimeMap.remove(fromUser);
		//diaryIdMap.remove(fromUser);
	}

	private void addDiaryRecordMap(InTextMsg inTextMsg, int diaryId) {
		diaryRecordTimeMap.put(inTextMsg.getFromUserName(), inTextMsg.getCreateTime());
		diaryIdMap.put(inTextMsg.getFromUserName(), diaryId);
		
		log.debug("put diaryRecordMap.key=" + inTextMsg.getFromUserName() + ",value =" + diaryRecordTimeMap.get(inTextMsg.getFromUserName()));
	}

	private int getDiaryType(InTextMsg inTextMsg) {
		TDiaryType tag = TDiaryType.dao.getDiaryTypeByTypeNameAndUserId(inTextMsg.getContent(), inTextMsg.getFromUserName());
		if (null == tag || null == tag.getInt("TYPE_ID")) {
			tag = TDiaryType.dao.getDiaryTypeBySeqAndUserId(inTextMsg.getContent(), inTextMsg.getFromUserName());
		}
		return null == tag ? 0 : tag.getInt("TYPE_ID");
	}

	private String createReturn4AddTag(InTextMsg inTextMsg) {
		return StringUtils.format(ADD_TAG_RET, inTextMsg.getContent());
	}

	private int addTag(InTextMsg inTextMsg) {
		int newSeg = 10;
		List<TDiaryType> tagList = TDiaryType.dao.getDiaryTypeByUserIdOrderBySeqDesc(inTextMsg.getFromUserName());
		if (PublicHelper.isNotEmpty(tagList)) {
			newSeg = tagList.get(0).getInt("SEQ") + 1;
		}
		
		TDiaryType tag = new TDiaryType();
		//tag.set("TYPE_ID", "");
		tag.set("SEQ", newSeg);
		tag.set("TYPE_NAME", inTextMsg.getContent());
		tag.set("USER_ID", inTextMsg.getFromUserName());
		tag.save();
		return tag.getInt("TYPE_ID");
	}

	/**
	 * 1分钟内的小于5个文字的回复才是设置标签
	 * @param inTextMsg
	 * @return
	 */
	private boolean judgeIsSetDiaryTag(InTextMsg inTextMsg) {
		log.debug("begin judgeIsSetDiaryTag. content:" + inTextMsg.getContent() + " msgCreateTime:" + inTextMsg.getCreateTime() + " lastRecordTime" + diaryRecordTimeMap.get(inTextMsg.getFromUserName()));
		// 先判断回复长度
		if (inTextMsg.getContent().trim().length() > 5) {
			return false;
		}
		
		// 在判断时间间隔
		Integer msgCreateTime = inTextMsg.getCreateTime();
		String msgFromUser = inTextMsg.getFromUserName();
		Integer lastRecordTime =  diaryRecordTimeMap.get(msgFromUser);
		if (null == lastRecordTime || (msgCreateTime.intValue() - lastRecordTime.intValue()) > 60) {
			return false;
		}
		return true;
	}

	/**
	 * 构造tag回复信息
	 * @param inTextMsg
	 * @return
	 */
	private String createReturn4AddDiary(InTextMsg inTextMsg) {
		StringBuffer sb = new StringBuffer();
		sb.append(StringUtils.format(ADD_DIARY_RET_1, inTextMsg.getFromUserName()));
		
		List<TDiaryType> typeList = TDiaryType.dao.getDiaryTypeByUserId(inTextMsg.getFromUserName());
		
		if (PublicHelper.isNotEmpty(typeList)) {
			for (TDiaryType type : typeList) {
				sb.append(StringUtils.format(ADD_DIARY_RET_2, type.getInt("SEQ"), type.getStr("TYPE_NAME")));
			}
		}
		
		
		sb.append(ADD_DIARY_RET_3);
		
		return sb.toString();
	}

	public int addShortDiary(InTextMsg inTextMsg) {
		TDiary diary = new TDiary();
		//diary.set("DIARY_ID", "");
		diary.set("CATEGORY_ID", 1);
		diary.set("TYPE_ID", defaultDiaryType.get("TYPE_ID"));
		diary.set("DIARY_SUBJECT", "");
		diary.set("DIARY_CONTENT", inTextMsg.getContent());
		diary.set("USER_ID", inTextMsg.getFromUserName());
		diary.set("PUBLISH_TIME", new Date());
		diary.set("PUBLIC_FLAG", true);
		//diary.set("VIEW_NUM", 0);
		//diary.set("FEEDBACK_NUM", 0);
		diary.set("WEATHER", "");
		diary.set("LOCATION", "");
		
		diary.save();
		
		return diary.getInt("DIARY_ID");
	}	
}
