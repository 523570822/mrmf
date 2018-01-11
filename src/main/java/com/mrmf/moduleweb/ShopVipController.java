package com.mrmf.moduleweb;

import com.mrmf.entity.Organ;
import com.mrmf.entity.shop.VipMember;
import com.mrmf.service.VipMember.VipMemberService;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.organ.OrganService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 蔺哲 on 2017/6/27.
 */
@Controller
@RequestMapping("/shopVip")
public class ShopVipController {
    @Autowired
    private VipMemberService vipMemberService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrganService organService;
    /**
     * 到查看编辑商城vip用户页面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toQuery")
    public ModelAndView toQuery(HttpServletRequest request) throws Exception{
        Boolean isOrganAdmin = (Boolean) MAppContext
                .getSessionVariable("isOrganAdmin");
        String organId = (String) MAppContext.getSessionVariable("organId");
        if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
            if (StringUtils.isEmpty(organId)) {
                throw new BaseException("当前登录企业信息缺失！");
            } else {
                // request.setAttribute("smallsorts",
                // smallsortService.findAll(organId));
            }
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("organId",organId);
        mv.setViewName("vipMember/query");
        return mv;
    }

    /**
     * 查询本店关联的vip用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/query")
    public FlipInfo<VipMember> query(HttpServletRequest request){
        FlipInfo<VipMember> fpi = new FlipPageInfo<VipMember>(request);
        return vipMemberService.query(fpi);
    }

    /**
     * 去新增和修改的页面
     * @param request
     * @return
     */
    @RequestMapping("toUpsert")
    public ModelAndView toUpsert(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.addObject("organId",request.getParameter("organId"));
        String vipMemberId = request.getParameter("vipMemberId");
        if(!StringUtils.isEmpty(vipMemberId)){
            mv.addObject("vipMember",vipMemberService.queryById(vipMemberId));
        }
        mv.setViewName("vipMember/upsert");
        return mv;
    }

    /**
     * 保存和修改
     * @param request
     * @param vipMember
     */
    @RequestMapping("/upsert")
    public ModelAndView upsert(HttpServletRequest request ,VipMember vipMember){
        ModelAndView mv = new ModelAndView();
        ReturnStatus returnStatus = vipMemberService.upsert(vipMember);
        if(returnStatus.isSuccess()){
            mv.setViewName("redirect:/shopVip/toQuery.do");
        }else {
            mv.addObject("returnStatus",returnStatus);
            mv.setViewName("vipMember/upsert");
        }
        return mv;
    }

    /**
     * 设置状态
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateState")
    public ReturnStatus updateState(HttpServletRequest request){
        String vipMember = request.getParameter("vipMemberId");
        String state = request.getParameter("state");
        return vipMemberService.updateState(vipMember,state);
    }

    /**
     * 去审核页面
     * @return
     */
    @RequestMapping("/toShenHe")
    public ModelAndView toShenHe(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("vipMember/shenhe");
        return mv;
    }

    /**
     * 审核list
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/shenhe")
    public FlipInfo shenhe(HttpServletRequest request){
        FlipInfo fpi = new FlipPageInfo(request);
        return vipMemberService.queryList(fpi);
    }
    /**
     * 去审核记录页面
     */
    @RequestMapping(value = "/toCheckVipGoodsHistory")
    public ModelAndView toCheckVipGoodsHistory() throws Exception{
        Boolean isOrganAdmin = (Boolean) MAppContext
                .getSessionVariable("isOrganAdmin");
        String organId = (String) MAppContext.getSessionVariable("organId");
        if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
            if (StringUtils.isEmpty(organId)) {
                throw new BaseException("当前登录企业信息缺失！");
            } else {
                // request.setAttribute("smallsorts",
                // smallsortService.findAll(organId));
            }
        }
        ModelAndView mv =new ModelAndView();
        mv.addObject("organId",organId);
        mv.setViewName("vipMember/history");
        return mv;
    }
    /**
     * 审核记录
     */
    @ResponseBody
    @RequestMapping(value = "/queryHistory")
    public FlipInfo queryHistory(HttpServletRequest request){
        FlipInfo fpi = new FlipPageInfo(request);
        return vipMemberService.queryHistory(fpi);
    }
    /**
     * 更改商品状态 返回回文
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/doShenhe")
    public ReturnStatus doShenhe(HttpServletRequest request){
        return  vipMemberService.shenhe(request);
    }
    /**
     * 去商城管理员页面
     */
    @RequestMapping("/toShopMember")
    public ModelAndView toShopMember(@RequestParam(required = false) String parentId, HttpServletRequest request) throws Exception{
        Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
        String organId = (String) MAppContext.getSessionVariable("organId");
        if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
            if (StringUtils.isEmpty(organId)) {
                throw new BaseException("当前登录企业信息缺失！");
            } else {
                parentId = organId;
            }
        }
        Organ organ = organService.queryById(parentId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("organ",organ);
        mv.addObject("type",request.getParameter("type"));
        mv.setViewName("shopAdmin/upsert");
        return mv;
    }
    /**
     * 修改商城管理员
     */
    @RequestMapping("/upsertShopMember")
    public ModelAndView upsertShopMember(@RequestParam String phone,@RequestParam String _id,HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        Organ organ = organService.queryById(_id);
        ReturnStatus result = vipMemberService.updateOrganMember(organ, phone);
        mv.addObject("type",request.getParameter("type"));
        mv.addObject("returnStatus",result);
        mv.addObject("organ",organ);
        mv.setViewName("shopAdmin/upsert");
        return mv;
    }
    /**
     * 解决_id字段注入问题，去除“_”前缀处理
     * @param request
     * @param binder
     */
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        binder.setFieldMarkerPrefix(null);
    }
}
