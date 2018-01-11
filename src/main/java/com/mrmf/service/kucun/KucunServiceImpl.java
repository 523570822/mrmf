package com.mrmf.service.kucun;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Bumen;
import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.bean.KuncunAlert;
import com.mrmf.entity.kucun.StockControlSum;
import com.mrmf.entity.kucun.WInstoreroom;
import com.mrmf.entity.kucun.WOutstoreroom;
import com.mrmf.entity.kucun.WPinpai;
import com.mrmf.entity.kucun.WStoreroom;
import com.mrmf.entity.kucun.WStoretuihuo;
import com.mrmf.entity.kucun.WWupin;
import com.mrmf.service.common.Arith;
import com.mrmf.service.common.PinYinUtil;
import com.mrmf.service.organ.OrganService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("kucunService")
public class KucunServiceImpl implements KucunService {
	@Autowired
	private EMongoTemplate mongoTemplate;
	@Autowired
	private OrganService organService;

	@Override
	public FlipInfo<WPinpai> queryOrganPinpai(FlipInfo<WPinpai> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WPinpai.class);
		return fpi;
	}

	@Override
	public String queryParentOrganId(String organId) {
		Organ organ = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(organId)), Organ.class);
		while (!"0".equals(organ.getParentId())) {
			organ = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(organ.getParentId())),
					Organ.class);
		}
		return organ.get_id();
	}

	@Override
	public WPinpai queryWPinpaiById(String pinpaiId) {
		WPinpai pinpai = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(pinpaiId)), WPinpai.class);
		return pinpai;
	}

	@Override
	public ReturnStatus upsertPinpai(WPinpai pinpai) {
		pinpai.setIdIfNew();
		pinpai.setCreateTimeIfNew();
		if (StringUtils.isEmpty(pinpai.getZjfCode())) {
			String zjfCode = PinYinUtil.getFirstSpell(pinpai.getName());
			pinpai.setZjfCode(zjfCode);
		}
		mongoTemplate.save(pinpai);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(pinpai);
		return status;
	}

	@Override
	public ReturnStatus removePinpai(String pinpaiId) {
		mongoTemplate.remove(Query.query(Criteria.where("_id").is(pinpaiId)),
				WPinpai.class);
		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<WWupin> queryOrganWupin(FlipInfo<WWupin> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WWupin.class);
		for (WWupin wupin : fpi.getData()) {
			Bumen bumen = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(wupin.getBumen())),
					Bumen.class);
			if (bumen != null)
				wupin.setBumenName(bumen.getName());
			Code code = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(wupin.getDanwei())),
					Code.class);
			if (code != null)
				wupin.setDanweiName(code.getName());
			WPinpai pinpai = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(wupin.getPinpai())),
					WPinpai.class);
			if (pinpai != null)
				wupin.setPinpaiName(pinpai.getName());
		}
		return fpi;
	}

	@Override
	public WWupin queryWWupinById(String wupinId) {
		WWupin wupin = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(wupinId)), WWupin.class);
		return wupin;
	}

	@Override
	public List<WPinpai> queryPinPaiList(String organId) {
		List<WPinpai> pinpais = mongoTemplate.find(
				Query.query(Criteria.where("organId").is(organId)),
				WPinpai.class);
		return pinpais;
	}

	@Override
	public List<Code> queryCodeList(String type) {
		List<Code> codes = mongoTemplate.find(
				Query.query(Criteria.where("type").is(type)), Code.class);
		return codes;
	}

	@Override
	public ReturnStatus upsertWupin(WWupin wupin) {
		wupin.setIdIfNew();
		wupin.setCreateTimeIfNew();
		if (StringUtils.isEmpty(wupin.getZjfCode())) {
			String zjfCode = PinYinUtil.getFirstSpell(wupin.getMingcheng());
			wupin.setZjfCode(zjfCode);
		}
		WWupin w = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(wupin.get_id())),
				WWupin.class);
		if (w != null) {
			wupin.setPrice_xss(w.getPrice_xss());
		}
		mongoTemplate.save(wupin);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(wupin);
		return status;
	}

	@Override
	public ReturnStatus removeLeibie(String leibieId) {
		mongoTemplate.remove(Query.query(Criteria.where("_id").is(leibieId)),
				WWupin.class);
		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<WStoreroom> queryOrganWStoreroom(FlipInfo<WStoreroom> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WStoreroom.class);
		for (WStoreroom room : fpi.getData()) {
			Bumen bumen = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(room.getBumen())),
					Bumen.class);
			if (bumen != null)
				room.setBumenName(bumen.getName());
			WPinpai pinpai = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(room.getPinpai())),
					WPinpai.class);
			if (pinpai != null)
				room.setPinpaiName(pinpai.getName());
			WWupin wupin = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(room.getWupinId())),
					WWupin.class);
			if (wupin != null)
				room.setWupinName(wupin.getMingcheng());
			double priceAll = Arith.mul(room.getPrice(), room.getNum());
			room.setPrice_all(priceAll);
			room.setWeight_all(room.getWeight() * room.getNum());
		}
		return fpi;
	}

	@Override
	public FlipInfo<WInstoreroom> queryOrganWInstoreroom(
			FlipInfo<WInstoreroom> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WInstoreroom.class);
		for (WInstoreroom in : fpi.getData()) {
			if (in.getShenhe()) {
				in.setShenheName("已审核");
			} else {
				in.setShenheName("未审核");
			}
			WWupin wupin = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getWupinId())),
					WWupin.class);
			if (wupin != null)
				in.setWupinName(wupin.getMingcheng());
			Bumen bumen = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getBumen())),
					Bumen.class);
			if (bumen != null)
				in.setBumenName(bumen.getName());
			Code code = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getDanwei())),
					Code.class);
			if (code != null)
				in.setDanweiName1(code.getName());
			WPinpai pinpai = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getPinpai())),
					WPinpai.class);
			if (pinpai != null)
				in.setPinpaiName(pinpai.getName());
			if (in.getShenhe_fen() == null) {
				in.setShenhefenName("");
			} else if (in.getShenhe_fen()) {
				in.setShenhefenName("是");
			} else {
				in.setShenhefenName("否");
			}
			if(!"".equals(in.getReturn_organId())&&in.getReturn_organId()!=null){
				Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(in.getReturn_organId())), Organ.class);
				in.setReturn_organName(organ.getName());
			}
			in.setPrice_all(Arith.mul(in.getPrice(), in.getNum()));
			in.setPrice_all_xs(Arith.mul(in.getPrice_xs(), in.getNum()));
		}
		return fpi;
	}

	@Override
	public FlipInfo<WOutstoreroom> queryOrganWOutstoreroom(
			FlipInfo<WOutstoreroom> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WOutstoreroom.class);
		for (WOutstoreroom in : fpi.getData()) {
			if (in.getShenhe()) {
				in.setShenheName("已审核");
			} else {
				in.setShenheName("未审核");
			}
			WWupin wupin = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getWupinId())),
					WWupin.class);
			if (wupin != null)
				in.setWupinName(wupin.getMingcheng());
			Bumen bumen = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getBumen())),
					Bumen.class);
			if (bumen != null)
				in.setBumenName(bumen.getName());
			Code code = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getDanwei())),
					Code.class);
			if (code != null)
				in.setDanweiName1(code.getName());
			WPinpai pinpai = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(in.getPinpai())),
					WPinpai.class);
			if (pinpai != null)
				in.setPinpaiName(pinpai.getName());
			if (in.getGuazhang_flag() != null && in.getGuazhang_flag()) {
				in.setGuizhangName("是");
			} else {
				in.setGuizhangName("否");
			}
			if (in.getFlag() != null && in.getFlag()) {
				in.setFlagName("是");
			} else {
				in.setFlagName("否");
			}
			if (in.getShenhe_fen() == null) {
				in.setShenhefenName("");
			} else if (in.getShenhe_fen()) {
				in.setShenhefenName("是");
			} else {
				in.setShenhefenName("否");
			}
			in.setPrice_all_xs(Arith.mul(in.getPrice_xs(), in.getNum()));
			in.setPrice_all(Arith.mul(in.getPrice(), in.getNum()));
			if (!StringUtils.isEmpty(in.getDanwei())) {
				Organ organ = mongoTemplate.findOne(
						Query.query(Criteria.where("_id").is(in.getDanwei())),
						Organ.class);
				if (organ != null) {
					in.setDanweiname(organ.getName());
				}
			}

		}
		return fpi;
	}

	@Override
	public WInstoreroom queryWInstoreroomById(String rukuId) {
		WInstoreroom ruku = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(rukuId)),
				WInstoreroom.class);
		return ruku;
	}

	@Override
	public ReturnStatus upsertWInstoreroom(WInstoreroom instoreRoom) {
		instoreRoom.setIdIfNew();
		instoreRoom.setCreateTimeIfNew();
		if (StringUtils.isEmpty(instoreRoom.getZjfCode())) {
			String zjfCode = PinYinUtil.getFirstSpell(instoreRoom
					.getMingcheng());
			instoreRoom.setZjfCode(zjfCode);
		}
		instoreRoom.setCome_time(new Date());
		mongoTemplate.save(instoreRoom);
		if (instoreRoom.getShenhe()) {
			addWStoreroom(instoreRoom);
		}
		ReturnStatus status = new ReturnStatus(true);
		status.setData(instoreRoom);
		return status;
	}

	@Override
	public List<WWupin> queryWWupinList(String organId) {
		List<WWupin> wupins = mongoTemplate.find(
				Query.query(Criteria.where("organId").is(organId)),
				WWupin.class);
		for (WWupin wupin : wupins) {
			if (!StringUtils.isEmpty(wupin.getPinpai())) {
				WPinpai pinpai = mongoTemplate.findOne(Query.query(Criteria
						.where("_id").is(wupin.getPinpai())), WPinpai.class);
				wupin.setPinpaiName(pinpai.getName());
			}
		}
		return wupins;
	}

	@Override
	public ReturnStatus addWStoreroom(WInstoreroom instoreroom) {
		Criteria criteria = new Criteria();
		criteria.andOperator(
				Criteria.where("wupinId").is(instoreroom.getWupinId()),
				Criteria.where("organId").is(instoreroom.getOrganId()));
		WStoreroom wstoreRoom = mongoTemplate.findOne(Query.query(criteria),
				WStoreroom.class);
		if (wstoreRoom == null) {
			wstoreRoom = new WStoreroom();
			wstoreRoom.setIdIfNew();
			wstoreRoom.setParentOrganId(instoreroom.getParentOrganId());
			wstoreRoom.setOrganId(instoreroom.getOrganId());
			wstoreRoom.setWupinId(instoreroom.getWupinId());
			wstoreRoom.setNum(instoreroom.getNum());

			// wstoreRoom.setPrice_all(wstoreRoom.getPrice()*wstoreRoom.getNum());

		} else {

			// wstoreRoom.setNum(wstoreRoom.getNum()+instoreroom.getNum());
			double num = Arith.add(wstoreRoom.getNum(), instoreroom.getNum());
			wstoreRoom.setNum(num);
		}
		wstoreRoom.setMingcheng(instoreroom.getMingcheng());
		wstoreRoom.setPrice(instoreroom.getPrice());

		wstoreRoom.setNote(instoreroom.getNote());
		wstoreRoom.setWeight(instoreroom.getWeight());
		wstoreRoom.setBumen(instoreroom.getBumen());
		wstoreRoom.setZjfCode(instoreroom.getZjfCode());
		// wstoreRoom.setJingjie()
		wstoreRoom.setUseful_life(instoreroom.getUseful_life());
		wstoreRoom.setPinpai(instoreroom.getPinpai());
		wstoreRoom.setGuige(instoreroom.getGuige());
		mongoTemplate.save(wstoreRoom);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus removeRuku(String rukuId) {
		WInstoreroom inRoomm = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(rukuId)),
				WInstoreroom.class);
		if (inRoomm.getShenhe()) {
			Criteria criteria = new Criteria();
			criteria.andOperator(
					Criteria.where("wupinId").is(inRoomm.getWupinId()),
					Criteria.where("organId").is(inRoomm.getOrganId()));
			WStoreroom wstoreRoom = mongoTemplate.findOne(
					Query.query(criteria), WStoreroom.class);
			if (wstoreRoom.getNum() < inRoomm.getNum()) {
				return new ReturnStatus(false, "库存数量不足");
			} else {
				double num = Arith.sub(wstoreRoom.getNum(), inRoomm.getNum());
				wstoreRoom.setNum(num);
				mongoTemplate.save(wstoreRoom);
			}
		}
		inRoomm.setDelete_flag(true);
		mongoTemplate.save(inRoomm);
		// mongoTemplate.remove(inRoomm);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus checkRuku(String rukuId,Boolean returnFlag) {
		WInstoreroom room = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(rukuId)),
				WInstoreroom.class);
		if(returnFlag==true){
			Criteria criteria = new Criteria();
			criteria.andOperator(
					Criteria.where("wupinId").is(room.getWupinId()),
					Criteria.where("organId").is(room.getOrganId()));
			WStoreroom wsr = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
			wsr.setNum(Arith.add(wsr.getNum(), room.getNum()));
			mongoTemplate.save(wsr);
			room.setShenhe(true);
		}else{
			ReturnStatus status = addWStoreroom(room);
			if (status.isSuccess()) {
				room.setShenhe(true);
			}
			if (room.getShenhe_fen() != null) {
				room.setShenhe_fen(true);
			}
			WOutstoreroom outroom = mongoTemplate.findOne(
					Query.query(Criteria.where("danhao").is(room.getDanhao())),
					WOutstoreroom.class);
			if (outroom != null) {
				outroom.setShenhe_fen(true);
				mongoTemplate.save(outroom);
			}
		}
		
		mongoTemplate.save(room);
		return new ReturnStatus(true);
	}

	@Override
	public List<Organ> branch(String parentId) {
		List<Organ> brance = mongoTemplate.find(
				Query.query(Criteria.where("parentId").is(parentId)),
				Organ.class);
		return brance;
	}

	@Override
	public ReturnStatus upsertWOutstoreroom(WOutstoreroom outstoreRoom) {
		outstoreRoom.setIdIfNew();
		outstoreRoom.setCreateTimeIfNew();
		if (StringUtils.isEmpty(outstoreRoom.getZjfCode())) {
			String zjfCode = PinYinUtil.getFirstSpell(outstoreRoom
					.getMingcheng());
			outstoreRoom.setZjfCode(zjfCode);
		}
		outstoreRoom.setCome_time(new Date());
		outstoreRoom.setShenhe_fen(false);
		if (outstoreRoom.getShenhe()) {
			ReturnStatus status = outWStoreroom(outstoreRoom);
			if (status.isSuccess()) {
				mongoTemplate.save(outstoreRoom);
				// if(!StringUtils.isEmpty(outstoreRoom.getDanwei())){
				// outStoreRoomToBranch(outstoreRoom);
				// }
			} else {
				return new ReturnStatus(false, "库存不足出库失败");
			}

		} else {
			mongoTemplate.save(outstoreRoom);
		}
		ReturnStatus status = new ReturnStatus(true);
		status.setData(outstoreRoom);
		return status;
	}

	@Override
	public ReturnStatus outWStoreroom(WOutstoreroom outstoreroom) {
		Criteria criteria = new Criteria();
		criteria.andOperator(
				Criteria.where("wupinId").is(outstoreroom.getWupinId()),
				Criteria.where("organId").is(outstoreroom.getOrganId()));
		WStoreroom wstoreRoom = mongoTemplate.findOne(Query.query(criteria),
				WStoreroom.class);

		// wstoreRoom.setPrice_all(wstoreRoom.getPrice()*wstoreRoom.getNum());
		if (wstoreRoom != null && wstoreRoom.getNum() >= outstoreroom.getNum()) {
			double num = Arith.sub(wstoreRoom.getNum(), outstoreroom.getNum());
			wstoreRoom.setNum(num);
			mongoTemplate.save(wstoreRoom);
			return new ReturnStatus(true);
		} else {
			return new ReturnStatus(false, "库存不足");
		}
	}

	@Override
	public ReturnStatus outStoreRoomToBranch(WOutstoreroom outstoreroom) {
		if (!StringUtils.isEmpty(outstoreroom.getDanwei())) {
			WInstoreroom inroom = new WInstoreroom();
			inroom.setIdIfNew();
			inroom.setParentOrganId(outstoreroom.getOrganId());
			inroom.setOrganId(outstoreroom.getDanwei());
			inroom.setWupinId(outstoreroom.getWupinId());
			inroom.setCode(outstoreroom.getCode());
			inroom.setMingcheng(outstoreroom.getMingcheng());
			inroom.setPrice(outstoreroom.getPrice());
			inroom.setNum(outstoreroom.getNum());
			inroom.setPlace1(outstoreroom.getPlace1());
			inroom.setLianxiren(outstoreroom.getStaff());
			inroom.setPlace2(outstoreroom.getPlace2());
			inroom.setPhone(outstoreroom.getPhone());
			inroom.setStaff(outstoreroom.getLingyong());
			inroom.setNote(outstoreroom.getNote());
			inroom.setWeight(outstoreroom.getWeight());
			inroom.setWeight_all(outstoreroom.getWeight_all());
			inroom.setBumen(outstoreroom.getBumen());
			inroom.setZjfCode(outstoreroom.getZjfCode());
			inroom.setCome_time(outstoreroom.getCome_time());
			inroom.setDanhao(outstoreroom.getDanhao());
			inroom.setDanweiname(outstoreroom.getDanweiname());
			inroom.setZupanhao(outstoreroom.getZupanhao());
			inroom.setGuige(outstoreroom.getGuige());
			inroom.setDelete_flag(false);
			/*
			 * OrganSetting organSetting = null; try { organSetting =
			 * organService.querySetting(outstoreroom.getDanwei()); } catch
			 * (BaseException e) { 
			 * e.printStackTrace(); }
			 * 
			 * inroom.setCreateTime(outstoreroom.getCreateTime());
			 * if(organSetting!=null){ if(organSetting.getRukuShenhe()){
			 * inroom.setShenhe(false); inroom.setShenhe_fen(false);
			 * outstoreroom.setShenhe_fen(false);
			 * mongoTemplate.save(outstoreroom); }else{ inroom.setShenhe(true);
			 * inroom.setShenhe_fen(true); addWStoreroom(inroom);
			 * outstoreroom.setShenhe_fen(true);
			 * mongoTemplate.save(outstoreroom); } }
			 */
			inroom.setShenhe_fen(true);
			inroom.setShenhe(true);
			ReturnStatus status = addWStoreroom(inroom);
			if (status.isSuccess()) {
				outstoreroom.setShenhe_fen(true);
				mongoTemplate.save(outstoreroom);
				mongoTemplate.save(inroom);
			} else {
				new ReturnStatus(false);
			}

		}
		return new ReturnStatus(true);
	}

	@Override
	public WOutstoreroom queryWOutstoreroomById(String chukuId) {
		WOutstoreroom outroom = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(chukuId)),
				WOutstoreroom.class);
		return outroom;
	}

	@Override
	public ReturnStatus removeChuku(String chukuId) {
		WOutstoreroom outRoom = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(chukuId)),
				WOutstoreroom.class);
		outRoom.setDelete_flag(true);
		mongoTemplate.save(outRoom);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus checkChuku(String chukuId) {
		WOutstoreroom outroom = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(chukuId)),
				WOutstoreroom.class);
		ReturnStatus status = outWStoreroom(outroom);
		if (status.isSuccess()) {
			outroom.setShenhe(true);
			mongoTemplate.save(outroom);
			// if(!StringUtils.isEmpty(outroom.getDanwei())){
			// outStoreRoomToBranch(outroom);
			// }
		} else {
			return new ReturnStatus(false, "库存不足出库失败");
		}

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus checkHedui(String heduiId) {
		WOutstoreroom outroom = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(heduiId)),
				WOutstoreroom.class);
		ReturnStatus status = outStoreRoomToBranch(outroom);
		if (status.isSuccess()) {
			return new ReturnStatus(true);
		} else {
			return new ReturnStatus(false);
		}

	}

	public double getNum(double num) {
		BigDecimal bg = new BigDecimal(num);
		double f1 = bg.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
		double d = Double.parseDouble(String.format("%.0f", f1));
		return d;
	}

	@Override
	public ReturnStatus childLeiBieUpdate(String leibieId, Double pricexs,
			String organId) {
		WWupin wupin = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(leibieId)), WWupin.class);
		if (wupin != null) {
			Map<String, Double> price_xss = wupin.getPrice_xss();
			if (price_xss != null) {
				boolean flag = false;
				Iterator it_d = price_xss.entrySet().iterator();
				while (it_d.hasNext()) {
					Map.Entry entry_d = (Map.Entry) it_d.next();
					Object key = entry_d.getKey();
					Object value = entry_d.getValue();
					if (organId.equals((String) key)) {
						price_xss.put((String) key, pricexs);
						flag = true;
						break;
					}
				}
				if (!flag) {
					price_xss.put(organId, pricexs);
				}
			} else {
				price_xss = new HashMap<String, Double>();
				price_xss.put(organId, pricexs);
			}
			wupin.setPrice_xss(price_xss);
			mongoTemplate.save(wupin);
		}
		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<WStoretuihuo> queryWStoretuihuo(FlipInfo<WStoretuihuo> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WStoretuihuo.class);
		for (WStoretuihuo t : fpi.getData()) {
			WWupin wupin = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(t.getWupinId())),
					WWupin.class);
			if (wupin != null)
				t.setWupinName(wupin.getMingcheng());
			Bumen bumen = mongoTemplate.findOne(
					Query.query(Criteria.where("_id").is(t)), Bumen.class);
			if (bumen != null)
				t.setBumenName(bumen.getName());
			if (t.getFlag() != null && t.getFlag()) {
				t.setFlagName("是");
			} else {
				t.setFlagName("否");
			}
		}
		return fpi;
	}

	@Override
	public StockControlSum stockSum(String organId, String wupinId,
			boolean shenhe) {
		StockControlSum scs = new StockControlSum();
		double sum = 0;
		double priceSum = 0;
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("wupinId").is(wupinId), Criteria
				.where("shenhe").is(shenhe),
				Criteria.where("organId").is(organId));
		List<WInstoreroom> inroom = mongoTemplate.find(Query.query(criteria),
				WInstoreroom.class);
		for (WInstoreroom in : inroom) {
			sum = Arith.add(sum, in.getNum());
			priceSum = Arith.add(priceSum,
					Arith.mul(in.getNum(), in.getPrice()));
		}
		scs.setInSum(sum);
		scs.setInPriceSum(priceSum);
		sum = 0;
		priceSum = 0;

		List<WOutstoreroom> outroom = mongoTemplate.find(Query.query(criteria),
				WOutstoreroom.class);
		for (WOutstoreroom out : outroom) {
			sum = Arith.add(sum, out.getNum());
			priceSum = Arith.add(priceSum,
					Arith.mul(out.getNum(), out.getPrice_xs()));
		}
		scs.setOutSum(sum);
		scs.setOutPriceSum(priceSum);
		sum = 0;
		priceSum = 0;
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("organId").is(organId),
				Criteria.where("wupinId").is(wupinId));
		List<WStoretuihuo> tuihuo = mongoTemplate.find(Query.query(c),
				WStoretuihuo.class);
		for (WStoretuihuo t : tuihuo) {
			sum = Arith.add(sum, t.getNum());
			priceSum = Arith.add(priceSum, Arith.mul(t.getNum(), t.getPrice()));
		}
		scs.setTuiHuoPriceSum(priceSum);
		scs.setTuiHuoSum(sum);
		return scs;
	}

	@Override
	public ReturnStatus upsertWStoretuihuo(String code, double num,
			String kucunId) {
		WStoreroom room = mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(kucunId)),
				WStoreroom.class);
		if (room != null) {
			if (num > room.getNum()) {
				return new ReturnStatus(false, "库存不足");
			} else {
				room.setNum(Arith.sub(room.getNum(), num));
				WStoretuihuo tuihuo = new WStoretuihuo();
				tuihuo.setIdIfNew();
				tuihuo.setParentOrganId(room.getParentOrganId());
				tuihuo.setOrganId(room.getOrganId());
				tuihuo.setCode(code);
				tuihuo.setWupinId(room.getWupinId());
				tuihuo.setMingcheng(room.getMingcheng());
				tuihuo.setPrice(room.getPrice());
				tuihuo.setNum(num);
				tuihuo.setPrice_all(Arith.mul(num, room.getPrice()));
				tuihuo.setWeight(room.getWeight());
				tuihuo.setWeight_all(Arith.mul(num, room.getWeight()));
				tuihuo.setBumen(room.getBumen());
				tuihuo.setCreateTimeIfNew();
				mongoTemplate.save(room);
				mongoTemplate.save(tuihuo);
				return new ReturnStatus(true);
			}
		} else {
			return new ReturnStatus(false, "库存不足");
		}
	}

	@Override
	public ReturnStatus upsertWStoretuihuo(String organId,String code, double num,Integer reAddr) {
		
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("wupinId").is(code));
		WStoreroom room = mongoTemplate.findOne(
				Query.query(criteria),
				WStoreroom.class);
		WWupin wupin=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(code)), WWupin.class);
		if (room != null) {
			if (num > room.getNum()) {
				return new ReturnStatus(false, "库存不足");
			} else {
				if(1==reAddr){//退货到总公司
					//1先判断总公司是否出库过此货物到当前公司
					Criteria c = new Criteria();
					c.andOperator(Criteria.where("wupinId").is(code),Criteria.where("danwei").is(organId));
					WOutstoreroom wost=mongoTemplate.findOne(Query.query(c), WOutstoreroom.class);
					if(wost==null){
						return new ReturnStatus(false, "总部未向本公司出货此商品");
					}else{
						//2向总公司的入库信息表里面插入一条数据为当前退货商品并且置为待审核
						WInstoreroom wisr = new WInstoreroom();
						wisr.setIdIfNew();
						wisr.setParentOrganId(wost.getParentOrganId());
						wisr.setOrganId(wost.getOrganId());
						wisr.setCode(wost.getCode());
						wisr.setWupinId(wost.getWupinId());
						wisr.setMingcheng(wost.getMingcheng());
						wisr.setPrice(wisr.getPrice());
						wisr.setNum(num);
						//wisr.setPrice(price)
						wisr.setPlace1(wost.getPlace1());
						wisr.setLianxiren(wost.getLingyong());
						wisr.setWeight(wost.getWeight());
						wisr.setNote("总公司退货");
						wisr.setZjfCode(wost.getZjfCode());
						wisr.setCome_time(new Date());
						wisr.setUseful_life(room.getUseful_life());
						wisr.setDanhao(wost.getDanhao());
						wisr.setZupanhao(wost.getZupanhao());
						wisr.setGuige(wost.getGuige());
						wisr.setShenhe(false);
						wisr.setPrice_xs(wost.getPrice_xs());
						wisr.setPinpai(wost.getPinpai());
						wisr.setCreateTimeIfNew();
						wisr.setReturn_flag(true);
						wisr.setReturn_organId(organId);
						wisr.setDelete_flag(false);
						//wisr.setShenhe_fen(true);
						mongoTemplate.save(wisr);
					}
				}
				room.setNum(Arith.sub(room.getNum(), num));
				WStoretuihuo tuihuo = new WStoretuihuo();
				tuihuo.setIdIfNew();
				tuihuo.setParentOrganId(room.getParentOrganId());
				tuihuo.setOrganId(room.getOrganId());
				tuihuo.setCode(wupin.getCode());
				tuihuo.setWupinId(room.getWupinId());
				tuihuo.setMingcheng(room.getMingcheng());
				tuihuo.setPrice(room.getPrice());
				tuihuo.setNum(num);
				tuihuo.setPrice_all(Arith.mul(num, room.getPrice()));
				tuihuo.setWeight(room.getWeight());
				tuihuo.setWeight_all(Arith.mul(num, room.getWeight()));
				tuihuo.setBumen(room.getBumen());
				tuihuo.setCreateTimeIfNew();
				mongoTemplate.save(room);
				mongoTemplate.save(tuihuo);
				return new ReturnStatus(true);
			}
		} else {
			return new ReturnStatus(false, "库存不足");
		}
	}

	@Override
	public List<WWupin> findWWupins(String organId) {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), WWupin.class);
	}

	@Override
	public KuncunAlert calculateAlert(WWupin wWupin) {
		List<WStoreroom> wStorerooms = mongoTemplate.find(Query.query(Criteria.where("wupinId").is(wWupin.get_id())), WStoreroom.class);
		double num =0;
		for (WStoreroom wStoreroom : wStorerooms) {
			 num += wStoreroom.getNum();
		}
		KuncunAlert kuncunAlert = new KuncunAlert();
		if(wWupin.getJingjie()>num) {
			kuncunAlert.setType(wWupin.getMingcheng());
			kuncunAlert.setAlertNumber(wWupin.getJingjie());
			kuncunAlert.setNumber(num);
			return kuncunAlert;
		} 
		return null;
	}
	
}
