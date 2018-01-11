package com.mrmf.sqlDao;

import com.mrmf.entity.sqlEntity.VipGoodsHistory;
import com.osg.entity.FlipInfo;

/**
 * Created by 蔺哲 on 2017/9/4.
 */
public interface VipGoodsHistoryDao {
    public FlipInfo<VipGoodsHistory> allstudent(FlipInfo fpi);
    public VipGoodsHistory findById(Long id);
    public int update(Long orderId, int status, String msg);
}
