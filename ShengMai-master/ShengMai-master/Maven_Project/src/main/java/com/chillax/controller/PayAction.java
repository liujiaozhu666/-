package com.chillax.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chillax.common.MessageUtil;
import com.chillax.common.NonceString;
import com.chillax.common.PayUtil;
import com.chillax.common.PaymentPo;
import com.chillax.common.UUIDHexGenerator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class PayAction {

	/*private int total_fee;总金额*/
	private String body;// 商品描述
	private String detail;// 商品详情
	private String attach;// 附加数据
	private String time_start;// 交易起始时间
	private String time_expire;// 交易结束时间
	private String openid;// 用户标识
	/*private JSONArray jsonArray = new JSONArray();*/
	
	@RequestMapping(value="/pay")
	@ResponseBody
	public JSONArray pay(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
		
		/*request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");*/
		response.setContentType("application/json;charset=UTF-8");
		
		body = new String(request.getParameter("body").getBytes("UTF-8"), "ISO-8859-1");
		String appid = "wxae798d23271f843e";// 小程序ID
		String mch_id = "1489170842";// 商户号
		String nonce_str = UUIDHexGenerator.generate();// 随机字符串
		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String code = PayUtil.createCode(8);
		/*String out_trade_no = mch_id + today + code;// 商户订单号*/
		/*String out_trade_no = "SM"+System.currentTimeMillis();*/
		String out_trade_no = NonceString.generate();/*商户订单号*/
		String spbill_create_ip = "127.0.0.1";// 终端IP
		String notify_url = "https://nat.oldwei.com/Maven_Project/PayResult.do";// 通知地址
		String trade_type = "JSAPI";// 交易类型
		String openid = request.getParameter("openid");// 用户标识
		System.out.println("openid:"+openid);
		
		PaymentPo paymentPo = new PaymentPo();
		
		
		
		paymentPo.setAppid(appid);
		paymentPo.setMch_id(mch_id);
		paymentPo.setNonce_str(nonce_str);
		String newbody = new String(body.getBytes("ISO-8859-1"), "UTF-8");// 以utf-8编码放入paymentPo，微信支付要求字符编码统一采用UTF-8字符编码
		paymentPo.setBody(newbody);
		paymentPo.setOut_trade_no(out_trade_no);
		paymentPo.setTotal_fee(request.getParameter("total_fee"));
		paymentPo.setSpbill_create_ip(spbill_create_ip);
		paymentPo.setNotify_url(notify_url);
		paymentPo.setTrade_type(trade_type);
		paymentPo.setOpenid(openid);
		// 把请求参数打包成数组
		Map sParaTemp = new HashMap();
		
		
		
		sParaTemp.put("appid", paymentPo.getAppid());
		sParaTemp.put("mch_id", paymentPo.getMch_id());
		sParaTemp.put("nonce_str", paymentPo.getNonce_str());
		sParaTemp.put("body", paymentPo.getBody());
		sParaTemp.put("out_trade_no", paymentPo.getOut_trade_no());
		sParaTemp.put("total_fee", paymentPo.getTotal_fee());
		
		System.out.println("total_fee:"+paymentPo.getTotal_fee());
		
		sParaTemp.put("spbill_create_ip", paymentPo.getSpbill_create_ip());
		sParaTemp.put("notify_url", paymentPo.getNotify_url());
		sParaTemp.put("trade_type", paymentPo.getTrade_type());
		sParaTemp.put("openid", paymentPo.getOpenid());
		// 除去数组中的空值和签名参数
		Map sPara = PayUtil.paraFilter(sParaTemp);
		String prestr = PayUtil.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String key = "&key=m9v3jdelu6dqgu8jphscw633hw2qelpc"; // 商户支付密钥
		// MD5运算生成签名
		String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
		paymentPo.setSign(mysign);
		// 打包要发送的xml
		/*String respXml = MessageUtil.messageToXML(paymentPo);
		System.out.println(":::::::"+respXml);*/
		
		/*拼接XML------------------------------------------------------------------*/
		//拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去  
        String xml = "<xml>" + "<appid>" + appid + "</appid>"   
                + "<body><![CDATA[" + newbody + "]]></body>"   
                + "<mch_id>" + mch_id + "</mch_id>"   
                + "<nonce_str>" + nonce_str + "</nonce_str>"   
                + "<notify_url>" + notify_url + "</notify_url>"   
                + "<openid>" + openid + "</openid>"   
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"   
                + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"   
                + "<total_fee>" + request.getParameter("total_fee") + "</total_fee>"  
                + "<trade_type>" + trade_type + "</trade_type>"   
                + "<sign>" + mysign + "</sign>"  
                + "</xml>";
		
		
		// 打印respXml发现，得到的xml中有“__”不对，应该替换成“_”
		/*respXml = respXml.replace("__", "_");
		String wrongOpenid = openid.replace("__", "_");
		respXml = respXml.replace(wrongOpenid, openid);
		System.out.println("新的respXml");
		System.out.println(respXml);*/
		
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 统一下单API接口链接
		String param = xml;//respXml和xml换位
		System.out.println("。。。。。。。。："+param);
		// String result = SendRequestForUrl.sendRequest(url, param);//发起请求
		String result = PayUtil.httpRequest(url, "POST", param);
		System.out.println("result:"+result);
		// 将解析结果存储在HashMap中
		Map map = new HashMap();
		
		
		
		InputStream in = new ByteArrayInputStream(result.getBytes("UTF-8"));
		// 读取输入流
		SAXReader reader = new SAXReader();
		
		
		
		Document document = reader.read(in);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
		for (Element element : elementList) {
			map.put(element.getName(), element.getText());
		}
		// 返回信息
		String return_code = (String)map.get("return_code");// 返回状态码
		String return_msg = (String)map.get("return_msg");// 返回信息
		System.out.println("return_msg" + return_msg);
		System.out.println("return_code" + return_code);
		JSONObject JsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		
		if (return_code == "SUCCESS" || return_code.equals(return_code)) {
			// 业务结果
			String prepay_id = (String)map.get("prepay_id");// 返回的预付单信息
			String nonceStr = UUIDHexGenerator.generate();
			JsonObject.put("nonceStr", nonceStr);
			JsonObject.put("package", "prepay_id=" + prepay_id);
			Long timeStamp = System.currentTimeMillis() / 1000;
			JsonObject.put("timeStamp", timeStamp + "");
			String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id
					+ "&signType=MD5&timeStamp=" + timeStamp;
			// 再次签名
			String paySign = PayUtil.sign(stringSignTemp, "&key=m9v3jdelu6dqgu8jphscw633hw2qelpc", "utf-8").toUpperCase();
			JsonObject.put("paySign", paySign);
			jsonArray.add(JsonObject);
		}
		in.close();
		
		return jsonArray;
	}

	/*public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}*/

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	/*public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}*/

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
