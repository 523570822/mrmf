package com.osg.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.mrmf.entity.Article.Article;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import com.osg.entity.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.io.XMLWriter;


public class MessageUtil {
	/**
	 * 微信所有消息推送类型
	 */
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";  
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";

	/**
	 * 将xml 转换成map集合
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	/**
	 * 将文本消息对象转为xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 组装文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime((int) new Date().getTime());
		text.setContent(content);
		text.setMsgId("8266505834556878986221475074427698774559663961902471455485337489");
		return textMessageToXml(text);
	}

	/**
	 * 将图文消息对象转为xml
	 * @param textMessage
	 * @return
	 */
	public static String newsMessageToXml(TextMessage textMessage) throws IOException {
		Document document = DocumentHelper.createDocument();

		Element root =  document.addElement("xml");


		Element toUserNameElement = root.addElement("ToUserName");
		toUserNameElement.addText(textMessage.getToUserName());

		Element fromUserNameElement = root.addElement("FromUserName");
		fromUserNameElement.addText(textMessage.getFromUserName());

		Element createTimeElement = root.addElement("CreateTime");
		createTimeElement.addText((int) new Date().getTime()+"");
		createTimeElement.addAttribute("type","int");

		Element msgTypeElement = root.addElement("MsgType");
		msgTypeElement.addText("news");

		Element articleCountElement = root.addElement("ArticleCount");
		articleCountElement.addText("1");

		Element articlesElement = root.addElement("Articles");
		Element itemElement = articlesElement.addElement("item");
		Element TitleElement = itemElement.addElement("Title");
		TitleElement.addText(textMessage.getTitle());
		Element DescriptionElement = itemElement.addElement("Description");
		DescriptionElement.addText(textMessage.getDescription());
		Element PicUrlElement = itemElement.addElement("PicUrl");
		PicUrlElement.addText(textMessage.getPicUrl());
		Element UrlElement = itemElement.addElement("Url");
		UrlElement.addText(textMessage.getUrl());


		Element mainTitleElement = root.addElement("Title");
		mainTitleElement.addText(textMessage.getTitle());
		Element MainUrlElement = root.addElement("Url");
		MainUrlElement.addText(textMessage.getUrl());


		//创建字符串缓冲区
		StringWriter stringWriter = new StringWriter();
		//设置文件编码
		OutputFormat xmlFormat = new OutputFormat();
		xmlFormat.setEncoding("UTF-8");
		// 设置换行
		xmlFormat.setNewlines(true);
		// 生成缩进
		xmlFormat.setIndent(true);
		// 使用4个空格进行缩进, 可以兼容文本编辑器
		xmlFormat.setIndent("    ");

		//创建写文件方法
		XMLWriter xmlWriter = new XMLWriter(stringWriter,xmlFormat);
		//写入文件
		xmlWriter.write(document);
		//关闭
		xmlWriter.close();
		return stringWriter.toString();
	}

	/**
	 * 组装图文消息
	 * @param toUserName
	 * @param fromUserName
	 * @param article
	 * @return
	 */
	public static String initNewsTextWithData(String toUserName, String fromUserName, Article article) throws IOException {
		TextMessage news = new TextMessage();
		news.setFromUserName(toUserName);
		news.setToUserName(fromUserName);
		news.setMsgType(MessageUtil.MESSAGE_NEWS);
		news.setCreateTime((int) article.getCreateTime().getTime());
		news.setArticleCount("1");
		news.setTitle(article.getTitle());
		news.setDescription(article.getDescription());
		news.setPicUrl(article.getPicUrl());
		news.setUrl(article.getUrl());
		news.setMsgId("8266505834556878986221475074427698774559663961902471455485337489");
		return newsMessageToXml(news);
	}
	
	/**
	 * 产生文本
	 * @return
	 */
	public static String getText(){
		StringBuffer sb = new StringBuffer();
		sb.append("我是小喵，我朋友的爱豆鲁迅说，“学医救不了中国”，所以我毅然操起铅笔杆。");
		sb.append("尽管你即将看到一些无节操的文字，但请你相信，生活中的我是真的温柔贤淑。");
		sb.append("我的私房照还没有拍好，所以你不能知道我长什么样了，但是你可以回复“裸照”试试。");
		sb.append("其他的功能我还没有搞好，你现在可以猛戳任性STAR，会有天使替我给你做发型！");
		return sb.toString();
	}
}
