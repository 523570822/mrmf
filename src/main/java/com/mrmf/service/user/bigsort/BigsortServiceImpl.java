package com.mrmf.service.user.bigsort;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.user.Bigsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("bigsortService")
public class BigsortServiceImpl implements BigsortService {

    @Autowired
    private EMongoTemplate mongoTemplate;

    @Override
    public FlipInfo<Bigsort> query(FlipInfo<Bigsort> fpi) throws BaseException {
        mongoTemplate.findByPage(Query.query(Criteria.where("delete_flag").ne(true)), fpi, Bigsort.class);
        return fpi;
    }

    @Override
    public Bigsort queryById(String bigsortId) throws BaseException {
        Bigsort bigsort = mongoTemplate.findById(bigsortId, Bigsort.class);
        if (bigsort == null)
            throw new BaseException("指定id的服务大类信息不存在");
        else {
            return bigsort;
        }
    }

    @Override
    public ReturnStatus upsert(Bigsort bigsort) {
        bigsort.setIdIfNew();
        bigsort.setCreateTimeIfNew();

        mongoTemplate.save(bigsort);
        ReturnStatus status = new ReturnStatus(true);
        status.setData(bigsort);
        return status;
    }

    @Override
    public List<Bigsort> findAll(String organId) throws BaseException {
        return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId).and("delete_flag").ne(true)), Bigsort.class);
    }

    public Bigsort findOne(String organId, String title) throws BaseException {
        return mongoTemplate.findOne(Query.query(Criteria.where("organId").is(organId).and("name").is(title)), Bigsort.class);
    }
}
