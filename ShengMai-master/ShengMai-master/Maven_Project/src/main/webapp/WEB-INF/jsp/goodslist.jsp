<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'foodlist.jsp' starting page</title>

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
					src="images/title_arrow.gif" /> 菜品列表
			</div>
		</div>
		<div id="TitleArea_End"></div>
	</div>


	<!-- 过滤条件 -->
	<div id="QueryArea">
		<form action="/wirelessplatform/food.html" method="get">
			<input type="hidden" name="method" value="search"> <input
				type="text" name="keyword" title="请输入菜品名称"> <input
				type="submit" value="搜索">
		</form>
		
		<span style="float: right; margin-top: -20px;margin-right: 30px" align="right"> 
			<span class="FunctionButton"> 
				<a href="redirect.do?page=addgoods">添加菜品</a>
			</span>
		</span>
	</div>
	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<table class="MainArea_Content" align="center" cellspacing="0"
			cellpadding="0">
			<!-- 表头-->
			<thead>
				<tr align="center" valign="middle" id="TableTitle">
					<td>菜品编号</td>
					<td>菜品展示图</td>
					<td>菜品名称</td>
					<td>菜品价格</td>
					<td>菜品总销量</td>
					<td>操作</td>
				</tr>
			</thead>
			<!--显示数据列表 -->
			<tbody id="TableData">
<c:forEach items="${goods}" var="good">
				<tr class="TableDetail1">
					<td align="center">${good.id}&nbsp;</td>
					<td align="center"><img src="${good.pic}" width=60px height=60px/></td>
					<td align="center">${good.name}&nbsp;</td>
					<td align="center">${good.price}元&nbsp;</td>
					<td align="center">${good.sold}份&nbsp;</td>
					<td style="width:100px"><a href="selectByGoodId.do?goodId=${good.id}&goodPic=${good.pic}" class="FunctionButton">更新</a> <a
						href="/wirelessplatform/food.html?method=delete&id=1"
						onClick="return delConfirm();" class="FunctionButton">删除</a></td>
				</tr>
</c:forEach>

			</tbody>
		</table>

	</div>
</body>
</html>
