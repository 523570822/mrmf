package com.mrmf.moduleweb;

import com.mrmf.entity.Organ;
import com.mrmf.entity.OrganPositionSetting;
import com.mrmf.entity.PositionSetting;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.organPosition.OrganPosition;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
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
 * Created by Lin on 2017/3/26.
 */
@Controller
@RequestMapping("/OrganPosition")
public class OrganPositionController {
    @Autowired
    private OrganService organService;
    @Autowired
    private OrganPosition organPosition;
    @RequestMapping("/toQuery")
    public ModelAndView toQuery(@RequestParam(required = false) String parentId, HttpServletRequest request)
            throws Exception {
        Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
        String organId = (String) MAppContext.getSessionVariable("organId");
        if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
            if (StringUtils.isEmpty(organId)) {
                throw new BaseException("当前登录企业信息缺失！");
            } else {
                parentId = organId;
            }
        }
        if (!StringUtils.isEmpty(parentId) && !"0".equals(parentId)) {
            Organ organ = organService.queryById(parentId);
            request.setAttribute("parentOrgan", organ);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("organPosition/query");
        return mv;
    }
    @RequestMapping("/toAdd")
    public ModelAndView toAdd(String organId){
        ModelAndView mv = new ModelAndView();
        OrganPositionSetting opc = organPosition.queryPosition(organId);
        if(opc!=null){
            mv.addObject("opc",opc);
        }
        mv.addObject("organId",organId);
        mv.setViewName("organPosition/addPosition");
        return mv;
    }


    @RequestMapping("/doAdd")
    public void add(OrganPositionSetting opc, HttpServletResponse response) throws Exception{
        PrintWriter out = response.getWriter();
        opc.setState(0);
        List<PositionSetting> opcs = opc.getPositionSettingsList();
        List<String> imgs = opc.getImages();
       if(opcs!=null){
           if(opcs.size()>0){//进行非空处理
               List<PositionSetting> newPosition = new ArrayList<PositionSetting>();
               for (PositionSetting position:opcs){
                   if(position.getStaffAmount()!=null||position.getOrganAmount()!=null||position.getAdminAmount()!=null){
                       newPosition.add(position);
                   }
               }
               opc.setPositionSettingsList(newPosition);
           }
       }
        if(imgs!=null){
            if(imgs.size()>0){//进行非空处理
                List<String> newString = new ArrayList<String>();
                for (String str:imgs){
                    if(!StringUtils.isEmpty(str)){
                        newString.add(str);
                    }
                }
                opc.setImages(newString);
            }
        }
        if(StringUtils.isEmpty(opc.get_id())){
            organPosition.saveOrganPosition(opc);
            if(!StringUtils.isEmpty(opc.get_id())){
                out.print(true);
            }else{
                out.print(false);
            }
        }else{
            organPosition.saveOrganPosition(opc);
            out.print(true);
        }
    }
    @RequestMapping("/enable/{organId}")
    @ResponseBody
    public ReturnStatus enable(@PathVariable String organId) throws Exception{
        String state = "0";
        return organPosition.UpdateState(organId,state);
    }
    @RequestMapping("/disable/{organId}")
    @ResponseBody
    public ReturnStatus disable(@PathVariable String organId) throws Exception{
        String state = "1";
        return organPosition.UpdateState(organId,state);
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
