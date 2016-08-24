package com.cheng.controller;

import com.cheng.weixin.controller.WeiXinOauthController;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.api.SnsApi;
import com.jfinal.weixin.sdk.jfinal.ApiController;

public class EditController extends ApiController{
	
	static Log log = Log.getLog(EditController.class);
	
	/**
	 * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		
		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}
	
	

	
	public void index() {
		String code=getPara("code");
		String state=getPara("state");
		String openId=null;
		ApiResult apiResult=null;
		System.out.println("这里是code"+code);

			String appId=ApiConfigKit.getApiConfig().getAppId();
			System.out.println("这里是appId"+appId);
			String secret=ApiConfigKit.getApiConfig().getAppSecret();
			//通过code换取网页授权access_token
			SnsAccessToken snsAccessToken=SnsAccessTokenApi.getSnsAccessToken(appId,secret,code);
			System.out.println("这里是snsAccessToken"+snsAccessToken);
//			String json=snsAccessToken.getJson();
			String token=snsAccessToken.getAccessToken();
			openId=snsAccessToken.getOpenid();
			//拉取用户信息(需scope为 snsapi_userinfo)
			apiResult=SnsApi.getUserInfo(token, openId);
		//log.warn("getUserInfo:"+apiResult.getJson());
		System.out.println("这里是openid"+openId);
		render("edit.html");
	}
	

}
