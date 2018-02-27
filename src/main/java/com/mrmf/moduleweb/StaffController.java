package com.mrmf.moduleweb;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.service.bumen.BumenService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.staff.staffpost.StaffpostService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.Constants;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.util.QRCodeUtil;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公司信息管理相关
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	private StaffService staffService;

	@Autowired
	private StaffpostService staffpostService;

	@Autowired
	private BumenService bumenService;

	@Autowired
	private OrganService organService;
	@RequestMapping("/toQuery")
	public ModelAndView toQuery(@RequestParam(required = false) String organId, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Staff> query(HttpServletRequest request) throws Exception {
		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils.isEmpty((String) fpi.getParams().get("organId")))
					fpi.getParams().put("organId", organId);
			}
		}
		fpi = staffService.query(fpi);
		return fpi;
//		String sidx = (String) fpi.getParams().get("sidx");
//		if("userNum".equals(sidx)||"".equals(sidx)){
//			List<Staff> list =  staffService.queryUserOfstaff(fpi);
//			fpi.setData(list);
//			return fpi;
//		}else {
//			return staffService.queryUserOfStaff1(fpi);
//		}
	}
	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam String organId, @RequestParam(required = false) String staffId,
			HttpServletRequest request) throws Exception {
		if (!StringUtils.isEmpty(organId)) {
			request.setAttribute("staffposts", staffpostService.findAll(organId));
			request.setAttribute("bumens", bumenService.findAll(organId));
		}
		Staff staff;
		if (!StringUtils.isEmpty(staffId)) {
			staff = staffService.queryById(staffId);
		} else {
			staff = new Staff();
		}

		request.setAttribute("ffstaff", staff);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Staff staff, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = staff.get_id();
		ReturnStatus status = staffService.upsert(staff);
		if (!StringUtils.isEmpty(staff.getOrganId())) {
			request.setAttribute("staffposts", staffpostService.findAll(staff.getOrganId()));
			request.setAttribute("bumens", bumenService.findAll(staff.getOrganId()));
		}
		if (status.isSuccess()) {
			Boolean isOrganAdmin = (Boolean) request.getSession().getAttribute("isOrganAdmin");
			if (isOrganAdmin != null && !isOrganAdmin) { // 非管理员跳转
				staff.set_id(originId);// 恢复之前的id
				request.setAttribute("ffstaff", staff);
				mv.setViewName("staff/upsert");
			} else { // 管理员跳转，包括admin和总公司管理员
				mv.setViewName("staff/query");
			}

		} else {
			Staff ss = staffService.queryStaffByPhone(staff.getPhone());
			if(ss!=null){
				request.setAttribute("oldOrganId",ss.getOrganId());
			}
			staff = (Staff)status.getEntity();
			staff.set_id(originId);// 恢复之前的id
			request.setAttribute("ffstaff", staff);
			request.setAttribute("returnStatus", status);
			mv.setViewName("staff/upsert");
		}
		return mv;
	}

	/**
	 * 进入查询微信签约技师列表界面
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toQueryWe")
	public ModelAndView toQueryWe() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/queryWe");
		return mv;
	}

	@RequestMapping("/queryWe")
	@ResponseBody
	public FlipInfo<Staff> queryWe(HttpServletRequest request) throws Exception {
		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils.isEmpty((String) fpi.getParams().get("organId")))
					fpi.getParams().put("weOrganIds|all", organId);
			}
		}
		fpi = staffService.query(fpi);
		return fpi;
	}

	@RequestMapping("/removeFromOrgan")
	@ResponseBody
	public ReturnStatus removeFromOrgan(String staffId) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return staffService.removeFromOrgan(organId, staffId);
	}
	@RequestMapping("/removeStaff")
	@ResponseBody
	public ReturnStatus removeStaff(String staffId) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return staffService.removeStaff(staffId);
	}

	/**
	 * 扫码入职店面，二维码信息未修改
	 * @return
	 */
	@RequestMapping(value = "/organCode")
	public ReturnStatus makeCode(HttpServletRequest req, HttpServletResponse res) {
		String organId = req.getParameter("organId");
		InputStream is =null;
		boolean flag = false;
		try {
			Organ organ = organService.queryById(organId);
			URL obj = OSSFileUtil.getFileUrl(OSSFileUtil.pubBucketName,organ.getLogo());
			String ll = "http://"+obj.getHost()+"/"+obj.getPath();
			URL urlObj = new URL(ll);
			is = urlObj.openStream();
			return null;
		} catch (IOException e) {
			flag = true;
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage( ));
		} finally {
			try {
				if(flag){
					is = new FileInputStream(new File(req.getRealPath("icon_logo.png")));
				}
				String url = Constants.getProperty("shop.url")+"api/member/myHome/addOrgan.jhtml?organId="+organId;
				QRCodeUtil.encodeQRCode(url, res.getOutputStream( ), 2, is);
				is.close( );
			}catch (Exception e){
				e.printStackTrace();
			}
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
