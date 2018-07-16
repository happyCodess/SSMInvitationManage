<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帖子列表显示页面</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/jquery-easyui/themes/default/easyui.css" />  
<link rel="stylesheet" type="text/css" href="<%=basePath%>/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/common.js"></script> 
<script type="text/javascript" src="<%=basePath%>/jquery-easyui/utils.js"></script> 
<script type="text/javascript">
	$(function(){
		
		//-----------------------------对于form表单的验证 ---------------------------------------------------------------------------
		
		//数值验证组件 
		//$('#age').numberbox({
			//min:0 , //允许的最小值
			//max:150 , //允许的最大值
			//required:true , //必填字段  定义是否字段应被输入
			//missingMessage:'年龄必填!' ,   //当文本框是空时出现的提示文字
			//precision:0    //显示在小数点后面的最大精度
		//});
		
		//日期组件
		//$('#birthday').datebox({
			//required:true ,   //必填字段  定义是否字段应被输入
			//missingMessage:'生日必填!' ,  //当文本框是空时出现的提示文字
			//editable:false  //定义是否用户可以往文本域中直接输入文字
		//});
		
		//日期时间组件
		$('#createdate').datetimebox({
			required:true , 
			missingMessage:'时间必填!' ,
			editable:false   //定义是否用户可以往文本域中直接输入文字
		});
		
		
		var flag ;		//undefined 判断新增和修改方法 
		
		///---------------------datagrid部分----------------------------------------------------------------------------------
		$('#tt').datagrid({
			idField: 'id',    //只要创建数据表格 就必须要加 ifField
			url: '${pageContext.request.contextPath}/init.do',
			title: '帖子信息',
			//width: '1000',
			height:450 ,
			fitColumns: true,				//宽度自适应
			striped: true ,					//隔行变色特性
			rownumbers:true,				//显示行号
			//singleSelect:true ,				//单选模式 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用 
				{							//如果需要多选，需要禁止单选模式 
					field:'ck' ,
					width:50 ,
					checkbox: true
				}
			]],
			columns:[[
				{field:'id',title:'编号',width:120},
				{
					field:'author',
					title:'作者',
					width:120,
					align:'center' ,   //居中显示
					styler:function(value , record){
						if(value == 'admin'){
							//return 'background:blue;';   //如果用户名为admin，变蓝色
						}
					}
				},
				{
					field:'title',
					title:'标题',
					width:120,
				},
				{field:'summary',title:'摘要',width:120},
				{field:'createDate',title:'创建日期',width:120},
				/* {
					field:'password',
					title:'密码',
					width:120,
					hidden: true   //将密码隐藏
				}, */
/* 				{
					field:'sex',
					title:'性别',
					width:120,
					formatter:function(value , record , index){
						if(value == 1){
							return '<span style=color:red; >男</span>' ;
						} else if( value == 2){
							return '<span style=color:green; >女</span>' ; 
						}
						//console.info(value);
						//console.info(record);
						//console.info(index); 
					}
				},
				{
					field:'city',
					title:'城市',
					width:120,
					formatter:function(value , record , index){
						var str = '';
						$.ajax({
							type:'post' , 
							url : 'user/getCityName' ,
							cache:false ,
							async: false ,		//同步请求
							data:{id:value},
							dataType:'json' ,
							success:function(result){   
								//str = result ;    //dataType:'text' 
								str = result.name ; //dataType:'json' 
							}
						});
						return str ;
					}
					
				}, */

			]],
			pagination: true ,   //在底部显示分页栏
			pageSize: 10 ,		 //每页显示多少个
			pageList:[5,10,15,20,50], //初始化页面尺寸的选择列表
			toolbar:[
				{
				    iconCls:"icon-add",//按钮上的图标
				        text:"添加帖子", //按钮的文字
				        handler:function(){
				        	flag = 'add';   //改变flag的值
							$('#myform').get(0).reset();
				            $("#mydialog").dialog("open");
				        }
				},
				{
				    iconCls:"icon-edit",//按钮上的图标
				        text:"编辑帖子",//按钮的文字
				        handler:function(){
				        	flag = 'edit';   //改变flag的值
							var arr =$('#tt').datagrid('getSelections');    //获取被选中的行，返回的是数组
							if(arr.length != 1){
								$.messager.show({
									title:'提示信息!',
									msg:'只能选择一行记录进行修改!'
								});
							} else {
								$('#mydialog').dialog({
									title:'修改帖子'
								});
								$('#mydialog').dialog('open'); //打开窗口
								$('#myform').get(0).reset();   //清空表单数据 
								$('#myform').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
									id:arr[0].id ,
									author:arr[0].author ,
									title:arr[0].title ,
									summary:arr[0].summary ,
									createdate:arr[0].createDate,
									
									/* sex:arr[0].sex ,
									age:arr[0].age ,
									city:arr[0].city , */

								});
							}
				        }
				},
				{
				    iconCls:"icon-remove",//按钮上的图标
				        text:"删除帖子",//按钮的文字
				        handler:function(){
				            //console.log('删除');   //在浏览器控制台打印日志
				        	var arr =$('#tt').datagrid('getSelections');
							if(arr.length <=0){
								$.messager.show({
									title:'提示信息!',
									msg:'至少选择一行记录进行删除!'
								});
							} else {
								
								$.messager.confirm('提示信息' , '确认删除?' , function(r){
										if(r){
												var ids = '';
												for(var i =0 ;i<arr.length;i++){
													ids += arr[i].id + ',' ;
												}
												ids = ids.substring(0 , ids.length-1);
												$.post('delete.do' , {ids:ids} , function(result){
													//1 刷新数据表格 
													$('#tt').datagrid('reload');
													//2 清空idField   
													$('#tt').datagrid('clearSelections');   //unselectAll取消选中当前页所有的行。   clearSelections清除所有的选择。
													//3 给提示信息 
													$.messager.show({
														title:"提示信息!" , 
														msg:result.message
													});
												},"json");
										} else {
											return ;
										}
								});
							}
				        }
				},
				{
				    iconCls:"icon-print",//按钮上的图标
				        text:"导入帖子", //按钮的文字
				        handler:function(){
				            $("#import").dialog("open");
				        }
				},
				{
				    iconCls:"icon-print",//按钮上的图标
				        text:"导出帖子", //按钮的文字
				        handler:function(){
				        	flag = 'downloadExcel';   //改变flag的值
				           
				        }
				},
				{
				    iconCls:"icon-search",//按钮上的图标
				        text:"查询帖子",//按钮的文字
				        handler:function(){
				            //console.log('查询');   //在浏览器控制台打印日志
				            $('#lay').layout('expand' , 'north');
				        }
				}
			]
		});
		
		
		
		
		//-----------添加和修改提交表单方法-------------------------------------------------------------------------------------------------------------
		$('#btn1').click(function(){
				if($('#myform').form('validate')){
					$.ajax({
						type: 'post' ,
						url: flag=='add'?'save.do':'update.do' ,
					    //url:'user/save',
						cache:false ,
						data:$('#myform').serialize() ,
						dataType:'json' ,
						success:function(result){
							//1 关闭窗口
							$('#mydialog').dialog('close');
							//2刷新datagrid 
							$('#tt').datagrid('reload');
							//3 提示信息
							$.messager.show({
								title:"提示信息!" , 
								msg:result.message
							});
						} ,
						error:function(result){
							$.meesager.show({
								title:result.status , 
								msg:result.message
							});
						}
					});
				} else {
					$.messager.show({
						title:'提示信息!' ,
						msg:'数据验证不通过,不能保存!'
					});
				}
		});
		//-----------Excel导入提交表单方法-------------------------------------------------------------------------------------------------------------
		//这里是个注意点。必须强调用form的submit来提交才行，如果用ajax的post提交方式正常的输入框参数会提交到后台，但是文件提交到后台的仅仅是文件名，只有用submit提交才能把文件内容也提交过去。
		//ajax通过datatype：json参数可以识别后台传递过来的json数据，而下述form表单不是ajax，所以需要eval()来获取json数据为什么要 eval这里要添加 “("("+data+")");//”呢？ 原因在于：eval本身的问题。 由于json是以”{}”的方式来开始以及结束的，在JS中，它会被当成一个语句块来处理，所以必须强制性的将它转换成一种表达式。 加上圆括号的目的是迫使eval函数在处理JavaScript代码的时候强制将括号内的表达式（expression）转化为对象，而不是作为语句（statement）来执行。举一个例子，例如对象字面量{}，如若不加外层的括号，那么eval会将大括号识别为JavaScript代码块的开始和结束标记，那么{}将会被认为是执行了一句空语句。

		$('#btn3').click(function(){
				if($('#file1').filebox('getValue')!=null && $('#file1').filebox('getValue')!=""){
					 $("#importExcel").form('submit',{
						type: 'post' ,
						url:  'import.do' ,
						cache:false ,
						onSubmit:function(){
							//开启上传文件转圈等待
							MaskUtil.mask();
						},
						success:function(result){
							result=eval('('+result+')');
							//关闭上传文件转圈等待效果
							MaskUtil.unmask();
							//1 关闭窗口
							$('#import').dialog('close');
							//2刷新datagrid 
							$('#tt').datagrid('reload');
							//3 提示信息
							$.messager.show({
								title:"提示信息!" , 
								msg:result.message
							});
						} ,
						error:function(result){
							$.meesager.show({
								title:result.status , 
								msg:result.message
							});
						}
					});
				} else {
					$.messager.show({
						title:'提示信息!' ,
						msg:'请选择需要上传的 excel!'
					});
				}
		});
		
		/**
		 * 关闭窗口方法
		 */
		$('#btn2').click(function(){
			$('#mydialog').dialog('close');
		});
		$('#btn4').click(function(){
			$('#import').dialog('close');
		});
		
		
		$('#searchbtn').click(function(){
			$('#tt').datagrid('load' ,serializeForm($('#mysearch')));
		});
		
		//查询时清空按钮
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#tt').datagrid('load' ,{});  //清空数据
		});
	});
	
	//js方法：序列化表单 			
	function serializeForm(form){
		var obj = {};
		$.each(form.serializeArray(),function(index){
			if(obj[this['name']]){
				obj[this['name']] = obj[this['name']] + ','+this['value'];
			} else {
				obj[this['name']] =this['value'];
			}
		});
		return obj;
	}
	function fileexport() { //点击确定按钮的时候
	    var file = ($("#FileUpload").val());
	    if (file == "") {
	        $.messager.alert('Excel批量用户导入', '请选择将要上传的文件!');        
	    }
	   /*  else {
	        var stuff = file.match(/^(.*)(/.)(.{1,8})$/)[3];
	        if (stuff != 'xls') {
	            $.messager.alert('Excel批量用户导入','文件类型不正确，请选择.xls文件!'); 
	        }
	        else {
	            $('#exportload').window('open'); //显示进度条

	            $('#uploadexcel').form('submit', {
	                onSubmit: function() {
	                    return true;
	                },
	                success: function(data) {
	                      $.messager.alert('Excel批量用户导入', data, 'info');
	                }

	            }); 
	            
	        }
	    }  */
	}

</script>
</head>
<body>

	<div id="lay" class="easyui-layout" fit=true style="width: 100%; height: 1000px">
		<!-- 帖子搜索部分 -->
	    <div region="north" title="帖子查询" split="true" collapsed=true  style="height: 100px;">
	    	<div style="margin-left: 100px;margin-top: 20px;">
	    		<form id="mysearch" method="post">
						作者:<input name="author" class="easyui-validatebox"  value="" />
						创建时间:<input name="createdate"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="" />	
						<a id="searchbtn" class="easyui-linkbutton">查询</a> <a id="clearbtn" class="easyui-linkbutton">清空</a>
				</form>
	    	</div>
	    </div>
		<!-- 帖子列表部分 -->
	    <div region="center"  style="padding: 5px; background: #eee;">
			<table id="tt"></table>
	    </div>
	</div>
	

	
	
	<!-- modal：模态窗口      draggable：窗口不可拖动    closed：默认关闭-->
	<div id="mydialog" title="新增帖子" modal=true  draggable=false class="easyui-dialog" closed=true style="width:300px;">
   		<form id="myform" action="" method="post">
   				<input type="hidden" name="id" value="" />
   				<table>
   					<tr>
   						<td>标题:</td>
   						<td><input type="text" name="title" class="easyui-validatebox" required=true  missingMessage="标题必填!" value="" /></td>
   					</tr>
   					<tr>
   						<td>作者:</td>
   						<td><input type="text" name="author" class="easyui-validatebox" required=true  missingMessage="作者必填!"   value="" /></td>
   					</tr>
   					
   					<!-- <tr>
   						<td>性别:</td>
   						<td>
   							男<input type="radio" checked="checked" name="sex" value="1" />
   							女<input type="radio" name="sex" value="2" />
   						</td>
   					</tr>
   					<tr>
   						<td>年龄:</td>
   						<td><input id="age" type="text"  name="age" value="" /></td>
   					</tr>
   					<tr>
   						<td>所属城市:</td>
   						<td>
   							<input name="city" class="easyui-combobox" url="user/getCity" valueField="id" textField="name"  value="" />
   						</td>
   					</tr> 
   					<tr>
   						<td>所属城市:</td>
   						<td>
   							<input name="city" class="easyui-combobox" url="user/getCity" valueField="id" textField="name"  value="" />
   						</td>
   					</tr>
   					-->
   					
   					<tr>
   						<td>摘要:</td>
   						<td><input id="summary" type="text" name="summary" value="" /></td>
   					</tr>
   					<tr>
   						<td>创建日期:</td>
   						<td><input id="createdate" style="width:160px;"  type="text" name="createdate" value="" /></td>
   					</tr>
   					<tr align="center">
   						<td colspan="2">
   							<a id="btn1" class="easyui-linkbutton">确定</a>
   							<a id="btn2" class="easyui-linkbutton">关闭</a>
   						</td>
   					</tr>   					 					    					    					    					    					    					    					    					
   				</table>
   			</form> 			
		</div>
<div id="import" class="easyui-window" title="Excel批量导入" closed="true" modal="true" minimizable="false" maximizable="false" collapsible="false" iconCls="icon-save" style="width:300px;height:200px;padding:5px;background: #fafafa;">
		    <div class="easyui-layout" fit="true">			     
		       <div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;" mce_style="padding:10px;background:#fff;border:1px solid #ccc;">                			      	   			    
			       <form id="importExcel" enctype="multipart/form-data"  method="post">
                       导入Excel文件:<input class="easyui-filebox" id="file1" name="file1" data-options="buttonText:'浏览',prompt:'请选择文件'"/>                   
                    </form>
			    </div>
			        
			        <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
				        <a id="btn3" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
				        <a id="btn4" class="easyui-linkbutton" iconCls="icon-cancel">取消</a> 
			        </div> 
			    
		    </div> 
	    </div>    

</body>
  

</html>
