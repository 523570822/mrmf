package com.mrmf.service.wxOAuth2;

import com.mrmf.entity.User;
import com.mrmf.entity.WxShare;
import com.osg.entity.AccessToken;

/**
 * Created by liuzhen on 17/3/28.
 */
public interface WXOAuth2Service {

    /**
     * 获取临时token 5分钟会改变一次
     * @param code
     * @return
     */
    public AccessToken getOAuth2TokenByCode(String code,String inviaterId);

    /**
     * 获取授权登录用户信息
     * @param accessToken
     * @return
     */
    public User getOAuth2UserInfoByToken(AccessToken accessToken);

    /**
     * 保存当前分享
     * @param inviator
     */
    public void saveWxShare(String inviator,String userID);

    /**
     * 查询当前用户是否抢过红包
     * @param userId
     * @return
     */
    public WxShare findWxShareWithUserId(String userId);

}
