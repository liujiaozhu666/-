package com.chillax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chillax.common.StreamUtil;

@Controller
public class PayResult {

	private static final long serialVersionUID = 1L;
	private static final Logger L = Logger.getLogger(PayResult.class);
	
	@RequestMapping("/PayResult.do")
	protected void doPayResult(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String reqParams = StreamUtil.read(request.getInputStream());
		L.info("-------Ö§¸¶½á¹û:"+reqParams);
		StringBuffer sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
		response.getWriter().append(sb.toString());
	}
}
