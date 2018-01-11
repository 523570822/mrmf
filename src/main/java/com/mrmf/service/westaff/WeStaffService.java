package com.mrmf.service.westaff;

import com.mrmf.entity.Staff;

public interface WeStaffService {

    /**
     * 根据id获取技师
     * @param staffId
     * @return
     */
    Staff getStaffById(String staffId);

}
