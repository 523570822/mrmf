package com.mrmf.module.app;

import com.mrmf.entity.Organ;
import com.mrmf.entity.stage.StageMent;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.stage.StageService;
import com.osg.entity.AndroidPoint;
import com.osg.entity.FaceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stageApp")
public class StageAppController {
@Autowired
    private StageService stageService;
@Autowired
private OrganService organService;
    /**
     *
     * 镜台首次安装
     * {"phone":"15620512895","type":"user"}
     */
    @RequestMapping(value ="/firstInstall", method = RequestMethod.POST)
    @ResponseBody
    public      FaceStatus getCodeByPhone(StageMent stageMent, AndroidPoint androidPoint, HttpServletRequest request, HttpServletResponse response) {
        List<AndroidPoint> androidPoints= new ArrayList<AndroidPoint>();   androidPoints.add(androidPoint);
        stageMent.setAndroidPoints(androidPoints);

        FaceStatus status = stageService.upsert(stageMent);

        return status;


    }
    /**
     *
     * 镜台首次安装
     * {"phone":"15620512895","type":"user"}
     */
    @RequestMapping(value ="/first_code", method = RequestMethod.GET)
    public ModelAndView first_code(String devicedId, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        List<Organ> organlist = organService.queryOrganList();
        mv.setViewName("stage/first_code");
       mv.addObject("devicedId",devicedId);
       mv.addObject("organlist",organlist);

        return mv;

    }

    /**
     *
     * 检验是否首次安装镜台
     * {"phone":"15620512895","type":"user"}
     */
    @RequestMapping(value ="/first_test", method = RequestMethod.POST)
    @ResponseBody
    public      FaceStatus first_test(String devicedId, HttpServletRequest request, HttpServletResponse response) {

        FaceStatus status;
        StageMent stageMent = stageService.findOne(devicedId);

        if(stageMent!=null){
            status = new FaceStatus(true, "设备已经绑定");
        }else {
            status = new FaceStatus(false, "设备没有绑定");
        }




        return status;


    }


}
