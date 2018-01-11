package com.mrmf.moduleweb.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
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

import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.report.ColligateService;
import com.mrmf.service.report.DailyIncomeService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.user.bigsort.BigsortService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.DataEntity;
import com.osg.framework.BaseException;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 店面综合业务报表
 */
@Controller
@RequestMapping("/colligateReport")
public class ColligateController {

	@Autowired
	private ColligateService colligateService;

	@Autowired
	private UsersortService usersortService; // 会员类型

	@Autowired
	private BigsortService bigsortService; // 服务大类

	@Autowired
	private SmallsortService smallsortService; // 服务项目

	@Autowired
	private StaffService staffService;

	@Autowired
	private DailyIncomeService dailyIncomeService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		// request.setAttribute("ffusersorts",
		// usersortService.findAll(organId));
		request.setAttribute("ffbigsorts", bigsortService.findAll(organId));
		request.setAttribute("ffsmallsorts", smallsortService.findAll(organId));
		// request.setAttribute("ffstaffs", staffService.findAll(organId));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("report/colligate");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public List<Userpart> query(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return colligateService.query(organId, startTime, endTime);
	}

	@RequestMapping("/queryWaimai")
	@ResponseBody
	public List<WWaimai> queryWaimai(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return dailyIncomeService.queryWaimai(organId, startTime, endTime);
	}
	@RequestMapping("/download")
	public ModelAndView export( @RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		File template = new File(
				request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_colligateReport_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		
		//加载外卖记录
		List<WWaimai> waimais =  dailyIncomeService.queryWaimai(organId, startTime, endTime);
		List<String> waimaiId = new ArrayList<String>();
		int waimaiYejiTotal = 0;   //外卖业绩
		int waimaiCount = 0;      //外卖客数
		for(WWaimai ww:waimais){
			waimaiYejiTotal += ww.getMoney1();
			if(!waimaiId.contains(ww.getKaidanId())){
				waimaiId.add(ww.getKaidanId());
			}
		}
		waimaiCount = waimaiId.size();
		//加载全部消费记录
		List<Userpart> projects=colligateService.query(organId, startTime, endTime);
		double xjyj1=0,xjyj=0,skyj=0,zje=0,zhkyj=0, ldyj=0, ldyjlj =0, xjlj =0, sklj =0, ljzje =0,   ljhk =0, wmks =0, wmyj =0, xkkje =0,  xkje =0, zkj = 0;
		int zrs=0,zdnank=0,zdnvk=0,fzdnank=0,fzdnvk=0 ,ljzrs =0,nanklj =0, nvklj =0,xkkks =0, xkks =0;
		Map<String,Double> smallsorts = new HashMap<String,Double>();
		Map<String,Integer> smallsortsCount = new HashMap<String,Integer>();
		for(Userpart up:projects){
			if((up.getType()==0)&&!(up.getMiandan())){
				xjyj1 += up.getMoney2();
			}
			if("1002".equals(up.getUsersortType())){
				xjyj += up.getMoney_xiaofei()+up.getMoney2();
			}else {
				xjyj += up.getMoney2();
			}
			//skyj += d.money_xiaofei;
			skyj += up.getMoney_yinhang_money();
			//zje += d.money2+ d.money_xiaofei;
			zje += up.getMoney2()+up.getMoney_xiaofei();
			zrs++;
			if (up.getDian1()
					|| up.getDian2()
					|| up.getDian3()) { // 点活
				if ("男".equals(up.getSex()))
					zdnank++;
				else
					zdnvk++;
			} else { // 非点
				if ("男".equals(up.getSex()))
					fzdnank++;
				else
					fzdnvk++;
			}
			if (!StringUtils.isEmpty(up.getSmallsort())) {
				if (!smallsorts.containsKey(up.getSmallsort())) {
					//smallsorts[d.smallsort] = 0;
					smallsorts.put(up.getSmallsort(), (double) 0);
				}
				if (!smallsortsCount.containsKey(up.getSmallsort())) {
					//smallsortsCount[d.smallsort] = 0;
					smallsortsCount.put(up.getSmallsort(), 0);
				}

				if (up.getType()==0
						&&StringUtils.isEmpty(up.getIncardId())) { // 非会员消费
					smallsorts.put(up.getSmallsort(), smallsorts.get(up.getSmallsort())+up.getMoney2());
					//smallsorts[d.smallsort] += d.money2;
					//smallsortsCount[d.smallsort]++;
					smallsortsCount.put(up.getSmallsort(), smallsortsCount.get(up.getSmallsort())+1);
				} else if (up.getType()==1) { // 会员卡消费
					//smallsorts[d.smallsort] += d.money_xiaofei;
					smallsorts.put(up.getSmallsort(), smallsorts.get(up.getSmallsort())+up.getMoney_xiaofei());
					//smallsortsCount[d.smallsort]++;
					smallsortsCount.put(up.getSmallsort(), smallsortsCount.get(up.getSmallsort())+1);
				}
			}

			if ("男".equals(up.getSex()))
				nanklj++;
			else
				nvklj++;

			//ldyj += d.yeji1;
			ldyj += up.getYeji1();
			//ldyj += d.yeji2;
			ldyj+=up.getYeji2();
			//ldyj += d.yeji3;
			ldyj+=up.getYeji3();

			if (up.getType()==0
					&&!StringUtils.isEmpty(up.getIncardId()) ) { // 办卡
				//xkkje += d.money2;
				xkkje += up.getMoney2();
				xkkks++;
			}
			if (up.getType()==3) { // 会员卡续费
				//xkje += d.money2;
				xkje += up.getMoney2();
				xkks++;
			}
		}
		xjlj = xjyj;
		sklj = skyj;
		ljzje = zje;
		ljzrs = zrs;
		zhkyj = skyj;
		ljhk = zhkyj;
		//服务类别
		//var bigcodeObj = {}, bigcodeCountObj = {};
		//Map<String,Double> bigcodeObj=new HashMap<String,Double>();
		//Map<String,Integer> bigcodeCountObj = new HashMap<String,Integer>();
		List<Bigsort> bs = bigsortService.findAll(organId);
		List<Smallsort> ms = smallsortService.findAll(organId);
		
		List sortList= new LinkedList();
		/*for(Smallsort ss:ms){
			Map<String,Object> ssMap = new HashMap<String,Object>();
			double bigcodeObj = 0;
			int bigcodeCountObj = 0;
			 //je = smallsorts[ss._id], count = smallsortsCount[ss._id];
			Double je = smallsorts.get(ss.get_id());
			Integer count =  smallsortsCount.get(ss.get_id());
			if (je==null)
				je = (double) 0;
			if (count==null)
				count = 0;
			bigcodeObj+=je;
			bigcodeCountObj+=count;
			ssMap.put("bigcodeObj", bigcodeObj);
			ssMap.put("bigcodeCountObj", bigcodeCountObj);
			for(Bigsort gs:bs){
				if(gs.get_id().equals(ss.getBigcode())){
					ssMap.put("name", gs.getName());
					break;
				}
			}
			sortList.add(ssMap);
		}*/
		
		for(Bigsort gs:bs){
			double bigcodeObj = 0;
			int bigcodeCountObj = 0;
			Map<String,Object> ssMap = new HashMap<String,Object>();
			for(Smallsort ss:ms){
				if(gs.get_id().equals(ss.getBigcode())){
					Double je = smallsorts.get(ss.get_id());
					Integer count =  smallsortsCount.get(ss.get_id());
					if (je==null)
						je = (double) 0;
					if (count==null)
						count = 0;
					bigcodeObj+=je;
					bigcodeCountObj+=count;
					
				}
			}
			ssMap.put("bigcodeObj", bigcodeObj);
			ssMap.put("bigcodeCountObj", bigcodeCountObj);
			ssMap.put("name", gs.getName());
			ssMap.put("sortyj", gs.getName()+"业绩");
			ssMap.put("sortks", gs.getName()+"客数");
			sortList.add(ssMap);
			
			
		}
		Collections.reverse(sortList);
		Map dataSet = new HashMap();
		dataSet.put("xjyj", xjyj1+xkje);
		dataSet.put("ldyjlj", ldyjlj);
		dataSet.put("zje", zje);
		dataSet.put("zrs", zrs);
		dataSet.put("zdnank", zdnank);
		dataSet.put("zdnvk", zdnvk);
		dataSet.put("fzdnank", fzdnank);
		dataSet.put("fzdnvk", fzdnvk);
		dataSet.put("zhkyj", zhkyj);
		dataSet.put("ldyj", ldyj);
		dataSet.put("ldyjlj", ldyjlj);
		dataSet.put("xjlj", xjyj1+xkje);
		dataSet.put("sklj", sklj);
		dataSet.put("ljzje", ljzje);
		dataSet.put("ljzrs", ljzrs);
		dataSet.put("nanklj", nanklj);
		dataSet.put("nvklj", nvklj);
		dataSet.put("ljhk", ljhk);
		dataSet.put("xkkje", xkkje);
		dataSet.put("xkkks", xkkks);
		dataSet.put("xkje", xkje);
		dataSet.put("xkks", xkks);
		dataSet.put("zkj", zkj);
		dataSet.put("skyj", skyj);
		dataSet.put("waimaiYejiTotal", waimaiYejiTotal);
		dataSet.put("waimaiCount", waimaiCount);
		dataSet.put("sortList", JsonUtils.fromJson(JsonUtils.toJson(sortList), List.class));
		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "店面综合业务报表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/downloadHelpFile")
	public ModelAndView downloadHelpFile(Integer flag,HttpServletRequest request,HttpServletResponse response) {
		File helpFile = null;
		DownloadResponse ds = new DownloadResponse(response,1);
		try {
			if(flag == 1) {
				helpFile = new File(request.getSession().getServletContext()
						.getRealPath("/WEB-INF/template/0-任性猫项目微信用户端操作手册--20160923.docx"));
				ds.download(helpFile, "任性猫项目微信用户端操作手册_20160923.docx");
			} else if(flag == 2) {
				helpFile = new File(request.getSession().getServletContext()
						.getRealPath("/WEB-INF/template/1-任性猫项目微信技师端操作手册--20160923.doc"));
				ds.download(helpFile, "任性猫项目微信技师端操作手册_20160923.doc");
			} else if(flag == 3) {
				helpFile = new File(request.getSession().getServletContext()
						.getRealPath("/WEB-INF/template/2-任性猫项目后台用户操作手册--20160923.doc"));
				ds.download(helpFile, "任性猫项目后台用户操作手册_20160923.doc");
			}
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
