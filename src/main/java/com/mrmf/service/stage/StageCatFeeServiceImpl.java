package com.mrmf.service.stage;

import com.mrmf.entity.stage.StageCategoryFees;
import com.mrmf.service.base.BaseServiceImpl;
import com.osg.framework.mongodb.EMongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("stageCatFeeService")
public class StageCatFeeServiceImpl extends BaseServiceImpl<StageCategoryFees> implements StageCatFeeService  {

    public StageCatFeeServiceImpl(){
        super();
    }
    @Autowired
    private EMongoTemplate mongoTemplate;



}
