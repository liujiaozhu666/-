package com.chillax.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.chillax.common.Configure;
import com.chillax.common.HttpRequest;
import com.chillax.common.RandomStringGenerator;
import com.chillax.common.Signature;
import com.chillax.model.OrderInfo;
import com.chillax.model.OrderReturnInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * 统一下单接口
 */
@Controller
public class Xiadan {

	private static final long serialVersionUID = 1L;
	private static final Logger L = Logger.getLogger(Xiadan.class);
	
	@RequestMapping("/xiadan.do")
	protected void doXiaDan(HttpServletRequest request, HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			/*order.setNonce_str("jk2j34kjkl2j34kljk23j4k456klp0ad");*/
			order.setBody("可口可乐");
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			/*order.setOut_trade_no("65jsd5hgn55xng633cdf187lcd376nfs");*/
			order.setTotal_fee(1);
			order.setSpbill_create_ip("127.0.1.1");
			order.setNotify_url("https://nat.oldwei.com/Maven_Project/PayResult.do");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			//生成签名
			String sign = Signature.getSign(order);
			System.out.println("sign:"+sign);
			order.setSign(sign);
			
			
			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			System.out.println(result);
			L.info("---------下单返回:"+result);
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("xml", OrderReturnInfo.class); 

			OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
			JSONObject json = new JSONObject();
			json.put("prepay_id", returnInfo.getPrepay_id());
			response.getWriter().append(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------", e);
		}
	}
	
}
