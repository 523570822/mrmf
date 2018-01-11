package com.mrmf.sqlDao.Impl;

import com.mrmf.entity.sqlEntity.Member;
import com.mrmf.entity.sqlEntity.MrmfShop;
import com.mrmf.sqlDao.MemberDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/9/16.
 */
@Component
public class MemberDaoImpl implements MemberDao {
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**根据手机号查用户
     * @param phone
     * @return
     */
    @Override
    public Map findByPhone(String phone) {
        String sql = "SELECT * FROM member WHERE phone="+phone;
        return jdbcTemplate.queryForMap(sql);
    }
    @Override
    public Member queryByPhone(String phone) {
        String sql = "SELECT * FROM member WHERE phone=?";
        Object[] args={phone};
        List<Member> list = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper(Member.class));
        return list.isEmpty()?null:list.get(0);
    }
    /**
     * 根据id查询member
     * @param id
     * @return
     */
    public Member queryById(Long id){
        String sql = "SELECT * FROM member WHERE id=?";
        Object[] args={id};
        List<Member> list = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper(Member.class));
        return list.isEmpty()?null:list.get(0);
    }
    /**
     * 查询店面并加上 预存款字段
     * @param ids
     * @return
     */
    @Override
    public Map<String,Double> findByIds(List<String> ids) {
        Map<String,Double> result = new HashMap<>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        String sql = "SELECT * FROM mrmf_shop WHERE organ_id IN (:ids)";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ids",ids);
        List<MrmfShop> list =jdbc.query(sql, paramMap, new RowMapper<MrmfShop>() {
            @Override
            public MrmfShop mapRow(ResultSet resultSet, int i) throws SQLException {
                MrmfShop mrmfShop = new MrmfShop();
                mrmfShop.setId(resultSet.getLong("id"));
                mrmfShop.setPrestore(resultSet.getDouble("prestore"));
                mrmfShop.setOrganId(resultSet.getString("organ_id"));
                return mrmfShop;
            }
        });
        for(MrmfShop m:list){
            result.put(m.getOrganId(),m.getPrestore());
        }
        return result;
    }
    /**修改店面预存款
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
     * 修改member的手机号
     * @param
     * @return
     */
    public int updateMemberPhone(Long id,String newPhone){
        String sql = "UPDATE member SET phone="+newPhone+" WHERE id="+id;
        return jdbcTemplate.update(sql);
    }
}
