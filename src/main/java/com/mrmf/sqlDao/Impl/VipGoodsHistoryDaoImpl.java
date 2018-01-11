package com.mrmf.sqlDao.Impl;

import com.mrmf.entity.sqlEntity.VipGoodsHistory;
import com.mrmf.sqlDao.VipGoodsHistoryDao;
import com.osg.entity.FlipInfo;
import com.osg.framework.util.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/9/4.
 */
@Component
public class VipGoodsHistoryDaoImpl implements VipGoodsHistoryDao {
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 分页查询
     * @param fpi
     * @return
     */
    public FlipInfo<VipGoodsHistory> allstudent(FlipInfo fpi) {
        Map map = fpi.getParams();
        String organId = map.get("organId").toString();
        String sidx = map.get("sidx").toString();
        String sord = map.get("sord").toString();
        String name = map.get("name").toString();
        String phone = map.get("phone").toString();

        String sql = "SELECT * FROM vipGoods_history where organ_id="+organId+ " AND status=0";
        String count = "SELECT count(*) FROM vipGoods_history where organ_id="+organId+ " AND status=0";
        if(!StringUtils.isEmpty(name)){
            sql+=" AND member_name LIKE '%"+name+"%'";
            count+=" AND member_name LIKE '%"+name+"%'";
        }
        if(!StringUtils.isEmpty(phone)){
            sql+=" AND phone LIKE '%"+phone+"%'";
            count+=" AND phone LIKE '%"+phone+"%'";
        }
        int num =jdbcTemplate.queryForInt(count);

        if(StringUtils.isEmpty(sidx)){
            sidx = "create_date";
            sord = "desc";
        }
        if(sidx.equals("createDate")){
            sidx = "create_date";
        }
        if(sidx.equals("goodsPrice")){
            sidx = "goods_price";
        }
        sql+=" ORDER BY "+sidx+" "+sord;
        sql+=" LIMIT "+(fpi.getPage()-1)*fpi.getSize()+","+fpi.getSize();
        List<VipGoodsHistory> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(VipGoodsHistory.class));
        fpi.setData(list);
        fpi.setTotal(num);
        return fpi;
    }
    public VipGoodsHistory findById(Long id){
        String sql= "SELECT * FROM vipGoods_history WHERE id="+id;
        List<VipGoodsHistory> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(VipGoodsHistory.class));
        return list.get(0);
    }

    /**
     * 修改审核订单状态
     * @param orderId
     * @param status
     * @return
     */
    public int update(Long orderId,int status,String msg){
        String update = "UPDATE vipGoods_history SET `desc`=? ,`status`=? WHERE id=?";
        Object[] args={msg,status,orderId};
        return jdbcTemplate.update(update,args);
    }
}
