package com.mrmf.sqlDao;

import com.mrmf.entity.sqlEntity.Member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/9/16.
 */
public interface MemberDao {
    public Map findByPhone(String phone);
    public Member queryByPhone(String phone);
    public Member queryById(Long id);
    public int updatePrestore(Long id, BigDecimal prestore);
    public Map<String,Double> findByIds(List<String> ids);
    public int updateMemberPhone(Long id,String newPhone);
}
