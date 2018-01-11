package com.mrmf.sqlDao.Impl;

import com.mrmf.sqlDao.OrderDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by 蔺哲 on 2017/10/18.
 */
@Component
public class OrderDaoImpl implements OrderDao {
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    public void updateOrder(Long id,int status){
        String sql = "UPDATE `order` SET status="+status+" WHERE id="+id;
        jdbcTemplate.update(sql);
    }
}
