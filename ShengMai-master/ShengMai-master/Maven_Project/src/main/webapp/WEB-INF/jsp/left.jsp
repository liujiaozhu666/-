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

<title>Frame left</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/page_common.js"></script>
<link href="css/common_style_blue.css" rel="stylesheet"
	type="text/css" />



<script type="text/javascript">
	// 显示或隐藏二级菜单 
	function menuClick(menuDiv) {
		$(".MenuLevel2").not($(menuDiv).next()).hide();
		$(menuDiv).next().toggle();
	}

	$(function() {
		// 默认只显示第1个二级菜单
		$(".MenuLevel2").hide();
		$(".MenuLevel2:first").show();
	});
</script>

<!-- 内容总宽度为 3px边框 * 2 + 155px内容 = 161px; -->
	<style type="text/css">
<!--
html{
height: 100%;
}
body {
	background: none repeat scroll 0 0 #D8EDFC;
	margin: 0;
	padding: 0;
}
#Menu {
    margin: 0;
    padding: 0;
    width: 155px;
	background: none repeat scroll 0 0 #D8EBF7;
    list-style: none outside none;
	
	margin-left: 3px;
	border-top: 3px solid #4891C6;
}
#Menu .level1 {
 color: #005790;
    font-weight: bold;
    padding-bottom: 1px;
	  cursor: pointer;
}
#Menu .level1 .level1Style {
  background: url("images/img/menu_btn_bg.gif") no-repeat scroll 0 0 transparent;
    height: 23px;
    padding-left: 20px;
    padding-top: 5px;
    width: 135px;
	margin-bottom: -4px
}
#Menu .level1 .level1Style .Icon {
	margin-top: -2px;
}
#Menu .level1 .MenuLevel2 {
 background: none repeat scroll 0 0 #D8EBF7;
    list-style: none outside none;
    margin: 0;
    padding: 0;
}
#Menu .level1 .MenuLevel2 .level2Style{
	color: #005790;
    font-weight: normal;
	border-top: 1px solid #EFF6FB;
	height: 18px;
	padding-left: 43px;
	padding-top: 5px;
	width: 112px;
	background-image:url(images/img/menu_arrow_single.gif);
	background-color: #8EC4E9;
	background-repeat: no-repeat;
	background-position: 29px center;
}
-->
	</style>

</head>

<body>
	<ul id="Menu">
		<li class="level1">
			<div onClick="menuClick(this);" class="level1Style">
				<img src="images/func20001.gif" class="Icon" /> 系统功能
			</div>
			<ul class="MenuLevel2">
				<li class="level2 level2Style"><a target="right"
					href="getAllShops.do">商户管理</a></li>
				<li class="level2 level2Style"><a target="right"
					href="redirect.do?page=goodstype">菜品类别管理</a></li>
				<li class="level2 level2Style"><a target="right"
					href="selectAllGoods.do">菜品管理</a></li>
				<li class="level2 level2Style"><a target="right"
					href="redirect.do?page=orderlist">订单</a></li>
			</ul>
		</li>
	</ul>
</body>
</html>
