package com.chillax.controller;

public class TotalController {

	/*public SortedMap<String, String> doTotal(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//��ҳ��Ȩ���ȡ���ݵĲ���
		String openId = request.getParameter("openId"); 	
		//���ת��Ϊ��Ϊ��λ
		//�̻�������� 
		String appid = "xxxx";
		String appsecret = "xxxx";
		String partner = "xxxx";
		String partnerkey = "xxxx";
		
		
		//��ȡopenId�����ͳһ֧���ӿ�https://api.mch.weixin.qq.com/pay/unifiedorder
				String currTime = TenpayUtil.getCurrTime();
				//8λ����
				String strTime = currTime.substring(8, currTime.length());
				//��λ�����
				String strRandom = TenpayUtil.buildRandom(4) + "";
				//10λ���к�,�������е�����
				String strReq = strTime + strRandom;
				
				
				//�̻���
				String mch_id = partner;
				//���̻���  �Ǳ���
				//String sub_mch_id="";
				//�豸��   �Ǳ���
				String device_info="WEB";
				//����� 
				String nonce_str = strReq;
				//��Ʒ����
				//String body = describe;
				
				//��Ʒ������������޸�
				String body = "test";
				//��������
				String attach = "0";
				//�̻�������
				String out_trade_no = NonceString.generate();
				
				//int intMoney = Integer.parseInt(finalmoney);
				
				//�ܽ���Է�Ϊ��λ������С����
				//int total_fee = intMoney;
				//�������ɵĻ��� IP
				String spbill_create_ip = request.getRemoteAddr();
				//�� �� �� �� ʱ ��   �Ǳ���
				String time_start =TenpayUtil.getStartTime();
				//����ʧЧʱ��      �Ǳ���
				String time_expire =TenpayUtil.getEndTime();;
				//��Ʒ���   �Ǳ���
				String goods_tag = "WXG";
				
				//����notify_url�� ֧����ɺ�΢�ŷ�����������Ϣ�������жϻ�Ա�Ƿ�֧���ɹ����ı䶩��״̬�ȡ�
				String notify_url ="http://www.xxxx.com/notifyServlet";
				
				String trade_type = "JSAPI";
				String openid = openId;
				//�Ǳ���
//				String product_id = "";
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", appid);  
				packageParams.put("mch_id", mch_id);  
				packageParams.put("nonce_str", nonce_str);  
				packageParams.put("body", body);  
				packageParams.put("attach", attach);  
				packageParams.put("out_trade_no", out_trade_no);  
				//����д�Ľ��Ϊ �ֵ�ʱ�޸�
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
				System.out.println("��ȡԤ֧��sign:"+sign);
				String xml="<xml>"+
						"<appid>"+appid+"</appid>"+
						"<mch_id>"+mch_id+"</mch_id>"+
						"<nonce_str>"+nonce_str+"</nonce_str>"+
						"<sign>"+sign+"</sign>"+
						"<body><![CDATA["+body+"]]></body>"+
						"<attach>"+attach+"</attach>"+
						"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
						//������д�� �ֵ�ʱ�޸�
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
						request.setAttribute("ErrorMsg", "ͳһ֧���ӿڻ�ȡԤ֧����������");
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
