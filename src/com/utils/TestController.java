package com.utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestController {
	@RequestMapping("upload.do")
	public String upload(@RequestParam MultipartFile[] attachs,HttpServletRequest request){
		String savepath= request.getSession().getServletContext().getRealPath("upload");
		for (MultipartFile attach : attachs) {
			if(!attach.isEmpty()){
				File savefile= new File(savepath+"/"+attach.getOriginalFilename());
				try {
					FileUtils.copyInputStreamToFile(attach.getInputStream(), savefile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.setAttribute("message", "文件上传成功");
			}
		}
		
		
		return "index";
	}
	
	@RequestMapping("ceshi.do")
	public String ceshi(HttpServletRequest request){
		request.setAttribute("message", "测试成功进来了");
		return "index";
		
	}
	
}
