package com.mrmf.entity.Article;

import com.mrmf.service.common.Configure;
import com.osg.entity.DataEntity;

import java.util.Date;

/**
 * Created by liuzhen on 17/4/11.
 */
public class Article extends DataEntity {
    //创建时间
    private Date CreateTime;
    //消息类型
    private String MsgType;
    //图文消息个数<=10
    private String ArticleCount;
    //多条图文消息
    private String Articles;
    //消息标题
    private String Title;
    //图文消息描述
    private String Description;
    //图片链接
    private String PicUrl;
    //点击图文跳转的链接
    private String Url;
    private String isDelete;

    public Article(){
        isDelete = "0";
        MsgType = "news";
        Title = "任性猫美业互联网介绍篇";
        Description = "任性猫美业互联网介绍篇";
        PicUrl = Configure.DOMAIN_URL+"/mrmf/module/resources/images/article.jpg";
        Url = Configure.DOMAIN_URL+"/mrmf/w/aboutus/articles.do";
        CreateTime = new Date();
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
        ArticleCount = articleCount;
    }

    public String getArticles() {
        return Articles;
    }

    public void setArticles(String articles) {
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
