package com.mrmf.moduleweb.stage;

import com.mrmf.entity.Staff;
import com.mrmf.entity.stage.StageCategoryFees;
import com.mrmf.service.stage.StageCatFeeService;
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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 公司镜台管理相关
 */
@Controller
@RequestMapping("/stage")
public class StageController {
    @Autowired
    private StageCatFeeService stageCatFeeService;
    @RequestMapping("/toQuerySort")
    public ModelAndView toQuery(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("stage/querySort");
        return mv;


    }
    @RequestMapping("/query")
    @ResponseBody
    public FlipInfo<StageCategoryFees> query(HttpServletRequest request) throws Exception {
        FlipInfo<StageCategoryFees> fpi = new FlipPageInfo<StageCategoryFees>(request);
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

        fpi = stageCatFeeService.query(fpi);
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
    /**
     * 新增分类
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpsert")
    public ModelAndView toUpsert(@RequestParam(required = false) String _id, HttpServletRequest request) throws Exception {
        ReturnStatus returnStatus;
        returnStatus = new ReturnStatus(false, " 操作失败");
        StageCategoryFees stageCategoryFees;
        if (!StringUtils.isEmpty(_id)) {
            stageCategoryFees = stageCatFeeService.queryById(_id);
            returnStatus = new ReturnStatus(true, " 操作成功");


        } else {
            stageCategoryFees = new StageCategoryFees();
        }


        request.setAttribute("returnStatus", returnStatus);
        request.setAttribute("ffstageCategoryFees", stageCategoryFees);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("stage/upsertSort");
        return mv;
    }


    @RequestMapping("/upsert")
    public ModelAndView upsert(StageCategoryFees stageCategoryFees, BindingResult results, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
    //   stageCatFeeService.saveOrUpdate(stageCategoryFees);




                mv.setViewName("stage/querySort");



        return mv;
    }

    @RequestMapping("/upsert123")
    public ModelAndView upsert123(Staff staff, String _id, BindingResult results, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        //  stageCategoryFees.set_id(_id);
        //     stageCatFeeService.saveOrUpdate(stageCategoryFees);




        mv.setViewName("stage/querySort");



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
