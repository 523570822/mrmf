package com.osg.framework.web.httpclient;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.osg.framework.util.type.BaseTypeUtil;
import com.osg.framework.web.httpclient.binder.MResponseTypeBinder;
import com.osg.framework.web.httpclient.exception.MHttpClientException;

/**
 * Http client
 * 
 * @author xiangf
 * @since JDK5.0
 */
public class MHttpClient {

	private HttpClient httpClient;
	private MResponseTypeBinder binder;
	private String url;
	private static Log log = LogFactory.getLog(MHttpClient.class);

	public MHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public <T> T get(Map<String, String> param)throws Exception {
		return get(param, null);
	}
	/**
	 * 发送Http Get请求
	 * 
	 * @param param
	 *            参数
	 * @param clazz
	 *            返回类型
	 * @return 请求结果
	 * @throws Exception
	 */
	public <T> T get(Map<String, String> param, Class<T> clazz)
			throws Exception {
		if (binder == null) {
			throw new MHttpClientException("Http client: Empty response binder");
		}
		if (BaseTypeUtil.isEmptyString(url)) {
			throw new MHttpClientException("Http client: URL response binder");
		}
		StringBuffer cUrl = new StringBuffer(url);
		if (param != null && param.size() > 0) {
			cUrl.append("?");
			Iterator<Entry<String, String>> it = param.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) it.next();
				cUrl.append(entry.getKey());
				cUrl.append("=");
				cUrl.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				if (it.hasNext()) {
					cUrl.append("&");
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("Request:" + cUrl.toString());
		}
		HttpGet httpget = new HttpGet(cUrl.toString());
		T t = null;
		InputStream is = null;
		try {
			HttpResponse response = httpClient.execute(httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK
					&& response.getStatusLine().getStatusCode() != HttpStatus.SC_ACCEPTED
					&& response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED
					&& response.getStatusLine().getStatusCode() != HttpStatus.SC_CONTINUE) {
				throw new MHttpClientException(
						"Http client: Illegal http status:"
								+ response.getStatusLine().getStatusCode(),
						response.getStatusLine().getStatusCode());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null && clazz != null) {
				if (log.isDebugEnabled()) {
					String content = EntityUtils.toString(entity, "UTF-8");
					log.debug("Response:" + content);
					t = binder.toObject(content, clazz);
				} else {
					is = entity.getContent();
					if (binder != null) {
						t = binder.toObject(is, clazz);
					}
				}
			}

		} catch (Exception e) {
			httpget.abort();
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return t;
	}

	/**
	 * 暂未实现
	 * 
	 * @param param
	 * @param clazz
	 * @return
	 */
	public <T> T post(Map<String, String> param, Class<T> clazz) {
		// 未实现
		return null;
	}

	/**
	 * 暂未实现
	 * 
	 * @param param
	 */
	public void post(Map<String, String> param) {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MResponseTypeBinder getBinder() {
		return binder;
	}

	public void setBinder(MResponseTypeBinder binder) {
		this.binder = binder;
	}
	/**
	 * 发送POST请求
	 *
	 * @param url         请求地址url
	 * @param params      需要发送的请求参数字符串
	 * @param connTimeout 连接超时时间（毫秒），如果为null则默认为180秒
	 * @param readTimeout 读取超时时间（毫秒），如果为null则默认为180秒
	 * @return 请求响应内容，如果为null则表示请求异常
	 */
	public static String doPost(String url, String params, Integer connTimeout, Integer readTimeout)
	{
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try
		{
			URL realUrl = new URL(url);
			// 打开和URL之间的连接,根据url
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.addRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			//conn.setRequestProperty("Content-Type", contentType == null? "application/json" : contentType);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 设置请求超时时间和读取超时时间
			conn.setConnectTimeout(connTimeout == null ? 5000 : connTimeout);
			conn.setReadTimeout(readTimeout == null ? 5000 : readTimeout);

			// 获取URLConnection对象对应的输出流，设置utf-8编码
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应,设置utf-8编码
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null)
				result += line;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		//使用finally块来关闭输出流、输入流
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
	}
}
