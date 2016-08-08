# weiriji
微信微日记基本框架
微日记框架使用jfinal框架开发，运行项目之前，请认真阅读/doc目录下jfinal使用手册；
##项目主要结构
/src/com/cheng 为核心代码文件
/src/com 同级目录为配置文件
/src/weiriji_config.txt 为核心配置文件：配置jdbc驱动，微信测试帐号回调Token,appId,appSecret;测试帐号下，消息加解密方式默认为明文，否则会出现xml解析出错

mail.properties 文件（邮箱注册、找回密码会使用到）

sms.properties(发送短信相关的配置，如果不是用可以忽略)

job.properties(如果项目中需要使用到定时器可以参考)

##项目核心包
项目核心包为jfinal-weixin-1.8-bin-with-src.jar及jfinal-2.2-bin-with-src.jar，其他依赖包自行百度

jfinal-weixin API参考文档：http://www.dreamlu.net/jfinal-weixin/apidocs/

jfinal框架社区提问：http://www.jfinal.com/

##项目运行
配置微信平台开发本地调试环境，在/src/weiriji_config.txt中更改自己微信测试帐号appId,appSecret；

在项目中已添加jetty包，找到/src/com/cheng/common/APPConfig.java文件，以 run as java application方式运行，http运行端口已设置为80端口； 

微信平台服务器配置地址：http://你自己的域名/msg   
Token:weiriji

至此，项目已可运行！

