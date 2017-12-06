package com.chillax.common;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {

	public static HashMap parseXML(HttpServletRequest request) throws Exception, IOException{   
        HashMap map=new HashMap();   
        // ͨ��IO���Document   
        SAXReader reader = new SAXReader();   
        Document doc = reader.read(request.getInputStream());   
        //�õ�xml�ĸ��ڵ�   
        Element root=doc.getRootElement();   
        recursiveParseXML(root,map);   
        return map;   
    }
	private static void recursiveParseXML(Element root,HashMap map){
        //�õ����ڵ���ӽڵ��б�
        List<Element> elementList=root.elements();
        //�ж���û����Ԫ���б�
        if(elementList.size()==0){
            map.put(root.getName(), root.getTextTrim());
        }
        else{
            //����
            for(Element e:elementList){
                recursiveParseXML(e,map);
            }
        }
    }
	//xstream��չ  
	private static XStream xstream = new XStream(new XppDriver() {  
	        @Override
			public HierarchicalStreamWriter createWriter(Writer out) {  
	            return new PrettyPrintWriter(out) {  
	                // ������xml�ڵ㶼����CDATA���  
	                boolean cdata = true;  
	  
	                @Override
					public void startNode(String name, Class clazz) {  
	                    super.startNode(name, clazz);  
	                }  
	  
	                @Override
					protected void writeText(QuickWriter writer, String text) {  
	                    if (cdata) {  
	                        writer.write("<![CDATA[");  
	                        writer.write(text);  
	                        writer.write("]]>");  
	                    } else {  
	                        writer.write(text);  
	                    }  
	                }  
	            };  
	        }  
	});   
    public static String messageToXML(PaymentPo paymentPo){   
        xstream.alias("xml",PaymentPo.class);   
        return xstream.toXML(paymentPo);   
    }
}
