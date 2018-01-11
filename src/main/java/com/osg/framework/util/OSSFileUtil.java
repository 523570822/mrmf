package com.osg.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import com.osg.framework.Constants;

public class OSSFileUtil {
	private static final Logger logger = LoggerFactory.getLogger(OSSFileUtil.class);

	private static String accessKeyId = Constants.ALI_OSS_ACCESSKEY;
	private static String accessKeySecret = Constants.ALI_OSS_ACCESSSECRET;
	public static final String pubBucketName = Constants.ALI_OSS_PUBBUCKET;
	public static final String privBucketName = Constants.ALI_OSS_PRIVBUCKET;
	public static String endpoint = Constants.ALI_OSS_ENDPOINT;
	public static final String imageHost = Constants.ALI_OSS_IMAGE_HOST;
	public static OSSClient client = null;

	static {
		logger.info("init ali.oss(阿里云存储) start ...");
		logger.info("init ali.oss(阿里云存储) :pubBucketName:{" + pubBucketName + "},privBucketName:{" + privBucketName
				+ "},endpoint:{" + endpoint + "}");
		client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		logger.info("init ali.oss(阿里云存储) end");
	}

	private static void createBucket(String bucketName) {
		client.createBucket(bucketName);
	}

	public static String upload(InputStream content, long length, String fileid, String bucketName) {
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 必须设置ContentLength
		meta.setContentLength(length);
		Map<String, String> userMetadata = new HashMap<String, String>();
		userMetadata.put("flag", "a");
		meta.setUserMetadata(userMetadata);
		// 上传Object.
		PutObjectResult result = client.putObject(bucketName, fileid, content, meta);
		// 打印ETag
		return result.getETag();
	}

	public static OSSObject readFileObject(String fileid, String bucketName) {
		OSSObject file = client.getObject(bucketName, fileid);
		// 获取Object的输入流
		return file;
	}

	public static InputStream readFileInputStream(String fileid, String bucketName) {
		OSSObject file = OSSFileUtil.readFileObject(fileid, bucketName);
		// 获取Object的输入流
		return file.getObjectContent();
	}

	public static void deleteFile(String fileid, String bucketName) {
		logger.info(endpoint + ":" + bucketName);
		client.deleteObject(bucketName, fileid);
	}
	public static URL getFileUrl(String bucketName, String fileid) throws IOException {
		return client.generatePresignedUrl(bucketName,fileid,new Date());

	}
}
