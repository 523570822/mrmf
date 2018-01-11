package com.mrmf.service.redis;

import com.mrmf.entity.WeToken;
import com.mrmf.entity.wxpay.WxTemplate;

import java.util.Map;

/**
 * Created by Tibers on 16/4/21.
 */
public interface RedisService {
	/**
	 * 
	 * @param url
	 * @param type 用户端user 店铺端 organ 技师端 staff
	 * @return
	 */
    public Map<String, Object> getWechatPositioningMessage(String url,String type);
    
    /**
     * 模版消息发送
     * @param openId 接收者openId
     * @param type 接收者类型
     * @param templateID 模版ID
     * @param temp 消息对象
     * @throws Exception
     */
    public void send_template_message(String openId, String type, String templateID, WxTemplate temp) throws Exception;
    
    /**
     * 消息对象封装
     * @param firstValue 标头 
     * @param keyword1Value 参数1
     * @param keyword2Value 参数2
     * @param keyword3Value 参数3
     * @param keyword4Value 参数4 参数4为空时直接传入NULL
     * @param remarkValue 备注消息
     * @return
     */
    public WxTemplate getWxTemplate(String firstValue, String keyword1Value, String keyword2Value, String keyword3Value, String keyword4Value, String keyword5Value,String remarkValue);
    /**
     * 获取acesssToken 
     * @param type  user
     * @return
     * @throws Exception
     */
    public WeToken getTonkenInfo(String type) throws Exception;
    /**
     * 模板参数为第二种
     * @param keyword5Value
     * @param remarkValue
     * @return
     */
    public WxTemplate getWxTemplateTwo(String firstValue, String keyword1Value, String keyword2Value, String keyword3Value, String keyword4Value, String keyword5Value,String remarkValue);
    /**
     * 
     * @param linkUrl 
     * @param openId
     * @param type
     * @param templateID
     * @param temp
     * @throws Exception
     */
    public void send_template_urlmessage(String linkUrl,String openId, String type, String templateID, WxTemplate temp) throws Exception;


    /**
     * 优惠券发放通知
     */
    public WxTemplate getWxTemplateWithCoupon(String firstValue, String accountValue,
                                              String timeValue, String typeValue, String creditChangeValue,
                                              String creditNameValue, String numberValue,String amountValue,String remarkValue);

    /**
     *
     * @param firstValue   头信息
     * @param keyword1    解绑号码
     * @param keyword2    解绑时间
     * @param remarkValue   尾信息
     * @return
     */
    public WxTemplate getWxTemplateWithBangDing(String firstValue, String keyword1, String keyword2, String remarkValue);
}
