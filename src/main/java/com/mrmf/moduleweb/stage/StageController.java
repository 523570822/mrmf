package com.mrmf.moduleweb.stage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 公司镜台管理相关
 */
@Controller
@RequestMapping("/stage")
public class StageController {
    @RequestMapping("/toQuery")
    public ModelAndView toQuery(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/bigsort/query");
        return mv;
    }





}
