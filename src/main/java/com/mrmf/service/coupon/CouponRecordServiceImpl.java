package com.mrmf.service.coupon;

import com.mrmf.entity.bean.CouponRecordSum;
import com.mrmf.entity.coupon.ConponRecord;
import com.osg.entity.FlipInfo;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by 蔺哲 on 2017/7/21.
 */
@Service("couponRecordService")
public class CouponRecordServiceImpl implements CouponRecordService{
    @Autowired
    EMongoTemplate mongoTemplate;
    public static final int dayNum=15;//默认查15天
    public static final int monthNum=5;//默认查6个月
    public static final int yearNum=2;//默认查往前推两年
    /**
     * 分页显示优惠券发放记录和使用记录
     * @param fpi
     * @return
     */
    public FlipInfo<ConponRecord> queryByFpi(FlipInfo<ConponRecord> fpi){
        return mongoTemplate.findByPage(null,fpi,ConponRecord.class);
    }

    /**
     * 导出
     * @param fpi
     * @return
     */
    @Override
    public List<ConponRecord> queryByList(FlipInfo<ConponRecord> fpi) {
        Query query=new Query();
        Criteria criteria=new Criteria();
        mongoTemplate.processParams(query,criteria,fpi.getParams());
        List<ConponRecord> result =mongoTemplate.find(query, ConponRecord.class, "conponRecord");
        if(result!=null){
            for(ConponRecord re:result){
                re.setIsUsed("0".equals(re.getIsUsed())?"未使用":"已使用");
                re.setMoneyTypeString(re.getMoneyType()==0?"代金券":"折扣券");
            }
        }
        return result;
    }

    /**
     * 统计 划分数据
     * @param
     * @return
     */
    @Override
    public Map statistics(HttpServletRequest request) throws Exception {
        Criteria criteria=new Criteria();
        String gteCreateTime = request.getParameter("gte:createTime|date");
        String letCreateTime = request.getParameter("lte:createTime|date+1");
        String periodName = request.getParameter("period");
        String type = request.getParameter("isUsed");
        String time = "createTime";
        if("1".equals(type)){
            time = "usedTime";
            criteria.and("isUsed").is("1");
        }
        //控制时间范围
        if((StringUtils.isEmpty(gteCreateTime))&&(StringUtils.isEmpty(letCreateTime))){
            if("0".equals(periodName)){
                gteCreateTime = DateUtil.format(DateUtil.addDate(new Date(),-dayNum));
                letCreateTime = DateUtil.format(DateUtil.addDate(DateUtil.formatDate(gteCreateTime),dayNum));
            }else if ("1".equals(periodName)){
                gteCreateTime = DateUtil.format(DateUtil.addMonth(new Date(),-monthNum));
                letCreateTime = DateUtil.format(DateUtil.addMonth(DateUtil.formatDate(gteCreateTime),monthNum));
            }else if ("2".equals(periodName)){
                gteCreateTime = DateUtil.format(DateUtil.addYear(new Date(),-yearNum));
                letCreateTime = DateUtil.format(DateUtil.addYear(DateUtil.formatDate(gteCreateTime),yearNum));
            }
        }
        if((!StringUtils.isEmpty(gteCreateTime))&&(StringUtils.isEmpty(letCreateTime))){
            letCreateTime = DateUtil.format(new Date());
        }
        if((StringUtils.isEmpty(gteCreateTime))&&(!StringUtils.isEmpty(letCreateTime))){
            if("0".equals(periodName)){
                gteCreateTime = DateUtil.format(DateUtil.addDate(DateUtil.formatDate(letCreateTime),-dayNum));
            }else if ("1".equals(periodName)){
                gteCreateTime = DateUtil.format(DateUtil.addMonth(DateUtil.formatDate(letCreateTime),-monthNum));
            }else if ("2".equals(periodName)){
                gteCreateTime = DateUtil.format(DateUtil.addYear(DateUtil.formatDate(letCreateTime),-yearNum));
            }

        }
        //计算相差日，月，年
        int daysNum = DateUtil.daysOfTwo(DateUtil.formatDate(gteCreateTime),DateUtil.formatDate(letCreateTime));
        int monthsNum = DateUtil.getMonthSpace(gteCreateTime,letCreateTime);
        int yearsNum = DateUtil.yearDateDiff(gteCreateTime,letCreateTime);
        Map<String,Object> result = new HashMap<>();
        List<CouponRecordSum> datalist = new ArrayList<>();
        Date thisDay = DateUtil.formatDate(gteCreateTime);
        //0,1,2去查日，
        if("0".equals(periodName)){
            for(int i=0;i<=daysNum;i++){
                Date date = DateUtil.addDate(thisDay,+i);
                Long num = mongoTemplate.count(Query.query(Criteria.where(time).gte(DateUtil.dayEnd(date,1)).lte(DateUtil.dayEnd(date,0))),ConponRecord.class);
                CouponRecordSum couponRecordSum = new CouponRecordSum(date,(double) num);
                datalist.add(couponRecordSum);
            }
        }
        if("1".equals(periodName)){
            //循环六个月数据
            for(int i=0;i<=monthsNum;i++){
                Date date = DateUtil.addMonth(thisDay,+i);
                Long num = mongoTemplate.count(Query.query(Criteria.where(time).gte(DateUtil.dayEnd(date,1)).lte(DateUtil.monthLastDay(date))),ConponRecord.class);
                CouponRecordSum couponRecordSum = new CouponRecordSum(date,(double) num);
                datalist.add(couponRecordSum);
            }
        }
        if("2".equals(periodName)){
            //往前推3年
            for(int i=0;i<=yearsNum;i++){
                int year = DateUtil.getYear(DateUtil.addYear(thisDay,i));
                String fristTime = year+"-01-01 00:00:00";
                String lastTime = year+"-12-31 23:59:59";
                Long num = mongoTemplate.count(Query.query(Criteria.where(time).gte(DateUtil.formatDate(fristTime)).lte(DateUtil.formatDate(lastTime))),ConponRecord.class);
                CouponRecordSum couponRecordSum = new CouponRecordSum(DateUtil.formatDate(fristTime),(double) num);
                datalist.add(couponRecordSum);
            }
        }


        List<String> str = new ArrayList<>();//x轴日期文字
        List<Map> seriesDate = new ArrayList<>();//统计数据
        List<String> legenDate = new ArrayList<>();//描述
        List<Integer> intList = new ArrayList<>();
        Map<Integer,List> intMap = new HashMap<>();
        if("1".equals(type)){//使用统计
            legenDate.add("使用数量");
        }else {
            legenDate.add("发放数量");
        }
        for(int i = 0;i<datalist.size();i++){
            str.add(DateUtil.formatTime(datalist.get(i).get_id(),Integer.parseInt(periodName)));
            intList.add((int) datalist.get(i).getTotal());
            intMap.put(0,intList);
        }
        intMap.put(0,intList);
        for(int i = 0;i<legenDate.size();i++){
            Map<String,Object> map = new TreeMap<>();
            map.put("name",legenDate.get(i));
            map.put("type","bar");
            map.put("data",intMap.get(i));
            seriesDate.add(map);
        }
        result.put("xAxisData",str);
        result.put("legenDate",legenDate);
        result.put("seriesDate",seriesDate);
        return result;
    }

}
