package com.mrmf.moduleweb.weixin.organ;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.service.organPosition.OrganPosition;
import com.mrmf.service.westaff.WeStaffService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.WriteResult;
import com.mrmf.entity.Staff;
import com.mrmf.service.sys.SysUser.SysUserService;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 店铺用户管理
 */
@Controller
@RequestMapping("/weixin/organ/user")
public class OrganUserController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrganPosition organPosition;
	/**
	 * 注册技师管理主页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/staffToAudit")
	public ModelAndView staffToQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/organ/staff/auditStaff");
		return mv;
	}
	
	/**
	 * 注册技师管理数据获取
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAuditStaff")
	@ResponseBody
	public FlipInfo<Staff> queryStaff(HttpServletRequest request) throws Exception {
		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
		fpi = sysUserService.queryStaff(fpi);
		return fpi;
	}

	/**
	 * 跳转了查询带审核的工位
	 * @param request
	 * @return 要跳转的页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPosition")
	@ResponseBody
	public ModelAndView queryPosition(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/organ/position/queryPosition");
		return mv;
	}

	/**
	 * 审核技师
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "/toCheckPosition",method = RequestMethod.GET)
	public ModelAndView toCheckPosition(HttpServletRequest request) throws BaseException{
		ModelAndView mv = new ModelAndView();
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}
		String id = request.getParameter("id");
		PositionOrder positionOrder = organPosition.getPositonById(id);
		String staffId = positionOrder.getStaffId( );
		Staff staff = null;
		if(staffId != null && !staffId.equals("")) {
			try {
				staff =  sysUserService.queryByStaffId(staffId);
			} catch (BaseException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("没有待审核工位的技师的id啊！,所以不能进入审核页面");
		}
		mv.addObject("positionOrder", positionOrder);
		mv.addObject("staff", staff);
		mv.setViewName("weixin/organ/position/toCheckPosition");
		return mv;
	}

	@RequestMapping(value="/commitPositionCheck")
	public void commitPositionCheck(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String checkResult = request.getParameter("checkResult");

		sysUserService.updatePositionOrder(id,checkResult);

		WriteResult staffVerify = sysUserService.opd(id,checkResult);
		if(staffVerify != null) {
			response.getWriter().print("保存成功");
		} else {
			response.getWriter().print("保存失败");
		}
	}


	/**
	 * 查询所有的待审核的技师
	 */
	@RequestMapping(value ="/queryPositionList",method = RequestMethod.POST)
	@ResponseBody
	public FlipInfo<PositionOrder> queryPositionCheck(HttpServletRequest request) throws Exception {
		FlipInfo<PositionOrder> fpi = new FlipPageInfo<PositionOrder>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}
		fpi = sysUserService.queryPositionCheck(fpi,organId);
		return fpi;
	}

	/**
	 * 跳转了查询带审核的技师
	 * @param request
	 * @return 要跳转的页面
	 * @throws Exception
	 */
	/*staffCheckQuery*/
	@RequestMapping(value = "/queryStaffCheck",method = RequestMethod.GET)
	public ModelAndView querystaffCheck(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/organ/staff/queryStaffCheck");
		return mv;
	}
	/** 
	 * 查询所有的待审核的技师
	 */
	@RequestMapping(value ="/queryStaffCheckList",method = RequestMethod.POST)
	@ResponseBody
	public FlipInfo<Staff> queryStaffCheck(HttpServletRequest request) throws Exception {
		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}
		fpi = sysUserService.queryStaffCheck(fpi,organId);
		return fpi;
	}
	/**
	 * 审核技术
	 * @param request
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value = "/toCheckStaff",method = RequestMethod.GET)
	public ModelAndView toCheckStaff(HttpServletRequest request) throws BaseException{
		ModelAndView mv = new ModelAndView();
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}
		String staffId = request.getParameter("staffId");
		Staff staff = null;
		if(staffId != null && !staffId.equals("")) {
			try {
				staff =  sysUserService.queryByStaffId(staffId);
			} catch (BaseException e) {
				e.printStackTrace();
				System.out.println("OrganUserController的toCheckStaff 没有获取到技师" );
			}
		} else {
			throw new RuntimeException("没有待审核的技师的id啊！,所以不能进入审核页面");
		}
		mv.addObject("staff", staff);
		mv.setViewName("weixin/organ/staff/toCheckStaff");
		return mv;
	}
	
	@RequestMapping(value="/commitStaffCheck")
	public void commitStaffCheck(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String organId = request.getParameter("organId");
		String staffId = request.getParameter("staffId");
		String checkResult = request.getParameter("checkResult");
		String memo = request.getParameter("memo");
		sysUserService.findOneStaffAndAddOrganId(staffId,organId);
		WriteResult staffVerify = sysUserService.updateStaffVerify(organId,staffId,checkResult,memo);
		if(staffVerify != null) {
			response.getWriter().print("保存成功");
		} else {
			response.getWriter().print("保存失败");
		}
	}
	
}
