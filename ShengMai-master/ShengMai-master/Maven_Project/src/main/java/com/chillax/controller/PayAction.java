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

	/*private int total_fee;�ܽ��*/
	private String body;// ��Ʒ����
	private String detail;// ��Ʒ����
	private String attach;// ��������
	private String time_start;// ������ʼʱ��
	private String time_expire;// ���׽���ʱ��
	private String openid;// �û���ʶ
	/*private JSONArray jsonArray = new JSONArray();*/
	
	@RequestMapping(value="/pay")
	@ResponseBody
	public JSONArray pay(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
		
		/*request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");*/
		response.setContentType("application/json;charset=UTF-8");
		
		body = new String(request.getParameter("body").getBytes("UTF-8"), "ISO-8859-1");
		String appid = "wxae798d23271f843e";// С����ID
		String mch_id = "1489170842";// �̻���
		String nonce_str = UUIDHexGenerator.generate();// ����ַ���
		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String code = PayUtil.createCode(8);
		/*String out_trade_no = mch_id + today + code;// �̻�������*/
		/*String out_trade_no = "SM"+System.currentTimeMillis();*/
		String out_trade_no = NonceString.generate();/*�̻�������*/
		String spbill_create_ip = "127.0.0.1";// �ն�IP
		String notify_url = "https://nat.oldwei.com/Maven_Project/PayResult.do";// ֪ͨ��ַ
		String trade_type = "JSAPI";// ��������
		String openid = request.getParameter("openid");// �û���ʶ
		System.out.println("openid:"+openid);
		
		PaymentPo paymentPo = new PaymentPo();
		
		
		
		paymentPo.setAppid(appid);
		paymentPo.setMch_id(mch_id);
		paymentPo.setNonce_str(nonce_str);
		String newbody = new String(body.getBytes("ISO-8859-1"), "UTF-8");// ��utf-8�������paymentPo��΢��֧��Ҫ���ַ�����ͳһ����UTF-8�ַ�����
		paymentPo.setBody(newbody);
		paymentPo.setOut_trade_no(out_trade_no);
		paymentPo.setTotal_fee(request.getParameter("total_fee"));
		paymentPo.setSpbill_create_ip(spbill_create_ip);
		paymentPo.setNotify_url(notify_url);
		paymentPo.setTrade_type(trade_type);
		paymentPo.setOpenid(openid);
		// ������������������
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
		// ��ȥ�����еĿ�ֵ��ǩ������
		Map sPara = PayUtil.paraFilter(sParaTemp);
		String prestr = PayUtil.createLinkString(sPara); // ����������Ԫ�أ����ա�����=����ֵ����ģʽ�á�&���ַ�ƴ�ӳ��ַ���
		String key = "&key=m9v3jdelu6dqgu8jphscw633hw2qelpc"; // �̻�֧����Կ
		// MD5��������ǩ��
		String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
		paymentPo.setSign(mysign);
		// ���Ҫ���͵�xml
		/*String respXml = MessageUtil.messageToXML(paymentPo);
		System.out.println(":::::::"+respXml);*/
		
		/*ƴ��XML------------------------------------------------------------------*/
		//ƴ��ͳһ�µ��ӿ�ʹ�õ�xml���ݣ�Ҫ����һ�����ɵ�ǩ��һ��ƴ�ӽ�ȥ  
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
		
		
		// ��ӡrespXml���֣��õ���xml���С�__�����ԣ�Ӧ���滻�ɡ�_��
		/*respXml = respXml.replace("__", "_");
		String wrongOpenid = openid.replace("__", "_");
		respXml = respXml.replace(wrongOpenid, openid);
		System.out.println("�µ�respXml");
		System.out.println(respXml);*/
		
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";// ͳһ�µ�API�ӿ�����
		String param = xml;//respXml��xml��λ
		System.out.println("������������������"+param);
		// String result = SendRequestForUrl.sendRequest(url, param);//��������
		String result = PayUtil.httpRequest(url, "POST", param);
		System.out.println("result:"+result);
		// ����������洢��HashMap��
		Map map = new HashMap();
		
		
		
		InputStream in = new ByteArrayInputStream(result.getBytes("UTF-8"));
		// ��ȡ������
		SAXReader reader = new SAXReader();
		
		
		
		Document document = reader.read(in);
		// �õ�xml��Ԫ��
		Element root = document.getRootElement();
		// �õ���Ԫ�ص������ӽڵ�
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
		for (Element element : elementList) {
			map.put(element.getName(), element.getText());
		}
		// ������Ϣ
		String return_code = (String)map.get("return_code");// ����״̬��
		String return_msg = (String)map.get("return_msg");// ������Ϣ
		System.out.println("return_msg" + return_msg);
		System.out.println("return_code" + return_code);
		JSONObject JsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		
		if (return_code == "SUCCESS" || return_code.equals(return_code)) {
			// ҵ����
			String prepay_id = (String)map.get("prepay_id");// ���ص�Ԥ������Ϣ
			String nonceStr = UUIDHexGenerator.generate();
			JsonObject.put("nonceStr", nonceStr);
			JsonObject.put("package", "prepay_id=" + prepay_id);
			Long timeStamp = System.currentTimeMillis() / 1000;
			JsonObject.put("timeStamp", timeStamp + "");
			String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id
					+ "&signType=MD5&timeStamp=" + timeStamp;
			// �ٴ�ǩ��
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
