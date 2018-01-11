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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.bean.StaffSalary;
import com.mrmf.entity.bean.UserpartCrossOrganSum;
import com.mrmf.entity.user.UserpartCrossOrgan;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.user.crossOrgan.UserpartCrossOrganService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 跨店消费店面间对账查询、处理
 */
@Controller
@RequestMapping("/user/userpartCrossOrgan")
public class UserpartCrossOrganController {

	@Autowired
	private UserpartCrossOrganService userpartCrossOrganService;

	@Autowired
	private OrganService organService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		request.setAttribute("organList", organService.queryAllPartner(organId));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/crossOrgan");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<UserpartCrossOrgan> query(HttpServletRequest request) throws Exception {
		FlipInfo<UserpartCrossOrgan> fpi = new FlipPageInfo<UserpartCrossOrgan>(request);
		/*
		 * Boolean isOrganAdmin = (Boolean)
		 * MAppContext.getSessionVariable("isOrganAdmin"); String organId =
		 * (String) MAppContext.getSessionVariable("organId"); if (isOrganAdmin
		 * != null && isOrganAdmin) { // 企业管理员 if (StringUtils.isEmpty(organId))
		 * { throw new BaseException("当前登录企业信息缺失！"); } }
		 */
		fpi = userpartCrossOrganService.query(fpi);
		return fpi;
	}

	@RequestMapping("/totalHandle")
	@ResponseBody
	public UserpartCrossOrganSum totalHandle(@RequestParam(required = false) Integer status,
			@RequestParam(required = false) Integer ownerStatus, @RequestParam(required = false) String organId,
			@RequestParam(required = false) String ownerOrganId, @RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {

		return userpartCrossOrganService.totalHandle(status, ownerStatus, organId, ownerOrganId, startTime, endTime);
	}

	@RequestMapping("/handle")
	@ResponseBody
	public ReturnStatus handle(@RequestParam(required = false) Integer status,
			@RequestParam(required = false) Integer ownerStatus, @RequestParam(required = false) String organId,
			@RequestParam(required = false) String ownerOrganId, @RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, boolean isOwner, HttpServletRequest request)
					throws Exception {
		
		return userpartCrossOrganService.handle(status, ownerStatus, organId, ownerOrganId, startTime, endTime,
				isOwner);
	}
	@RequestMapping("/download")
	public ModelAndView export(@RequestParam(required = false) Integer status,
			@RequestParam(required = false) Integer ownerStatus, @RequestParam(required = false) String organId,
			@RequestParam(required = false) String ownerOrganId, @RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request,HttpServletResponse response) throws Exception {
		FlipInfo<UserpartCrossOrgan> fpi = new FlipPageInfo<UserpartCrossOrgan>(request);
		File template = new File(
				request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_crossorgan_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		UserpartCrossOrganSum sum = userpartCrossOrganService.totalHandle(status, ownerStatus, organId, ownerOrganId, startTime, endTime);
		List<UserpartCrossOrgan> projects=userpartCrossOrganService.queryList(fpi);

		Map dataSet = new HashMap();
		dataSet.put("crossorgan", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));
		dataSet.put("sum", sum.getTotalAmount());
		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "跨机构消费对账.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
