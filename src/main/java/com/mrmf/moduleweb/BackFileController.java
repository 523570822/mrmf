package com.mrmf.moduleweb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.osg.entity.Entity;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FileNameUtil;
import com.osg.framework.util.OSSFileUtil;

@Controller
@RequestMapping("/back/super/file")
public class BackFileController {

	private final static Logger logger = LoggerFactory.getLogger(BackFileController.class);

	/**
	 * 上传组件 更改文件名称为fileid
	 * 
	 * @param file
	 * @param body
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody Object uploadReFileidName(@RequestParam("onlyFile") MultipartFile[] files,
			MultipartHttpServletRequest request) throws IOException {
		String isPublic = request.getHeader("isPublic");
		ReturnStatus result = new ReturnStatus(false);

		// 当前登录用户id
		List<String> resFiles = new ArrayList<String>();
		for (MultipartFile multipartfile : files) {
			String fileId = oSSupload(multipartfile, isPublic);
			resFiles.add(fileId);
		}
		result.setData(resFiles);
		result.setSuccess(true);
		return result;
	}

	public String oSSupload(MultipartFile file, String isPublic) {
		try {

			// oos 云存储
			String etag = "";
			String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(file.getOriginalFilename());
			if (isPublic != null) {
				etag = OSSFileUtil.upload(file.getInputStream(), file.getSize(), ossId, OSSFileUtil.pubBucketName);
			} else {
				etag = OSSFileUtil.upload(file.getInputStream(), file.getSize(), ossId, OSSFileUtil.privBucketName);
			}

			if (etag != null) {
				return ossId;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/editorFileUpload", method = RequestMethod.POST)
	public @ResponseBody void editorFileUpload(MultipartHttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = response.getWriter();
		Map<String, MultipartFile> fileMap = request.getFileMap();
		String isPublic = "true";
		for (String key : fileMap.keySet()) {
			String fileId = oSSupload(fileMap.get(key), isPublic);
			String fileUrl = "http://" + OSSFileUtil.imageHost + "/" + fileId;
			String callback = request.getParameter("CKEditorFuncNum");
			out.println("<script type=\"text/javascript\"> ");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl + "',''" + ")");
			out.println("</script>");
			out.flush();
			out.close();
		}

	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public @ResponseBody Object download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileid = request.getParameter("fileid");
		String ispublic = request.getParameter("isPublic");

		String bucketName = OSSFileUtil.privBucketName;
		if (ispublic != null) {
			bucketName = OSSFileUtil.pubBucketName;
		}
		InputStream inputStream = OSSFileUtil.readFileInputStream(fileid, bucketName);

		// 设置输出的格式
		response.reset();
		response.setContentType(FileNameUtil.getSuffixNoPoint(fileid));
		response.addHeader("Content-Disposition",
				new String(("attachment; filename=\"" + fileid + "\"").getBytes("GB2312"), "iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[100];
		int len;
		try {
			while ((len = inputStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/delete/{fileid}", method = RequestMethod.GET)
	public @ResponseBody Object deleteFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fileid) {
		ReturnStatus result = new ReturnStatus(true);
		OSSFileUtil.deleteFile(fileid, OSSFileUtil.privBucketName);
		return result;
	}
}
