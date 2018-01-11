package com.mrmf.service.wxSendNewsMessage;

import com.mrmf.entity.Article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Created by liuzhen on 17/4/11.
 */

@Service("wxSendNewsMessageService")
public class WxSendNewsMessageServiceImpl implements WxSendNewsMessageService{
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 获取当前文章
     * @param count
     * @return
     */
    @Override
    public Article getCurrentArticleMessage(String count) {
//        mongoTemplate.save(new Article());
//        Article article = mongoTemplate.findOne(Query.query(Criteria.where("isDelete").is("0")),Article.class);
//        if (article != null){
//            return article;
//        }else {
//            return new Article();
//        }
        return new Article();
    }
}
