package com.chillax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.chillax.common.Configure;

@Controller
public class GetOpenId {

	@RequestMapping("/GetOpenId.do")
	protected void getOpen(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException{
		String code = request.getParameter("code");
		System.out.println("进入，code="+code);
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code");
                //设置请求器的配置
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        System.out.println("result:"+result);
        response.getWriter().append(result);
	}
}
