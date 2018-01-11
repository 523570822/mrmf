package com.mrmf.sqlDao;

import com.mrmf.entity.sqlEntity.MrmfShop;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 蔺哲 on 2017/9/27.
 */
public interface MrmfShopDao {
    public MrmfShop findByOrganId(String organId);
    public int updatePrestore(Long id, BigDecimal prestore);
    public int save(final MrmfShop mrmfShop);
    public int updateMrmfShop(MrmfShop mrmfShop);
    public MrmfShop findByPhone(Long memberId,String... organId);
    public List<MrmfShop> findAll();
}
