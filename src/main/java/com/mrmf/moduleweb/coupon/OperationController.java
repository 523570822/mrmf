package com.mrmf.moduleweb.coupon;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.coupon.Coupon;
import com.mrmf.entity.coupon.CouponGrant;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.service.coupon.OperationService;
import com.osg.entity.FlipInfo;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 编辑优惠券，发放配置类
 * Created by 蔺哲 on 2017/3/9.
 */
@Controller
@RequestMapping("/coupon/OperationController")
public class OperationController {
    @Autowired
    OperationService operationService;
    /**
     * 显示单个优惠券发放配置
     * @param request
     * @param m
     * @return
     */
    @RequestMapping("/ToDoGrant")
    public ModelAndView toQuery(HttpServletRequest request, Model m){
        ModelAndView mv = new ModelAndView();
        String grantId = request.getParameter("GrantId");
        mv.setViewName("coupon/DoGrant");
        if(StringUtils.isEmpty(grantId)){
            return mv;
        }
        CouponGrant grant = operationService.queryCouponGrant(grantId);
        mv.addObject("grant",grant);//m.addAttribute("grant",grant);
        return mv;
    }



    /**
     * 去添加优惠券的页面
     * @return
     */
    @RequestMapping("/toAdd")
    public ModelAndView toDoCoupon(HttpServletRequest request) {
        String couponGrandId = request.getParameter("couponGrandId");
        Coupon coupon = new Coupon();
        coupon.setGrantId(couponGrandId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("coupon", coupon);
        mv.setViewName("coupon/DoCoupon");
        return mv;
    }

    /**
     * 去修改优惠券页面
     * @param request
     * @return
     */
    @RequestMapping("/DoUpdate")
    public ModelAndView DoUpdate(HttpServletRequest request){
        String couponId = request.getParameter("couponId");
        Coupon coupon= operationService.queryOneCoupon(couponId);
        Organ organ = operationService.queryShopType(coupon.getShopId());
        ModelAndView mv = new ModelAndView();
        if(organ!=null){
            mv.addObject("typeList",organ.getType());
        }else{
            List<Code> list = operationService.queryCode("organType");
            List<String> lis = new ArrayList<String>();
            for (Code code:list){
                lis.add(code.getName());
            }
            mv.addObject("typeList",lis);
        }
        mv.addObject("coupon",coupon);
        mv.setViewName("coupon/DoCoupon");
        return mv;
    }

    /**
     * 保存优惠券
     * @param
     */
    @RequestMapping("/AddCoupon")
    public void AddCoupon(HttpServletResponse response,Coupon coupon) throws Exception {
        PrintWriter out =response.getWriter();
        Boolean donate = coupon.getDonate();
        if(donate==null){
            coupon.setDonate(false);
        }
        if(StringUtils.isEmpty(coupon.get_id())){//对象id是空，走保存
            coupon.setStartTime(new Date());
            coupon.setState("是");
            String id = operationService.AddCoupon(coupon);
            if(!StringUtils.isEmpty(id)){//保存成功会有id，不是null输出1
                out.print(1);
            }else{//id是空，说明保存失败
                out.print(0);
            }
        }else{//对象id不是空，走修改
            String id = operationService.AddCoupon(coupon);
            out.print(1);
        }
    }
    /**
     * 查询所有店面
     */
    @RequestMapping("/queryOrgan")
    public @ResponseBody FlipInfo<Organ> queryOrgan(HttpServletRequest request)throws Exception{
        FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
        FlipInfo<Organ> list = operationService.queryOrgan(fpi);
        return list;
    }
    /**
     * 查询店铺类型
     */
    @RequestMapping("/queryShopType")
    public @ResponseBody List<String> queryShopType(HttpServletRequest request){
        String organId = request.getParameter("organId");
        Organ organ = operationService.queryShopType(organId);
        return  organ.getType();
    }
    /**
     * 查询店面服务大类
     * @param request
     * @return
     */
    @RequestMapping("/queryBigsort")
    public @ResponseBody List<Bigsort> queryBig(HttpServletRequest request){
        String organId = request.getParameter("organId");
        String typeName = request.getParameter("typeName");
        List<Bigsort> list = operationService.queryBig(organId,typeName);
        return  list;
    }
    /**
     * 查询平台大类
     */
    @RequestMapping("/queryCode")
    public @ResponseBody List<Code> queryCode(HttpServletRequest request){
        String typeName = request.getParameter("typeName");
        String type = new String();
        if(typeName.equals("美容")){
            type="meiRongType";
        }else if (typeName.equals("美发")){
            type="hairType";
        }else if(typeName.equals("美甲")){
            type="meiJiaType";
        }else{
            type="zuLiaoType";
        }
        List<Code> list = operationService.queryCode(type);
        return  list;
    }
    /**
     * 查询小类
     * @param request
     * @return
     */
    @RequestMapping("/querySmallsort")
    public @ResponseBody List<Smallsort> querySmall(HttpServletRequest request){
        String bigcode = request.getParameter("bigcode");
        List<Smallsort> list = operationService.querySmall(bigcode);
        return list;
    }
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
}
