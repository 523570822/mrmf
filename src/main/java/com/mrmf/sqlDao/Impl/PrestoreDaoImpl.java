package com.mrmf.sqlDao.Impl;

import com.mrmf.entity.sqlEntity.Prestore;
import com.mrmf.sqlDao.PrestoreDao;
import com.osg.entity.FlipInfo;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/9/18.
 */
@Component
public class PrestoreDaoImpl implements PrestoreDao {
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public int save(final Double money, final String organId){
       return jdbcTemplate.update("insert into prestore(money,organ_id,type,create_date,modify_date,version) values(?,?,?,?,?,?)",new PreparedStatementSetter(){
           Timestamp a = DateUtil.currentTimestamp();
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setDouble(1,money);
                ps.setString(2,organId);
                ps.setInt(3,0);
                ps.setTimestamp(4,a);
                ps.setTimestamp(5,a);
                ps.setLong(6,0L);
            }
        });
    }
    public FlipInfo<Prestore> findByPage(FlipInfo fpi){
        Map map = fpi.getParams();
        Long memberId = Long.valueOf(map.get("shopId").toString());
        String sidx = map.get("sidx").toString();
        String sord = map.get("sord").toString();

        String sql = "SELECT * FROM prestore where organ_id="+memberId+" AND type=0";
        String count = "SELECT count(*) FROM prestore where organ_id="+memberId+" AND type=0";
        int num =jdbcTemplate.queryForInt(count);

        if(StringUtils.isEmpty(sidx)){
            sidx = "create_date";
            sord = "desc";
        }
        if(sidx.equals("createDate")){
            sidx = "create_date";
        }
        if(sidx.equals("money")){
            sidx = "money";
        }
        sql+=" ORDER BY "+sidx+" "+sord;
        sql+=" LIMIT "+(fpi.getPage()-1)*fpi.getSize()+","+fpi.getSize();
        List<Prestore> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Prestore.class));
        fpi.setData(list);
        fpi.setTotal(num);
        return fpi;
    }

}
