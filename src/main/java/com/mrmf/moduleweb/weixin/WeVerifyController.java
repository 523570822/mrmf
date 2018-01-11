package com.mrmf.moduleweb.weixin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.WeOrganComment;
import com.mrmf.entity.WeStaffCase;
import com.mrmf.service.verify.WeVerifyService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
/**
 * 微信审核相关业务
 */
@Controller
@RequestMapping("/weixin/verify")
public class WeVerifyController {
	@Autowired
	WeVerifyService weVerifyService;
	
	@RequestMapping("/staffcase/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/staffcase/query");
		return mv;
	}
	/**
	 * 查询技师案例列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/staffcase/query")
	@ResponseBody
	public FlipInfo<WeStaffCase> staffCaseQuery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WeStaffCase> fpi = new FlipPageInfo<WeStaffCase>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				

			}
		}else{
			
			
		}
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		fpi = weVerifyService.queryWeStaffCase(fpi);
		return fpi;
	}
	@RequestMapping("/staffcase/detail")
	public ModelAndView queryStaffCaseDetail(String caseId,HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		request.setAttribute("ffcase", weVerifyService.queryCaseById(caseId));
		mv.setViewName("weixin/staffcase/detail");
		return mv;
	}
	/**
	 * 删除技师案例
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/staffcase/delcase")
	@ResponseBody
	public ReturnStatus delcase(String caseId)
			throws Exception {
		return weVerifyService.delStaffCase(caseId);
		//return null;
	}
	/**
	 * 删除技师案例单个图片
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/staffcase/delcaseImg")
	@ResponseBody
	public ReturnStatus delcaseImg(String img,String _id)
			throws Exception {
		if(StringUtils.isEmpty(_id)) {
			throw new BaseException("没有获取案例id");
		}
		if(StringUtils.isEmpty(img)) {
			throw new BaseException("没有获取图片");
		}
		ReturnStatus status = weVerifyService.delcaseImg(_id,img);
		return status;
	}
	/**
	 * 单个图片置顶
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/staffcase/setCaseTopImg")
	@ResponseBody
	public ReturnStatus setCaseTopImg(String img,String _id)
			throws Exception {
		if(StringUtils.isEmpty(_id)) {
			throw new BaseException("没有获取案例id");
		}
		if(StringUtils.isEmpty(img)) {
			throw new BaseException("没有获取图片");
		}
		ReturnStatus status = weVerifyService.setCaseTopImg(_id,img);
		return status;
	}
	
	
	@RequestMapping("/staffcase/detailDel")
	@ResponseBody
	public ModelAndView detailDel(String caseId,HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		ReturnStatus status= weVerifyService.delStaffCase(caseId);
		if(status.isSuccess()){
			mv = toQuery(request);
		}else{
			request.setAttribute("ffcase", weVerifyService.queryCaseById(caseId));
			request.setAttribute("returnStatus", status);
			mv.setViewName("weixin/staffcase/detail");
		}
		
		return mv;
	}
	/**
	 * 进入查询用户评论界面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/usercomment/toQuery")
	public ModelAndView toQueryUserComment(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/usercomment/query");
		return mv;
	}
	@RequestMapping("/usercomment/query")
	@ResponseBody
	public FlipInfo<WeOrganComment> userCommentQuery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WeOrganComment> fpi = new FlipPageInfo<WeOrganComment>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
			}
		}else{
		}
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		fpi = weVerifyService.queryUserComment(fpi);
		return fpi;
	}
	@RequestMapping("/usercomment/delcomment")
	@ResponseBody
	public ReturnStatus delcomment(String commentId)
			throws Exception {
		return weVerifyService.delComment(commentId);
		//return null;
	}
}
