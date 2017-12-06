package com.chillax.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * ǩ����Ϣ
 * @author zuoliangzhu
 *
 */
public class SignInfo {

	private String appId;//С����ID	
	private String timeStamp;//ʱ���	
	private String nonceStr;//�����	
	@XStreamAlias("package")
	private String prepay_id;
	private String signType;//ǩ����ʽ
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
}
