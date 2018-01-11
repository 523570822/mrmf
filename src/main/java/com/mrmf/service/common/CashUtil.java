package com.mrmf.service.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * 微信企业付款工具类
 * @author yangshaodong
 */
public class CashUtil {



    public static Map<String, String> CashToWeChat(String partner_trade_no,String openid,int amount,String desc,String spbill_create_ip )throws Exception {
        Logger logger = Logger.getLogger("CashUtil");
        Map<String, String> returnResult = null;
    	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        logger.info("keyStore"+keyStore);
        FileInputStream instream = new FileInputStream(new File(CashUtil.class.getClassLoader().getResource("1306980701.p12").getPath()));
        logger.info("instream"+instream);
        try {
            keyStore.load(instream, "1306980701".toCharArray());
            logger.info("keyStore1"+keyStore);
        } finally {
            instream.close();
            logger.info("instream1"+instream);
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "1306980701".toCharArray())
                .build();
        logger.info("sslcontext"+sslcontext);
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        logger.info("sslsf"+sslsf);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        logger.info("httpClient"+httpClient);
		WXPayReqData reqData = WXPayReqData.buildWXPayReqDataForMchPay(partner_trade_no, openid, amount, desc, spbill_create_ip);
        logger.info("reqData"+reqData);
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        logger.info("xStreamForRequestPostData"+xStreamForRequestPostData);
        String postDataXML = xStreamForRequestPostData.toXML(reqData);
        logger.info("postDataXML@@@@@@@@@@"+postDataXML);
       // System.out.println(postDataXML);
        try {
        	HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
            logger.info("httpPost"+httpPost);
        	StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            logger.info("postEntity"+postEntity);
            httpPost.addHeader("Content-Type", "text/xml");
            logger.info("httpPost1"+httpPost);
            httpPost.setEntity(postEntity);
            logger.info("httpPost2"+httpPost);
            try {
                CloseableHttpResponse response = httpClient.execute(httpPost);
                logger.info("response"+response);
                HttpEntity entity = response.getEntity();
                logger.info("entity"+entity);
                String result = EntityUtils.toString(entity, "UTF-8");
                logger.info("result"+result);
                returnResult = xmlToMap(result);
                logger.info("returnResult"+returnResult);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            return returnResult;
        } finally {
        	httpClient.close();
        }
    }
    public  static Map<String, String> CashToWeChatByStaff(String partner_trade_no,String openid,int amount,String desc,String spbill_create_ip )throws Exception {
    	Map<String, String> returnResult = null;
    	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(CashUtil.class.getClassLoader().getResource("1338666501.p12").getPath()));
        try {
            keyStore.load(instream, "1338666501".toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "1338666501".toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
		WXPayReqData reqData = WXPayReqData.buildWXPayReqDataForMchPayStaff(partner_trade_no, openid, amount, desc, spbill_create_ip);
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String postDataXML = xStreamForRequestPostData.toXML(reqData);
        System.out.println(postDataXML);
        try {
        	HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
        	StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(postEntity);
            try {
                CloseableHttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                returnResult = xmlToMap(result);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            return returnResult;
        } finally {
        	httpClient.close();
        }
    }
    public  static Map<String, String> CashToWeChatByOrgan(String partner_trade_no,String openid,int amount,String desc,String spbill_create_ip )throws Exception {
        Map<String, String> returnResult = null;
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(CashUtil.class.getClassLoader().getResource("1338675401.p12").getPath()));
        try {
            keyStore.load(instream, "1338675401".toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "1338675401".toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        WXPayReqData reqData = WXPayReqData.buildWXPayReqDataForMchPayOrgan(partner_trade_no, openid, amount, desc, spbill_create_ip);
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String postDataXML = xStreamForRequestPostData.toXML(reqData);
        System.out.println(postDataXML);
        try {
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
            StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(postEntity);
            try {
                CloseableHttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                returnResult = xmlToMap(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnResult;
        } finally {
            httpClient.close();
        }
    }


    public static Map<String,String> xmlToMap(String returnResult) throws Exception {
	 	Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document doc =  reader.read(new StringReader(returnResult));
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		return map;
	}
    
}
