package com.mrmf.service.stage;

import com.mrmf.service.base.BaseServiceImpl;
import com.mrmf.entity.Organ;
import com.mrmf.entity.stage.StageMent;
import com.osg.entity.FaceStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
@Service("stageService")
public class StageServiceImpl extends BaseServiceImpl<StageMent> implements StageService   {

    public StageServiceImpl(){
        super();
    }
    @Autowired
    private EMongoTemplate mongoTemplate;

    @Override
    public FaceStatus upsert(StageMent stageMent) {
        FaceStatus status;
        if(StringUtils.isEmpty(stageMent.get_id())){// 新建

            stageMent.setIdIfNew();
            stageMent.setCreateTimeIfNew();

            String organId = stageMent.getOrganId();
            if (StringUtils.isEmpty(organId)) {
                status = new FaceStatus(false, "organId不能为空");
                status.setEntity(stageMent);
                return status;
            } else {
                // 设置员工parentId，即总公司id信息
                Organ organ = mongoTemplate.findById(organId, Organ.class);
                if (organ == null) {
                    status = new FaceStatus(false, "organId指定的公司信息不存在");
                    status.setEntity(stageMent);
                    return status;
                }
            }



            StageMent     stageMentF = findOne(stageMent.getAndroidPoints().get(0).getDevicedId());
            if(stageMentF!=null){
                status = new FaceStatus(false, "设备已经绑定过");
                status.setEntity(stageMent);
                return status;
            }

            StageMent     stageMentQ = findOneS(stageMent.getAndroidPoints().get(0).getName());
            if(stageMentF!=null){
                status = new FaceStatus(false, "名称已经存在");
                status.setEntity(stageMent);
                return status;
            }
        }
        //没有绑定过的 查看镜台是否已经绑定
     //   StageMent     stageMentE    = findOne(stageMent.getOrganId(),stageMent.getName(),stageMent.getAndroidPoints().get(0).getFloor());

        StageMent     stageMentE    = findOne(stageMent.getOrganId(),stageMent.getAndroidPoints().get(0).getName(),null);
        if(stageMentE!=null){
            status = new FaceStatus(false, "镜台已被绑定,请联系客服");
            status.setEntity(stageMent);
            return status;
        }
        StageMent     stageMentG   = findOne(stageMent.getOrganId(),null,null);
        if(stageMentG==null){

        }else{

            stageMent.set_id(stageMentG.get_id());
           stageMent.getAndroidPoints().addAll(stageMentG.getAndroidPoints());


        //    stageMent.getAndroidPoints().add(stageMentG.getAndroidPoints().get(0));
        }
        mongoTemplate.save(stageMent);
        status = new FaceStatus(true, "成功添加设备。");
        status.setEntity(stageMent);
        return status;

    }

    @Override
    public StageMent findOne(String devicedId) {
        FaceStatus status;
        Criteria criteria=Criteria.where("androidPoints.devicedId").is(devicedId);
        Query query=new Query(criteria);
        StageMent     stageMentF = mongoTemplate.findOne(query,StageMent.class);

        return stageMentF;
    }

    @Override
    public void upsertAndSave(StageMent stageMent) {

        mongoTemplate.save(stageMent);


    }

    @Override
    public StageMent findOne(String organId, String name, String floor) {

        FaceStatus status;
        Criteria criteria;
        if(floor==null){
            if(name==null){
                criteria=Criteria.where("organId").is(organId);
            }else{
                criteria=Criteria.where("organId").is(organId).and( "name").is(name);
            }

        }else{
             criteria=Criteria.where("androidPoints.floor").is(floor).and("organId").is(organId).and( "name").is(name);
        }


        Query query=new Query(criteria);
        StageMent     stageMentF = mongoTemplate.findOne(query,StageMent.class);
        return stageMentF;
    }

  @Override
    public StageMent findOneS( String name) {

        Class<? extends String> ww = name.getClass();

        String ddd = ww.toString();
      //  ww.determineCollectionName(entityClass)
        FaceStatus status;
        Criteria criteria;
        String asd=ww.getName();
        criteria=Criteria.where(asd).is("");
        Query query=new Query(criteria);
        StageMent     stageMentF = mongoTemplate.findOne(query,StageMent.class);
        return stageMentF;
    }
}
