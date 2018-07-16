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
//@Scope("prototype")代表多例
@Controller
@Scope("prototype")
public class InvitationController {
	//通过自动装配。替代setter
	@Autowired
	private InvitationService invService;
	private  static  final Logger logger = Logger.getLogger(InvitationController.class);
	//接收前台请求
	@RequestMapping("init.do")
	@ResponseBody
	public String searchList(@RequestParam(value="page",required=false) String page,
							 @RequestParam(value="rows",required=false) String rows,
							 HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		 Pagination condition=new Pagination();
	     Map<String,Object> map=new HashMap<String,Object>();
	     String author=request.getParameter("author");
		//组装关键字,在条件参数前后增加%,实现模糊查询
		if(author!=null){
			condition.setAuthor("%"+author+"%");
		}
		//参数page代表当前页，rows代表每页行数
		Integer startRow=1;
		Integer endRow=11;
		Integer newPage=1;
		Integer newRows=10;
		if(page==null || rows==null){
			//计算起始行，每页条数*（当前页-1）
			startRow =(newPage-1)*newRows;
			condition.setStartRow(startRow);
			//计算结束行=起始行+每页条数
			endRow=startRow+newRows+1;
			condition.setEndRow(endRow);
		}else{
			startRow=(Integer.valueOf(page)-1)*(Integer.valueOf(rows));
			condition.setStartRow(startRow);
			//计算结束行=起始行+每页条数
			endRow=startRow+Integer.valueOf(rows)+1;
			condition.setEndRow(endRow);
		}
		
		//调用查询列表方法。查询帖子列表
		List<Invitation> invList=invService.searchList(condition);
		// 格式化日期
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(invList, jsonConfig);
		//调用查询帖子总条数方法，查询帖子总条数
		Integer totalRows=invService.selectCountByParam(condition);
		logger.info("哈哈哈");
		JSONObject result=new JSONObject();
		result.put("total", totalRows);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);  
		//跳转index.jsp页面显示帖子列表  对应spring-servlet.xml文件中的前缀和后缀,形成index.jsp跳转路径
		//return "result";
		return null;
	}
	//添加
	@RequestMapping("save.do")
	public String addInvitation(Invitation invitation,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		String date=request.getParameter("createdate");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		invitation.setCreateDate(sdf.parse(date));
		Integer result=invService.addInvitation(invitation);
		JSONObject job=new JSONObject();
		if(result>0){
			job.put("message", "添加成功");
		}else{
			job.put("message", "添加失败");
		}
		ResponseUtil.write(response, job);
		return null;
	}
	//修改
	@RequestMapping("update.do")
	public String updateInvitation(Invitation invitation,HttpServletResponse response,HttpServletRequest request) throws Exception{
		String date=request.getParameter("createdate");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		invitation.setCreateDate(sdf.parse(date));
		Integer result=invService.updateInvitation(invitation);
		JSONObject job=new JSONObject();
		if(result>0){
			job.put("message", "修改成功");
		}else{
			job.put("message", "修改失败");
		}
		ResponseUtil.write(response, job);
		return null;
	}
	//删除
	@RequestMapping("delete.do")
	public String deleteInvitation(@RequestParam(value="ids")String ids,HttpServletResponse response) throws Exception{
		String[] idStr = ids.split(",");
			Integer result=0;
			for (String id : idStr) {
			 result=invService.deleteInvitation(Integer.parseInt(id));
			}
			JSONObject job=new JSONObject();
			if(result>0){
				job.put("message", "修改成功");
			}else{
				job.put("message", "修改失败");
			}
			ResponseUtil.write(response, job);
			return null;
	}
	
	//接收前台请求，执行Excel上传功能
	@RequestMapping("import.do")
	public String importExcel(@RequestParam("file1") CommonsMultipartFile file,HttpServletRequest request, Invitation model,HttpServletResponse response) throws Exception{
		/* //上传文件存在的位置(项目在Tomcat中实际发布运行的根路径)
        String path=req.getSession().getServletContext().getRealPath("upload");
        //获得上传文件的名字
        String filename=file.getOriginalFilename();
        //加载上传的文件
        File targetfile=new File(path,filename);
        if(!targetfile.exists()){
            //查看目录是否存在，如何不存在就创建这个目录
            targetfile.mkdirs();
        }
        try{
            //保存文件
            file.transferTo(targetfile);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/
		// 文件保存路径
		//String filePath = realPath + "upload/"+ file.getOriginalFilename();
		
		int adminId = 1;
		     //获取上传的文件
		     //MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		    // MultipartFile file = multipart.getFile("import");//upfile是页面中input的name
		     InputStream in = file.getInputStream();
		     String result="";
		     //数据导入
		     try{
		    	 invService.importExcel(in,file);
		     }catch(Exception exception){
		    	 result="上传失败";
		    	 exception.printStackTrace();
		     }
		     in.close();
		     JSONObject job=new JSONObject();
				if(result.equals("")){
					job.put("message", "上传成功");
				}else{
					job.put("message", "上传失败");
				}
				ResponseUtil.write(response, job);
		     return null;
	}
	//接收前台请求，执行Excel下载功能
	@RequestMapping("export.do")
	public String downloadExcel(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException{
			//文件名
		    String fileName ="帖子信息表";
		    if(fileName!=""){
		        response.reset(); //清除buffer缓存
		       
		        XSSFWorkbook workbook=null;
		        //导出Excel对象
		        workbook = invService.exportExcel();
		        OutputStream output;
		        try {
		        	 // 指定下载的文件名
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
	//接收前台请求，执行Word下载功能
	@RequestMapping("downLoad.do")
	public void downLoadWord(HttpServletResponse response,HttpServletRequest request){
		//从数据库获取数据
		invService.downLoadWord(response,request);
		
	}
}

