package com.chillax.common;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class HttpRequest {
	//���ӳ�ʱʱ�䣬Ĭ��10��
    private static final int socketTimeout = 10000;

    //���䳬ʱʱ�䣬Ĭ��30��
    private static final int connectTimeout = 30000;
	/**
	 * post����
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	public static String sendPost(String url, Object xmlObj) throws ClientProtocolException, IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		

        
		HttpPost httpPost = new HttpPost(url);
		//���XStream�Գ���˫�»��ߵ�bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml", xmlObj.getClass());
        //��Ҫ�ύ��API�����ݶ���ת����XML��ʽ����Post��API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        //��ָ��ʹ��UTF-8���룬����API������XML�����Ĳ��ܱ��ɹ�ʶ��
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //����������������
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
	}
	/**
	 * �Զ���֤�����������������֤��
	 * @author pc
	 *
	 */
	public static class MyX509TrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
      }
}
