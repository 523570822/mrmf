package com.mrmf.service.finance;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.Account;
import com.mrmf.entity.WeRed;
import com.mrmf.entity.WeSysCardChargeHis;
import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.WeUserWalletHis;
import com.osg.entity.FlipInfo;

public interface FinanceService {

	/**
	 * 查询用户余额
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeUserWalletHis> queryUserVallet(FlipInfo<WeUserWalletHis> fpi);
	/**
	 * 查询
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WeUserWalletHis> queryUserWalletHisAsExport(String userName,String organId, String startTimeStr, String endTimeStr);
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<String> queryUserIds(String userName);
	/**
	 * 
	 * @param staffName
	 * @return
	 */
	public List<String> queryStaffIds(String staffName);
	/**
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeRed> queryStaffWeRed(FlipInfo<WeRed> fpi);
	/**
	 * 查询技师红包记录
	 * @param staffName
	 * @param startTimeStr
	 * @param endTimeStr
	 * @return
	 */
	public List<WeRed> queryWeRedsAsExport(String staffName, String startTimeStr, String endTimeStr);
	/**
	 * 根据名字查询id
	 * @param organName
	 * @return
	 */
	public List<String> queryOrganIds(String organName);
	/**
	 * 查询平台卡
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeSysCardChargeHis> querySysCard(FlipInfo<WeSysCardChargeHis> fpi);
	/**
	 * 查询平台卡作为导出
	 * @param organName
	 * @param startTimeStr
	 * @param endTimeStr
	 * @return
	 */
	public List<WeSysCardChargeHis> querySyscardAsExport(String organName, String startTimeStr, String endTimeStr);
	/**
	 * 查询分账记录
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeUserPayFenzhang> queryFenZhang(FlipInfo<WeUserPayFenzhang> fpi);
	/**
	 * 导出用户分账
	 * @param userName
	 * @param startTimeStr
	 * @param endTimeStr
	 * @return
	 */
	public List<WeUserPayFenzhang> queryFenZhangAsExport(String userName, String startTimeStr, String endTimeStr);
	
}
