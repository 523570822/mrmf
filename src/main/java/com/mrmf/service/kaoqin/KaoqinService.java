package com.mrmf.service.kaoqin;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.kaoqin.KBancidingyi;
import com.mrmf.entity.kaoqin.KKaoqin;
import com.mrmf.entity.kaoqin.KKaoqinleibie;
import com.mrmf.entity.kaoqin.KKaoqintime;
import com.mrmf.entity.kaoqin.KPaiban;
import com.mrmf.entity.kaoqin.KQingjiadengji;
import com.mrmf.entity.staff.Staffpost;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;

public interface KaoqinService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */
	
	/**
	 * 保存考勤班次
	 * @param bancidingyi
	 */
	public void saveBanci(KBancidingyi bancidingyi);
	/**
	 * 查询班次定义
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<KBancidingyi> queryBancidingyi(FlipInfo<KBancidingyi> fpi) throws BaseException;
	/**
	 * 查询考勤类别
	 * @param fpi 
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<KKaoqinleibie> queryKaoqinleibie(FlipInfo<KKaoqinleibie> fpi)  throws BaseException;
	
	/**
	 * 新增考类别
	 * @param kaoqinleibie 考勤类别对象
	 * @throws BaseException
	 */
	public void saveKaoqinleibie(KKaoqinleibie kaoqinleibie) throws BaseException;
	/**
	 * 根据id去查找考勤类别
	 * @param kaoqinleibieId 考勤类别的id
	 * @return 考勤类别对象
	 */
	public KKaoqinleibie findKaoqinleibieById(String kaoqinleibieId);
	/**
	 * 更新考勤类别
	 * @param kaoqinleibie
	 */
	public void updateKaoqinleibie(KKaoqinleibie kaoqinleibie);
	/**
	 * 删除
	 * @param kaoqinleibieId
	 */
	public void deleteKaoqinleibie(String kaoqinleibieId);
	/**
	 * 查找班次定义
	 * @param banciId  班次的id
	 * @return
	 */
	public KBancidingyi queryBancidingyiById(String banciId);
	/**
	 * 删除班次
	 * @param banciId
	 */
	public void deleteBanci(String banciId);
	/**
	 * 查询所有的请假登记
	 * @param fpi
	 * @return
	 */
	public FlipInfo<KQingjiadengji> queryQingjiadengji(FlipInfo<KQingjiadengji> fpi);
	/**
	 * 通过id 去查找请假登记
	 * @param qingjiadengjiId
	 * @return
	 */
	public KQingjiadengji findQingjiadengjiById(String qingjiadengjiId);
	
	/**
	 * 查找所有的在职技师
	 * @param qingjiadengjiId
	 * @return
	 */
	public List<Staff> findWorkStaff(String organId);
	/**
	 * 查找所有的考勤类别  通过的organId
	 * @param organId
	 * @return
	 */
	public List<KKaoqinleibie> findKaoqinleibie(String organId);
	/**
	 * 保存请假登记
	 * @param qingjiadengji
	 */
	public void upsertQingjiadengji(KQingjiadengji qingjiadengji);
	/**
	 * 查询技师
	 * @param organId
	 * @param names
	 * @return
	 */
	public Staff findWorkStaff(String organId, String names);
	/**
	 * 查询考勤类型
	 * @param organId
	 * @param type1
	 * @return
	 */
	public KKaoqinleibie findKaoqinleibie(String organId, String type1);
	/**
	 * 删除考勤登记
	 * @param qingjiadengjiId
	 */
	public void deleteQingjiadengji(String qingjiadengjiId);
	/**
	 * 查询所有的请假登记  包括已经删除的
	 * @param fpi
	 * @return
	 */
	public FlipInfo<KQingjiadengji> findQingjiadengji(FlipInfo<KQingjiadengji> fpi);
	/**
	 * 分页查找所有的在职技师
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Staff> findWorkStaffByPage(FlipInfo<Staff> fpi,String organId);
	/**
	 * 查找岗位名称
	 * @param dutyId
	 * @return
	 */
	public Staffpost findDutyName(String dutyId);
	/**
	 * 查询班次定义
	 * @param organId
	 * @return
	 */
	public List<KBancidingyi> findBancidingyis(String organId);
	/**
	 * 保存排班
	 * @param kPaiban
	 */
	public void savePaiban(KPaiban kPaiban);
	/**
	 * 查询排班
	 * @param fpi
	 * @return
	 */
	public FlipInfo<KPaiban> findPaiBan(FlipInfo<KPaiban> fpi,String organId);
	
	/**
	 * 查询某一个时间段点的签到情况
	 */
	public void  signConditions(Date startTime, Date endTime, String organId,
			String staffId,Date endTimeRecord);
	
	/**
	 * 签到打卡情况查询
	 */
	public void queryStaffSignAndSetPaibanState(Date startTime, Date endTime, String organId,
			String staffId) throws ParseException;
	
	/**
	 * 保存考勤时间  也就是下次查询的时候从哪里查询
	 * @param kKaoqintime
	 */
	public void saveKKaoqintime(KKaoqintime kKaoqintime);
	/**
	 * 查询考勤时间  也就是下次查询的时候从哪里查询
	 */
	public boolean queryKKaoqintime(String organId,String staffId,int type);
	/**
	 * 查询早退的员工
	 * @param fpi
	 * @return
	 */
	public FlipInfo<KKaoqin> findZaotui(FlipInfo<KKaoqin> fpi);
	
	/**
	 * 查询迟到的员工
	 * @param fpi
	 * @return
	 */
	public FlipInfo<KKaoqin> findChiDao(FlipInfo<KKaoqin> fpi);
	/**
	 * 查询上次签到的时间
	 * @param organId
	 * @param get_id
	 * @return
	 */
	public KKaoqintime queryKaoqintime(String organId, String get_id,int type);
	/**
	 * 记录上次遍历排班和员工的签到情况
	 * @param organId
	 * @param staffId
	 * @param endTime
	 */
	public void updateKaoqintime(String organId, String staffId, Date endTime,int type);
	/**
	 * 查询旷工情况
	 * @param fpi
	 * @return
	 */
	public FlipInfo<KKaoqin> findKuangGong(FlipInfo<KKaoqin> fpi);
	/**
	 * 查询员工签到情况列表
	 * @param fpi
	 * @param organId
	 * @return
	 */
	public FlipInfo<KPaiban> queryStaffSign(FlipInfo<KPaiban> fpi,
			String organId);
	/**
	 * 查询排班
	 * @param paibanId
	 */
	public KPaiban findPaiBanById(String paibanId);
	
	/**
	 * 查询排班的技师
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Staff> findPaiBanStaff(FlipInfo<Staff> fpi);
	/**
	 * 转换排班的名称
	 * @param data
	 */
	public void convertToName(List<KPaiban> data);
	/**
	 * 转换排班的名称
	 * @param kPaiban
	 */
	public void convertToOneName(KPaiban kPaiban);
	/**
	 * 查询技师
	 * @param staffId
	 */
	public Staff findStaffById(String staffId);
	/**
	 * 判断是否有该排班
	 * @param string
	 * @param yearmonth
	 * @return
	 */
	public KPaiban getPaiBan(String staffId, int yearmonth);
	/**
	 * 判断是否有该排班
	 * @param string
	 * @param yearmonth
	 * @return
	 */
	public boolean isExistPaiban(String string);
	/**
	 * 统计考勤
	 * @param organId 公司的id
	 * @throws ParseException
	 */
	public void setKaoqin(String organId) throws ParseException;
	
	/**
	 * 初始化考勤数据
	 */
	public void initKaoqin();
	/**
	 * 初始化公司考勤
	 * @param organ
	 */
	public void initKaoqinByOrgan(Organ organ);
}
