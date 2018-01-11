package com.mrmf.service.weuser;

import java.util.List;

import com.mrmf.entity.user.Bigsort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeCarousel;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WeStaffCase;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;

public interface WeUserService {
	/**  微信用户调用的接口  */
	
	/**
	 * 查询精美发型
	 * @param fpiWeStaffCases 查询结果需要的参数
	 * @param latitude 
	 * @param longitude 
	 * @param city 
	 * @return  返回所有的结果
	 */
	public FlipInfo<WeStaffCase> queryBeautyALL(FlipInfo<WeStaffCase> fpiWeStaffCases
			,String type,String hotOrder,String priceOrder, double longitude, double latitude, String city,int priceCount,List<String> typeList);


	public List<Bigsort> findBigSortByOrganId(String organId) ;

	/**
	 * 查询美甲  足疗  美容 的案例
	 * @param fpiWeStaffCases 查询结果需要的参数
	 * @return  返回所有的结果
	 */
	public FlipInfo<WeStaffCase> queryTypeProgramALL(FlipInfo<WeStaffCase> fpiWeStaffCases
			,String type,String hotOrder,String priceOrder,double longitude,double latitude, String city);
	/**
	 * 查询所有的集合
	 * @param hairType 指定一要查询的那个类型的啊
	 * @return 精美发型的类型集合
	 */
	public List<Code> findBeautyType(String hairType);

	/**
	 * 查询经典的案例
	 * @param title 案例的名称
	 * @return  符合条件的案例;
	 * 
	 */
	public FlipInfo<WeStaffCase> queryBeautyCase(FlipInfo<WeStaffCase> fpiWeStaffCases,String title,String homeType);

	/**
	 * 查询技师
	 * @param name 技师名称
	 * @return  符合条件的案例;
	 *
	 */
	public FlipInfo<Staff> queryStaff(FlipInfo<Staff> fpiWeStaffCases,String name);


	/**
	 * 查询技师
	 * @param staffId  技师i
	 * @return 技师
	 */
	public Staff queryStaffById(String staffId);
	/**
	 * 根据用户的ID  查找用户
	 * @param userId  用户的id
	 * @return 用户
	 */
    public User queryUserById(String userId);
    /**
     * 根据案例的id去查询案例
     */
    public WeStaffCase queryCaseById(String caseId);
    
    /**
     * 根据获取技师列表
     */
    public FlipInfo<Staff> queryStaffALL(Query query,FlipInfo<Staff> staffs,double longitude,double latitude);



    /**
     * 通过用户的位置   查询距离  去排序技师
     */
    public FlipInfo<Staff> queryStaffByUser(Query query,double longitude,double latitude,double maxDistance,FlipInfo<Staff> fpi);


	/**
	 * 通过用户的位置   查询距离  去排序技师 并根据名字获取
	 */
	public FlipInfo<Staff> queryStaffByName(Query query,double longitude,double latitude,double maxDistance,FlipInfo<Staff> fpi,String name);
    /**
     * 查询技师根据距离和技师的 id
     */
    public Staff queryStaffByIdAndDistance(String staffId, double longitude,
			double latitude);
    /**
     * 根据技师的id去查询案例
     */
	public FlipInfo<WeStaffCase> queryCaseByStaffId(String staffId,FlipInfo<WeStaffCase> weStaffCases);
	/**添加一预约  */
	public void addAppoint(WeOrganOrder weOrganOrder);
	/**
	 * 根据openId获取用户信息
	 * @param openid
	 * @return
	 */
	public User queryUserByOpenId(String openid);

	/**
	 *  关注之后案例关注数量加1
	 * @param  followCount
	 */
	public void updateFollowCount(int followCount,String caseId);

	/**
	 * 根据店铺的ID 去查询店铺
	 * @param organId
	 * @return
	 */
	public Organ findOrganById(String organId);

	/**
	 * 通过手机号查询用户
	 * @param phone 用户的手机号
	 * @return
	 */
	public User findUserByPhone(String phone);
	
	/**
	 * 查询技师是否存在可预约时间
	 */
	public boolean findStaffServiceTime(String staffId);
	
	/**
	 * 验证订单是否存在
	 * @param weOrganOrder
	 * @return
	 */
	public ReturnStatus verifyOrder(WeOrganOrder weOrganOrder);
	/**
	 * 查询城市列表
	 * @return
	 */
	public List<WeBCity> cityList();
	
	/**
	 * 查询技师发表的案例
	 * @param staffId
	 * @return
	 */
	public List<WeStaffCase> findWeStaffCases(String staffId);

	/**
	 * 判断是否发表过案例
	 * @param staffId
	 * @return
	 */
	public boolean isHaveWeStaffCase(String staffId);

	/**
	 * 查询所有的轮播图片
	 */
	public List<WeCarousel> findCarousels();

	/**
	 * 查询code 通过类型
	 * @param type
	 * @return
	 */
	public List<Code> findCodeByType(String type);





	//public void addCaseByLocation();
} 
