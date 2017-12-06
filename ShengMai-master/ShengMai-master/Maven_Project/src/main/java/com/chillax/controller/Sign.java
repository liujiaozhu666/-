package com.chillax.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.chillax.common.Configure;
import com.chillax.common.RandomStringGenerator;
import com.chillax.common.Signature;
import com.chillax.model.SignInfo;

@Controller
public class Sign {

	private static final long serialVersionUID = 1L;
	private static final Logger L = Logger.getLogger(Sign.class);
	
	@RequestMapping("/sign.do")
	protected void doSign(HttpServletRequest request, HttpServletResponse response){
		try {
			String prepay_id = request.getParameter("prepay_id");
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Configure.getAppID());
			long time = System.currentTimeMillis()/1000;
			signInfo.setTimeStamp(String.valueOf(time));
			signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			signInfo.setPrepay_id("prepay_id="+prepay_id);
			signInfo.setSignType("MD5");
			//生成签名
			String sign = Signature.getSign(signInfo);
			
			JSONObject json = new JSONObject();
			json.put("timeStamp", signInfo.getTimeStamp());
			json.put("nonceStr", signInfo.getNonceStr());
			json.put("package", signInfo.getPrepay_id());
			json.put("signType", signInfo.getSignType());
			json.put("paySign", sign);
			L.info("-------再签名:"+json.toJSONString());
			response.getWriter().append(json.toJSONString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			L.error("-------", e);
		}
	}
}
