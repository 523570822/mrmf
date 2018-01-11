package com.mrmf.sqlDao.Impl;


import com.mrmf.sqlDao.InformationDao;
import com.osg.framework.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;


/**
 * Created by 蔺哲 on 2017/9/16.
 */
@Component
public class InformationDaoImpl implements InformationDao {
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertInformationDao(String link,Long memberId,Long staffId,String msg) {
        String sql = "INSERT INTO information (create_date,modify_date,version,title,content,type,status,action,link,member_id,is_delete)";
        Timestamp a = DateUtil.currentTimestamp();
        sql +=" VALUES ('"+a+"','"+a+"',0,'vip商品审核通知','"+msg+"',5,0,1,'"+link+"','"+memberId+"',0)";
        jdbcTemplate.update(sql);
    }


}
