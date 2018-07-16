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
	//����Ͱ͹�˾Java�����淶Ҫ��ӿ��в�Ҫдpublic�����Σ�Խ��Խ��
	//��ѯ�����б��Է�ҳ��ʽչ�֣�֧��ģ����ѯ
	List<Invitation> searchList(Pagination condition);
	//��ѯƥ��������������
	 Integer selectCountByParam(Pagination condition);
	//ɾ��һ������
	 Integer deleteInvitation(Integer id);
	//Excel�������ݿ�
	 void importExcel(InputStream in, MultipartFile file);
	//Excel����
	 XSSFWorkbook exportExcel() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException;
	//Word����ʱ�����ݿ��ȡ����
	 void downLoadWord(HttpServletResponse response,HttpServletRequest request);
	 //�������
	Integer addInvitation(Invitation invitation);
	//�޸�����
	Integer updateInvitation(Invitation invitation);
	
}
