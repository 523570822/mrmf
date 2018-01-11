package com.mrmf.module.coupon;

import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.service.coupon.CouponService;
import com.mrmf.service.user.bigsort.BigsortService;
import com.mrmf.service.usermy.UserMyService;
import com.osg.entity.ReturnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 蔺哲 on 2017/3/22.
 */
@Controller
@RequestMapping("/module/coupon")
public class couponController {
    @Autowired
    private BigsortService bigsortService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserMyService userMyService;
    @RequestMapping("/queryCoupon")
    @ResponseBody
    public ReturnStatus queryCoupon(String userId, double price, String orderId, HttpServletRequest request) throws Exception{
        WeOrganOrder order=userMyService.getOrderDetail(orderId);
        String title = order.getTitle();
        String staffId = order.getOrganId();
        Bigsort bigsort = bigsortService.findOne(staffId,title);//根据名称和店铺得到大类对象
        List myCouponList = couponService.queryMyCoupon(userId,bigsort,price);
        if(0 ==myCouponList.size()){
            return new ReturnStatus(1);
        }
        return new ReturnStatus(0);
    }

    @RequestMapping("/queryMyCoupon")
    public ModelAndView queryMyCoupon(String userId, double price, String orderId, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        WeOrganOrder order=userMyService.getOrderDetail(orderId);
        if(2 ==order.getType()){ //预约技师
            //根据用户id等信息查符合的优惠券
            String title = order.getTitle();
            String staffId = order.getOrganId();
            Bigsort bigsort = bigsortService.findOne(staffId,title);//根据名称和店铺得到大类对象
            List myCouponList = couponService.queryMyCoupon(userId,bigsort,price);
            mv.setViewName("user/usermy/myOrder/couponList");
            mv.addObject("myCouponList",myCouponList);
        }else{//预约店铺
            //根据用户id等信息查符合的优惠券
            String title = order.getTitle();
            String organId = order.getOrganId();
            Bigsort bigsort = bigsortService.findOne(organId,title);//根据名称和店铺得到大类对象
            List myCouponList = couponService.queryMyCoupon(userId,bigsort,price);
            mv.setViewName("user/usermy/myOrder/couponList");
            mv.addObject("myCouponList",myCouponList);
        }
        mv.addObject("orderId",orderId);
        mv.addObject("price",price);
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
