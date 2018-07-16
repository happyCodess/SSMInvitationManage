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
//添加@Service注解，表示将该实现类自动注入到spring容器中
//添加事物管理注解，用于删除
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {
	//protected final Logger log =LoggerFactory.getLogger(this.getClass());
	//自动倒回，例如需要Page类型的属性，通过@autowired自动装配方式，从容器中去查找到，并返回给该属性,替代setter方法
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
	//excel导入
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
		    //遍历listob数据，把数据放到List中
		    for (int i = 0; i < listob.size(); i++) {
		        List<Object> ob = listob.get(i);
		        Invitation invitation = new Invitation();

		        //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
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
		        //单条数据插入
			    invMapper.insertInfoBatch(invitation);
		        //salaryList.add(invitation);
		    }
		    //批量插入数据
		 /* log.info("插入开始");
		  invMapper.insertInfoBatch(salaryList);*/
		   
		
	}
	//excel导出
	public XSSFWorkbook exportExcel() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException  {
		// TODO Auto-generated method stub
		
		    //根据条件查询数据，把数据装载到一个list中
		    List<Invitation> list = invMapper.selectInvitation();
		    List<ExcelBean> excel=new ArrayList<ExcelBean>();
		    Map<Integer,List<ExcelBean>> map=new LinkedHashMap<Integer, List<ExcelBean>>();
		    XSSFWorkbook xssfWorkbook=null;
		    //设置标题栏
		    excel.add(new ExcelBean("帖子编号","id",0));
		    excel.add(new ExcelBean("帖子标题","title",0));
		    excel.add(new ExcelBean("帖子摘要","summary",0));
		    excel.add(new ExcelBean("帖子作者","author",0));
		    excel.add(new ExcelBean("创建日期","createDate",0));
		    map.put(0, excel);
		    String sheetName =  "信息表";
		    //调用ExcelUtil的方法
		    xssfWorkbook = ExcelUtil.createExcelFile(Invitation.class, list, map, sheetName);
		    return xssfWorkbook;
		
		
	}
	//Word导出
	public void downLoadWord(HttpServletResponse response,HttpServletRequest request) {
		// TODO Auto-generated method stub
		//根据条件查询数据，把数据装载到一个list中
	    List<Invitation> list = invMapper.selectInvitations();
	    HashMap<String, Object> map =new HashMap<String, Object>();
	    Base64Utils base64=new Base64Utils();
	    for(int i=0;i<list.size();i++){
	    	map.put("id", list.get(i).getId());
	    	map.put("summary", list.get(i).getSummary());
	    	map.put("title", list.get(i).getTitle());
	    	map.put("author", list.get(i).getAuthor());
	    	map.put("createdate", list.get(i).getCreateDate());
	    	map.put("image", base64.getImageBase("E:/测试.png"));
	    }
	   
	    wordUtil.downLoadDoc("word下载.doc","word.ftl",map,request,response);
	}
	//添加帖子
	public Integer addInvitation(Invitation invitation) {
		// TODO Auto-generated method stub
		return invMapper.addInvitation(invitation);
	}
	//修改帖子
	public Integer updateInvitation(Invitation invitation) {
		// TODO Auto-generated method stub
		return invMapper.updateInvitation(invitation);
	}

	

	

}
