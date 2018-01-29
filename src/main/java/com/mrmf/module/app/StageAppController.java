package com.mrmf.module.app;

import com.mrmf.entity.stage.StageMent;
import com.mrmf.service.stage.StageService;
import com.osg.entity.AndroidPoint;
import com.osg.entity.FaceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stageApp")
public class StageAppController {
@Autowired
    private StageService stageService;
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


}