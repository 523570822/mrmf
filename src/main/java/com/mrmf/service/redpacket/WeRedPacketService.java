package com.mrmf.service.redpacket;

import com.mrmf.entity.WeRed;
import com.mrmf.entity.WeRedRecord;
import com.osg.entity.FlipInfo;

public interface WeRedPacketService {
	/**
	 * 查询所有的系统平台红包
	 * @param fpi
	 */
	 public FlipInfo<WeRed> findWeReds(FlipInfo<WeRed> fpi);
	/**
	 * 保存红包
	 * @param weRed
	 */
	public void saveRedPacket(WeRed weRed);
	/**
	 * 查询红包详情记录
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeRedRecord> findWeRedRecords(FlipInfo<WeRedRecord> fpi);
}
