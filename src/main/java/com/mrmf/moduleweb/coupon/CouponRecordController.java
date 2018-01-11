package com.mrmf.moduleweb.coupon;

import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.coupon.ConponRecord;
import com.mrmf.service.coupon.CouponRecordService;
import com.mrmf.service.finance.FinanceService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.util.*;
import com.osg.framework.web.context.MAppContext;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/7/21.
 */
@Controller
@RequestMapping("/coupon/couponRecord")
public class CouponRecordController {
    @Autowired
    CouponRecordService couponRecordService;
    @Autowired
    FinanceService financeService;
    @RequestMapping("/toQuery")
    public ModelAndView toQuery(){
        return new ModelAndView("coupon/queryRecord");
    }

    /**
     * 分页查询优惠券发放情况
     * @param request
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public FlipInfo<ConponRecord> queryByFpi(HttpServletRequest request){
        FlipInfo<ConponRecord> fpi = new FlipPageInfo<ConponRecord>(request);
        return couponRecordService.queryByFpi(fpi);
    }
    /**
     * 去统计页面
     */
    @RequestMapping("/toStatistics")
    public ModelAndView toStatistics(){
        return new ModelAndView("coupon/statistics");
    }
    /**
     * 各种条件统计
     */
    @RequestMapping("/statistics")
    @ResponseBody
    public Map statistics(HttpServletRequest request)throws Exception{
        Map result = couponRecordService.statistics(request);
        return result;
    }
    /**
     * 导出
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportConponRecord")
    public ModelAndView exportConponRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
        String organId = (String) MAppContext.getSessionVariable("organId");
        if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
            if (StringUtils.isEmpty(organId)) {
                throw new BaseException("当前登录企业信息缺失！");
            }
        }
        File template = new File(request.getSession().getServletContext()
                .getRealPath("/WEB-INF/template/template_couponRecord1_export.xls"));
        File outputExcel = new File(request.getSession().getServletContext()
                .getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
        FlipInfo<ConponRecord> fpi = new FlipPageInfo<ConponRecord>(request);
        List<ConponRecord> list=couponRecordService.queryByList(fpi);
        Map dataSet = new HashMap();
        dataSet.put("couponRecord", JsonUtils.fromJson(JsonUtils.toJson(list), List.class));
        try {
            ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
            DownloadResponse ds = new DownloadResponse(response);
            ds.download(outputExcel, "优惠券发放表.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
