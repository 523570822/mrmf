package com.mrmf.moduleweb.coupon;


import com.mrmf.entity.Organ;
import com.mrmf.entity.User;
import com.mrmf.entity.coupon.CouponGrant;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.service.coupon.CouponGrantService;
import com.mrmf.service.coupon.CouponService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.sys.SysUser.SysUserService;
import com.mrmf.service.user.bigsort.BigsortService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.userpart.UserpartService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 优惠券方法配置
 * Created by Lin on 2017/3/5.
 */
@Controller
@RequestMapping("/coupon/colligateCoupon")//给类下所有方法加一个colligateCoupon的请求路径
public class CouponController {
    @Autowired
    CouponService couponService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    CouponGrantService couponGrantService;
    /**
     * 转换时间和_id
     * @param request
     * @param binder
     */
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        // 解决_id字段注入问题，去除“_”前缀处理
        binder.setFieldMarkerPrefix(null);
    }
    /**
     * 优惠券管理
     * @return
     */
    @RequestMapping(value="/toCoupon")
    public ModelAndView AdministrationCoupon(){ //后台优惠券管理
        System.out.println("请求被控制器拦截，并分配方法");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("coupon/query");
        return mv;
    }

    /**
     * 显示优惠券配置
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/toQuery")
    public @ResponseBody List<CouponGrant> query(HttpServletRequest request)throws Exception{
        //FlipInfo<CouponGrant> fpi = new FlipPageInfo<CouponGrant>(request);
        String bus = request.getParameter("business") ;
        List<CouponGrant> list= couponService.QueryGrant(bus);
        return list;
    }

    /**
     * 按条件显示优惠券
     * @return
     */
    @RequestMapping(value="/queryCoupon")
    public @ResponseBody List queryCoupon(String grantId,HttpServletRequest request){
        List list = couponService.QueryCoupon(grantId);
        return list;
    }

    /**
     * 保存优惠券发放配置
     * @throws Exception
     */
    @RequestMapping(value="/addCoupon")
    public void addCoupon(HttpServletRequest request, HttpServletResponse response, CouponGrant grant)throws Exception{
        PrintWriter out =response.getWriter();
        String couID = couponService.addCoupon(grant);
        if(couID!=null||couID.length()>0){
            out.print(1);
        }else{
            out.print(0);
        }
    }

    /**
     * 修改状态(禁用启用)
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("disable/{couponGrantId}")
    @ResponseBody
    public ReturnStatus disable(@PathVariable String couponGrantId) throws Exception {
        ReturnStatus status = couponService.disable(couponGrantId);
        return status;
    }
    @RequestMapping("disable2/{couponId}")
    @ResponseBody
    public ReturnStatus disable2(@PathVariable String couponId) throws Exception {
        ReturnStatus status = couponService.disable2(couponId);
        return status;
    }

    /**
     * 开启发放配置
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/enable/{couponGrantId}")
    @ResponseBody
    public ReturnStatus enable(@PathVariable String couponGrantId) throws Exception {
        ReturnStatus status = couponService.enable(couponGrantId);
        return status;
    }
    @RequestMapping("/enable2/{couponId}")
    @ResponseBody
    public ReturnStatus enable2(@PathVariable String couponId) throws Exception {
        ReturnStatus status = couponService.enable2(couponId);
        return status;
    }

}
