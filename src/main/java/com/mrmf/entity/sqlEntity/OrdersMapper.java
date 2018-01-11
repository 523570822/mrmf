package com.mrmf.entity.sqlEntity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 蔺哲 on 2017/9/1.
 */
public class OrdersMapper implements RowMapper<Orders> {
    public Orders mapRow(ResultSet rs, int rownum) throws SQLException {
        Orders order = new Orders();
        order.setId(rs.getString("id"));
        order.setPhone(rs.getString("phone"));
        order.setPrice(rs.getInt("price"));
        order.setConsignee(rs.getString("consignee"));
        return order;
    }
}
