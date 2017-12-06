package com.chillax.common;

public class Configure {

	private static String key = "m9v3jdelu6dqgu8jphscw633hw2qelpc";//你的商户的api秘钥

	private static String mch_id = "1489170842";//商户号
	//小程序ID
	private static String appID = "wxae798d23271f843e";//小程序id
	//商户号
	private static String secret = "2eae32cc04d9b07762a1746d3ba28e4e";

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}
}
