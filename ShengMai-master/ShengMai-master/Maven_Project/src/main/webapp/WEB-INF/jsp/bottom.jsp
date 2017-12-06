<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>Frame bottom</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="js/jquery.js"></script>
<link href="css/common_style_blue.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
body {
	margin: 0;
}

img {
	vertical-align: inherit;
	border: 0;
}

a:link, a:hover, a:visited {
	color: #A9DCFF;
	text-decoration: none;
}

#StatusBar {
	background-color: #4386B7;
	border-top: 1px solid #FFFFFF;
	height: 19px;
	width: 100%;
}

#StatusBar #StatusBar_Links {
	color: #A9DCFF;
	float: left;
	font-family: "宋体";
	font-size: 12px;
	padding-left: 20px;
	padding-top: 3px;
}

#StatusBar #StatusBar_Right {
	color: #A9DCFF;
	float: right;
	font-family: "宋体";
	font-size: 12px;
	padding-right: 20px;
	padding-top: 4px;
}
</style>

</head>

<body>
	<div id="StatusBar">
		<!-- 链接 -->
		<div id="StatusBar_Links"></div>
		<!-- 右侧功能按钮 -->
		<div id="StatusBar_Right">
			<!-- 版本 -->
			<a href="javascript:void(0)"> </a>
		</div>
	</div>
</body>
</html>
