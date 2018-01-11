package com.mrmf.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.OrganSetting;
import com.mrmf.entity.Staff;
import com.mrmf.entity.bean.StaffTichengReport;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.staff.StaffFloatTicheng;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.staff.ticheng.StaffFloatTichengService;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;

@Service("staffTichengReportService")
public class StaffTichengReportServiceImpl implements StaffTichengReportService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private StaffService staffService;

	@Autowired
	private OrganService organService;

	@Autowired
	private StaffFloatTichengService staffFloatTichengService;

	@Override
	public List<StaffTichengReport> query(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		List<Userpart> userpartList = queryBy(criterias, organId, startTime, endTime);

		// 外卖记录
		criterias = new ArrayList<>();
		Criteria criteria = new Criteria();
		criterias.add(Criteria.where("organId").is(organId));
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		criterias.add(Criteria.where("guazhang_flag").is(false));
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		List<WWaimai> waimaiList = mongoTemplate.find(Query.query(criteria), WWaimai.class);

		// 分段提成定义
		List<StaffFloatTicheng> fenduanList = staffFloatTichengService.queryAllFenduan(organId);
		// 最高提成定义
		List<StaffFloatTicheng> zuigaoList = staffFloatTichengService.queryAllZuigao(organId);

		OrganSetting setting = organService.querySetting(organId);

		List<StaffTichengReport> result = new ArrayList<>();
		List<Staff> staffList = staffService.findAll(organId);
		for (Staff staff : staffList) {
			StaffTichengReport ss = new StaffTichengReport();
			result.add(ss);
			ss.setStaffId(staff.get_id());
			ss.setStaffName(staff.getName());
			ss.setBumenId(staff.getBumenId());

			// 流水
			double sankeLiushui = 0, waimaiLiushui = 0, cardLiushui = 0, newCardLiushui = 0, xufeiLiushui = 0,
					cardWaimaiLiushui = 0, miandanLiushui = 0, weixinLiushui = 0;
			// 提成
			double sankeTicheng = 0, waimaiTicheng = 0, cardTicheng = 0, newCardTicheng = 0, xufeiTicheng = 0,
					cardWaimaiTicheng = 0, miandanTicheng = 0, weixinTicheng = 0;

			for (Userpart up : userpartList) {
				boolean is = false;
				boolean isBanka = (up.getType() == 0 && !StringUtils.isEmpty(up.getIncardId())) || up.getType() == 10;
				double ticheng = 0, yeji = 0;
				if (staff.get_id().equals(up.getStaffId1())) {
					is = true;
					ticheng += up.getSomemoney1();
					yeji += up.getYeji1();
				}
				if (staff.get_id().equals(up.getStaffId2())) {
					is = true;
					ticheng += up.getSomemoney2();
					yeji += up.getYeji2();
				}
				if (staff.get_id().equals(up.getStaffId3())) {
					is = true;
					ticheng += up.getSomemoney3();
					yeji += up.getYeji3();
				}

				if (is) {
					if (up.getMiandan()) { // 免单
						miandanLiushui += yeji;
						miandanTicheng += ticheng;
					} else {
						if (up.getType() == 0 && !isBanka) { // 散客
							sankeLiushui += yeji;
							sankeTicheng += ticheng;
						}
						if (up.getType() == 1 || up.getType() == 11) { // 主卡、子卡消费
							cardLiushui += up.getMoney_xiaofei();
							cardTicheng += ticheng;
						}
						if (isBanka) { // 办卡
							newCardLiushui += yeji;
							newCardTicheng += ticheng;
						}
						if (up.getType() == 3) { // 续费
							xufeiLiushui += yeji;
							xufeiTicheng += ticheng;
						}
						if (up.getType() == 2) { // 微信
							weixinLiushui += yeji;
							weixinTicheng += ticheng;
						}
					}
				}
			}

			// 外卖统计
			for (WWaimai waimai : waimaiList) {
				if (staff.get_id().equals(waimai.getStaffId1())) {
					if (waimai.getIsCard()) {
						cardWaimaiLiushui += waimai.getYeji1();
						cardWaimaiTicheng += waimai.getTicheng1();
					} else {
						waimaiLiushui += waimai.getYeji1();
						waimaiTicheng += waimai.getTicheng1();
					}
				} else if (staff.get_id().equals(waimai.getStaffId2())) {
					if (waimai.getIsCard()) {
						cardWaimaiLiushui += waimai.getYeji2();
						cardWaimaiTicheng += waimai.getTicheng2();
					} else {
						waimaiLiushui += waimai.getYeji2();
						waimaiTicheng += waimai.getTicheng2();
					}
				}
			}

			ss.setSankeLiushui(sankeLiushui);
			ss.setSankeTicheng(sankeTicheng);
			ss.setWaimaiLiushui(waimaiLiushui);
			ss.setWaimaiTicheng(waimaiTicheng);
			ss.setCardLiushui(cardLiushui);
			ss.setCardTicheng(cardTicheng);
			ss.setNewCardLiushui(newCardLiushui);
			ss.setNewCardTicheng(newCardTicheng);
			ss.setXufeiLiushui(xufeiLiushui);
			ss.setXufeiTicheng(xufeiTicheng);
			ss.setCardWaimaiLiushui(cardWaimaiLiushui);
			ss.setCardWaimaiTicheng(cardWaimaiTicheng);
			ss.setMiandanLiushui(miandanLiushui);
			ss.setMiandanTicheng(miandanTicheng);
			ss.setWeixinLiushui(weixinLiushui);
			ss.setWeixinTicheng(weixinTicheng);

			double totalLiushui = sankeLiushui + waimaiLiushui + cardLiushui + newCardLiushui + xufeiLiushui
					+ cardWaimaiLiushui + miandanLiushui + weixinLiushui;
			double totalTicheng = sankeTicheng + waimaiTicheng + cardTicheng + newCardTicheng + xufeiTicheng
					+ cardWaimaiTicheng + miandanTicheng + weixinTicheng;
			ss.setTotalLiushui(totalLiushui);
			ss.setTotalTicheng(totalTicheng);

			List<String> tichengLiushui = setting.getTichengLiushui();
			if (tichengLiushui != null) {
				double liushui = 0;
				for (String tl : tichengLiushui) {
					if ("sanke".equals(tl)) {
						liushui += sankeLiushui;
					} else if ("waimai".equals(tl)) {
						liushui += waimaiLiushui;
					} else if ("huiyuanka".equals(tl)) {
						liushui += cardLiushui;
					} else if ("banka".equals(tl)) {
						liushui += newCardLiushui;
					} else if ("xufei".equals(tl)) {
						liushui += xufeiLiushui;
					} else if ("huiyuankawaimai".equals(tl)) {
						liushui += cardWaimaiLiushui;
					} else if ("miandan".equals(tl)) {
						liushui += miandanLiushui;
					}
				}

				// 分段提成计算
				double fenduanTicheng = 0, tichenged = 0, tt = 0;
				for (StaffFloatTicheng ti : fenduanList) {
					if (liushui >= ti.getYeji1()) {
						double t = ti.getYeji2() - tichenged;
						if (t + tichenged > liushui)
							t = liushui - tichenged;
						tt = ti.getTicheng();
						fenduanTicheng += t * ti.getTicheng() / 100d;
						tichenged += t;
					}
				}
				if (tichenged < liushui) {
					fenduanTicheng += (liushui - tichenged) * tt / 100d;
				}
				ss.setTichengFenduan(
						new BigDecimal(fenduanTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

				// 最高提成计算
				double zuigaoTicheng = 0;
				for (StaffFloatTicheng ti : zuigaoList) {
					if (liushui >= ti.getYeji1()) {
						zuigaoTicheng = liushui * ti.getTicheng() / 100d;
						break;
					}
				}
				ss.setTichengZuigao(new BigDecimal(zuigaoTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			}

		}
		return result;
	}

	private List<Userpart> queryBy(List<Criteria> criterias, String organId, Date startTime, Date endTime)
			throws BaseException {
		if (criterias == null)
			criterias = new ArrayList<>();
		Criteria criteria = new Criteria();
		criterias.add(Criteria.where("organId").is(organId));
		criterias.add(Criteria.where("flag2").is(true)); // 已结账
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		List<Userpart> resultList = mongoTemplate
				.find(Query.query(criteria.andOperator(criterias.toArray(new Criteria[criterias.size()])))
						.with(new Sort(Direction.DESC, "createTime")), Userpart.class);
		return resultList;
	}

}
