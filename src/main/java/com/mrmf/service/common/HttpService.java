package com.mrmf.service.common;












import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.Charsets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

/**
* @ClassName: HttpService
* @Description: 初始化需要在Configure指定密钥的为止，以及密码
* @author fudk fudk_k@sina.com
* @date 2016年3月26日 下午8:05:46
*
*/ 
public class HttpService {
    private static Log logger = LogFactory.getLog(HttpService.class);

    private static CloseableHttpClient httpClient = buildHttpClient();

    private static int socketTimeout = 5000;

    private static int connectTimeout = 5000;

    private static int requestTimeout = 5000;

    public static CloseableHttpClient buildHttpClient() {

        try {
        	/*
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(Configure.getCertLocalPath()));
            try {
                keyStore.load(instream, Configure.getCertPassword().toCharArray());
            } finally {
                instream.close();
            }


            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			*/
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(requestTimeout)
                    .setSocketTimeout(socketTimeout).build();
            
            ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charsets.toCharset("utf-8")).build();
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(100000).build(); 
            
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();  
            ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();  
            registryBuilder.register("http", plainSF);  
            //指定信任密钥存储对象和连接套接字工厂  
            try {  
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();  
                LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
                registryBuilder.register("https", sslSF);  
            } catch (KeyStoreException e) {  
                throw new RuntimeException(e);  
            } catch (KeyManagementException e) {  
                throw new RuntimeException(e);  
            } catch (NoSuchAlgorithmException e) {  
                throw new RuntimeException(e);  
            }  
            Registry<ConnectionSocketFactory> registry = registryBuilder.build(); 
            
          //设置连接管理器  
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);  
            connManager.setDefaultConnectionConfig(connConfig);  
            connManager.setDefaultSocketConfig(socketConfig);  
            //构建客户端  
            httpClient= HttpClientBuilder.create().setConnectionManager(connManager).build();

            /*httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    //.setSSLSocketFactory(sslsf)
                    .build();*/

            return httpClient;
        } catch (Exception e) {
            throw new RuntimeException("error create httpclient......", e);
        }
    }
    
    static class AnyTrustStrategy implements TrustStrategy{  
        
        @Override  
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
            return true;  
        }  
          
    } 


    public static String doGet(String requestUrl) throws Exception {
        HttpGet httpget = new HttpGet(requestUrl);
        try {


            logger.error("Executing request " + httpget.getRequestLine());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            
            return httpClient.execute(httpget, responseHandler);
        } finally {
            httpget.releaseConnection();
        }
    }

    public static String doPost(String url, Object object2Xml) {

        String result = null;
        
        HttpPost httpPost = new HttpPost(url);

        String postDataXML = XMLParser.toXML(object2Xml);

        logger.info("API POST DATA:");
        logger.info(postDataXML);

        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        logger.info("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            logger.error("http get throw ConnectionPoolTimeoutException(wait time out)", e);

        } catch (ConnectTimeoutException e) {
            logger.error("http get throw ConnectTimeoutException", e);

        } catch (SocketTimeoutException e) {
            logger.error("http get throw SocketTimeoutException", e);

        } catch (Exception e) {
            logger.error("http get throw Exception", e);

        } finally {
            httpPost.releaseConnection();
        }

        return result;
    }
}
