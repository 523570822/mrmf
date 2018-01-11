package com.mrmf.moduleweb.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.mrmf.entity.Function;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBDistrict;
import com.mrmf.entity.WeBRegion;
import com.mrmf.service.wes.WeSService;
import com.osg.entity.DataEntity;
import com.osg.framework.util.StringUtils;

/**
 * 城市、区域、商圈相关api
 */
@Controller
@RequestMapping("/weixin/s")
public class WeSController {

	@Autowired
	private WeSService weSService;

	@RequestMapping("/queryCity")
	@ResponseBody
	public List<WeBCity> queryCity() throws Exception {
		return weSService.queryCity();
	}

	@RequestMapping("/queryDistrict")
	@ResponseBody
	public List<WeBDistrict> queryDistrict(@RequestParam String cityId) throws Exception {
		return weSService.queryDistrict(cityId);
	}

	@RequestMapping("/queryRegion")
	@ResponseBody
	public List<WeBRegion> queryRegion(@RequestParam String districtId) throws Exception {
		return weSService.queryRegion(districtId);
	}

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		List<DataEntity> dataList = weSService.findAll();
		// 添加根节点
		Function root = new Function();
		root.set_id("0");
		root.setParentId("0");
		root.setName("菜单树");
		dataList.add(root);

		request.setAttribute("fftree", dataList);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/s/query");
		return mv;
	}

	@RequestMapping("/toUpsertCity")
	public ModelAndView toUpsertCity(@RequestParam(required = false) String cityId, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		WeBCity city;
		if (StringUtils.isEmpty(cityId)) {
			city = new WeBCity();
		} else {
			city = weSService.queryCityById(cityId);
		}
		request.setAttribute("ffcity", city);
		mv.setViewName("weixin/s/upsertCity");
		return mv;
	}

	@RequestMapping("/upsertCity")
	public ModelAndView upsert(WeBCity city, BindingResult results, HttpServletRequest request) throws Exception {
		weSService.upsertCity(city);
		return toQuery(request);
	}

	@RequestMapping("/toUpsertDistrict")
	public ModelAndView toUpsertDistrict(@RequestParam(required = false) String cityId,
			@RequestParam(required = false) String districtId, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		WeBDistrict district;
		if (StringUtils.isEmpty(districtId)) {
			district = new WeBDistrict();
			district.setCityId(cityId);
		} else {
			district = weSService.queryDistrictById(districtId);
		}
		request.setAttribute("ffdistrict", district);
		mv.setViewName("weixin/s/upsertDistrict");
		return mv;
	}

	@RequestMapping("/upsertDistrict")
	public ModelAndView upsert(WeBDistrict district, BindingResult results, HttpServletRequest request)
			throws Exception {
		weSService.upsertDistrict(district);
		return toQuery(request);
	}

	@RequestMapping("/toUpsertRegion")
	public ModelAndView toUpsertRegion(@RequestParam(required = false) String cityId,
			@RequestParam(required = false) String districtId, @RequestParam(required = false) String regionId,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		WeBRegion region;
		if (StringUtils.isEmpty(regionId)) {
			region = new WeBRegion();
			region.setCityId(cityId);
			region.setDistrictId(districtId);
		} else {
			region = weSService.queryRegionById(regionId);
		}
		request.setAttribute("ffregion", region);
		mv.setViewName("weixin/s/upsertRegion");
		return mv;
	}

	@RequestMapping("/upsertRegion")
	public ModelAndView upsert(WeBRegion region, BindingResult results, HttpServletRequest request) throws Exception {
		weSService.upsertRegion(region);
		return toQuery(request);
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
