package com.osg.entity;


import com.mrmf.entity.Article.Article;

import java.util.List;

/**
 *
 * 	消息的基类
 * @author yangshaodong
 *
 */
public class BaseMessage {
	//接收方微信号
	private String ToUserName;
	//发送方微信号
	private String FromUserName;
	//创建时间
	private int CreateTime;
	//消息类型
	private String MsgType;
	//图文消息个数<=10
	private String ArticleCount;
	//多条图文消息
	private List<Article> Articles;
	//消息标题
	private String Title;
	//图文消息描述
	private String Description;
	//图片链接
	private String PicUrl;
	//点击图文跳转的链接
	private String Url;

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public List getArticles() {
		return Articles;
	}

	public void setArticles(List articles) {
		Articles = articles;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public int getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(int createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
