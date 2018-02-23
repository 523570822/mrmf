package com.mrmf.service.stage;

import com.mrmf.entity.stage.StageMent;
import com.osg.entity.FaceStatus;

import java.util.List;

public interface StageService  {
    /**
     * 新增/修改镜台信息
     *
     * @param stageMent
     * @return
     */
    public FaceStatus upsert(StageMent stageMent);

    /**
     * 通过设备编码查询镜台状态
     */
    public StageMent findOne(String devicedId);

    /**
     * 更新或修改实体
     * @param stageMent
     */
    public void    upsertAndSave(StageMent stageMent);

    /**
     *
     * @param organId   //门店编码
     * @param name    //镜台编号
     * @param floor    //镜台层次
     * @return
     */
    public StageMent findOne(String organId,String name,String floor);

    public void saveOrUpdate(StageMent t);
    public void deleteById(String id);
    public StageMent queryById(String id);
    public List<StageMent> query();
    public StageMent findOneS( String organId,String name);



}
