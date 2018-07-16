package com.controller;

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pojo.Invitation;
import com.service.InvitationService;
import com.sun.jmx.snmp.Timestamp;
import com.utils.DateJsonValueProcessor;
import com.vo.Pagination;
import com.vo.ResponseUtil;
//@Scope("prototype")�������
@Controller
@Scope("prototype")
public class InvitationController {
	//ͨ���Զ�װ�䡣���setter
	@Autowired
	private InvitationService invService;
	private  static  final Logger logger = Logger.getLogger(InvitationController.class);
	//����ǰ̨����
	@RequestMapping("init.do")
	@ResponseBody
	public String searchList(@RequestParam(value="page",required=false) String page,
							 @RequestParam(value="rows",required=false) String rows,
							 HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		 Pagination condition=new Pagination();
	     Map<String,Object> map=new HashMap<String,Object>();
	     String author=request.getParameter("author");
		//��װ�ؼ���,����������ǰ������%,ʵ��ģ����ѯ
		if(author!=null){
			condition.setAuthor("%"+author+"%");
		}
		//����page����ǰҳ��rows����ÿҳ����
		Integer startRow=1;
		Integer endRow=11;
		Integer newPage=1;
		Integer newRows=10;
		if(page==null || rows==null){
			//������ʼ�У�ÿҳ����*����ǰҳ-1��
			startRow =(newPage-1)*newRows;
			condition.setStartRow(startRow);
			//���������=��ʼ��+ÿҳ����
			endRow=startRow+newRows+1;
			condition.setEndRow(endRow);
		}else{
			startRow=(Integer.valueOf(page)-1)*(Integer.valueOf(rows));
			condition.setStartRow(startRow);
			//���������=��ʼ��+ÿҳ����
			endRow=startRow+Integer.valueOf(rows)+1;
			condition.setEndRow(endRow);
		}
		
		//���ò�ѯ�б�������ѯ�����б�
		List<Invitation> invList=invService.searchList(condition);
		// ��ʽ������
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(invList, jsonConfig);
		//���ò�ѯ������������������ѯ����������
		Integer totalRows=invService.selectCountByParam(condition);
		logger.info("������");
		JSONObject result=new JSONObject();
		result.put("total", totalRows);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);  
		//��תindex.jspҳ����ʾ�����б�  ��Ӧspring-servlet.xml�ļ��е�ǰ׺�ͺ�׺,�γ�index.jsp��ת·��
		//return "result";
		return null;
	}
	//���
	@RequestMapping("save.do")
	public String addInvitation(Invitation invitation,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		String date=request.getParameter("createdate");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		invitation.setCreateDate(sdf.parse(date));
		Integer result=invService.addInvitation(invitation);
		JSONObject job=new JSONObject();
		if(result>0){
			job.put("message", "��ӳɹ�");
		}else{
			job.put("message", "���ʧ��");
		}
		ResponseUtil.write(response, job);
		return null;
	}
	//�޸�
	@RequestMapping("update.do")
	public String updateInvitation(Invitation invitation,HttpServletResponse response,HttpServletRequest request) throws Exception{
		String date=request.getParameter("createdate");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		invitation.setCreateDate(sdf.parse(date));
		Integer result=invService.updateInvitation(invitation);
		JSONObject job=new JSONObject();
		if(result>0){
			job.put("message", "�޸ĳɹ�");
		}else{
			job.put("message", "�޸�ʧ��");
		}
		ResponseUtil.write(response, job);
		return null;
	}
	//ɾ��
	@RequestMapping("delete.do")
	public String deleteInvitation(@RequestParam(value="ids")String ids,HttpServletResponse response) throws Exception{
		String[] idStr = ids.split(",");
			Integer result=0;
			for (String id : idStr) {
			 result=invService.deleteInvitation(Integer.parseInt(id));
			}
			JSONObject job=new JSONObject();
			if(result>0){
				job.put("message", "�޸ĳɹ�");
			}else{
				job.put("message", "�޸�ʧ��");
			}
			ResponseUtil.write(response, job);
			return null;
	}
	
	//����ǰ̨����ִ��Excel�ϴ�����
	@RequestMapping("import.do")
	public String importExcel(@RequestParam("file1") CommonsMultipartFile file,HttpServletRequest request, Invitation model,HttpServletResponse response) throws Exception{
		/* //�ϴ��ļ����ڵ�λ��(��Ŀ��Tomcat��ʵ�ʷ������еĸ�·��)
        String path=req.getSession().getServletContext().getRealPath("upload");
        //����ϴ��ļ�������
        String filename=file.getOriginalFilename();
        //�����ϴ����ļ�
        File targetfile=new File(path,filename);
        if(!targetfile.exists()){
            //�鿴Ŀ¼�Ƿ���ڣ���β����ھʹ������Ŀ¼
            targetfile.mkdirs();
        }
        try{
            //�����ļ�
            file.transferTo(targetfile);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/
		// �ļ�����·��
		//String filePath = realPath + "upload/"+ file.getOriginalFilename();
		
		int adminId = 1;
		     //��ȡ�ϴ����ļ�
		     //MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		    // MultipartFile file = multipart.getFile("import");//upfile��ҳ����input��name
		     InputStream in = file.getInputStream();
		     String result="";
		     //���ݵ���
		     try{
		    	 invService.importExcel(in,file);
		     }catch(Exception exception){
		    	 result="�ϴ�ʧ��";
		    	 exception.printStackTrace();
		     }
		     in.close();
		     JSONObject job=new JSONObject();
				if(result.equals("")){
					job.put("message", "�ϴ��ɹ�");
				}else{
					job.put("message", "�ϴ�ʧ��");
				}
				ResponseUtil.write(response, job);
		     return null;
	}
	//����ǰ̨����ִ��Excel���ع���
	@RequestMapping("export.do")
	public String downloadExcel(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException{
			//�ļ���
		    String fileName ="������Ϣ��";
		    if(fileName!=""){
		        response.reset(); //���buffer����
		       
		        XSSFWorkbook workbook=null;
		        //����Excel����
		        workbook = invService.exportExcel();
		        OutputStream output;
		        try {
		        	 // ָ�����ص��ļ���
			        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName,"UTF-8")+".xlsx");
			        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			        response.setHeader("Pragma", "no-cache");
			        response.setHeader("Cache-Control", "no-cache");
			        response.setDateHeader("Expires", 0);
		            output = response.getOutputStream();
		            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
		            bufferedOutPut.flush();
		            workbook.write(bufferedOutPut);
		            bufferedOutPut.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		return null;
	}
	//����ǰ̨����ִ��Word���ع���
	@RequestMapping("downLoad.do")
	public void downLoadWord(HttpServletResponse response,HttpServletRequest request){
		//�����ݿ��ȡ����
		invService.downLoadWord(response,request);
		
	}
}

