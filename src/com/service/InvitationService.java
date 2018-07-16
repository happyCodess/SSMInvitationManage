package com.service;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.pojo.Invitation;
import com.vo.Pagination;

public interface InvitationService {
	//阿里巴巴公司Java开发规范要求接口中不要写public等修饰，越简单越好
	//查询帖子列表，以分页形式展现，支持模糊查询
	List<Invitation> searchList(Pagination condition);
	//查询匹配条件的总条数
	 Integer selectCountByParam(Pagination condition);
	//删除一个帖子
	 Integer deleteInvitation(Integer id);
	//Excel导入数据库
	 void importExcel(InputStream in, MultipartFile file);
	//Excel导出
	 XSSFWorkbook exportExcel() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException;
	//Word导出时从数据库获取数据
	 void downLoadWord(HttpServletResponse response,HttpServletRequest request);
	 //添加帖子
	Integer addInvitation(Invitation invitation);
	//修改帖子
	Integer updateInvitation(Invitation invitation);
	
}
