package com.mrmf.module.aboutus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.User;
import com.mrmf.entity.WeToken;
import com.mrmf.entity.wxpay.CommonUtil;
import com.mrmf.service.redis.RedisService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 关于我们的控制器
 */
@Controller
@RequestMapping("/aboutus")
public class AboutUsController {
	@Autowired
	private RedisService redisService;
	@RequestMapping("/toAboutUs")
	public ModelAndView toAboutUs(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(true);
		String TOKEN = "";
		try {
			WeToken weToken = redisService.getTonkenInfo("user");
			TOKEN = weToken.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject action_info = new JSONObject();
		JSONObject paramJSON = new JSONObject();
		paramJSON.put("expire_seconds",1800);
		paramJSON.put("action_name","QR_SCENE");
		paramJSON.put("action_info",action_info);

		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + TOKEN + "";//返回类型是二维码，scope获取用户信息类型
		String s = doPostJson(url, paramJSON.toString());
		JSONObject parse = (JSONObject) JSONObject.parse(s);
		String ticket = parse.get("ticket").toString();
		String ticket1 = CommonUtil.urlEncodeUTF8(ticket);
		String url2 = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket1 + "";
		ModelAndView mv = new ModelAndView();
		mv.addObject("showqrcode",url2);
		mv.setViewName("aboutus/index");
		return mv;
	}

	@RequestMapping("/welcome")
	public ModelAndView welcome(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aboutus/welcome");
		return mv;
	}
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}
	/**
	 * 返回文章详情页面
	 * @return
	 */
	@RequestMapping(value = "/articles",produces = "text/html; charset=utf-8",method = RequestMethod.GET)
	public ModelAndView getArtcles(){
		System.out.println("articles.do");
		return new ModelAndView("aboutus/article");
	}
}
