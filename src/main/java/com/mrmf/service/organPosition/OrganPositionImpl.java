package com.mrmf.service.organPosition;

import com.mrmf.entity.*;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.organPisition.FormPositionBean;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.userPay.UserPayService;
import com.mrmf.service.weorgan.WeOrganService;
import com.mrmf.service.wesysconfig.WeSysConfigService;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.GpsUtil;
import com.osg.framework.util.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by 蔺哲 on 2017/3/27.
 */
@Service("organPosition")


public class OrganPositionImpl implements OrganPosition {
    @Autowired
    private EMongoTemplate mongoTemplate;
    @Autowired
    private WeOrganService weOrganService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private UserPayService usrPayservice;
    @Autowired
    private WeSysConfigService weSysConfigService;
    /**
     * 保存工位配置
     * @param opc
     * @return
     */
    public String saveOrganPosition(OrganPositionSetting opc){
        mongoTemplate.save(opc);
        return opc.get_id();
    }

    /**
     * 修改工位配置
     * @param organId
     * @param state
     * @return
     */
    public ReturnStatus UpdateState(String organId,String state){
        ReturnStatus result;
        Organ organ = mongoTemplate.findById(organId, Organ.class);
        if (organ == null) {
            result = new ReturnStatus(false);
            result.setMessage("指定id的公司信息不存在");
        } else {
            organ.setOrganPositionState(state);
            mongoTemplate.save(organ);
            if("1".equals(state)){
                mongoTemplate.remove(Query.query(Criteria.where("organId").is(organId)),OrganPositionSetting.class);
            }
            result = new ReturnStatus(true);
            result.setEntity(organ); // 返回更新后的对象
        }

        return result;
    }

    /**
     * 查询工位配置
     * @param organId
     * @return
     */
    public OrganPositionSetting queryPosition(String organId){
      return mongoTemplate.findOne(Query.query(Criteria.where("organId").is(organId)),OrganPositionSetting.class);
    }


    /**
     * 查询工位记录
     * @param organId
     * @param begin 开始时间
     * @param end  结束时间
     * @return
     */
    public List<OrganPositionDetails> queryYearMonth(String organId, Date begin, Date end){
       List list = mongoTemplate.find(Query.query(Criteria.where("organId").is(organId).and("state").ne(2).and("time").gte(begin).lte(end)),OrganPositionDetails.class);
        return list;
    }

    /**
     * 保存当前月工位状态
     * @param list
     * @return
     */
    public List<OrganPositionDetails> saveOrganPositionDetails(List list){
        mongoTemplate.insertAll(list);
        return list;
    }
    /**
     * 按月份查询单个工位
     */
    public List<OrganPositionDetails> queryOnePosition(String organId,Date begin, Date end){
        List list = mongoTemplate.find(Query.query(Criteria.where("organId").is(organId).and("state").ne(2).and("time").gte(begin).lte(end)),OrganPositionDetails.class);
        return list;
    }

    /**
     * 执行批量插入
     * @param organPositionDetailsList
     */
    public void insertDetailsAll(List<OrganPositionDetails> organPositionDetailsList){
        mongoTemplate.insertAll(organPositionDetailsList);
    }
    public Staff queryStaff(String staffId){
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)),Staff.class);
    }
    public Organ queryOrgan(String organId){
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)),Organ.class);
    }
    public FlipInfo<Organ> queryOrganListByfollowCount(double longitude, double latitude, FlipInfo<Organ> fpi) {
        fpi=mongoTemplate.findByPage(Query.query(Criteria.where("organPositionState").is("0")), fpi, Organ.class);
        for(Organ organ:fpi.getData()){
//			System.out.println(organ.get_id());
            double dis= GpsUtil.distance(latitude, longitude,organ.getGpsPoint().getLatitude() , organ.getGpsPoint().getLongitude());
            if(dis<1){
                DecimalFormat df  = new DecimalFormat("###.000");
                organ.setDistance(Double.parseDouble(df.format(dis))*1000);
                organ.setUnit("m");
            }else{
                DecimalFormat df1  = new DecimalFormat("###.0");
                organ.setDistance(Double.parseDouble(df1.format(dis)));
                organ.setUnit("km");
            }
            Integer evaluateCount=organ.getEvaluateCount();
            int aver=0;
            if(evaluateCount!=null&&evaluateCount!=0){
                int zanCount=organ.getZanCount();
                aver=(int)Math.ceil((double)zanCount/evaluateCount);
                if(aver>5){
                    aver=5;
                }
                organ.setZanCount(aver);
            }else{
                aver=organ.getZanCount();
                if(aver>5){
                    aver=5;
                    organ.setZanCount(aver);
                }
            }
            GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
            organ.setGpsPoint(gpsPoint);
        }
        return fpi;
    }
    @Override
    public FlipInfo<Organ> queryOrganListByUser(double longitude,
                                                double latitude, double maxDistance, FlipInfo<Organ> fpi) {
        fpi=mongoTemplate.findByPageGeo(longitude, latitude, maxDistance, Query.query(Criteria.where("organPositionState").is("0")), fpi, Organ.class);
        for(Organ organ:fpi.getData()){
            //double dis=GpsUtil.distance(longitude, latitude, organ.getGpsPoint().getLongitude(), organ.getGpsPoint().getLatitude());

            if(organ.getDistance()<1){
                DecimalFormat df  = new DecimalFormat("###.000");
                organ.setDistance(Double.parseDouble(df.format(organ.getDistance()))*1000);
                organ.setUnit("m");
            }else{
                DecimalFormat df1  = new DecimalFormat("###.0");
                organ.setDistance(Double.parseDouble(df1.format(organ.getDistance())));
                organ.setUnit("km");
            }
            Integer evaluateCount=organ.getEvaluateCount();
            int aver=0;
            if(evaluateCount!=null&&evaluateCount!=0){
                int zanCount=organ.getZanCount();
                aver=(int)Math.ceil((double)zanCount/evaluateCount);
                if(aver>5){
                    aver=5;
                }
                organ.setZanCount(aver);
            }else{
                aver=organ.getZanCount();
                if(aver>5){
                    aver=5;
                    organ.setZanCount(aver);
                }
            }
            GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
            organ.setGpsPoint(gpsPoint);
        }
        return fpi;
    }
    public FlipInfo<Organ> queryOrganPosition(FlipInfo<Organ> organList){
        List<String> list = new ArrayList<>();
        for (Organ organ:organList.getData()){
            list.add(organ.get_id());
        }
        List<OrganPositionSetting> settingList = mongoTemplate.find(Query.query(Criteria.where("organId").in(list)),OrganPositionSetting.class);
        for(Organ organ:organList.getData()){
            for (OrganPositionSetting setting:settingList){
                if(organ.get_id().equals(setting.getOrganId())){
                    organ.setNum1(setting.getNum());
                    organ.setLeaseMoney(setting.getLeaseMoney());
                    organ.setLeaseType(setting.getLeaseType());
                }
            }
        }


        return organList;
    }

    /**
     * 插入支付订单
     * @param positionOrder
     * @return
     */
    public PositionOrder insertPositionOrder(PositionOrder positionOrder){
        mongoTemplate.save(positionOrder);
        return positionOrder;
    }

    @Override
    public ReturnStatus saveDetails(PositionOrder positionOrder, FormPositionBean bean) {
        return null;
    }

    /**
     * 插入选定的日期们
     * @param positionOrder
     * @param bean
     * @return
     */
    public ReturnStatus staffPayPositionOrder(PositionOrder positionOrder, FormPositionBean bean, HttpServletRequest request) throws BaseException {
        ReturnStatus result = new ReturnStatus(true);
        List<String> timeList = bean.getTimeList();
        //得到开始时间
        Date beginTime = DateUtil.formatDate(timeList.get(0));
        Date endTime = DateUtil.formatDate(timeList.get(timeList.size()-1));
        //得到选中日期对应的时间
        Map map = getTimeMap(timeList,positionOrder.getOrganId(),beginTime,endTime);
        //遍历map
        SortedMap sortedMap = getDetailsNum(positionOrder.getOrganId(),timeList,map);

        Double totalMoney = positionOrder.getTotalMoney();
        Staff staff = staffService.queryById(positionOrder.getStaffId());//获取判断条件
        Organ organ = weOrganService.queryOrganById(positionOrder.getOrganId());
        positionOrder.setBeginTime(beginTime);
        positionOrder.setEndTime(endTime);
        positionOrder.setNewCreate();
        positionOrder.setState(0);//默认为0
        if((Boolean)sortedMap.get("success") ) {//先查是否有工位
            if( 0 == positionOrder.getLeaseType()) {//在判断是否是租赁模式
                positionOrder.setState(1);//修改为1
                if (totalMoney > staff.getTotalIncome( )) {//微信支付
                    positionOrder.setWxMoney(new BigDecimal(totalMoney).subtract(new BigDecimal(staff.getTotalIncome( ))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( ));
                    mongoTemplate.insert(positionOrder);
                    SortedMap<Object, Object> params = usrPayservice.getPrepay_id(request, positionOrder);
                    result.setParams(params);
                    result.setMessage(positionOrder.get_id( ));
                } else {
                    positionOrder.setWxMoney(0.0);//微信支付0
                    positionOrder.setMyMoney(totalMoney);
                    mongoTemplate.insert(positionOrder);
                    if (staff != null && organ != null) {
                        staff.setTotalIncome(new BigDecimal(staff.getTotalIncome( )).subtract(new BigDecimal(totalMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( ));
                        organ.setWalletAmount(new BigDecimal(organ.getWalletAmount( )).add(new BigDecimal(totalMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( ));
                        mongoTemplate.save(staff);
                        mongoTemplate.save(organ);
                    }
                    SortedMap<Object, Object> params = new TreeMap<Object, Object>( );
                    params.put("out_trade_no", positionOrder.get_id( ));
                    params.put("_id",positionOrder.get_id());
                    ReturnStatus res = staffPaySuccess(params);//添加支付记录
                    if (! res.isSuccess( )) {
                        result.setSuccess(false);
                        result.setMessage("支付失败");
                        staff.setTotalIncome(staff.getTotalIncome( ) + totalMoney);//有问题
                        organ.setWalletAmount(organ.getWalletAmount( ) - totalMoney);
                        mongoTemplate.save(staff);
                        mongoTemplate.save(organ);
                        return result;
                    } else {
                        result.setMessage("支付成功");
                        result.setParams(params);
                    }

                }
            }else {
                mongoTemplate.save(positionOrder);
            }
            List<Integer> dateNum = (List) sortedMap.get("dateNum");
            List<OrganPositionDetails> positionDetails = new ArrayList<>();
            Integer state;
            if(positionOrder.getLeaseType()==0){
                state = 1;
            }else {
                state = 0;
            }
            for (int i = 0;i<dateNum.size();i++){
                int num = dateNum.get(i);
                String organId = bean.getOrganId();
                Date dd = DateUtil.formatDate((String)timeList.get(i));
                OrganPositionDetails details = new OrganPositionDetails(positionOrder.get_id(),organId,num,dd,state);
                positionDetails.add(details);
            }
            insertDetailsAll(positionDetails);
            //存入分配好的工位们
            if(1 == positionOrder.getLeaseType()){ //预约模式
                SortedMap<Object, Object> params = new TreeMap<Object, Object>( );
                params.put("_id", positionOrder.get_id( ));
                result.setMessage("预约完成");
                result.setParams(params);
            }else{//支付完成
                //向staff表里插入数据
                List<String> weOrganIds = staff.getWeOrganIds();
                if(!weOrganIds.contains(organ.get_id())){
                    weOrganIds.add(organ.get_id());
                }
                mongoTemplate.save(staff);

                //签约技师
                List<String> stringList = new ArrayList<>();
                List<WeOrganStaffVerify> staffList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staff.get_id())), WeOrganStaffVerify.class);
                for(WeOrganStaffVerify staffVerify:staffList){
                    stringList.add(staffVerify.getOrganId());
                }
                if(!stringList.contains(organ.get_id())){
                    WeOrganStaffVerify weOrganStaffVerify = new WeOrganStaffVerify();
                    weOrganStaffVerify.setNewCreate();
                    weOrganStaffVerify.setOrganId(organ.get_id());
                    weOrganStaffVerify.setOrganName(organ.getName());
                    weOrganStaffVerify.setStaffId(staff.get_id());
                    weOrganStaffVerify.setStaffName(staff.getName());
                    weOrganStaffVerify.setState(1);
                    weOrganStaffVerify.setMemo("工位模式签约成功");
                    mongoTemplate.save(weOrganStaffVerify);
                }

                List<OrganPositionDetails> positionOrderList = mongoTemplate.find(Query.query(Criteria.where("positionOrderId").is(positionOrder.get_id())), OrganPositionDetails.class);
                for(OrganPositionDetails positionDetaill:positionOrderList){
                    Date time = positionDetaill.getTime( );
                    int day = Integer.parseInt(DateUtil.format(time, "YYYYMMdd"));
                    mongoTemplate.remove(Query.query(Criteria.where("staffId").is(staff.get_id()).and("day").is(day)), WeStaffCalendar.class);
                    WeStaffCalendar calendar = new WeStaffCalendar();
                    calendar.setNewCreate();
                    calendar.setOrganId(organ.get_id());
                    calendar.setStaffId(staff.get_id());
                    calendar.setDay(day);
                    calendar.setTime0(false);
                    calendar.setTime1(false);
                    calendar.setTime2(false);
                    calendar.setTime3(false);
                    calendar.setTime4(false);
                    calendar.setTime5(false);
                    calendar.setTime6(false);
                    calendar.setTime7(false);
                    calendar.setTime8(false);
                    calendar.setTime9(true);
                    calendar.setTime10(true);
                    calendar.setTime11(true);
                    calendar.setTime12(true);
                    calendar.setTime13(true);
                    calendar.setTime14(true);
                    calendar.setTime15(true);
                    calendar.setTime16(true);
                    calendar.setTime17(true);
                    calendar.setTime18(true);
                    calendar.setTime19(true);
                    calendar.setTime20(true);
                    calendar.setTime21(true);
                    calendar.setTime22(true);
                    calendar.setTime23(false);
                    mongoTemplate.save(calendar);
                }
            }
            return result;
            }
        result.setSuccess(false);
        result.setMessage((String) sortedMap.get("message"));//工位已满
        return result;

    }

    @Override
    public ReturnStatus staffPaySuccess(Map<Object, Object> map) throws BaseException {
        System.out.println("进入技师分帐的逻辑@@@@@@@@@@@@");
        ReturnStatus result = new ReturnStatus(false);
        String id = (String) map.get("out_trade_no");
        System.out.println("获取Id+++++++++++++++++"+id);
        PositionOrder positionOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), PositionOrder.class);

        OrganPositionSetting ops = weOrganService.getOrganPositionSetting(positionOrder.getOrganId());
        positionOrder.setFenZhangList(ops.getPositionSettingsList());
        mongoTemplate.save(positionOrder);

        Staff staff = staffService.queryById(positionOrder.getStaffId());
        System.out.println("获取到技师"+staff.get_id());
        Organ organ = weOrganService.queryOrganById(positionOrder.getOrganId());
        System.out.println("获取到店铺"+organ.get_id());
        //向技师表中插入店铺
        List<String> weOrganIds = staff.getWeOrganIds( );
        if(!weOrganIds.contains(organ.get_id())){
            weOrganIds.add(organ.get_id());
        }
        staff.setWeOrganIds(weOrganIds);
        mongoTemplate.save(staff);

        System.out.println("保存店铺钱包变动历史");
        //插入钱包变动记录
        if(staff != null ){
            if( staff.getTotalIncome()>0){
                WeUserWalletHis staffWalletHis = new WeUserWalletHis();
                staffWalletHis.setNewId( );
                staffWalletHis.setNewCreate( );
                staffWalletHis.setOrderId("6");
                staffWalletHis.setUserId(staff.get_id( ));
                if(positionOrder.getWxMoney() > 0){
                    staffWalletHis.setAmount(-(staff.getTotalIncome()));
                }else{
                    staffWalletHis.setAmount(-(positionOrder.getTotalMoney()));
                }
                staffWalletHis.setDesc("技师工位租赁支付");
                mongoTemplate.insert(staffWalletHis);
            }
        }
        System.out.println("保存店铺钱包变动历史");
        if(organ != null){
            WeUserWalletHis organWalletHis = new WeUserWalletHis();
            organWalletHis.setNewId( );
            organWalletHis.setNewCreate( );
            organWalletHis.setOrderId("5");
            organWalletHis.setUserId(organ.get_id( ));
            organWalletHis.setAmount(positionOrder.getTotalMoney());
            organWalletHis.setDesc("店铺工位租赁收入");
            mongoTemplate.insert(organWalletHis);
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        if(positionOrder.getWxMoney() > 0 ){
            System.out.println("进入保存逻辑++++++");
            staff.setTotalIncome(0.0); //清空技师钱包
            organ.setWalletAmount(new BigDecimal(organ.getWalletAmount( )).add(new BigDecimal(positionOrder.getTotalMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( ));
            System.out.println("获取钱包余额++++++"+organ.getWalletAmount( ));
            mongoTemplate.save(staff);
            mongoTemplate.save(organ);
        }

        result.setSuccess(true);
        return result;
    }

    @Override
    public PositionOrder getPositonById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)),PositionOrder.class);
    }

    @Override
    public List<String> getPositionInfo(String staffId) throws ParseException {

        List<PositionOrder> positionOrders = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId)), PositionOrder.class);

        List<String> organIds = new ArrayList<>();
        for(PositionOrder positionOrder:positionOrders){

            Date dateStart = DateUtil.currentDate(new Date());
            Date dateEnd = DateUtil.addDate(dateStart, 1);
            List<Criteria> criteria = new ArrayList<>();
            criteria.add(Criteria.where("time").gte(dateStart));
            criteria.add(Criteria.where("time").lt(dateEnd));//时间
            criteria.add(Criteria.where("positionOrderId").is(positionOrder.get_id()));
            Criteria criteria1 = new Criteria();
            criteria1.andOperator(criteria.toArray(new Criteria[criteria.size()]));
            List<OrganPositionDetails> positionOrderIds = mongoTemplate.find(Query.query(criteria1), OrganPositionDetails.class);

            if(positionOrderIds !=null && positionOrderIds.size() > 0){
                if(!organIds.contains(positionOrder.getOrganId())){
                    organIds.add(positionOrder.getOrganId());
                }
            }



        }

        return organIds;
    }

    //按日期划分记录
    public Map getTimeMap(List<String> timeList,String organId,Date beginTime,Date endTime){
        //没有从00和59查询
        List<OrganPositionDetails> organPositionDetails = queryYearMonth(organId,beginTime,endTime);
        Map map = new TreeMap();
        for (int i = 0;i<timeList.size();i++){//根据选择日期划分 查到的时间段的数据
            List<Integer> list = new ArrayList();
            for(OrganPositionDetails details:organPositionDetails){
                String detailsTime = DateUtil.format(details.getTime());
                if(timeList.get(i).equals(detailsTime)){
                    list.add(details.getNum());
                }
            }
            map.put(i,list);
        }
        return map;
    }
    //统计和分配工位
    public SortedMap getDetailsNum(String organId,List<String> timeList,Map map){
       SortedMap sortedMap = new TreeMap();
        OrganPositionSetting setting = queryPosition(organId);
        int settingNum = setting.getNum();
        List<Integer> dateNum = new ArrayList<>();
        for (int i = 0;i<map.size();i++){
            List<Integer> numList = (List)map.get(i);
            if(numList.size()>=settingNum){
                sortedMap.put("success",false);
                sortedMap.put("message",timeList.get(i)+"的工位已满");
                return sortedMap;
            }else if(numList.size()==0){
                dateNum.add(1);
            }else {
                for (int j = 1;j<=settingNum;j++){
                    if(!numList.contains(j)){
                        dateNum.add(j);
                        break;
                    }
                }
            }
        }
        sortedMap.put("success",true);
        sortedMap.put("dateNum",dateNum);
        return sortedMap;
    }

    /**
     * 查询技师指定日期区间 的订单
     * @param staffId
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<PositionOrder> queryOrderList(String staffId,Date beginTime,Date endTime){
        return mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("beginTime").gte(beginTime).lte(endTime).and("state").in(0,1)),PositionOrder.class);
    }

    /**
     * 得到订单们对应的租赁记录
     * @param orderIdList
     * @return
     */
    public List<OrganPositionDetails> queryDetailsByOrderIdList(List<String> orderIdList){
        return mongoTemplate.find(Query.query(Criteria.where("positionOrderId").in(orderIdList).and("state").ne(2)),OrganPositionDetails.class);
    }
}
