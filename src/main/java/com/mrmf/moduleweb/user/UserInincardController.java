package com.mrmf.moduleweb.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.user.incard.IncardService;
import com.mrmf.service.user.inincard.InincardService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 子卡相关
 */
@Controller
@RequestMapping("/user/userInincard")
public class UserInincardController {

	@Autowired
	private InincardService inincardService;

	@Autowired
	private IncardService incardService;

	@Autowired
	private UsersortService usersortService; // 会员类型

	@Autowired
	private SmallsortService smallsortService; // 服务项目
	@Autowired
	private OrganService organService;
	/**
	 * 进入子卡创建界面
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		request.setAttribute("ffusersorts", usersortService.findAll(organId));
		request.setAttribute("ffsmallsorts", smallsortService.findAllValid(organId));

		request.setAttribute("ffincard", incardService.queryById(id));

		ModelAndView mv = new ModelAndView("user/userinincard/upsert");
		return mv;
	}

	/**
	 * 删除会员卡信息
	 * 
	 * @param id
	 * @param danciTui 退卡单次款额
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public ReturnStatus remove(String id, double danciTui) throws Exception {
		return inincardService.remove(id, danciTui);
	}

	/**
	 * 根据会员卡id查询关联的子卡信息列表
	 * 
	 * @param incardId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query")
	@ResponseBody
	public List<Userinincard> query(String incardId) throws Exception {
		return inincardService.query(incardId);
	}
	
	@RequestMapping("/queryByFpi")
	@ResponseBody
	public FlipInfo<Userinincard> queryByFpi(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		FlipInfo<Userinincard> fpi = new FlipPageInfo<Userinincard>(request);
		String date = (String) fpi.getParams().get("eq:createTime|date#and");
		if(!StringUtils.isEmpty(date)){
			fpi.getParams().remove("eq:createTime|date#and");
			String[] startAndEnd = DateUtil.getDateStartEnd(date);
			fpi.getParams().put("gte:createTime|date", startAndEnd[0]);
			fpi.getParams().put("lte:createTime|date+1", startAndEnd[1]);
		}
		String parOrganId = (String) fpi.getParams().get("organId");
		if(StringUtils.isEmpty(parOrganId)){
			fpi.getParams().put("organId", organId); // 只能查询本公司信息
			return inincardService.queryByFpi(fpi);
		}else{
			List<Organ> organList = organService.queryOrganListByParentId(organId);
			boolean flag = false;
			if(organList!=null&&organList.size()>0){
				for(Organ organ : organList){
					if(parOrganId.equals(organ.get_id())){
						flag = true;
						break;
					}
				}
				if(flag){
					return inincardService.queryByFpi(fpi);
				}
			}
		}
		return fpi;
		
	}
	@RequestMapping("/download")
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		FlipInfo<Userinincard> fpi = new FlipPageInfo<Userinincard>(request);
		String date = (String) fpi.getParams().get("eq:createTime|date#and");
		List<Userinincard> list=null;
		if(!StringUtils.isEmpty(date)){
			fpi.getParams().remove("eq:createTime|date#and");
			String[] startAndEnd = DateUtil.getDateStartEnd(date);
			fpi.getParams().put("gte:createTime|date", startAndEnd[0]);
			fpi.getParams().put("lte:createTime|date+1", startAndEnd[1]);
		}
		String parOrganId = (String) fpi.getParams().get("organId");
		if(StringUtils.isEmpty(parOrganId)){
			fpi.getParams().put("organId", organId); // 只能查询本公司信息
			list = inincardService.queryInCard(fpi);
		}else{
			List<Organ> organList = organService.queryOrganListByParentId(organId);
			boolean flag = false;
			if(organList!=null&&organList.size()>0){
				for(Organ organ : organList){
					if(parOrganId.equals(organ.get_id())){
						flag = true;
						break;
					}
				}
				if(flag){
					list =  inincardService.queryInCard(fpi);
				}
			}
		}
		File template = new File(
				request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_userpart_export_zika.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));

		// 18-1-12补丁  修改导出换算金额
		for(Userinincard userinincard : list){
			userinincard.setHuansuanMoney(userinincard.getShengcishu()*userinincard.getDanci_money());
		}
		Map dataSet = new HashMap();
		dataSet.put("incard", JsonUtils.fromJson(JsonUtils.toJson(list), List.class));

		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "卡中卡明细.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 新建子卡信息
	 * 
	 * @param userpart
	 * @param results
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public ReturnStatus insert(Userpart userpart, BindingResult results, HttpServletRequest request) {
		return inincardService.insert(userpart);
	}

	/**
	 * 新增子卡消费信息
	 * 
	 * @param userpart
	 * @param results
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertXiaofei")
	@ResponseBody
	public ReturnStatus insertXiaofei(Userpart userpart, BindingResult results, HttpServletRequest request) {
		return inincardService.insertXiaofei(userpart);
	}

	/**
	 * 删除子卡消费信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/removeXiaofei")
	@ResponseBody
	public ReturnStatus removeXiaofei(String id) throws Exception {
		return inincardService.removeXiaofei(id);
	}

	/**
	 * 查询未结账的子卡消费记录
	 * 
	 * @param inincardId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUserpart")
	@ResponseBody
	public List<Userpart> queryUserpart(String inincardId) throws Exception {
		return inincardService.queryUserpart(inincardId);
	}

	/**
	 * 子卡消费结账
	 * 
	 * @param inincardId
	 * @param xiaocishu
	 * @param xianjin
	 * @param money_cash
	 * @param money3
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jiezhang")
	@ResponseBody
	public ReturnStatus jiezhang(String inincardId, @RequestParam(required = false) String xiaocishu,
			@RequestParam(required = false) String xianjin, @RequestParam(required = false) String money_cash,
			@RequestParam(required = false) String money3, String passwd) throws Exception {
		int xiaocishu2 = 0;
		if (!StringUtils.isEmpty(xiaocishu)) {
			xiaocishu2 = Integer.parseInt(xiaocishu);
		}
		double xianjin2 = 0;
		if (!StringUtils.isEmpty(xianjin)) {
			xianjin2 = Double.parseDouble(xianjin);
		}
		double money_cash2 = 0;
		if (!StringUtils.isEmpty(money_cash)) {
			money_cash2 = Double.parseDouble(money_cash);
		}
		double money32 = 0;
		if (!StringUtils.isEmpty(money3)) {
			money32 = Double.parseDouble(money3);
		}
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		return inincardService.jiezhang(inincardId, xiaocishu2, xianjin2, money_cash2, money32, passwd);
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
