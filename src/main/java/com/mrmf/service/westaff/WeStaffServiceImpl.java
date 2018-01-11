package com.mrmf.service.westaff;

import com.mrmf.entity.Staff;
import com.osg.framework.mongodb.EMongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service("weStaffService")
public class WeStaffServiceImpl implements WeStaffService {


    @Autowired
    private EMongoTemplate mongoTemplate;

    @Override
    public Staff getStaffById(String staffId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)),Staff.class);
    }
}
