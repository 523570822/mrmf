package com.mrmf.moduleweb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Organ;
import com.mrmf.entity.OrganSetting;
import com.mrmf.entity.kucun.StockControlSum;
import com.mrmf.entity.kucun.WInstoreroom;
import com.mrmf.entity.kucun.WOutstoreroom;
import com.mrmf.entity.kucun.WPinpai;
import com.mrmf.entity.kucun.WStoreroom;
import com.mrmf.entity.kucun.WStoretuihuo;
import com.mrmf.entity.kucun.WWupin;
import com.mrmf.service.bumen.BumenService;
import com.mrmf.service.kucun.KucunService;
import com.mrmf.service.organ.OrganService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 库存控制类
 * 
 * @author zhanglijie
 */
@Controller
@RequestMapping("/kucun")
public class KucunController {
	@Autowired
	private KucunService kucunService;
	@Autowired
	private BumenService bumenService;
	@Autowired
	private OrganService organService;

	/**
	 * 进入品牌列表页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pinpai/toQuery")
	public ModelAndView toPinpaiQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/pinpai/query");
		return mv;
	}

	/**
	 * 查询品牌列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pinpai/query")
	@ResponseBody
	public FlipInfo<WPinpai> pinpaiquery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WPinpai> fpi = new FlipPageInfo<WPinpai>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", parentId);
				}

			}
		}
		fpi = kucunService.queryOrganPinpai(fpi);
		return fpi;
	}

	/**
	 * 更新品牌信息
	 * 
	 * @param organId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pinpai/toUpsert")
	public ModelAndView toUpsertPinpai(
			@RequestParam(required = false) String pinpaiId,
			HttpServletRequest request) throws Exception {
		WPinpai pinpai;
		if (!StringUtils.isEmpty(pinpaiId)) {
			// bumen = bumenService.queryById(pinpaiId);
			pinpai = kucunService.queryWPinpaiById(pinpaiId);
		} else {
			pinpai = new WPinpai();
			String organId = (String) MAppContext.getSessionVariable("organId");
			String parentId = kucunService.queryParentOrganId(organId);
			pinpai.setOrganId(parentId);
		}

		request.setAttribute("ffpinpai", pinpai);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/pinpai/upsert");
		return mv;
	}

	/**
	 * 修改或者保存品牌
	 * 
	 * @param bumen
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pinpai/upsert")
	public ModelAndView pinpaiupsert(WPinpai pinpai, BindingResult results,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// String pinpaiId = pinpai.get_id();
		ReturnStatus status = kucunService.upsertPinpai(pinpai);
		if (status.isSuccess()) {
			mv = toPinpaiQuery(request);
		} else {
			// bumen.set_id(originId);// 恢复之前的id
			request.setAttribute("ffpinpai", pinpai);
			request.setAttribute("returnStatus", status);
			mv.setViewName("kucun/pinpai/upsert");
		}

		return mv;
	}

	/**
	 * 删除品牌
	 * 
	 * @param pinpaiId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pinpai/remove/{pinpaiId}")
	@ResponseBody
	public ReturnStatus removePinpai(@PathVariable String pinpaiId)
			throws Exception {
		return kucunService.removePinpai(pinpaiId);
	}

	/**
	 * 进入类别列表页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/leibie/toQuery")
	public ModelAndView toLeibieQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if(!parentId.equals(organId)){
			request.setAttribute("filiale", true);
		}
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/leibie/query");
		return mv;
	}

	/**
	 * 查询物品类别列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/leibie/query")
	@ResponseBody
	public FlipInfo<WWupin> leiBiequery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WWupin> fpi = new FlipPageInfo<WWupin>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", parentId);
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
				}

			}
		}
		fpi = kucunService.queryOrganWupin(fpi);
		return fpi;
	}

	/**
	 * 更新类别信息
	 * 
	 * @param organId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/leibie/toUpsert")
	public ModelAndView toUpsertLeibie(
			@RequestParam(required = false) String leibieId,
			HttpServletRequest request) throws Exception {
		WWupin leibie;
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if (!StringUtils.isEmpty(leibieId)) {
			// bumen = bumenService.queryById(pinpaiId);
			leibie = kucunService.queryWWupinById(leibieId);
		} else {
			leibie = new WWupin();
			leibie.setOrganId(parentId);
		}
		request.setAttribute("codes", kucunService.queryCodeList("wupinUnit"));
		request.setAttribute("pinpais", kucunService.queryPinPaiList(parentId));
		request.setAttribute("bumens", bumenService.findAll(parentId));
		request.setAttribute("ffleibie", leibie);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/leibie/upsert");
		return mv;
	}

	/**
	 * 添加或修改物品类别
	 * 
	 * @param wupin
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/leibie/upsert")
	public ModelAndView leibieupsert(WWupin wupin, BindingResult results,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// String pinpaiId = pinpai.get_id();
		ReturnStatus status = kucunService.upsertWupin(wupin);
		if (status.isSuccess()) {
			mv = toLeibieQuery(request);
		} else {
			// bumen.set_id(originId);// 恢复之前的id
			request.setAttribute("ffleibie", wupin);
			request.setAttribute("returnStatus", status);
			mv.setViewName("kucun/leibie/upsert");
		}

		return mv;
	}

	/**
	 * 删除类别信息
	 * 
	 * @param leibieId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/leibie/remove/{leibieId}")
	@ResponseBody
	public ReturnStatus removeLiebie(@PathVariable String leibieId)
			throws Exception {
		return kucunService.removeLeibie(leibieId);
	}

	/**
	 * 跳转到库存查询页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/kucun/toQuery")
	public ModelAndView toKucunQuery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WPinpai> fpi = new FlipPageInfo<WPinpai>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", parentId);
				}
			}
		}

		ModelAndView mv = new ModelAndView();
		fpi = kucunService.queryOrganPinpai(fpi);
		request.setAttribute("ffpinpai", fpi.getData());
		mv.setViewName("kucun/kucun/query");
		return mv;
	}

	/**
	 * 库存列表查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/kucun/query")
	@ResponseBody
	public FlipInfo<WStoreroom> kucunquery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WStoreroom> fpi = new FlipPageInfo<WStoreroom>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					fpi.getParams().remove("shenhe|boolean");
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", organId);
					fpi.getParams().remove("sg_pinpai");
					fpi.getParams().remove("sg_wupinId");
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
				}

			}
		}
		fpi = kucunService.queryOrganWStoreroom(fpi);
		return fpi;
	}

	@RequestMapping("/ruku/toQuery")
	public ModelAndView toRukuQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if(parentId.equals(organId)){
			request.setAttribute("HQ", true);
		}
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/ruku/query");
		return mv;
	}

	/**
	 * 入库查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ruku/query")
	@ResponseBody
	public FlipInfo<WInstoreroom> rukuquery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WInstoreroom> fpi = new FlipPageInfo<WStoreroom>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", organId);
					fpi.getParams().put("delete_flag|boolean", "false");
					fpi.getParams().remove("sg_wupinId");
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
				}

			}
		}
		fpi = kucunService.queryOrganWInstoreroom(fpi);
		return fpi;
	}

	@RequestMapping("/ruku/upsert")
	public ModelAndView rukuupsert(WInstoreroom instoreroom,
			BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// String pinpaiId = pinpai.get_id();
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if (organId.equals(parentId)) {
			instoreroom.setParentOrganId("0");
		} else {
			instoreroom.setParentOrganId(parentId);
		}
		instoreroom.setOrganId(organId);
		OrganSetting organSetting = organService.querySetting(organId);
		if (organSetting.getRukuShenhe()) {
			instoreroom.setShenhe(false);
		} else {
			instoreroom.setShenhe(true);
		}
		instoreroom.setDelete_flag(false);

		ReturnStatus status = kucunService.upsertWInstoreroom(instoreroom);
		if (status.isSuccess()) {
			mv = toRukuQuery(request);
		} else {
			// bumen.set_id(originId);// 恢复之前的id
			request.setAttribute("ffruku", instoreroom);
			request.setAttribute("returnStatus", status);
			mv.setViewName("kucun/ruku/upsert");
		}

		return mv;
	}

	@RequestMapping("/ruku/toUpsert")
	public ModelAndView toUpsertRuku(
			@RequestParam(required = false) String rukuId,
			HttpServletRequest request) throws Exception {
		WInstoreroom ruku;
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if (!StringUtils.isEmpty(rukuId)) {
			// bumen = bumenService.queryById(pinpaiId);
			ruku = kucunService.queryWInstoreroomById(rukuId);
		} else {
			ruku = new WInstoreroom();
			ruku.setOrganId(organId);
			Organ organ = organService.queryById(organId);
			ruku.setDanhao(organ.getAb() + "_" + getDanhao(4));
		}
		request.setAttribute("codes", kucunService.queryCodeList("wupinUnit"));
		request.setAttribute("pinpais", kucunService.queryPinPaiList(parentId));
		request.setAttribute("bumens", bumenService.findAll(organId));
		if (organId.equals(parentId)) {
			request.setAttribute("ffleibies",
					kucunService.queryWWupinList(parentId));
		} else {
			FlipInfo<WWupin> fpi = new FlipInfo<WWupin>();
			List<WWupin> list = kucunService.queryWWupinList(parentId);
			fpi.setData(list);
			fpi = changePrice_xs(fpi, organId);
			request.setAttribute("ffleibies", fpi.getData());
		}

		request.setAttribute("ffruku", ruku);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/ruku/upsert");
		return mv;
	}

	/**
	 * 删除入库信息
	 * 
	 * @param rukuId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ruku/remove/{rukuId}")
	@ResponseBody
	public ReturnStatus removeRuku(@PathVariable String rukuId)
			throws Exception {
		return kucunService.removeRuku(rukuId);
	}

	/**
	 * 跳转到入库审核页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rukushenhe/toQuery")
	public ModelAndView toRukuShenHeQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId=kucunService.queryParentOrganId(organId);
		if(parentId.equals(organId)){
			request.setAttribute("HQ", true);
		}
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/rukushenhe/query");
		return mv;
	}

	/**
	 * 入库审核查询入库待审核信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rukushenhe/query")
	@ResponseBody
	public FlipInfo<WInstoreroom> rukushenhequery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WInstoreroom> fpi = new FlipPageInfo<WStoreroom>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", organId);
					fpi.getParams().put("shenhe|boolean", "false");
					fpi.getParams().put("delete_flag|boolean", "false");
				}

			}
		}
		fpi = kucunService.queryOrganWInstoreroom(fpi);
		return fpi;
	}

	/**
	 * 入库审核
	 * 
	 * @param rukuId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ruku/check/{rukuId}/{returnFlag}")
	@ResponseBody
	public ReturnStatus checkRuku(@PathVariable String rukuId,@PathVariable Boolean returnFlag) throws Exception {
		return kucunService.checkRuku(rukuId,returnFlag);
	}

	/**
	 * 跳转到出库查询页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chuku/toQuery")
	public ModelAndView toChukuQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/chuku/query");
		return mv;
	}

	/**
	 * 查询出库列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chuku/query")
	@ResponseBody
	public FlipInfo<WOutstoreroom> chukuquery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WOutstoreroom> fpi = new FlipPageInfo<WOutstoreroom>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", organId);
					fpi.getParams().put("delete_flag|boolean", "false");
					fpi.getParams().remove("sg_wupinId");
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
				}

			}
		}
		fpi = kucunService.queryOrganWOutstoreroom(fpi);
		return fpi;
	}

	/**
	 * 添加出库信息
	 * 
	 * @param outstoreroom
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chuku/upsert")
	public ModelAndView chukuupsert(WOutstoreroom outstoreroom,
			BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// String pinpaiId = pinpai.get_id();
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if (organId.equals(parentId)) {
			outstoreroom.setParentOrganId("0");
		} else {
			outstoreroom.setParentOrganId(parentId);
		}
		outstoreroom.setOrganId(organId);
		OrganSetting organSetting = organService.querySetting(organId);
		if (organSetting.getChukuShenhe()) {
			outstoreroom.setShenhe(false);
		} else {
			outstoreroom.setShenhe(true);
		}
		outstoreroom.setDelete_flag(false);

		ReturnStatus status = kucunService.upsertWOutstoreroom(outstoreroom);
		if (status.isSuccess()) {
			mv = toChukuQuery(request);
		} else {
			// bumen.set_id(originId);// 恢复之前的id
			// request.setAttribute("ffruku", instoreroom);
			request.setAttribute("returnStatus", status);
			mv.setViewName("kucun/chuku/upsert");
		}

		return mv;
	}

	/**
	 * 进入出库页面
	 * 
	 * @param chukuId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chuku/toUpsert")
	public ModelAndView toUpsertChuku(
			@RequestParam(required = false) String chukuId,
			HttpServletRequest request) throws Exception {
		WOutstoreroom chuku;
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		String organType = "BRANCH";
		if (organId.equals(parentId)) {
			organType = "HQ";
			request.setAttribute("branch", kucunService.branch(parentId));
		}
		request.setAttribute("organType", organType);
		if (!StringUtils.isEmpty(chukuId)) {
			// bumen = bumenService.queryById(pinpaiId);
			chuku = kucunService.queryWOutstoreroomById(chukuId);
		} else {
			chuku = new WOutstoreroom();
			chuku.setOrganId(organId);
			Organ organ = organService.queryById(organId);
			chuku.setDanhao(organ.getAb() + "_" + getDanhao(4));
		}
		request.setAttribute("codes", kucunService.queryCodeList("wupinUnit"));
		request.setAttribute("pinpais", kucunService.queryPinPaiList(parentId));
		request.setAttribute("bumens", bumenService.findAll(parentId));
		if (organId.equals(parentId)) {
			request.setAttribute("ffleibies",
					kucunService.queryWWupinList(parentId));
		} else {
			FlipInfo<WWupin> fpi = new FlipInfo<WWupin>();
			List<WWupin> list = kucunService.queryWWupinList(parentId);
			fpi.setData(list);
			fpi = changePrice_xs(fpi, organId);
			request.setAttribute("ffleibies", fpi.getData());
		}
		request.setAttribute("ffchuku", chuku);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/chuku/upsert");
		return mv;
	}

	/**
	 * 删除出库信息
	 * 
	 * @param chukuId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chuku/remove/{chukuId}")
	@ResponseBody
	public ReturnStatus removeChuku(@PathVariable String chukuId)
			throws Exception {
		return kucunService.removeChuku(chukuId);
	}

	/**
	 * 进入出库审核页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chukushenhe/toQuery")
	public ModelAndView toChukuShenHeQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/chukushenhe/query");
		return mv;
	}

	/**
	 * 查询出库待审核的出库信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chukushenhe/query")
	@ResponseBody
	public FlipInfo<WOutstoreroom> chukushenhequery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WOutstoreroom> fpi = new FlipPageInfo<WOutstoreroom>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", organId);
					fpi.getParams().put("shenhe|boolean", "false");
					fpi.getParams().put("delete_flag|boolean", "false");
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
				}

			}
		}
		fpi = kucunService.queryOrganWOutstoreroom(fpi);
		return fpi;
	}

	@RequestMapping("/chuku/check/{chukuId}")
	@ResponseBody
	public ReturnStatus checkChuku(@PathVariable String chukuId)
			throws Exception {
		return kucunService.checkChuku(chukuId);
	}

	/**
	 * 跳转到入库核对页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hedui/toQuery")
	public ModelAndView toHeduiQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/hedui/query");
		return mv;
	}

	@RequestMapping("/hedui/query")
	@ResponseBody
	public FlipInfo<WOutstoreroom> heduiquery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WOutstoreroom> fpi = new FlipPageInfo<WOutstoreroom>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("danwei", organId);
					fpi.getParams().put("shenhe|boolean", "true");
					fpi.getParams().put("shenhe_fen|boolean", "false");
					fpi.getParams().put("delete_flag|boolean", "false");
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
				}

			}
		}
		fpi = kucunService.queryOrganWOutstoreroom(fpi);
		return fpi;
	}

	/**
	 * 子公司入库核对
	 * 
	 * @param heduiId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hedui/check/{heduiId}")
	@ResponseBody
	public ReturnStatus checkHedui(@PathVariable String heduiId)
			throws Exception {
		return kucunService.checkHedui(heduiId);
	}

	/**
	 * 进入子公司类别查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/childCategory/toQuery")
	public ModelAndView toChildLeibieQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if(!parentId.equals(organId)){
			request.setAttribute("filiale", true);
		}
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/childleibie/query");
		return mv;
	}

	/**
	 * 查询子公司类别
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/childCategory/query")
	@ResponseBody
	public FlipInfo<WWupin> childleiBiequery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WWupin> fpi = new FlipPageInfo<WWupin>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					String parentId = kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", parentId);
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
				}

			}
		}
		fpi = kucunService.queryOrganWupin(fpi);
		fpi = changePrice_xs(fpi, organId);
		return fpi;
	}

	/**
	 * 子公司修改类别价格
	 * 
	 * @param leibieId
	 * @param pricexs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/childleibie/update")
	@ResponseBody
	public ReturnStatus childLeiBieUpdate(String leibieId, Double pricexs)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		return kucunService.childLeiBieUpdate(leibieId, pricexs, organId);
	}

	/**
	 * 如果是子公司就要转换价格
	 * 
	 * @param fpi
	 * @param organId
	 * @return
	 */
	public FlipInfo<WWupin> changePrice_xs(FlipInfo<WWupin> fpi, String organId) {
		for (WWupin wupin : fpi.getData()) {
			Map<String, Double> price = wupin.getPrice_xss();
			if (price != null) {
				Iterator<String> keys = price.keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					if (organId.equals(key)) {
						Double value = price.get(key);
						wupin.setPrice_xs(value);
						break;
					}
				}
			}
		}
		return fpi;
	}

	@RequestMapping("/stockcontrol/toQuery")
	public ModelAndView toStockControlQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		if(!parentId.equals(organId)){
			request.setAttribute("filiale", true);
		}
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		request.setAttribute("ffleibies",kucunService.queryWWupinList(parentId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/stockcontrol/query");
		return mv;
	}

	@RequestMapping("/tuihuo/toQuery")
	public ModelAndView totuihuoQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kucun/tuihuo/query");
		return mv;
	}

	@RequestMapping("/tuihuo/query")
	@ResponseBody
	public FlipInfo<WStoretuihuo> tuihuoquery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WStoretuihuo> fpi = new FlipPageInfo<WStoretuihuo>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils
						.isEmpty((String) fpi.getParams().get("organId"))) {
					// String parentId =
					// kucunService.queryParentOrganId(organId);
					fpi.getParams().put("organId", organId);
					fpi.getParams().remove("sg_wupinId");
					fpi.setSortField("createTime");
					fpi.setSortOrder("DESC");
					fpi.getParams().remove("shenhe|boolean");
				}

			}
		}
		fpi = kucunService.queryWStoretuihuo(fpi);
		return fpi;
	}

	/**
	 * 库存管理汇总
	 * 
	 * @param wupinId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stockcontrolSum/total")
	@ResponseBody
	public StockControlSum stockcontrolSum(String wupinId,
			HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {

			}
		}
		// return weUserPayFenzhangService.totalOrgan(organId, state, startTime,
		// endTime);
		return kucunService.stockSum(organId, wupinId, true);
	}

	@RequestMapping("/tuihuo/upsert")
	@ResponseBody
	public ReturnStatus tuihuoUpsert(String code, Double num,Integer reAddr)
			throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		return kucunService.upsertWStoretuihuo(organId,code, num,reAddr);
	}

	public static String getDanhao(int n) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String danhao = format.format(date);
		if (n < 1 || n > 10) {
			throw new IllegalArgumentException("cannot random " + n
					+ " bit number");
		}
		Random ran = new Random();
		if (n == 1) {
			return String.valueOf(ran.nextInt(10));
		}
		int bitField = 0;
		char[] chs = new char[n];
		for (int i = 0; i < n; i++) {
			while (true) {
				int k = ran.nextInt(10);
				if ((bitField & (1 << k)) == 0) {
					bitField |= 1 << k;
					chs[i] = (char) (k + '0');
					break;
				}
			}
		}
		return danhao + new String(chs);
	}

	@InitBinder
	public void InitBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
				dateFormat, true));
		// 解决_id字段注入问题，去除“_”前缀处理
		binder.setFieldMarkerPrefix(null);
	}
}
