package com.mrmf.sqlDao;

import com.mrmf.entity.sqlEntity.Prestore;
import com.osg.entity.FlipInfo;

/**
 * Created by 蔺哲 on 2017/9/18.
 */
public interface PrestoreDao {
    public int save(Double money, String organId);
    public FlipInfo<Prestore> findByPage(FlipInfo fpi);
}
