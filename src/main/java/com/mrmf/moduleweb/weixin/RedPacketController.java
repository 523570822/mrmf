package com.mrmf.moduleweb.weixin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.WeRed;
import com.mrmf.entity.WeRedRecord;
import com.mrmf.entity.WeSysConfig;
import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.user.Userpart;
import com.mrmf.module.wx.utils.WeixinRedPacket;
import com.mrmf.service.redpacket.WeRedPacketService;
import com.mrmf.service.wesysconfig.WeSysConfigService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FlipPageInfo;

/**
 * 系统业务参数设置相关
 */
@Controller
@RequestMapping("/weixin/redPacket")
public class RedPacketController {
	@Autowired
	private WeRedPacketService weRedPacketService;
	/**
	 * 跳转到红包管理界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toRedPacket")	
	public ModelAndView toRedPacket(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("weixin/redpacket/query");
		return mv;
	}
	/**
	 * 查询所有的平台红包
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryRedPacket")
	@ResponseBody
	public FlipInfo<WeRed> queryRedPacket(HttpServletRequest request) {
		FlipInfo<WeRed> fpi = new FlipPageInfo<WeRed>(request);
		return weRedPacketService.findWeReds(fpi);
	}
	
	/**
	 * 查询红包详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryWeRedRecord")
	@ResponseBody
	public FlipInfo<WeRedRecord> queryWeRedRecord(HttpServletRequest request) {
		FlipInfo<WeRedRecord> fpi = new FlipPageInfo<WeRedRecord>(request);
		return weRedPacketService.findWeRedRecords(fpi);
	}
	
	/**
	 * 跳转发送红包界面
	 * @param request
	 */
	@RequestMapping("/sendRedPacket")	
	public ModelAndView sendRedPacket(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("weixin/redpacket/insert");
		return mv;
	}
	/**
	 * 跳转到红包详情界面
	 * @param request
	 */
	@RequestMapping("/toRedPacketDetail")	
	public ModelAndView toRedPacketDetail(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("weixin/redpacket/redPacketDetail");
		String redId = request.getParameter("redId");
		mv.addObject("redId", redId);
		return mv;
	}
	/**
	 * 发送红包
	 * @param request
	 * @param binder
	 */
	@RequestMapping("/insertWeRed")
	public ModelAndView insertWeRed(HttpServletRequest request,WeRed weRed, BindingResult results) {
		ModelAndView mv = new ModelAndView("weixin/redpacket/query");
		weRed.setNewId();
		weRed.setOrganId("-1");
		weRed.setRestCount(weRed.getCount());
		weRed.setRestAmount(weRed.getAmount());
		weRed.setCreateTimeIfNew();
		List<BigDecimal> smallRedsB = WeixinRedPacket.initMoney(new BigDecimal(weRed.getAmount()), new BigDecimal("0.01"), weRed.getCount());
		List<Double> smallRedsD = new ArrayList<Double>();
		for (BigDecimal smallRed : smallRedsB) {
			smallRedsD.add(smallRed.doubleValue());
		}
		weRed.setSmallReds(smallRedsD);
		weRedPacketService.saveRedPacket(weRed);
		return mv;
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
