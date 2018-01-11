package com.mrmf.moduleweb.weixin;

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

import com.mongodb.util.Hash;
import com.mrmf.entity.Organ;
import com.mrmf.entity.WeCarousel;
import com.mrmf.entity.WeSysConfig;
import com.mrmf.entity.WeUserCompensate;
import com.mrmf.entity.WeUserFeedback;
import com.mrmf.service.wesysconfig.WeSysConfigService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;

/**
 * 系统业务参数设置相关
 */
@Controller
@RequestMapping("/weixin/sysConfig")
public class WeSysConfigController {

	@Autowired
	private WeSysConfigService weSysConfigService;

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(HttpServletRequest request) throws Exception {
		request.setAttribute("ffconfig", weSysConfigService.query());

		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/sysConfig/upsert");
		return mv;
	}

	/**
	 * 保存参数功能
	 * @param config 参数类型
	 * @param results  参数值
	 * @param request request
	 * @return mv
	 * @throws Exception e
	 */
	@RequestMapping("/upsert")
	public ModelAndView upsert(WeSysConfig config, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		ReturnStatus status = weSysConfigService.upsert(config);
		if (status.isSuccess()) {
			mv = toUpsert(request);
		} else {
			request.setAttribute("ffconfig", config);
			request.setAttribute("returnStatus", status);
			mv.setViewName("weixin/sysConfig/upsert");
		}

		return mv;
	}
	
	@RequestMapping("/toCarousel")
	public ModelAndView carousel(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/carousel/carousel");
		return mv;
	}
	@RequestMapping("/addCarouselImg")
	@ResponseBody
	public Boolean addCarouselImg(HttpServletRequest request){
		String imgId = request.getParameter("imgId");
		return weSysConfigService.addCarouselImg(imgId);
	}
	@RequestMapping("/queryCarouselImg")
	@ResponseBody
	public FlipInfo<WeCarousel> queryCarouselImg(HttpServletRequest request){
		FlipInfo<WeCarousel> fpi = new FlipPageInfo<WeCarousel>(request);
		fpi = weSysConfigService.queryCarouselImg(fpi);
		return fpi;
	}
	@RequestMapping("/deleteCarouselImg")
	public ModelAndView deleteCarouselImg(@RequestParam String carouselId,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/carousel/carousel");
		weSysConfigService.deleteCarouselImg(carouselId);
		return mv;
    }
	@RequestMapping("/toUserFeedBack")
	public ModelAndView toUserFeedBack(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/feedback/queryUser");
		return mv;
    }
	@RequestMapping("/toStaffFeedBack")
	public ModelAndView toStaffFeedBack(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/feedback/queryStaff");
		return mv;
	}
	@RequestMapping("/toOrganFeedBack")
	public ModelAndView toOrganFeedBack(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/feedback/queryOrgan");
		return mv;
	}
	@RequestMapping("/queryFeedBacks")
	@ResponseBody
	public FlipInfo<WeUserFeedback> queryFeedBacks(HttpServletRequest request){
		FlipInfo<WeUserFeedback> feedBacks = new FlipPageInfo<WeUserFeedback>(request);
		feedBacks = weSysConfigService.findFeedBacks(feedBacks);
		return feedBacks;
	}
	@RequestMapping("/toCompensate")
	public ModelAndView toCompensate(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/compensate/query");
		return mv;
	}
	@RequestMapping("/toCompensateDetail")
	public ModelAndView toCompensateDetail(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/compensate/compensateDetail");
		String compensateId =  request.getParameter("compensateId");
		WeUserCompensate weUserCompensate = weSysConfigService.findCompensateById(compensateId);
		request.setAttribute("ffweUserCompensate", weUserCompensate);
		mv.addObject("state", weUserCompensate.getState());
		return mv;
	}
	
	@RequestMapping("/queryCompensate")
	@ResponseBody
	public FlipInfo<WeUserCompensate> queryCompensate(HttpServletRequest request){
		FlipInfo<WeUserCompensate> compensateFiFlipInfo = new FlipPageInfo<WeUserCompensate>(request);
		compensateFiFlipInfo = weSysConfigService.findCompensates(compensateFiFlipInfo);
		return compensateFiFlipInfo;
	}
	
	//upsertCompensate
	@RequestMapping("/upsertCompensate")
	public ModelAndView upsertCompensate(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("weixin/compensate/query");
		String result =  request.getParameter("result");
		String resultDesc =  request.getParameter("resultDesc");
		String compensateId = request.getParameter("_id");
		if(!StringUtils.isEmpty(result) && !StringUtils.isEmpty(resultDesc) && !StringUtils.isEmpty(compensateId)) {
		     weSysConfigService.saveCompensate(compensateId,result,resultDesc);
		}
		return mv;
	}
	
	@RequestMapping("/exportUserFeed")
	public ModelAndView exportUserFeed(String userName,@RequestParam(required=false)Date startTime,@RequestParam(required=false)Date endTime,HttpServletRequest request,HttpServletResponse response)throws Exception{
		File template=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_userFeed_export.xls"));
		File outputExcel=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/tmp/"+ DataEntity.getLongUUID()+".xls"));
		String type="user";
		List<WeUserFeedback> projects=weSysConfigService.exportUserFeed(userName,startTime,endTime,type);
		Map dataSet=new HashMap();
		dataSet.put("user", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));
		try{
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "用户意见反馈信息表.xls");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;		
	}
	@RequestMapping("/exportStaffFeed")
	public ModelAndView exportStaffFeed(String staffName,@RequestParam(required=false)Date startTime,@RequestParam(required=false)Date endTime,HttpServletRequest request,HttpServletResponse response)throws Exception{
		File template=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_userFeed_export.xls"));
		File outputExcel=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/tmp/"+ DataEntity.getLongUUID()+".xls"));
		String type="staff";
		List<WeUserFeedback> projects=weSysConfigService.exportUserFeed(staffName,startTime,endTime,type);
		Map dataSet=new HashMap();
		dataSet.put("user", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));
		
		try{
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet,type);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "技师意见反馈信息表.xls");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;		
	}
	@RequestMapping("/exportOrganFeed")
	public ModelAndView exportOrganFeed(String staffName,@RequestParam(required=false)Date startTime,@RequestParam(required=false)Date endTime,HttpServletRequest request,HttpServletResponse response)throws Exception{
		File template=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_userFeed_export.xls"));
		File outputExcel=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/tmp/"+ DataEntity.getLongUUID()+".xls"));
		String type="organ";
		List<WeUserFeedback> projects=weSysConfigService.exportUserFeed(staffName,startTime,endTime,type);
		Map dataSet=new HashMap();
		dataSet.put("user", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));
		
		try{
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet,type);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "店铺意见反馈信息表.xls");
		}catch(Exception e){
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
