package com.mrmf.service.stage;

import com.mrmf.entity.stage.StageCategoryFees;
import com.osg.entity.FlipInfo;

import java.util.List;

public interface StageCatFeeService {
    public void saveOrUpdate(StageCategoryFees t);
    public void deleteById(int id);
    public StageCategoryFees queryById(int id);
    public List<StageCategoryFees> query();
    public FlipInfo<StageCategoryFees> query(FlipInfo<StageCategoryFees> fpi);
}
