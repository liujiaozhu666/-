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


<title>外卖平台后台管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<frameset rows="100px,*,19px" framespacing="0" border="0"
	frameborder="0">

	<frame src="redirect.do?page=top" scrolling="no" noresize />
	<frameset cols="178px,*">
		<frame noresize src="redirect.do?page=left" scrolling="yes" />
		<frame noresize name="right" src="redirect.do?page=right" scrolling="yes" />
	</frameset>
	<frame noresize name="status_bar" scrolling="no"
		src="redirect.do?page=bottom" />

</frameset>

<noframes>
	<body>
		你的浏览器不支持框架布局，推荐你使用
		<a href="http://www.firefox.com.cn/download/"
			style="text-decoration: none;">火狐浏览器</a>,
		<a href="http://www.google.cn/intl/zh-CN/chrome/"
			style="text-decoration: none;">谷歌浏览器</a>
	</body>
</noframes>
</html>