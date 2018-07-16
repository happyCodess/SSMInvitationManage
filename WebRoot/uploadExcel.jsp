<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附件上传</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/jquery-easyui/themes/default/easyui.css" />  
<link rel="stylesheet" type="text/css" href="<%=basePath%>/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/common.js"></script> 

</head>
<body>
	<div id="mydialog" class="easyui-dialog" title="导入excel文件" style="width: 400px; height: 300px;" data-options="modal:true">
		<form id="uploadExcel"  method="post" enctype="multipart/form-data">  
   			选择文件：　<input id = "excel" name = "excel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">  
		</form>  
		<div style="text-align: center; padding: 5px 0;">
			<a id = "booten" href="javascript:void(0)" class="easyui-linkbutton"
				onclick="uploadExcel()" style="width: 80px" id="tt">导入</a>
		</div>
	</div>
</body>
</html>