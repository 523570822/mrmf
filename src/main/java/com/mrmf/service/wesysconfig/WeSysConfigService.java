package com.mrmf.service.wesysconfig;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.WeCarousel;
import com.mrmf.entity.WeSysConfig;
import com.mrmf.entity.WeUserCompensate;
import com.mrmf.entity.WeUserFeedback;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface WeSysConfigService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询平台业务设置信息
	 * 
	 * @return
	 * @throws BaseException
	 */
	public WeSysConfig query() throws BaseException;

	/**
	 * 新增/修改平台业务设置信息
	 * 
	 * @param config
	 * @return
	 */
	public ReturnStatus upsert(WeSysConfig config);
	/**
	 * 添加轮播图片
	 * @param imgId
	 */
	public Boolean addCarouselImg(String imgId);
	/**
	 * 查询所有的轮播图片
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeCarousel> queryCarouselImg(FlipInfo<WeCarousel> fpi);
	/**
	 * 删除轮播图片
	 * @param carouselId
	 */
	public void deleteCarouselImg(String carouselId);
	/**
	 * 查收意见反馈
	 * @param feedBacks
	 * @return
	 */
	public FlipInfo<WeUserFeedback> findFeedBacks(
			FlipInfo<WeUserFeedback> feedBacks);
	/**
	 * 查询所有的赔付
	 * @param compensateFiFlipInfo
	 * @return
	 */
	public FlipInfo<WeUserCompensate> findCompensates(
			FlipInfo<WeUserCompensate> compensateFiFlipInfo);
	/**
	 * 根据赔付的id 获得赔付实体
	 * @param compensateId
	 * @return
	 */
	public WeUserCompensate findCompensateById(String compensateId);
	/**
	 * 保存赔付对象
	 * @param weUserCompensate
	 */
	public void saveCompensate(String compensateId, String result,
			String resultDesc);
/**
 * 
 * @param userName
 * @param startTime
 * @param endTime
 * @return
 */
	public List<WeUserFeedback> exportUserFeed(String userName, Date startTime, Date endTime,String type);





}
