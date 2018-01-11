package com.mrmf.service.organPosition;


import com.mrmf.entity.Organ;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.OrganPositionSetting;
import com.mrmf.entity.Staff;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.organPisition.FormPositionBean;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/3/27.
 */
public interface OrganPosition {
    public String saveOrganPosition(OrganPositionSetting opc);
    public ReturnStatus UpdateState(String organId,String state);
    public OrganPositionSetting queryPosition(String organId);
    public List<OrganPositionDetails> queryYearMonth(String organId, Date begin, Date end);
    public List<OrganPositionDetails> saveOrganPositionDetails(List list);
    public List<OrganPositionDetails> queryOnePosition(String organId,Date begin, Date end);
    public void insertDetailsAll(List<OrganPositionDetails> organPositionDetailsList);
    public Staff queryStaff(String staffId);
    public Organ queryOrgan(String organId);
    public FlipInfo<Organ> queryOrganListByfollowCount(double longitude, double latitude, FlipInfo<Organ> fpi);
    public FlipInfo<Organ> queryOrganListByUser(double longitude,
                                                double latitude, double maxDistance, FlipInfo<Organ> fpi);
    public FlipInfo<Organ> queryOrganPosition(FlipInfo<Organ> organList);
    public PositionOrder insertPositionOrder(PositionOrder positionOrder);
    public ReturnStatus saveDetails(PositionOrder positionOrder,FormPositionBean bean);
    ReturnStatus staffPayPositionOrder(PositionOrder positionOrder, FormPositionBean bean, HttpServletRequest request) throws BaseException;
    ReturnStatus staffPaySuccess(Map<Object, Object> map)throws BaseException;
    PositionOrder getPositonById(String id);
    public List<PositionOrder> queryOrderList(String staffId,Date beginTime,Date endTime);
    public List<OrganPositionDetails> queryDetailsByOrderIdList(List<String> orderIdList);

    /**
     * 判断当天店铺是否有工位
     */
    List<String> getPositionInfo(String staffId) throws ParseException;

}
