package com.mrmf.service.coupon;

import com.mrmf.entity.coupon.ConponRecord;
import com.osg.entity.FlipInfo;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/7/21.
 */
public interface CouponRecordService {
    public FlipInfo<ConponRecord> queryByFpi(FlipInfo<ConponRecord> fpi);

    List<ConponRecord> queryByList(FlipInfo<ConponRecord> fpi);

    Map statistics(HttpServletRequest request) throws Exception;
}
