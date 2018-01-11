package com.mrmf.service.kaoqin;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.WeStaffSign;
import com.mrmf.entity.kaoqin.KBancidingyi;
import com.mrmf.entity.kaoqin.KKaoqin;
import com.mrmf.entity.kaoqin.KKaoqinleibie;
import com.mrmf.entity.kaoqin.KKaoqintime;
import com.mrmf.entity.kaoqin.KPaiban;
import com.mrmf.entity.kaoqin.KQingjiadengji;
import com.mrmf.entity.staff.Staffpost;
import com.mrmf.service.common.Arith;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.GetSetUtil;
import com.osg.framework.util.StringUtils;

@Service("kaoqinService")
public class KaoqinServiceImpl implements KaoqinService {
	@Autowired
	private EMongoTemplate mongoTemplate;
	@Override
	public void saveBanci(KBancidingyi bancidingyi) {
		mongoTemplate.save(bancidingyi);
	}
	
	@Override
	public FlipInfo<KBancidingyi> queryBancidingyi(FlipInfo<KBancidingyi> fpi)
			throws BaseException {
		Query query =  new Query();
		query.addCriteria(Criteria.where("delete_flag").is(false));
		return mongoTemplate.findByPage(query, fpi, KBancidingyi.class);
	}
	
	@Override
	public FlipInfo<KKaoqinleibie> queryKaoqinleibie(FlipInfo<KKaoqinleibie> fpi)
			throws BaseException {
		Query query = new Query();
		query.addCriteria(Criteria.where("delete_flag").is(false));
		return  mongoTemplate.findByPage(query, fpi, KKaoqinleibie.class);
	}
	@Override
	public void saveKaoqinleibie(KKaoqinleibie kaoqinleibie)
			throws BaseException {
		mongoTemplate.save(kaoqinleibie);
	}
	@Override
	public KKaoqinleibie findKaoqinleibieById(String kaoqinleibieId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(kaoqinleibieId));
		return mongoTemplate.findOne(query, KKaoqinleibie.class);
	}
	@Override
	public void updateKaoqinleibie(KKaoqinleibie kaoqinleibie) {
		mongoTemplate.save(kaoqinleibie);
	}
	@Override
	public void deleteKaoqinleibie(String kaoqinleibieId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(kaoqinleibieId));
		Update update = new Update();
		update.set("delete_flag", true);
		mongoTemplate.updateFirst(query, update, KKaoqinleibie.class);
	}
	@Override
	public KBancidingyi queryBancidingyiById(String banciId) {
		Query query  = new Query();
		query.addCriteria(Criteria.where("_id").is(banciId));
		return mongoTemplate.findOne(query, KBancidingyi.class);
	}
	
	@Override
	public void deleteBanci(String banciId) {
		Query query =  new Query();
		query.addCriteria(Criteria.where("_id").is(banciId));
		Update update = new Update();
		update.set("delete_flag", true);
		mongoTemplate.updateFirst(query, update, KBancidingyi.class);
	}
	
	@Override
	public FlipInfo<KQingjiadengji> queryQingjiadengji(FlipInfo<KQingjiadengji> fpi) {
		Query query = new Query();
		query.addCriteria(Criteria.where("delete_flag").is(false));
		return mongoTemplate.findByPage(query, fpi, KQingjiadengji.class);
	}
	
	@Override
	public KQingjiadengji findQingjiadengjiById(String qingjiadengjiId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(qingjiadengjiId));
		return mongoTemplate.findOne(query, KQingjiadengji.class);
	}

	@Override
	public List<Staff> findWorkStaff(String organId) {
		Query query =  new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("flag").is(Integer.parseInt("0")),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.find(query, Staff.class);
	}

	@Override
	public List<KKaoqinleibie> findKaoqinleibie(String organId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("delete_flag").is(false),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.find(query, KKaoqinleibie.class);
	}
	
	@Override
	public void upsertQingjiadengji(KQingjiadengji qingjiadengji) {
		mongoTemplate.save(qingjiadengji);
	}

	@Override
	public Staff findWorkStaff(String organId, String names) {
		Query query =  new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("flag").is(Integer.parseInt("0")),Criteria.where("_id").is(names),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Staff.class);
	}
	

	@Override
	public KKaoqinleibie findKaoqinleibie(String organId, String type1) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("delete_flag").is(false),Criteria.where("_id").is(type1),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, KKaoqinleibie.class);
	}

	@Override
	public void deleteQingjiadengji(String qingjiadengjiId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(qingjiadengjiId));
		Update update = new Update();
		update.set("delete_flag", true);
		mongoTemplate.updateFirst(query, update, KQingjiadengji.class);
	}
	
	@Override
	public FlipInfo<KQingjiadengji> findQingjiadengji(FlipInfo<KQingjiadengji> fpi) {
		return mongoTemplate.findByPage(null, fpi, KQingjiadengji.class);
	}

	@Override
	public FlipInfo<Staff> findWorkStaffByPage(FlipInfo<Staff> fpi,String organId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("flag").is(0),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.findByPage(query, fpi, Staff.class);
	}
	
	@Override
	public Staffpost findDutyName(String dutyId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(dutyId));
		return mongoTemplate.findOne(query, Staffpost.class);
	}

	@Override
	public List<KBancidingyi> findBancidingyis(String organId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("delete_flag").is(false),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.find(query, KBancidingyi.class);
	}

	@Override
	public void savePaiban(KPaiban kPaiban) {
		 mongoTemplate.save(kPaiban);
	}
	@Override
	public FlipInfo<KPaiban> findPaiBan(FlipInfo<KPaiban> fpi,String organId){
		 fpi.setSortField("yearmonth");
		 fpi.setSortOrder("DESC");
		 fpi = mongoTemplate.findByPage(null, fpi, KPaiban.class);
		 convertToName(fpi.getData());
		 return fpi;
	}
	
	
	
	@Override
	public KPaiban getPaiBan(String staffId, int yearmonth) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("staffId").is(staffId),Criteria.where("yearmonth").is(yearmonth));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, KPaiban.class);
	}
	
	@Override
	public boolean isExistPaiban(String staffId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("staffId").is(staffId));
		return mongoTemplate.exists(query, KPaiban.class);
	}

	/*
	 * 转换一下name
	 */
	@Override
	public void convertToName(List<KPaiban> data) {
		for (KPaiban kPaiban : data) {
			for(int i=1; i<= 31;i++) {
				if(i < 10) {
					if(!StringUtils.isEmpty(String.valueOf(GetSetUtil.getter(kPaiban, "Day0"+i)))) {
						String value = findOneBanci(String.valueOf(GetSetUtil.getter(kPaiban, "Day0"+i))).getNames();
						GetSetUtil.setter(kPaiban, "Day0"+i+"B", value, String.class);
					} else {
						GetSetUtil.setter(kPaiban, "Day0"+i+"B", "", String.class);
					}
				} else {
					if(!StringUtils.isEmpty(String.valueOf(GetSetUtil.getter(kPaiban, "Day"+i)))) {
						String value = findOneBanci(String.valueOf(GetSetUtil.getter(kPaiban, "Day"+i))).getNames();
						GetSetUtil.setter(kPaiban, "Day"+i+"B", value, String.class);
					} else {
						GetSetUtil.setter(kPaiban, "Day"+i+"B", "", String.class);
					}
				}
			}
				
			
		}
	}
	@Override
	public void convertToOneName(KPaiban kPaiban) {
		for(int i=1; i<= 31;i++) {
			if(i < 10) {
				if(!StringUtils.isEmpty(String.valueOf(GetSetUtil.getter(kPaiban, "Day0"+i)))) {
					String value = findOneBanci(String.valueOf(GetSetUtil.getter(kPaiban, "Day0"+i))).getNames();
					GetSetUtil.setter(kPaiban, "Day0"+i+"B", value, String.class);
				} else {
					GetSetUtil.setter(kPaiban, "Day0"+i+"B", "", String.class);
				}
			} else {
				if(!StringUtils.isEmpty(String.valueOf(GetSetUtil.getter(kPaiban, "Day"+i)))) {
					String value = findOneBanci(String.valueOf(GetSetUtil.getter(kPaiban, "Day"+i))).getNames();
					GetSetUtil.setter(kPaiban, "Day"+i+"B", value, String.class);
				} else {
					GetSetUtil.setter(kPaiban, "Day"+i+"B", "", String.class);
				}
			}
		}
	}
	private KBancidingyi findOneBanci(String banciId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(banciId));
		return mongoTemplate.findOne(query, KBancidingyi.class);
	}
	
	
	@Override
	public Staff findStaffById(String staffId) {
		return mongoTemplate.findById(staffId, Staff.class);
	}

	/**
	 * 查询自己的排班
	 * @param organId
	 * @param staffId
	 * @param date
	 * @return
	 */
	private List<KPaiban> queryKPaiban(String organId, String staffId, Date startTime,Date endTime) {
		Query query = new Query();
		query.addCriteria(Criteria.where("organId").is(organId));
		query.addCriteria(Criteria.where("staffId").is(staffId));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Integer startYearMonth =  new Integer(dateFormat.format(startTime));
		Integer endYearMonth =  new Integer(dateFormat.format(endTime));
		query.addCriteria(Criteria.where("yearmonth").gte(startYearMonth).lte(endYearMonth));
 		return mongoTemplate.find(query, KPaiban.class);
	}
	/**
	 * 设置考勤
	 */
	public void setKaoqin(String organId) throws ParseException {
		 List<Staff> staffs = findWorkStaff(organId);
		 if(staffs != null) {
			 for (Staff staff : staffs) {
		    	 KKaoqintime kaoqintime = queryKaoqintime(organId, staff.get_id(),1);  //查询考勤时间
		    	 if(kaoqintime != null ) {
			    	 Date startTime = kaoqintime.getCreateTime();   //开始时间
			    	 Date endTime = DateUtil.currentDate();
			    	 Date endTimeRecord = endTime;
			    	 endTime =  DateUtil.getYesterday();
			    	 String startTimeStr = DateUtil.getDateStr(startTime,"yyyyMMdd");;
					 String endTimeStr = DateUtil.getDateStr(endTime,"yyyyMMdd");
					 startTime = DateUtil.parse(startTimeStr,"yyyyMMdd");
					 endTime = DateUtil.parse(endTimeStr,"yyyyMMdd");
					 if(startTime.compareTo(endTime) <= 0) {
						 signConditions(startTime, endTime, organId, staff.get_id(),endTimeRecord);
						 //updateKaoqintime(organId,staff.get_id(),endTimeRecord,1);
				     }
		    	 }
			 }
		 } else {
			System.out.println("该公司还没有员工！");
		 }
	}
	
	
	
	/**
	 * startTime 开始时间的签到
	 * endTime 最后一天签到时间
	 */
	@Override
	public void signConditions(Date startTime, Date endTime, String organId,
			String staffId,Date endTimeRecord) {
		Integer startYearMonth = DateUtil.getYearMonth(startTime);
		Integer endYearMonth =  DateUtil.getYearMonth(endTime);
		Integer startDayInt =  DateUtil.getDay(startTime);
		Integer endDayInt =  DateUtil.getDay(endTime);
		List<KPaiban> kPaibans = queryKPaiban(organId, staffId, startTime, endTime);
		if(kPaibans!= null) {
			Map<String, String> dayPaiBans = new HashMap<String, String>();
			for(KPaiban kPaiban:kPaibans) {
				if(kPaiban.getYearmonth() == startYearMonth && kPaiban.getYearmonth() == endYearMonth) {   //等于开始的时间 
					for(int i= startDayInt; i <= endDayInt ; i ++ ) {
					    staffBanci(i,kPaiban,dayPaiBans);
					}
				}else  if(kPaiban.getYearmonth() == startYearMonth) {   //等于开始的时间 
					for(int i= startDayInt; i <=31 ; i ++ ) {
						staffBanci(i,kPaiban,dayPaiBans);
					}
				} else if(kPaiban.getYearmonth() == endYearMonth) { //等于结束的时间
					for(int i= 1; i <= endDayInt ; i ++ ) {
						staffBanci(i,kPaiban,dayPaiBans);
					}   
				} else {    //既不等于开始的时间也不等于结束的时间
					for(int i = 1; i <=31; i ++) {
						staffBanci(i,kPaiban,dayPaiBans); 
					}
				}
			}
		    try {
				queryStaffSign(dayPaiBans,staffId,organId,endTime,endTimeRecord);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} 
	}
	
	/**
	 * 技师的班次
	 */
	private void staffBanci(int i, KPaiban kPaiban,
			Map<String, String> dayPaiBans) {
		String dayPaiban = null;
		String day= null;
		if(i <10) {
			dayPaiban = String.valueOf(GetSetUtil.getter(kPaiban, "Day0" + i));
			day ="0"+i;
		} else {
			dayPaiban = String.valueOf(GetSetUtil.getter(kPaiban, "Day" + i));
			day = i+"";
		}
		if(!StringUtils.isEmpty(dayPaiban)) {
			dayPaiBans.put(kPaiban.getYearmonth()+ day, dayPaiban);
		}
	}
	/**
	 * 查询技师签到情况
	 * @param dayPaiBans 班次
	 * @param staffId  技师的id
	 * @param oragnId  技师的所在店铺的id
	 * @throws ParseException 
	 */
	public void queryStaffSign(Map<String, String> dayPaiBans, String staffId,String organId,Date endTime,Date endTimeRecord) throws ParseException {
		if(!dayPaiBans.isEmpty()) {
			updateKaoqintime(organId,staffId,endTimeRecord,1);
			for (Entry<String, String> dayPaiBan : dayPaiBans.entrySet()) {
				String  kBancidingyiId = dayPaiBan.getValue();
		        KBancidingyi kBancidingyi =  mongoTemplate.findById(kBancidingyiId, KBancidingyi.class);
		        String  signTime  = dayPaiBan.getKey();  
		        Date yearMonthDay = DateUtil.parse(dayPaiBan.getKey(),"yyyyMMdd"); 
		        if(kBancidingyi != null  && kBancidingyi.getKuatian() == true) {  //跨天逻辑
			        	Date startWork = DateUtil.parse(signTime + " " + kBancidingyi.getTime_a1(),"yyyyMMdd HH:mm");
			        	Date endWork = DateUtil.parse(signTime + " " + kBancidingyi.getTime_a2(),"yyyyMMdd HH:mm");
			        	if(!DateUtil.isSameDate(startWork,endTime)) {
							endWork = DateUtil.addDate(endWork, 1);
							startWork = DateUtil.setDayHour(startWork, -3);
							endWork = DateUtil.setDayHour(endWork, 3);
							List<WeStaffSign> weStaffSigns = queryEveryDayStaffSign(startWork, endWork, staffId, organId);
							if(weStaffSigns != null) {
								 if(weStaffSigns.size() == 0) {   //一次也没有签到  就是旷工
							        	saveKuanggong(organId, staffId, yearMonthDay,1);
							     } else if(weStaffSigns.size() == 1) {  //签到一次 目前也算旷工
							        	saveKuanggong(organId, staffId, yearMonthDay,2);
							     } else if(weStaffSigns.size() == 2) {
							    	    int count = 1;
										for (WeStaffSign weStaffSign : weStaffSigns) {
											if (count == 1) {
												if (kBancidingyi.getKaoqin_a1()) { // 上班是否考勤
													if (weStaffSign.getCreateTime().compareTo(startWork) > 0) { // 员工迟到了
														long differMinute = DateUtil.differMinutes(weStaffSign.getCreateTime(),
																startWork);
														saveKaoQin(1, differMinute, organId, staffId, weStaffSign);
													}
												}
												count++;
											} else {
												if (kBancidingyi.getKaoqin_a2()) { // 下班是否考勤
													if (weStaffSign.getCreateTime().compareTo(endWork) < 0) {
														long differMinute = DateUtil.differMinutes(endWork,
																weStaffSign.getCreateTime());
														saveKaoQin(2, differMinute, organId, staffId, weStaffSign);
													}
												}
											}
										}
										count = 1;
							     }
							}
			        	} else {
			        	  	 updateKaoqintime(organId,staffId,endTime,1);
			        	}
		        } else if(kBancidingyi != null  && kBancidingyi.getKuatian() == false) {
		        		Date startWork = DateUtil.parse(signTime + " " + kBancidingyi.getTime_a1(),"yyyyMMdd HH:mm");
						Date endWork = DateUtil.parse(signTime + " " + kBancidingyi.getTime_a2(),"yyyyMMdd HH:mm");
			        	Date dayStartTime  = DateUtil.parse(signTime + " 00:00:01","yyyyMMdd HH:mm:ss");
			        	Date dayEndTime  = DateUtil.parse(signTime + " 23:59:59","yyyyMMdd HH:mm:ss");
					    List<WeStaffSign> weStaffSigns = queryEveryDayStaffSign(dayStartTime, dayEndTime, staffId, organId);
					    if(weStaffSigns != null) {
					        if(weStaffSigns.size() == 0) {   //一次也没有签到  就是旷工
					        	saveKuanggong(organId, staffId, yearMonthDay,1);
					        } else if(weStaffSigns.size() == 1) {  //签到一次 目前也算旷工
					        	saveKuanggong(organId, staffId, yearMonthDay,2);
					        } else if(weStaffSigns.size() == 2) {
								int count = 1;
								for (WeStaffSign weStaffSign : weStaffSigns) {
									if (count == 1) {
										if (kBancidingyi.getKaoqin_a1()) { // 上班是否考勤
											if (weStaffSign.getCreateTime().compareTo(startWork) > 0) { // 员工迟到了
												long differMinute = DateUtil.differMinutes(weStaffSign.getCreateTime(),
														startWork);
												saveKaoQin(1, differMinute, organId, staffId, weStaffSign);
											}
										}
										count++;
									} else {
										if (kBancidingyi.getKaoqin_a2()) { // 下班是否考勤
											if (weStaffSign.getCreateTime().compareTo(endWork) < 0) {
												long differMinute = DateUtil.differMinutes(endWork,
														weStaffSign.getCreateTime());
												saveKaoQin(2, differMinute, organId, staffId, weStaffSign);
											}
										}
									}
								}
								count = 1;
							}
				        } else {
				        	saveKuanggong(organId, staffId, yearMonthDay,1);
				        }
		         }
			}
		}
	}
	/**
	 * 保存迟到早退
	 */
	public void saveKaoQin(int code,long differMinute,String organId,String staffId,WeStaffSign weStaffSign) {
	   KKaoqinleibie  kKaoqingleibie = queryKaoqinleibieByCode(code, organId);
	   double money_koufa = 0;
 	   if(differMinute <= 10) {
 		   money_koufa = Arith.mul(Arith.mul(kKaoqingleibie.getMoney1(), differMinute),  kKaoqingleibie.getBase1());
 	   } else if(differMinute>10 && differMinute <= 60) {
 		   money_koufa = Arith.mul(Arith.mul(kKaoqingleibie.getMoney2(), differMinute),  kKaoqingleibie.getBase2());
 	   } else if(differMinute > kKaoqingleibie.getBase3() ){
 		   money_koufa = kKaoqingleibie.getMoney3();
 	   }
 	   Staff staff = findWorkStaff(organId, staffId);
 	   KKaoqin kKaoqin = new KKaoqin();
 	   kKaoqin.setNewId();
 	   kKaoqin.setStaffId(staffId);
 	   kKaoqin.setStaffName(staff.getName());
 	   kKaoqin.setOrganId(organId);
 	   kKaoqin.setLeibieId(kKaoqingleibie.get_id());
 	   kKaoqin.setLeibieName(kKaoqingleibie.getNames());
 	   kKaoqin.setCode(code);
 	   kKaoqin.setCard((int)differMinute);
 	   kKaoqin.setMoney_koufa(money_koufa);
 	   kKaoqin.setDay(weStaffSign.getCreateTime());
 	   mongoTemplate.save(kKaoqin);
	}
	/**
	 * 保存旷工的方法
	 * @param organId  公司的Id
	 * @param staffId  技师的id
	 * @param yearMonthDay  yyyyMMdd
	 * @param division   1: 按照公司全天扣款       2:按照公司半天扣款
	 */
	public void saveKuanggong(String organId,String staffId,Date yearMonthDay,Integer division) {
		 KKaoqinleibie  kKaoqingleibie = queryKaoqinleibieByCode(3, organId);
		 Staff staff = findWorkStaff(organId, staffId);
		 KKaoqin kKaoqin = new KKaoqin();
	   	 kKaoqin.setNewId();
	   	 kKaoqin.setStaffId(staffId);
	   	 kKaoqin.setOrganId(organId);
	     kKaoqin.setStaffName(staff.getName());
	   	 kKaoqin.setLeibieId(kKaoqingleibie.get_id());
	   	 if(division == 2) {
	   		kKaoqin.setLeibieName("旷工半天");
	   	 } else {
	   		kKaoqin.setLeibieName(kKaoqingleibie.getNames());
	   	 }
	   	 kKaoqin.setCode(3);
	   	 kKaoqin.setDay(yearMonthDay);
	   	 kKaoqin.setMoney_koufa(Arith.div(kKaoqingleibie.getMoney1(), division));
	   	 mongoTemplate.save(kKaoqin);
	}
	
	/**
	 * 计算出相差的分钟数
	 * @param time1
	 * @param time2
	 * @return
	 */
	public long differMinutesDate(Date time1,Date time2) {
		return (time1.getTime()-time2.getTime())/(1000*60);
	}
	/**
	 * 查询员工签到  设置打开次数
	 */
	@Override
	public void queryStaffSignAndSetPaibanState(Date startTime, Date endTime, String organId,
			String staffId) {
		SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyyMM");
		Integer startYearMonth =  new Integer(yearMonthFormat.format(startTime));
		Integer endYearMonth =  new Integer(yearMonthFormat.format(endTime));
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
		Integer startDayInt =  new Integer(dayFormat.format(startTime));
		Integer endDayInt =  new Integer(dayFormat.format(endTime));
		List<KPaiban> kPaibans = queryKPaiban(organId, staffId, startTime, endTime);
		if(kPaibans !=null) {
			for(KPaiban kPaiban:kPaibans) {
				if(kPaiban.getYearmonth() == startYearMonth && kPaiban.getYearmonth() == endYearMonth) {   //等于开始的时间 
					for(int i= startDayInt; i <=endDayInt ; i ++ ) {
				        queryStaffsSign(i,kPaiban.getYearmonth()+"",staffId,organId,kPaiban.get_id());
					}
				}else  if(kPaiban.getYearmonth() == startYearMonth) {   //等于开始的时间 
					for(int i= startDayInt; i <=31 ; i ++ ) {
						queryStaffsSign(i,kPaiban.getYearmonth()+"",staffId,organId,kPaiban.get_id());
					}
				} else if(kPaiban.getYearmonth() == endYearMonth) {    //等于结束的时间
					for(int i= 1; i <= endDayInt ; i ++ ) {
						queryStaffsSign(i,kPaiban.getYearmonth()+"",staffId,organId,kPaiban.get_id());
					}   
				} else {    //既不等于开始的时间也不等于结束的时间
					for(int i = 1; i <=31; i ++) {
						queryStaffsSign(i,kPaiban.getYearmonth()+"",staffId,organId,kPaiban.get_id());
					}
				}
			}	
		}
	}
	/**
	 * 查询技师签到的次数  设置到排班表中
	 * @param i
	 * @param yearMonth
	 * @param staffId
	 * @param organId
	 */
	private void queryStaffsSign(int i, String yearMonth, String staffId,
			String organId,String kPaibanId) {
		try {
			if (i < 10) {
				Date dayStartTime = DateUtil.parse(yearMonth + "0" + i + " 00:00:01", "yyyyMMdd HH:mm:ss");
				Date dayEndTime = DateUtil.parse(yearMonth + "0" + i + " 23:59:59", "yyyyMMdd HH:mm:ss");
				List<WeStaffSign> weStaffSigns = queryEveryDayStaffSign(dayStartTime, dayEndTime, staffId, organId);
				if (weStaffSigns.size() > 0)
					updatePaibanDayState(kPaibanId, "0" + i, weStaffSigns.size());
			} else {
				Date dayStartTime = DateUtil.parse(yearMonth + i + " 00:00:01", "yyyyMMdd HH:mm:ss");
				Date dayEndTime = DateUtil.parse(yearMonth + i + " 23:59:59", "yyyyMMdd HH:mm:ss");
				List<WeStaffSign> weStaffSigns = queryEveryDayStaffSign(dayStartTime, dayEndTime, staffId, organId);
				if (weStaffSigns.size() > 0)
					updatePaibanDayState(kPaibanId, "" + i, weStaffSigns.size());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新某天day的状态
	 * @param kPaiban
	 * @param dayName
	 * @param value
	 */
	private void updatePaibanDayState(String kPaibanId,String dayName,int value) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(kPaibanId));
		Update update  = new Update();
		update.set("day"+dayName + "State", value);
		mongoTemplate.updateFirst(query, update, KPaiban.class);
	}
	
	/**
	 * 查询技师
	 * @param signTime
	 * @param staffId
	 * @param organId
	 * @return
	 * @throws ParseException 
	 */
	private List<WeStaffSign> queryTwoDaySignCondition(String signTime,String staffId,String organId) {
		SimpleDateFormat dateFormat  =  new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Query query = new Query();
		query.addCriteria(Criteria.where("staffId").is(staffId));
		query.addCriteria(Criteria.where("organId").is(organId));
        String everyDayStartTime  = signTime + "06:00:00";
        String everyDayEndTime  = signTime + " 12:00:00";
        try {
	        Date endTime = dateFormat.parse(everyDayEndTime);
	        Calendar cal = Calendar.getInstance();
			cal.setTime(endTime);
	  	    cal.add(Calendar.DATE, 1);
	  	    endTime = cal.getTime();
			query.addCriteria(Criteria.where("createTime").gte(dateFormat.parse(everyDayStartTime)).lte((endTime)));
			 return mongoTemplate.find(query, WeStaffSign.class);
        } catch (ParseException e) {
			e.printStackTrace();
			 return null;
		}
      
	}
	/**
	 * 哪一天
	 * @param signTime
	 * @return
	 */
	private List<WeStaffSign> queryEveryDayStaffSign(Date startTime,Date endTime,String staffId,String organId)  {
		Query query = new Query();
		query.addCriteria(Criteria.where("staffId").is(staffId));
		query.addCriteria(Criteria.where("organId").is(organId));
	    query.addCriteria(Criteria.where("createTime").gte(startTime).lte(endTime));
	    List<String> sortFields = new ArrayList<String>();
	    sortFields.add("createTime");
	    Sort sort = new Sort(Direction.ASC,sortFields);
	    query.with(sort);
	    return mongoTemplate.find(query, WeStaffSign.class);
	}
	/**
	 * 这里查询的是 初始化好的考勤类别
	 * @param code  表示码
	 * @param organId 公司的id
	 * @return
	 */
	private KKaoqinleibie queryKaoqinleibieByCode(Integer code,String organId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("code").is(code),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, KKaoqinleibie.class);
	}

	@Override
	public void saveKKaoqintime(KKaoqintime kKaoqintime) {
		mongoTemplate.save(kKaoqintime);
	}

	@Override
	public boolean queryKKaoqintime(String organId, String staffId,int type) {
		Query query = new Query();
		query.addCriteria(Criteria.where("organId").is(organId));
		query.addCriteria(Criteria.where("staffId").is(staffId));
		query.addCriteria(Criteria.where("type").is(type));
		return mongoTemplate.exists(query,KKaoqintime.class);
	}

	@Override
	public FlipInfo<KKaoqin> findZaotui(FlipInfo<KKaoqin> fpi) {
		Query query = new Query();
		query.addCriteria(Criteria.where("code").is(new Integer(2)));
		fpi.setSortField("day");
		fpi.setSortOrder("DESC");
		return mongoTemplate.findByPage(query, fpi, KKaoqin.class);
	}

	@Override
	public FlipInfo<KKaoqin> findChiDao(FlipInfo<KKaoqin> fpi) {
	    Query query = new Query();
		query.addCriteria(Criteria.where("code").is(new Integer(1)));
		fpi.setSortField("day");
		fpi.setSortOrder("DESC");
		return mongoTemplate.findByPage(query, fpi, KKaoqin.class);
	}

	@Override
	public KKaoqintime queryKaoqintime(String organId, String get_id,int type) {
		Query query = new Query();
		query.addCriteria(Criteria.where("organId").is(organId));
		query.addCriteria(Criteria.where("staffId").is(get_id));
		query.addCriteria(Criteria.where("type").is(type));
		return mongoTemplate.findOne(query,KKaoqintime.class);
	}

	@Override
	public void updateKaoqintime(String organId, String staffId, Date endTime,int type) {
		Query query = new Query();
		query.addCriteria(Criteria.where("organId").is(organId));
		query.addCriteria(Criteria.where("staffId").is(staffId));
		query.addCriteria(Criteria.where("type").is(type));
		Update update = new Update();
		update.set("createTime", endTime);
		mongoTemplate.updateFirst(query, update,KKaoqintime.class);
	}
	//查询旷工情况
	@Override
	public FlipInfo<KKaoqin> findKuangGong(FlipInfo<KKaoqin> fpi) {
		Query query = new Query();
		query.addCriteria(Criteria.where("code").is(new Integer(3)));
		fpi.setSortField("day");
		fpi.setSortOrder("DESC");
	    return mongoTemplate.findByPage(query, fpi, KKaoqin.class);
	}
	//查询所有的打卡情况
	@Override
	public FlipInfo<KPaiban> queryStaffSign(FlipInfo<KPaiban> fpi,
			String organId) {
		 fpi.setSortField("yearmonth");
		 fpi.setSortOrder("DESC");
		 fpi = mongoTemplate.findByPage(null, fpi, KPaiban.class);
		 return fpi;
	}

	@Override
	public KPaiban findPaiBanById(String paibanId) {
		return mongoTemplate.findById(paibanId, KPaiban.class);
	}

	@Override
	public FlipInfo<Staff> findPaiBanStaff(FlipInfo<Staff> fpi) {
		return mongoTemplate.findByPage(null, fpi, Staff.class);
	}
	
	@Override
	public void initKaoqin() {
		List<Organ> organs = mongoTemplate.findAll(Organ.class);
		for (Organ organ : organs) {
			initKaoqinBanci(organ);
			initKaoqinleibie(organ);
		}
	}
	
	@Override
	public void initKaoqinByOrgan(Organ organ) {
		initKaoqinBanci(organ);
		initKaoqinleibie(organ);
	}
	
	public void initKaoqinBanci(Organ organ) { 
	     KBancidingyi kBancidingyi = new KBancidingyi();
	     kBancidingyi.setIdIfNew();
	     kBancidingyi.setOrganId(organ.get_id());
	     kBancidingyi.setNames("早班");
	     kBancidingyi.setTime_a1("09:00");
	     kBancidingyi.setKaoqin_a1(true);
	     kBancidingyi.setTime_a2("18:00");
	     kBancidingyi.setKaoqin_a2(true);
	     kBancidingyi.setKuatian(false);
	     kBancidingyi.setDelete_flag(false);
	     mongoTemplate.save(kBancidingyi);
	     KBancidingyi kBancidingyi2 = new KBancidingyi();
	     kBancidingyi2.setIdIfNew();
	     kBancidingyi2.setOrganId(organ.get_id());
	     kBancidingyi2.setNames("中班");
	     kBancidingyi2.setTime_a1("12:00");
	     kBancidingyi2.setKaoqin_a1(true);
	     kBancidingyi2.setTime_a2("22:00");
	     kBancidingyi2.setKaoqin_a2(true);
	     kBancidingyi2.setKuatian(false);
	     kBancidingyi2.setDelete_flag(false);
	     mongoTemplate.save(kBancidingyi2);
	     KBancidingyi kBancidingyi3 = new KBancidingyi();
	     kBancidingyi3.setIdIfNew();
	     kBancidingyi3.setOrganId(organ.get_id());
	     kBancidingyi3.setNames("晚班");
	     kBancidingyi3.setTime_a1("21:00");
	     kBancidingyi3.setKaoqin_a1(true);
	     kBancidingyi3.setTime_a2("06:00");
	     kBancidingyi3.setKaoqin_a2(true);
	     kBancidingyi3.setKuatian(true);
	     kBancidingyi3.setDelete_flag(false);
	     mongoTemplate.save(kBancidingyi3);
	}
	
	public void initKaoqinleibie(Organ organ) {
		KKaoqinleibie kaoqinleibie = new KKaoqinleibie();
		kaoqinleibie.setIdIfNew();
		kaoqinleibie.setOrganId(organ.get_id());
		kaoqinleibie.setBase1(1);
		kaoqinleibie.setMoney1(1);
		kaoqinleibie.setBase2(1);
		kaoqinleibie.setMoney2(3);
		kaoqinleibie.setBase3(60);
		kaoqinleibie.setMoney3(100);
		kaoqinleibie.setCode(1);
		kaoqinleibie.setNote("迟到1分钟1元,迟到10分钟以上1分钟3元,迟到60分钟以上扣罚100元.");
		kaoqinleibie.setNames("迟到1分钟");
		kaoqinleibie.setGuding_flag(true);
		kaoqinleibie.setDelete_flag(false);
		mongoTemplate.save(kaoqinleibie);
		KKaoqinleibie kaoqinleibie2 = new KKaoqinleibie();
		kaoqinleibie2.setIdIfNew();
		kaoqinleibie2.setOrganId(organ.get_id());
		kaoqinleibie2.setBase1(1);
		kaoqinleibie2.setMoney1(1);
		kaoqinleibie2.setBase2(2);
		kaoqinleibie2.setMoney2(5);
		kaoqinleibie2.setBase3(60);
		kaoqinleibie2.setMoney3(100);
		kaoqinleibie2.setCode(2);
		kaoqinleibie2.setNote("早退1分钟1元,早退20分钟以上1分钟5元,早退60分钟以上扣罚100元.");
		kaoqinleibie2.setNames("早退1分钟");
		kaoqinleibie2.setGuding_flag(true);
		kaoqinleibie2.setDelete_flag(false);
		mongoTemplate.save(kaoqinleibie2);
		KKaoqinleibie kaoqinleibie3 = new KKaoqinleibie();
		kaoqinleibie3.setIdIfNew();
		kaoqinleibie3.setOrganId(organ.get_id());
		kaoqinleibie3.setBase1(0);
		kaoqinleibie3.setMoney1(100);
		kaoqinleibie3.setBase2(0);
		kaoqinleibie3.setMoney2(0);
		kaoqinleibie3.setBase3(0);
		kaoqinleibie3.setMoney3(0);
		kaoqinleibie3.setCode(3);
		kaoqinleibie3.setNote("直接罚款100元整");
		kaoqinleibie3.setNames("旷工1天");
		kaoqinleibie3.setGuding_flag(true);
		kaoqinleibie3.setDelete_flag(false);
		mongoTemplate.save(kaoqinleibie3);
	}
}
