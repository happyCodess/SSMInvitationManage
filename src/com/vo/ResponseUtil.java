package com.vo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
	 public static void write(HttpServletResponse response,Object object)throws Exception{
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter out=response.getWriter();//println()可以输出对象
        	//outPrintWriter.write(JSONArray.toJSONString(resultMap));write()只能输出字符串或者html
	        out.println(object);
	        out.flush();
	        out.close();
	    }
	 private String status ;
	    
	    private String message;

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public ResponseUtil(String status, String message) {
	        this.status = status;
	        this.message = message;
	    }

}
