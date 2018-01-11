package com.mrmf.sqlDao.Impl;

import com.mrmf.entity.sqlEntity.MrmfShop;
import com.mrmf.sqlDao.MrmfShopDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

/**
 * Created by 蔺哲 on 2017/9/27.
 */
@Component
public class MrmfShopDaoImpl implements MrmfShopDao {

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据id查询mysql店铺表
     * @param organId 店面id
     * @return
     */
    public MrmfShop findByOrganId(String organId){
        String sql = "SELECT * FROM mrmf_shop WHERE organ_id="+organId;
        List<MrmfShop> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(MrmfShop.class));
        return list.isEmpty()?null:list.get(0);
    }

    /**
     * 查询所有门店
     * @return
     */
    public List<MrmfShop> findAll(){
        String sql = "SELECT * FROM mrmf_shop ";
        List<MrmfShop> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(MrmfShop.class));
        return list;
    }
    /**
     * 根据memberId查找店面
     * @param memberId
     * @param organId
     * @return
     */
    public MrmfShop findByPhone(Long memberId,String... organId){
        String sql = "SELECT * FROM mrmf_shop WHERE member_id="+memberId;
        if(organId.length!=0){
            sql +=" AND organ_id="+organId[0];
        }
        List<MrmfShop> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(MrmfShop.class));
        return list.isEmpty()?null:list.get(0);
    }

    /**
     * 修改店铺预存款
     * @param id
     * @param prestore
     * @return
     */
    @Override
    public int updatePrestore(Long id, BigDecimal prestore) {
        String sql = "UPDATE mrmf_shop SET prestore=? WHERE id=?";
        Object[] args={prestore,id};
        return jdbcTemplate.update(sql,args);
    }

    /**
     * 修改mrmfshop
     * @param mrmfShop
     * @return
     */
    public int updateMrmfShop(MrmfShop mrmfShop) {
        String sql = "UPDATE mrmf_shop SET member_id=?,modify_date=?,version=? WHERE id=?";
        Object[] args={mrmfShop.getMemberId(),mrmfShop.getModifyDate(),mrmfShop.getVersion(),mrmfShop.getId()};
        return jdbcTemplate.update(sql,args);
    }

    /**
     * 插入数据，返回id
     * @param mrmfShop
     * @return
     */
    public int save(final MrmfShop mrmfShop){
        final Object[] args = {mrmfShop.getCommission(),mrmfShop.getPrestore(),mrmfShop.getOrganId(),mrmfShop.getMemberId(),mrmfShop.getCreateDate(),mrmfShop.getModifyDate(),mrmfShop.getVersion()};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("insert into mrmf_shop(commission,prestore,organ_id,member_id,create_date,modify_date,version) values(?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                for(int i=1;i<=args.length;i++){
                    ps.setObject(i,args[i-1]);
                }
                return ps;
            }
        }, keyHolder);
        int generatedId = keyHolder.getKey().intValue();
        return generatedId;
    }
}
