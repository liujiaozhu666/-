package com.chillax.controller;

public class TotalController {

	/*public SortedMap<String, String> doTotal(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//网页授权后获取传递的参数
		String openId = request.getParameter("openId"); 	
		//金额转化为分为单位
		//商户相关资料 
		String appid = "xxxx";
		String appsecret = "xxxx";
		String partner = "xxxx";
		String partnerkey = "xxxx";
		
		
		//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
				String currTime = TenpayUtil.getCurrTime();
				//8位日期
				String strTime = currTime.substring(8, currTime.length());
				//四位随机数
				String strRandom = TenpayUtil.buildRandom(4) + "";
				//10位序列号,可以自行调整。
				String strReq = strTime + strRandom;
				
				
				//商户号
				String mch_id = partner;
				//子商户号  非必输
				//String sub_mch_id="";
				//设备号   非必输
				String device_info="WEB";
				//随机数 
				String nonce_str = strReq;
				//商品描述
				//String body = describe;
				
				//商品描述根据情况修改
				String body = "test";
				//附加数据
				String attach = "0";
				//商户订单号
				String out_trade_no = NonceString.generate();
				
				//int intMoney = Integer.parseInt(finalmoney);
				
				//总金额以分为单位，不带小数点
				//int total_fee = intMoney;
				//订单生成的机器 IP
				String spbill_create_ip = request.getRemoteAddr();
				//订 单 生 成 时 间   非必输
				String time_start =TenpayUtil.getStartTime();
				//订单失效时间      非必输
				String time_expire =TenpayUtil.getEndTime();;
				//商品标记   非必输
				String goods_tag = "WXG";
				
				//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
				String notify_url ="http://www.xxxx.com/notifyServlet";
				
				String trade_type = "JSAPI";
				String openid = openId;
				//非必输
//				String product_id = "";
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", appid);  
				packageParams.put("mch_id", mch_id);  
				packageParams.put("nonce_str", nonce_str);  
				packageParams.put("body", body);  
				packageParams.put("attach", attach);  
				packageParams.put("out_trade_no", out_trade_no);  
				//这里写的金额为 分到时修改
				packageParams.put("total_fee", "1");  
				packageParams.put("spbill_create_ip", spbill_create_ip);
				packageParams.put("time_start", time_start); 
				packageParams.put("time_expire", time_expire); 
				packageParams.put("notify_url", notify_url);  
				
				packageParams.put("trade_type", trade_type);  
				packageParams.put("openid", openid);  

				RequestHandler reqHandler = new RequestHandler(request, response);
				reqHandler.init(appid, appsecret, partnerkey);
				
				String sign = reqHandler.createSign(packageParams);
				System.out.println("获取预支付sign:"+sign);
				String xml="<xml>"+
						"<appid>"+appid+"</appid>"+
						"<mch_id>"+mch_id+"</mch_id>"+
						"<nonce_str>"+nonce_str+"</nonce_str>"+
						"<sign>"+sign+"</sign>"+
						"<body><![CDATA["+body+"]]></body>"+
						"<attach>"+attach+"</attach>"+
						"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
						//金额，这里写的 分到时修改
						"<total_fee>"+"1"+"</total_fee>"+
						"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
						"<time_start>"+time_start+"</time_start>"+
						"<time_expire>"+time_expire+"</time_expire>"+
						"<notify_url>"+notify_url+"</notify_url>"+
						"<trade_type>"+trade_type+"</trade_type>"+
						"<openid>"+openid+"</openid>"+
						"</xml>";
				String allParameters = "";
				try {
					allParameters =  reqHandler.genPackage(packageParams);
					System.out.println("allParameters:"+allParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				String prepay_id="";
				try {
					System.out.println("xml:"+xml);
					prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
					System.out.println("prepay_id:"+prepay_id);
					if(prepay_id.equals("")){
						request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
						response.sendRedirect("error.jsp");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				SortedMap<String, String> finalpackage = new TreeMap<String, String>();
				String appid2 = appid;
				String timestamp = Sha1Util.getTimeStamp();
				String nonceStr2 = nonce_str;
				String prepay_id2 = "prepay_id="+prepay_id;
				String packages = prepay_id2;
				finalpackage.put("appId", appid2);  
				finalpackage.put("timeStamp", timestamp);  
				finalpackage.put("nonceStr", nonceStr2);  
				finalpackage.put("package", packages);  
				finalpackage.put("signType", "MD5");
				String finalsign = reqHandler.createSign(finalpackage);
				finalpackage.put("paySign",finalsign);
	}*/
}
