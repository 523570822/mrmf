package com.mrmf.service.wxSendNewsMessage;

import com.mrmf.entity.Article.Article;

/**
 * Created by liuzhen on 17/4/11.
 */
public interface WxSendNewsMessageService {

    /**
     * 获取当前文章
     * @param count
     * @return
     */
    public Article getCurrentArticleMessage(String count);
}
