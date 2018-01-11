package com.mrmf.service.wxOAuth2;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.Account;
import com.mrmf.entity.User;
import com.mrmf.entity.WxShare;
import com.mrmf.entity.wxpay.CommonUtil;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Configure;
import com.osg.entity.AccessToken;
import com.osg.framework.Constants;
import com.osg.framework.mongodb.EMongoTemplate;
import com.typesafe.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liuzhen on 17/3/28.
 */
@Service("wxOAuth2Service")
public class WXOAuth2ServiceImpl implements WXOAuth2Service {
    @Autowired
    private EMongoTemplate mongoTemplate;
    @Autowired
    private AccountService accountService;
    @Override
    public AccessToken getOAuth2TokenByCode(String code,String inviaterId) {
        AccessToken accessToken = new AccessToken();
        String reqUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ Configure.userAppID +"&secret="+Configure.userAppSecret+"&code="+code+"&grant_type=authorization_code";
        JSONObject jsonObject = (JSONObject) JSONObject.parse(CommonUtil.httpsRequest(reqUrl,"GET",null));
        if (jsonObject != null){
            accessToken.setOpenid(jsonObject.get("openid").toString());
            accessToken.setAccess_token(jsonObject.get("access_token").toString());
            accessToken.setExpires_in(jsonObject.get("expires_in").toString());
            Account account = new Account();
            account.setAccountName(accessToken.getOpenid());
            account.setAccountType("用户");
            account.setStatus("1");
            account.setInviterId(inviaterId);
            mongoTemplate.save(account);

        }
        return accessToken;
    }

    @Override
    public User getOAuth2UserInfoByToken(AccessToken accessToken) {
        String reqUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken.getAccess_token()+"&openid="+accessToken.getOpenid()+"&lang=zh_CN";
        JSONObject jsonObject = (JSONObject) JSONObject.parse(CommonUtil.httpsRequest(reqUrl,"GET",null));
        User user = new User();
        if (jsonObject != null){
            user.setName(jsonObject.get("nickname").toString());
            user.setSex("1".equals(jsonObject.get("sex").toString())?"男":"女");
            user.setCity(jsonObject.get("city").toString());
            user.setAvatar(jsonObject.getString("headimgurl"));
            mongoTemplate.save(user);
        }
        return user;
    }

    /**
     * 保存当前分享
     * @param inviator
     */
    @Override
    public void saveWxShare(String inviator,String userID) {
        WxShare wxShare = new WxShare(inviator);
        wxShare.setUserId(userID);
        mongoTemplate.save(wxShare);
    }

    /**
     * 查询当前用户是否抢过红包
     * @param userId
     * @return
     */
    @Override
    public WxShare findWxShareWithUserId(String userId) {
        List<WxShare> wxShareList = mongoTemplate.find(new Query(Criteria.where("userId").is(userId)).with(new Sort(Sort.Direction.DESC,"createTime")),WxShare.class);
        return wxShareList != null && wxShareList.size() > 0?wxShareList.get(0):null;
    }
}
