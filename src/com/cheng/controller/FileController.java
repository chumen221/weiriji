package com.cheng.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.cheng.interceptor.MultipartRequestInterceptor;
import com.cheng.utils.CommonUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

/**
 * @author cheng
 */

@Before(MultipartRequestInterceptor.class) 
public class FileController extends Controller {
	
	
	public void uploadfile(){
		
		UploadFile uploadFile = getFile("image", PathKit.getWebRootPath()
                + "/upload", 20 * 1024 * 1024, "utf-8"); // 最大上传20M的图片
		//获取文件名称
		// 异步上传时，无法通过uploadFile.getFileName()获取文件名
		String fileName = uploadFile.getFileName();
        
        // 异步上传时，无法通过File source = uploadFile.getFile();获取文件
        File source = new File(PathKit.getWebRootPath() + "/upload/" + fileName); // 获取临时文件对象
        
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String savePath = PathKit.getWebRootPath() + "/upload/"+ CommonUtils.getCurrentDate();
        
        if (".png".equals(extension) || ".jpg".equals(extension)
                || ".gif".equals(extension) || "jpeg".equals(extension)
                || "bmp".equals(extension)) {
            fileName = CommonUtils.getCurrentTime() + extension;

            try {
                FileInputStream fis = new FileInputStream(source);

                File targetDir = new File(savePath);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }

                File target = new File(targetDir, fileName);
                if (!target.exists()) {
                    target.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(target);
                byte[] bts = new byte[1024 * 20];
                while (fis.read(bts, 0, 1024 * 20) != -1) {
                    fos.write(bts, 0, 1024 * 20);
                }

                fos.close();
                fis.close();
                
                renderText("upload/" + CommonUtils.getCurrentDate() + "/" + fileName); // 相对地址，显示图片用
                source.delete();

            } catch (FileNotFoundException e) {
            	renderText("上传出现错误，请稍后再上传");
            } catch (IOException e) {
            	renderText("文件写入服务器出现错误，请稍后再上传");
            }catch (NullPointerException e) {
            	renderText("无法获取文件");
            }
            
            
        } else {
            source.delete();
            renderText("只允许上传png,jpg,jpeg,gif,bmp类型的图片文件");
        }
        
		
	}
}
