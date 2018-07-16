package com.service.impl;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.dao.InvitationMapper;

import com.pojo.Invitation;
import com.service.InvitationService;
import com.utils.Base64Utils;
import com.utils.ExcelUtil;
import com.utils.WordUtil;
import com.vo.ExcelBean;
import com.vo.Pagination;
//���@Serviceע�⣬��ʾ����ʵ�����Զ�ע�뵽spring������
//����������ע�⣬����ɾ��
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {
	//protected final Logger log =LoggerFactory.getLogger(this.getClass());
	//�Զ����أ�������ҪPage���͵����ԣ�ͨ��@autowired�Զ�װ�䷽ʽ����������ȥ���ҵ��������ظ�������,���setter����
	@Autowired
	private InvitationMapper invMapper;
	@Autowired
	private WordUtil wordUtil;
	public List<Invitation> searchList( Pagination condition) {
		// TODO Auto-generated method stub
		return invMapper.searchList(condition);
	}
	public Integer selectCountByParam(Pagination condition) {
		// TODO Auto-generated method stub
		return invMapper.selectCountByParam(condition);
	}

	public Integer deleteInvitation(Integer id) {
		// TODO Auto-generated method stub
		return invMapper.deleteInvitation(id);
	}
	//excel����
	public void importExcel(InputStream in, MultipartFile file) {
		// TODO Auto-generated method stub
		    List<List<Object>> listob = null;
		   // List<Invitation> salaryList = new ArrayList<Invitation>();  
			try {
				listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    //����listob���ݣ������ݷŵ�List��
		    for (int i = 0; i < listob.size(); i++) {
		        List<Object> ob = listob.get(i);
		        Invitation invitation = new Invitation();

		        //ͨ������ʵ�ְ�ÿһ�з�װ��һ��model�У��ٰ����е�model��List����װ��
		        invitation.setId(Integer.valueOf(String.valueOf(ob.get(0))));
		        invitation.setTitle(String.valueOf(ob.get(1)));
		        invitation.setSummary(String.valueOf(ob.get(2)));
		        invitation.setAuthor(String.valueOf(ob.get(3)));
		        try {
					invitation.setCreateDate(sdf.parse(String.valueOf(ob.get(4))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //�������ݲ���
			    invMapper.insertInfoBatch(invitation);
		        //salaryList.add(invitation);
		    }
		    //������������
		 /* log.info("���뿪ʼ");
		  invMapper.insertInfoBatch(salaryList);*/
		   
		
	}
	//excel����
	public XSSFWorkbook exportExcel() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException  {
		// TODO Auto-generated method stub
		
		    //����������ѯ���ݣ�������װ�ص�һ��list��
		    List<Invitation> list = invMapper.selectInvitation();
		    List<ExcelBean> excel=new ArrayList<ExcelBean>();
		    Map<Integer,List<ExcelBean>> map=new LinkedHashMap<Integer, List<ExcelBean>>();
		    XSSFWorkbook xssfWorkbook=null;
		    //���ñ�����
		    excel.add(new ExcelBean("���ӱ��","id",0));
		    excel.add(new ExcelBean("���ӱ���","title",0));
		    excel.add(new ExcelBean("����ժҪ","summary",0));
		    excel.add(new ExcelBean("��������","author",0));
		    excel.add(new ExcelBean("��������","createDate",0));
		    map.put(0, excel);
		    String sheetName =  "��Ϣ��";
		    //����ExcelUtil�ķ���
		    xssfWorkbook = ExcelUtil.createExcelFile(Invitation.class, list, map, sheetName);
		    return xssfWorkbook;
		
		
	}
	//Word����
	public void downLoadWord(HttpServletResponse response,HttpServletRequest request) {
		// TODO Auto-generated method stub
		//����������ѯ���ݣ�������װ�ص�һ��list��
	    List<Invitation> list = invMapper.selectInvitations();
	    HashMap<String, Object> map =new HashMap<String, Object>();
	    Base64Utils base64=new Base64Utils();
	    for(int i=0;i<list.size();i++){
	    	map.put("id", list.get(i).getId());
	    	map.put("summary", list.get(i).getSummary());
	    	map.put("title", list.get(i).getTitle());
	    	map.put("author", list.get(i).getAuthor());
	    	map.put("createdate", list.get(i).getCreateDate());
	    	map.put("image", base64.getImageBase("E:/����.png"));
	    }
	   
	    wordUtil.downLoadDoc("word����.doc","word.ftl",map,request,response);
	}
	//�������
	public Integer addInvitation(Invitation invitation) {
		// TODO Auto-generated method stub
		return invMapper.addInvitation(invitation);
	}
	//�޸�����
	public Integer updateInvitation(Invitation invitation) {
		// TODO Auto-generated method stub
		return invMapper.updateInvitation(invitation);
	}

	

	

}
