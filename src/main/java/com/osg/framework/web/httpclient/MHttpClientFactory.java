package com.osg.framework.web.httpclient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.osg.framework.web.httpclient.binder.MJsonResponseTypeBinder;
import com.osg.framework.web.httpclient.binder.MResponseTypeBinder;

/**
 * 
 * Http client 工厂类
 * 
 * @author xiangf
 * @since JDK5.0
 */
public class MHttpClientFactory {

	private HttpClient client;
	private MResponseTypeBinder binder;
	private int maxTotal;
	private int maxPerRoute;
	private int timeout;
	private int socketTimeout;
	private boolean skipSSLCheck;
	private static Log log = LogFactory.getLog(MHttpClientFactory.class);

	/**
	 * 使用默认参数创建MHttpClientFactory
	 * @throws Exception 
	 */
	public MHttpClientFactory() throws Exception {
		this.initHttpClient();
	}

	/**
	 * 创建MHttpClientFactory, 建议每个应用只实例化一个MHttpClientFactory对象，除非初始化策略不同
	 * 
	 * @param maxTotal
	 *            最大连接数
	 * @param maxPerRoute
	 *            每地址最大连接数
	 * @param timeout
	 *            超时时间
	 * @param socketTimeout
	 *            socket超时时间
	 * @param skipSSLCheck
	 *            是否忽略ssl证书验证
	 * @throws Exception 
	 */
	public MHttpClientFactory(int maxTotal, int maxPerRoute, int timeout,
			int socketTimeout, boolean skipSSLCheck) throws Exception {
		this.maxTotal = maxTotal;
		this.maxPerRoute = maxPerRoute;
		this.timeout = timeout;
		this.socketTimeout = socketTimeout;
		this.skipSSLCheck = skipSSLCheck;
		this.initHttpClient();
	}

	/**
	 * 获取HttpClient
	 * 
	 * @return MHttpClient
	 */
	public MHttpClient getClient() {
		if (binder == null) {
			binder = new MJsonResponseTypeBinder();
		}
		MHttpClient mc = new MHttpClient(client);
		mc.setBinder(binder);
		if (log.isDebugEnabled()) {
			log.debug("Generate http client:" + mc.toString() + " with binder:"
					+ binder);
		}
		return mc;
	}

	private void initHttpClient() throws Exception {
		SSLSocketFactory ssf = null;
		// 跳过SSL证书检查
		if (skipSSLCheck) {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
					// Do nothing
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
					// Do nothing
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			ssf = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} else {
			ssf = SSLSocketFactory.getSocketFactory();
		}

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, ssf));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);
		if (maxTotal != 0) {
			cm.setMaxTotal(maxTotal);
		}
		if (maxPerRoute != 0) {
			cm.setDefaultMaxPerRoute(maxPerRoute);
		}

		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		if (timeout != 0) {
			 HttpConnectionParams.setConnectionTimeout(params, timeout);
		}
		if (socketTimeout != 0) {
			 HttpConnectionParams.setSoTimeout(params, socketTimeout);
		}
		client = new DefaultHttpClient(cm, params);
		log.info("MHttpClientFactory initialized successfully, maxTotal:"
				+ maxTotal + ", maxPerRoute:" + maxPerRoute + ", timeout:"
				+ timeout + ", socketTimeout:" + socketTimeout
				+ ", skipSSLCheck:" + skipSSLCheck + ".");
	}

	public MResponseTypeBinder getBinder() {
		return binder;
	}

	/**
	 * 设置MResponseTypeBinder，默认为json binder
	 * 
	 * @param binder
	 */
	public void setBinder(MResponseTypeBinder binder) {
		this.binder = binder;
	}

}
