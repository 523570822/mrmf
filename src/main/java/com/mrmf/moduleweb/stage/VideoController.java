package com.mrmf.moduleweb.stage;

import com.mrmf.BaseController.BaseController;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.stage.StageCategoryFees;
import com.mrmf.entity.stage.Video;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.stage.StageCatFeeService;
import com.mrmf.service.stage.VideoService;
import com.osg.entity.Entity;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FileNameUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 公司镜台管理相关
 */
@Controller
@RequestMapping("/video")
public class VideoController extends BaseController {
    @Autowired
    private StageCatFeeService stageCatFeeService;
    @Autowired
    private OrganService organService;
    @Autowired
    private VideoService videoService;
    @RequestMapping("/toQueryVideo")
    public ModelAndView toQuery(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("stage/queryVideo");
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
            request.setAttribute("ffstageCategoryFees", stageCategoryFees);
            returnStatus = new ReturnStatus(true, " 操作成功");


        } else {
            stageCategoryFees = new StageCategoryFees();
         //   returnStatus = new ReturnStatus(false, " 操作失败");

        }

        List<Organ> organlist = organService.queryOrganList();


        ModelAndView mv = new ModelAndView();
        mv.addObject("organlist",organlist);
        mv.setViewName("stage/upsertVideo");
        return mv;
    }


    @RequestMapping("/upsert")
    public ModelAndView upsert(StageCategoryFees stageCategoryFees, BindingResult results, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isEmpty(stageCategoryFees.get_id())) {
            stageCategoryFees.setIdIfNew();
            stageCategoryFees.setCreateTimeIfNew();
        }
            stageCatFeeService.saveOrUpdate(stageCategoryFees);




                mv.setViewName("stage/queryVideo");



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
    @RequestMapping("/selectAllOrgan")
    @ResponseBody
    public Organ selectAllOrgan(){
     organService.queryCity("");
     return null;


    }
    @RequestMapping("/uploadFile")
    @ResponseBody
    public ReturnStatus uploadFile(@RequestParam("onlyFile") MultipartFile[] files, Video video, HttpServletRequest request)throws Exception{
        ReturnStatus returnStatus;
        returnStatus = new ReturnStatus(false, "上传失败");
        String etag = "";
        for (MultipartFile multipartfile : files) {
            String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(multipartfile.getOriginalFilename());


            etag = OSSFileUtil.upload(multipartfile.getInputStream(), multipartfile.getSize(), ossId, OSSFileUtil.pubBucketName);


        }
        if (etag!=null){
            video.setVideoUrl(etag);
            videoService.saveOrUpdate(video);

            returnStatus = new ReturnStatus(true, "上传成功");
}else{
   return  returnStatus;
}






   /*     String serverId = request.getParameter("serverId");
        WeToken weToken = redisService.getTonkenInfo("staff");

        URL url = new URL(new StringBuilder("https://api.weixin.qq.com/cgi-bin/media/get?access_token=").
                append(weToken.getToken()).append("&media_id=").append(serverId).toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream is = url.openStream();
        String imgName = weCommonService.downImg(is,request);
        String urlPath=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
        File imgFile=new File(urlPath);
        InputStream inFile=new FileInputStream(imgFile);
        String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
        String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
        is.close();
        inFile.close();*/
        return returnStatus;
    }
}
