package com.mrmf.service.redis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.WeToken;
import com.mrmf.entity.wxpay.MyX509TrustManager;
import com.mrmf.entity.wxpay.TemplateData;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.common.Configure;
import com.osg.framework.mongodb.EMongoTemplate;

/**
 * Created by Tibers on 16/4/21.
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    
    @Autowired
	private EMongoTemplate mongoTemplate;

    public String getTicket(String token) throws Exception {
        URL url = new URL(new StringBuilder("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=").append(token).append("&type=jsapi").toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String json = in.readLine();
        return JSONObject.parseObject(json).get("ticket").toString();
    }

    public String getToken(String type) throws Exception {
    	String appId="";
		String appSecret="";
		if("organ".equals(type)){
			appId=Configure.organAppID;
			appSecret=Configure.organAppSecret;
		}else if("staff".equals(type)){
			appId=Configure.staffAppID;
			appSecret=Configure.staffAppSecret;
		}else if("user".equals(type)){
			appId=Configure.userAppID;
			appSecret=Configure.userAppSecret;
		}
        URL url = new URL(new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=").append(appId).append("&secret=").append(appSecret).toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String json = in.readLine();
        return JSONObject.parseObject(json).get("access_token").toString();
    }

    private Map<String, Object> sign(String jsapi_ticket, String url,String type) {
        Map<String, Object> ret = new HashMap<>();
        String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String String1;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        String1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(String1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        if("user".equals(type)){
        	ret.put("appid", Configure.userAppID);
        }else if("organ".equals(type)){
        	ret.put("appid", Configure.organAppID);
        }else if("staff".equals(type)){
        	ret.put("appid", Configure.staffAppID);
        }
        return ret;
    }


    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    @Override
    public Map<String, Object> getWechatPositioningMessage(String url,String type) {
        Map<String, Object> result = null;
        	try {
        		WeToken weToken = getTonkenInfo(type);
            	result = sign(weToken.getTicket(), url,type);
            	
			} catch (Exception e) {
				e.printStackTrace();
			}
        
        return result;
    }
    
    
    /**
     * 公众号发送消息方法
     * @param openId 
     * @param type 类型：用户；技师；店铺
     * @param temp 消息对象，参照方法中注释部分
     * @param templateID 模版ID
     * @throws Exception
     */
	@Override
	public WeToken getTonkenInfo(String type) throws Exception {
		WeToken weToken=mongoTemplate.findOne(Query.query(Criteria.where("user_type").is(type)), WeToken.class);
		if(weToken == null){
        	weToken = new WeToken();
        	weToken.setIdIfNew();
        	String token = getToken(type);
        	weToken.setToken(token);
        	String ticket = getTicket(token);
        	weToken.setTicket(ticket);
        	Date date=new Date();
        	long createTokenTime = date.getTime();
        	weToken.setCreateTokenTime(createTokenTime);
        	weToken.setUser_type(type);
        	mongoTemplate.insert(weToken);
        } else {
	        Date date=new Date();
	    	long nowTime=date.getTime();
	    	long tokenTime=weToken.getCreateTokenTime();
	    	if((nowTime-tokenTime)>=3000*1000){
	    		String token=getToken(type);
	        	weToken.setToken(token);
	        	String ticket=getTicket(token);
	        	weToken.setTicket(ticket);
	        	weToken.setCreateTokenTime(nowTime);
	        	mongoTemplate.save(weToken);
	    	}
        }
    	return weToken;
	}
	
	@Override
	public void send_template_message(String openId, String type, String templateID, WxTemplate temp) throws Exception {
    	WeToken weToken = getTonkenInfo(type);
    	System.out.println("发送消息是token:"+weToken);
        String access_token = weToken.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        temp.setUrl("");
        temp.setTouser(openId);
        temp.setTopcolor("#000000");
        temp.setTemplate_id(templateID);
        String jsonString = JSONObject.toJSONString(temp);
        JSONObject jsonObject = httpRequest(url, "POST", jsonString);
        System.out.println(jsonObject);
        int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInteger("errcode")) {  
                 result = jsonObject.getInteger("errcode");
                 System.out.println("错误 errcode: " + jsonObject.getInteger("errcode") + "  errmsg:" + jsonObject.getString("errmsg"));  
             }  
         }
        System.out.println("模板消息发送结果："+result);
	}
	
	@Override
	public void send_template_urlmessage(String linkUrl,String openId, String type, String templateID, WxTemplate temp) throws Exception {
    	WeToken weToken = getTonkenInfo(type);
        String access_token = weToken.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        temp.setUrl(linkUrl);
        temp.setTouser(openId);
        temp.setTopcolor("#000000");
        temp.setTemplate_id(templateID);
        String jsonString = JSONObject.toJSONString(temp);
        JSONObject jsonObject = httpRequest(url, "POST", jsonString);
        System.out.println(jsonObject);
        int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInteger("errcode")) {  
                 result = jsonObject.getInteger("errcode");
                 System.out.println("错误 errcode: " + jsonObject.getInteger("errcode") + "  errmsg:" + jsonObject.getString("errmsg"));  
             }  
         }
        System.out.println("模板消息发送结果："+result);
	}
	
	
	
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE"); //得到SSLContext的实例
            sslContext.init(null, tm, new java.security.SecureRandom());  //初始化
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  //
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  //打开url连接
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  //设置输出流
            httpUrlConn.setDoInput(true);  //设置输入流
            httpUrlConn.setUseCaches(false);  //post请求不使用缓存
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;  
            while ((str = bufferedReader.readLine()) != null) { //输入流信息写到str 不为null就放入buffer内
                buffer.append(str);  
            }  
            bufferedReader.close();  //关闭bufferedReader
            inputStreamReader.close();  //关闭输入流对象
            // 释放资源  
            inputStream.close();  //关闭输入流
            inputStream = null;  //置空
            httpUrlConn.disconnect();  //断开连接
            jsonObject = JSONObject.parseObject(buffer.toString());  
        } catch (ConnectException ce) {  
            ce.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return jsonObject;  
    }  

    @Override
    public WxTemplate getWxTemplate(String firstValue, String keyword1Value,
    		String keyword2Value, String keyword3Value, String keyword4Value,String keyword5Value,
    		String remarkValue) {
    	WxTemplate temp = new WxTemplate();
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData first = new TemplateData();
        first.setColor("#000000");  
        first.setValue(firstValue);  
        m.put("first", first); 
        if (!StringUtils.isEmpty(keyword1Value)) {
	        TemplateData keyword1 = new TemplateData();  
	        keyword1.setColor("#000000");  
	        keyword1.setValue(keyword1Value);  
	        m.put("keyword1", keyword1);
        }
        if (!StringUtils.isEmpty(keyword2Value)) {
	        TemplateData keyword2 = new TemplateData();  
	        keyword2.setColor("#000000");  
	        keyword2.setValue(keyword2Value);  
	        m.put("keyword2", keyword2);
        }
        if (!StringUtils.isEmpty(keyword3Value)) {
	        TemplateData keyword3 = new TemplateData();  
	        keyword3.setColor("#000000");  
	        keyword3.setValue(keyword3Value);  
	        m.put("keyword3", keyword3);
        }
        if (!StringUtils.isEmpty(keyword4Value)) {
        	TemplateData keyword4 = new TemplateData();  
            keyword4.setColor("#000000");  
            keyword4.setValue(keyword4Value);  
            m.put("keyword4", keyword4);
		}
        if(!StringUtils.isEmpty(keyword5Value)){
        	TemplateData keyword5 = new TemplateData();  
            keyword5.setColor("#000000");  
            keyword5.setValue(keyword5Value);  
            m.put("keyword5", keyword5);
        }
        TemplateData remark = new TemplateData();  
        remark.setColor("#000000");  
        remark.setValue(remarkValue);  
        m.put("remark", remark);
        temp.setData(m);
        
        return temp;
    }

	@Override
	public WxTemplate getWxTemplateTwo(String firstValue, String keyword1Value,
			String keyword2Value, String keyword3Value, String keyword4Value,
			String keyword5Value, String remarkValue) {
		WxTemplate temp = new WxTemplate();
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData result = new TemplateData();
        result.setColor("#000000");  
        result.setValue(firstValue);  
        m.put("result", result); 
        if (!StringUtils.isEmpty(keyword1Value)) {
	        TemplateData withdrawMoney = new TemplateData();  
	        withdrawMoney.setColor("#000000");  
	        withdrawMoney.setValue(keyword1Value);  
	        m.put("withdrawMoney", withdrawMoney);
        }
        if (!StringUtils.isEmpty(keyword2Value)) {
	        TemplateData withdrawTime = new TemplateData();  
	        withdrawTime.setColor("#000000");  
	        withdrawTime.setValue(keyword2Value);  
	        m.put("withdrawTime", withdrawTime);
        }
        if (!StringUtils.isEmpty(keyword3Value)) {
	        TemplateData cardInfo = new TemplateData();  
	        cardInfo.setColor("#000000");  
	        cardInfo.setValue(keyword3Value);  
	        m.put("cardInfo", cardInfo);
        }
        if (!StringUtils.isEmpty(keyword4Value)) {
        	TemplateData arrivedTime = new TemplateData();  
        	arrivedTime.setColor("#000000");  
        	arrivedTime.setValue(keyword4Value);  
            m.put("arrivedTime", arrivedTime);
		}
        if(!StringUtils.isEmpty(keyword5Value)){
        	TemplateData keyword5 = new TemplateData();  
            keyword5.setColor("#000000");  
            keyword5.setValue(keyword5Value);  
            m.put("keyword5", keyword5);
        }
        TemplateData remark = new TemplateData();  
        remark.setColor("#000000");  
        remark.setValue(remarkValue);  
        m.put("remark", remark);
        temp.setData(m);
        
        return temp;
	}


    @Override
    public WxTemplate getWxTemplateWithCoupon(String firstValue, String accountValue,
                                       String timeValue, String typeValue, String creditChangeValue,
                                       String creditNameValue, String numberValue,String amountValue,String remarkValue) {
        WxTemplate temp = new WxTemplate();
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData result = new TemplateData();
        result.setColor("#000000");
        result.setValue(firstValue);
        m.put("first", result);
        if (!StringUtils.isEmpty(accountValue)) {
            TemplateData account = new TemplateData();
            account.setColor("#000000");
            account.setValue(accountValue);
            m.put("account", account);
        }
        if (!StringUtils.isEmpty(timeValue)) {
            TemplateData time = new TemplateData();
            time.setColor("#000000");
            time.setValue(timeValue);
            m.put("time", time);
        }
        if (!StringUtils.isEmpty(typeValue)) {
            TemplateData type = new TemplateData();
            type.setColor("#000000");
            type.setValue(typeValue);
            m.put("type", type);
        }
        if (!StringUtils.isEmpty(creditChangeValue)) {
            TemplateData creditChange = new TemplateData();
            creditChange.setColor("#000000");
            creditChange.setValue(creditChangeValue);
            m.put("creditChange", creditChange);
        }
        if(!StringUtils.isEmpty(creditNameValue)){
            TemplateData creditName = new TemplateData();
            creditName.setColor("#000000");
            creditName.setValue(creditNameValue);
            m.put("creditName", creditName);
        }
        if(!StringUtils.isEmpty(numberValue)){
            TemplateData number = new TemplateData();
            number.setColor("#000000");
            number.setValue(numberValue);
            m.put("number", number);
        }
        if(!StringUtils.isEmpty(amountValue)){
            TemplateData amount = new TemplateData();
            amount.setColor("#000000");
            amount.setValue(amountValue);
            m.put("amount", amount);
        }
        TemplateData remark = new TemplateData();
        remark.setColor("#000000");
        remark.setValue(remarkValue);
        m.put("remark", remark);
        temp.setData(m);

        return temp;
    }

    public WxTemplate getWxTemplateWithBangDing(String firstValue, String keyword1, String keyword2, String remarkValue) {
        WxTemplate temp = new WxTemplate();
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData result = new TemplateData();
        result.setColor("#000000");
        result.setValue(firstValue);
        m.put("first", result);
        if (!StringUtils.isEmpty(keyword1)) {
            TemplateData account = new TemplateData();
            account.setColor("#000000");
            account.setValue(keyword1);
            m.put("keyword1", account);
        }
        if (!StringUtils.isEmpty(keyword2)) {
            TemplateData time = new TemplateData();
            time.setColor("#000000");
            time.setValue(keyword2);
            m.put("keyword2", time);
        }
        TemplateData remark = new TemplateData();
        remark.setColor("#000000");
        remark.setValue(remarkValue);
        m.put("remark", remark);
        temp.setData(m);

        return temp;
    }
}
