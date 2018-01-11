package com.mrmf.moduleweb;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeUserCardCharge;
import com.mrmf.entity.bean.CardPayOnlineSum;
import com.mrmf.service.cardcontroller.CardPayOnlineService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.rank.RankService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.security.token.TokenManager;

@Controller
@RequestMapping("/card")
public class CardPayOnlineController {
	@Autowired
	private CardPayOnlineService cardPayOnlineService;
	@Autowired
	RankService rankservice;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private OrganService organService;

	@RequestMapping("/payOnline/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		request.setAttribute("organId", organId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cardPayController/query");
		return mv;
	}

	@RequestMapping("/payOnline/query")
	@ResponseBody
	public FlipInfo<WeUserCardCharge> query(HttpServletRequest request) throws Exception {
		FlipInfo<WeUserCardCharge> fpi = new FlipPageInfo<WeUserCardCharge>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {

			}
		} else {// 平台管理员
			if (StringUtils.isEmpty((String) fpi.getParams().get("organId"))) {
				String organName = (String) fpi.getParams().get("organName");
				if (!StringUtils.isEmpty(organName)) {
					String organIds = "";
					List<Organ> organLists = cardPayOnlineService.queryOrganList(organName);//得到店面集合
					Account account = tokenManager.getCurrentAccount();//有管理的城市和区域
					List<String> ids = organService.queryAdminOrganIds(account);//查询当前账号可管理的店铺id列表
					for (Organ o : organLists) {
						if (ids.size() == 0 || ids.contains(o.get_id()))
							organIds = organIds + o.get_id() + ",";
					}
					if (!"".equals(organIds)) {//不是空
						organIds = organIds.substring(0, organIds.length() - 1);
						fpi.getParams().remove("organName");
						fpi.getParams().put("in:organId|array", organIds);
					}
					SortedMap a = new TreeMap();
					a.put("s","s");

				}
			}
		}
		fpi = cardPayOnlineService.queryUserCardCharge(fpi);
		// for(WeUserCardCharge card:fpi.getData()){
		// System.out.println(card.get_id());
		// }
		return fpi;
	}

	/**
	 * 处理会员卡充值记录
	 * 
	 * @param cardId
	 * @param only
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dealwith")
	@ResponseBody
	public ReturnStatus dealWith(@RequestParam(required = false) String cardId,
			@RequestParam(required = false) boolean only, @RequestParam(required = false) String stateType,
			@RequestParam(required = false) Integer state, @RequestParam(required = false) Integer organState,
			@RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime,
			@RequestParam(required = false) String organName) throws Exception {
		if (only) {
			return cardPayOnlineService.dealWithOnlyOne(cardId, stateType);
		} else {
			Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
			String organId = "";
			if (isOrganAdmin != null && isOrganAdmin) {
				organId = (String) MAppContext.getSessionVariable("organId");
			}
			return cardPayOnlineService.dealWithAll(organId, stateType, organState, organName, startTime, endTime);
		}
	}

	@RequestMapping("/totalOrgan")
	@ResponseBody
	public CardPayOnlineSum totalOrgan(@RequestParam(required = false) String organName,
			@RequestParam(required = false) Integer state, @RequestParam(required = false) Integer organState,
			@RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime,
			HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String oid = (String) MAppContext.getSessionVariable("organId");
		CardPayOnlineSum cos;
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			cos = cardPayOnlineService.totalCardPayOnline(oid, organState, startTime, endTime, "1");
		} else {
			String organIds = "";
			if (!StringUtils.isEmpty(organName)) {
				List<Organ> organLists = cardPayOnlineService.queryOrganList(organName);
				Account account = tokenManager.getCurrentAccount();//有城市和区域
				List<String> ids = organService.queryAdminOrganIds(account);//in查询城市，区域
				for (Organ o : organLists) {
					if(ids.size() == 0 || ids.contains(o.get_id())){
						organIds = organIds + o.get_id() + ",";
					}
				}
				if (!"".equals(organIds)) {
					organIds = organIds.substring(0, organIds.length() - 1);
				}

			}
			cos = cardPayOnlineService.totalCardPayOnline(organIds, state, startTime, endTime, "2");
		}
		return cos;
	}

	@RequestMapping("/organCard/toQuery")
	public ModelAndView toQueryCard(HttpServletRequest request) throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		List<WeBCity> city = rankservice.cityList();
		request.setAttribute("ffcitys", city);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cardPayController/queryCard");
		return mv;
	}

	@RequestMapping("/organCard/query")
	@ResponseBody
	public FlipInfo<Organ> queryCard(HttpServletRequest request, String city, String district, String region,
			String name) throws Exception {
		FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {

			}
		} else {// 平台管理员
			fpi.getParams().remove("name");
			fpi.getParams().remove("city");
			fpi.getParams().remove("district");
			fpi.getParams().remove("region");
			fpi = cardPayOnlineService.queryOrgan(city, district, region, name, fpi);
		}
		fpi = cardPayOnlineService.queryOrganCardNum(fpi);
		// for(WeUserCardCharge card:fpi.getData()){
		// System.out.println(card.get_id());
		// }
		return fpi;
	}

	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
		// 解决_id字段注入问题，去除“_”前缀处理
		binder.setFieldMarkerPrefix(null);
	}
}
