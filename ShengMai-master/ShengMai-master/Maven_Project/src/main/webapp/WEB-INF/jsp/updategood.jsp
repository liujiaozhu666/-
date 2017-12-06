<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'updateFood.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/page_common.js"></script>
<link href="css/common_style_blue.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="css/index_1.css" />

</head>

<body>
	<!-- 页面标题 -->
	<div id="TitleArea">
		<div id="TitleArea_Head"></div>
		<div id="TitleArea_Title">
			<div id="TitleArea_Title_Content">


				<img border="0" width="13" height="13"
					src="images/title_arrow.gif" /> 更新新菜品



			</div>
		</div>
		<div id="TitleArea_End"></div>
	</div>

	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<!-- 表单内容 -->
		<form action="updateGood.do" method="post" enctype="multipart/form-data">
			<!-- 本段标题（分段标题） -->
			<div class="ItemBlock_Title">
				<img width="4" height="7" border="0"
					src="images/item_point.gif"> 菜品信息&nbsp;
			</div>
			<!-- 本段表单字段 -->
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<div class="ItemBlock2">
						<table cellpadding="0" cellspacing="0" class="mainForm">
							<tr>
								<td width="80px">更新菜品图片</td>
								<td><input type="file" name="pic" class="InputStyle" /></td>
							</tr>
							<tr>
								<td width="80px">更新菜品名称</td>
								<td><input type="text" name="name" class="InputStyle" value="${good.name}" /></td>
							</tr>
							<tr>
								<td width="80px">更新菜品总销量</td>
								<td><input type="text" name="sold" class="InputStyle" value="${good.sold}" /></td>
							</tr>
							<tr>
								<td width="80px">更新菜品价格</td>
								<td><input type="text" name="price" class="InputStyle" value="${good.price}" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>


			<!-- 表单操作 -->
			<div id="InputDetailBar">


				<input type="submit" value="修改" class="FunctionButtonInput">
				<input type="hidden" name="goodId" value="${good.id}" />
				<a href="javascript:history.go(-1);" class="FunctionButton">返回</a>
			</div>
		</form>
	</div>
</body>
</html>
