package com.mrmf.service.VipMember;

import com.mrmf.entity.Organ;
import com.mrmf.entity.shop.CheckVipGoodsHistory;
import com.mrmf.entity.sqlEntity.Member;
import com.mrmf.entity.sqlEntity.MrmfShop;
import com.mrmf.entity.sqlEntity.VipGoodsHistory;
import com.mrmf.entity.User;
import com.mrmf.entity.shop.VipMember;
import com.mrmf.service.organ.OrganService;
import com.mrmf.sqlDao.*;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 蔺哲 on 2017/6/27.
 */
@Service("VipMemberService")
public class VipMemberServiceImpl implements VipMemberService {
    @Autowired
    private EMongoTemplate mongoTemplate;

    @Autowired
    private VipGoodsHistoryDao vipGoodsHistoryDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private InformationDao informationDao;

    @Autowired
    private MrmfShopDao mrmfShopDao;

    @Autowired
    private OrganService organService;
    @Autowired
    private OrderDao orderDao;
    /**
     * 查询本店
     * @param fpi
     * @return
     */
    public FlipInfo<VipMember> query(FlipInfo<VipMember> fpi){
        String organId = (String) fpi.getParams().get("organId");
        fpi.getParams().remove("organId");
        fpi.getParams().remove("nd");
        fpi.getParams().remove("_search");
        fpi.getParams().remove("sidx");
        mongoTemplate.findByPage(Query.query(Criteria.where("organId").is(organId)),fpi,VipMember.class);
        return fpi;
    }
    /**
     * 查询店铺预存款
     */
    public double findPrestore(String organId){
        MrmfShop mrmfShop = mrmfShopDao.findByOrganId(organId);
        return mrmfShop==null?0.0:mrmfShop.getPrestore();
    }
    /**
     * 添加或修改本店VIP
     * @param vipMember
     * @return
     */
    public ReturnStatus upsert(VipMember vipMember){
        ReturnStatus returnStatus = new ReturnStatus(true);
        String phone = vipMember.getPhone();
        User user = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
        if(user==null){
            returnStatus.setSuccess(false);
            returnStatus.setMessage("该手机号未找到用户");
            return returnStatus;
        }
        VipMember oldVipMember = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(user.get_id()).and("organId").is(vipMember.getOrganId())),VipMember.class);
        if(oldVipMember!=null&& StringUtils.isEmpty(vipMember.get_id())){
            returnStatus.setSuccess(false);
            returnStatus.setMessage("本店已将该手机号的用户添加为vip");
            return returnStatus;
        }
        vipMember.setUserId(user.get_id());
        mongoTemplate.save(vipMember);
//        if(!user.getOrganIds().contains(vipMember.getOrganId())){
//            returnStatus.setMessage("本店已将该手机号的用户添加为vip,但未办理过会员卡");
//        }
        return returnStatus;
    }

    /**
     * 改变本店VIP会员状态
     * @param vipMemberId
     * @param state
     * @return
     */
    public ReturnStatus updateState(String vipMemberId, String state){
        ReturnStatus returnStatus = new ReturnStatus(true,"操作成功");
        VipMember vipMember = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(vipMemberId)),VipMember.class);
        if(vipMember==null){
            returnStatus.setSuccess(false);
            returnStatus.setMessage("操作失败");
            return returnStatus;
        }
        Integer stateCode  = new Integer(state);
        if(stateCode==3){//删除
            mongoTemplate.remove(Query.query(Criteria.where("_id").is(vipMemberId)),VipMember.class);
            return returnStatus;
        }
        if(stateCode==1){//可用
            vipMember.setState(true);
        }else if(stateCode==2){//不可用
            vipMember.setState(false);
        }
        mongoTemplate.save(vipMember);
        return returnStatus;
    }

    /**
     * 查询审核商品列表
     * @param fpi
     * @return
     */
    public FlipInfo queryList(FlipInfo fpi){
        return vipGoodsHistoryDao.allstudent(fpi);
    }

    /**
     * 查询当前店面的审核记录
     * @param fpi
     * @return
     */
    public FlipInfo<CheckVipGoodsHistory> queryHistory(FlipInfo fpi){
        return mongoTemplate.findByPage(null,fpi,CheckVipGoodsHistory.class);
    }
    /**
     *审核，扣钱
     * @param request
     * @return
     */
    @Transactional
    public ReturnStatus shenhe(HttpServletRequest request){
        String state = request.getParameter("state");
        String orderId = request.getParameter("orderId");
        String organId = request.getParameter("organId");
        //找到本订单
        VipGoodsHistory goodsOrder = vipGoodsHistoryDao.findById(Long.valueOf(orderId));
        BigDecimal price = new BigDecimal(goodsOrder.getGoodsPrice());
        String msg = "审核通过";//初始化msg
        int status = 0;//初始化status状态  0-失败 1-成功
        Integer stateCode = new Integer(state);//订单状态 0待审核 1未通过 2已通过
        if(stateCode==1){//审核通过 加0
            Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)), Organ.class);
            MrmfShop mrmfShop = mrmfShopDao.findByOrganId(organ.get_id());
            if(null == mrmfShop) {
                return new ReturnStatus(status, "未指定商城账号");
            }
            BigDecimal amount = BigDecimal.valueOf(mrmfShop.getPrestore());
            BigDecimal nowWalletAmount = amount.subtract(price);
            if(nowWalletAmount.doubleValue()<0){
                msg = "店铺余额不足";
                stateCode = 1;
            }else {
                mrmfShopDao.updatePrestore(mrmfShop.getId(),nowWalletAmount);
                stateCode = 2;
                status = 1;
            }
        }else {
            msg = "审核未通过";
            stateCode = 1;
        }
        String massge = "vip商品"+goodsOrder.getGoodsName()+(stateCode==1?"审核失败":"审核成功");
        //修改订单状态
        vipGoodsHistoryDao.update(Long.valueOf(orderId),stateCode,msg);
        orderDao.updateOrder(goodsOrder.getOrderId(),status==0?6:2);
        //发通知
        informationDao.insertInformationDao(goodsOrder.getGoodsId().toString(),goodsOrder.getMemberId(),null,"vip置换商品  "+goodsOrder.getGoodsName()+"  "+msg);
        //保存审核记录
        CheckVipGoodsHistory checkVipGoodsHistory = new CheckVipGoodsHistory(organId,price.doubleValue(),goodsOrder.getGoodsName(),Long.valueOf(orderId),stateCode,msg,goodsOrder.getMemberName(),goodsOrder.getPhone(),new Date());
        mongoTemplate.save(checkVipGoodsHistory);
        return new ReturnStatus(status,msg);
    }
    public VipMember queryById(String vipMemberId){
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(vipMemberId)),VipMember.class);
    }

    /**
     * 修改mysql 对应店管理员的member
     * @param
     * @return
     */
    @Transactional
    public ReturnStatus updateOrganMember(Organ organ,String newPhone){
        if(newPhone.equals(organ.getShopPhone())){
            return new ReturnStatus(0,"两次手机号一致");
        }
        //新绑定店铺管理员的member
        Member newMember = memberDao.queryByPhone(newPhone);
        if(newMember == null){
            return new ReturnStatus(0,"新管理员不存在");
        }
        MrmfShop newShop = mrmfShopDao.findByPhone(newMember.getId());
        if(newShop != null){
            return new ReturnStatus(0,"新管理员已被绑定关系");
        }
        MrmfShop mrmfShop = mrmfShopDao.findByOrganId(organ.get_id());
        if(mrmfShop != null){
            mrmfShop.setMemberId(newMember.getId());
        }else {
            mrmfShop = new MrmfShop(organ.get_id(),newMember.getId());
        }
        if(mrmfShop.getId()!=null){//修改
            mrmfShop.setVersion(mrmfShop.getVersion()+1);
            mrmfShop.setModifyDate(DateUtil.currentTimestamp());
            mrmfShopDao.updateMrmfShop(mrmfShop);
        }else {//插入
            mrmfShopDao.save(mrmfShop);
        }
        organ.setShopPhone(newPhone);
        organService.upsert(organ);
        return new ReturnStatus(1,"编辑成功");
    }
    /**
     * 修改技师，或用户手机号，对mysql的member表进行更改手机号操作
     */
    public boolean updateMemberByPhone(String oldPhone,String newPhone){
        Member oldMember = memberDao.queryByPhone(oldPhone);
        Member newMember = memberDao.queryByPhone(newPhone);
        if((oldMember != null) && (newMember == null)){
            memberDao.updateMemberPhone(oldMember.getId(),newPhone);
            return true;
        }else {
            return true;
        }
    }
}
