package com.mrmf.moduleweb.report;

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

import com.mrmf.entity.Bumen;
import com.mrmf.entity.bean.StaffSalary;
import com.mrmf.entity.bean.StaffTichengReport;
import com.mrmf.service.bumen.BumenService;
import com.mrmf.service.report.StaffTichengReportService;
import com.osg.entity.DataEntity;
import com.osg.framework.BaseException;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 员工提成表
 */
@Controller
@RequestMapping("/staffTichengReport")
public class StaffTichengReportController {

	@Autowired
	private StaffTichengReportService staffTichengReportService;

	@Autowired
	private BumenService bumenService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		request.setAttribute("ffbumens", bumenService.findAll(organId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("report/staffTicheng");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public List<StaffTichengReport> queryColligate(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return staffTichengReportService.query(organId, startTime, endTime);
	}
	@RequestMapping("/download")
	public ModelAndView export(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		File template = new File(
				request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_staffTicheng_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));

		List<StaffTichengReport> projects=staffTichengReportService.query(organId, startTime, endTime);
		List<Bumen>  bumens = bumenService.findAll(organId);
		for(StaffTichengReport report:projects){
			for(Bumen bumen:bumens){
				if(bumen.get_id().equals(report.getBumenId())){
					report.setBumenName(bumen.getName());
					break;
				}
			}
		}
		
		
		Map dataSet = new HashMap();
		dataSet.put("staffTicheng", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));

		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "员工提成时间段报表.xls");
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
