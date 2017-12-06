<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'boardList.jsp' starting page</title>

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
				<img border="0" width="13" height="13" src="images/title_arrow.gif" />
				商户列表
			</div>

		</div>
		<d iv id="TitleArea_End">
	</div>

	</div>


	<!-- 过滤条件 -->
	<div id="QueryArea">
		<form action="/wirelessplatform/board.html" method="get">
			<input type="hidden" name="method" value="search"> <input
				type="text" name="keyword" title="请输入商户名称"> <input
				type="submit" value="搜索">
		</form>

		<!-- 其他功能超链接 -->
		<span style="float: right; margin-top: -20px;margin-right: 30px"
			align="right"> <span class="FunctionButton"> <a
				href="redirect.do?page=addshops">添加商户</a>
		</span>
		</span>

	</div>

	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<table class="MainArea_Content" cellspacing="0" cellpadding="0">
			<!-- 表头-->
			<thead>
				<tr align="center" valign="middle" id="TableTitle">
					<td>商户编号</td>
					<td>商户LOGO</td>
					<td>商户名称</td>
					<td>近期促销活动</td>
					<td>总销量</td>
					<td>距离</td>
					<td>操作</td>
				</tr>
			</thead>
			<!--显示数据列表 -->
			<tbody id="TableData">
				<c:forEach items="${shops}" var="shop">
					<tr class="TableDetail1">
						<td align="center">${shop.id}&nbsp;</td>
						<td align="center"><img src="${shop.logo}" width=80px
							height=80px /></td>
						<td align="center">${shop.name}</td>
						<td align="center">${shop.activity}</td>
						<td align="center">${shop.sales}份</td>
						<td align="center">${shop.distance}公里</td>
						<td style="width:100px"><a
							href="selectByShopId.do?shopId=${shop.id}&shopLogo=${shop.logo}"
							class="FunctionButton">更新</a> <a
							href="/wirelessplatform/board.html?method=delete&id=1"
							onClick="return delConfirm();" class="FunctionButton">删除</a></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>


	</div>
</body>
</html>
