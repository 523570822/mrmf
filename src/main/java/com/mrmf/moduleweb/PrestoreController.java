package com.mrmf.moduleweb;

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.sqlEntity.Prestore;
import com.mrmf.entity.WeBCity;
import com.mrmf.service.VipMember.VipMemberService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.rank.RankService;
import com.mrmf.service.usercard.UsercardService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.security.token.TokenManager;
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
import java.util.List;

/**
 * Created by 蔺哲 on 2017/9/18.
 */
@Controller
@RequestMapping("/prestore")
public class PrestoreController {
    @Autowired
    private UsercardService usercardService;
    @Autowired
    RankService rankservice;
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private OrganService organService;

    @Autowired
    private VipMemberService vipMemberService;

    /**
     * 去店铺列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toQuery")
    public ModelAndView toQuery(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<WeBCity> city = rankservice.cityList();
        request.setAttribute("ffcitys", city);
        mv.setViewName("prestore/query");
        return mv;
    }

    /**
     * 店铺列表
     * @param request
     * @param city
     * @param district
     * @param region
     * @param organName
     * @param organAbname
     * @return
     * @throws Exception
     */
    @RequestMapping("/query")
    @ResponseBody
    public FlipInfo<Organ> query(HttpServletRequest request, String city, String district, String region,
                                    String organName, String organAbname) throws Exception {
        FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
        List<Organ> organList = usercardService.queryOrganList(city, district, region, organName, organAbname);
        String organIds = "";

        // 根据管理员能管辖的区域范围过滤
        Account account = tokenManager.getCurrentAccount();
        List<String> ids = organService.queryAdminOrganIds(account);
        for (Organ organ : organList) {
            if (ids.size() == 0 || ids.contains(organ.get_id()))
                organIds += organ.get_id() + ",";
        }
        if (organIds.length() > 0) {
            organIds = organIds.substring(0, organIds.length() - 1);
            fpi.getParams().put("in:_id|array", organIds);
            fpi.getParams().remove("city");
            fpi.getParams().remove("district");
            fpi.getParams().remove("region");
            fpi.getParams().remove("organName");
            fpi.getParams().remove("organAbname");
            //这里写充值店铺list
            fpi = organService.queryByShopPhone(fpi);
        }

        return fpi;
    }

    /**
     * 去充值商城预存款界面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpsert")
    public ModelAndView toUpsert(HttpServletRequest request) throws Exception {
        FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
        Organ organ = organService.queryByShopPhone(fpi).getData().get(0);
        Double prestore = vipMemberService.findPrestore(organ.get_id());
        request.setAttribute("prestore",prestore);
        request.setAttribute("organ",organ);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("prestore/upsert");
        return mv;
    }

    /**
     * 充店铺预存款
     * @param _id
     * @param charge
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/upsert")
    public ModelAndView upsert(@RequestParam String _id, @RequestParam double charge,
                               HttpServletRequest request) throws Exception {
        ReturnStatus returnStatus = organService.sysCharge(_id, charge);
        if (returnStatus.isSuccess()) {
            return toQuery(request);
        } else {
            Organ organ = organService.queryById(_id);
            request.setAttribute("returnStatus", returnStatus);
            Double prestore = vipMemberService.findPrestore(organ.get_id());
            request.setAttribute("prestore",prestore);
            request.setAttribute("organ",organ);
            ModelAndView mv = new ModelAndView();
            mv.setViewName("prestore/upsert");
            return mv;
        }
    }

    /**
     * 去查看充值记录
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toQueryHis")
    public ModelAndView toQueryHis(HttpServletRequest request) throws Exception {
       String organId = request.getParameter("_id");
        Organ organ = organService.queryById(organId);
        request.setAttribute("shopId", organ.get_id());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("prestore/queryHis");
        return mv;
    }

    /**
     * 分页显示单个店面预存款充值记录
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryHis")
    @ResponseBody
    public FlipInfo<Prestore> queryHis(HttpServletRequest request) throws Exception {
        FlipInfo<Prestore> fpi = new FlipPageInfo<Prestore>(request);
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
        fpi = organService.sysQueryChargeHis(fpi);
        return fpi;
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
