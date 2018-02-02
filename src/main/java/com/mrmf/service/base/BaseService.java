package com.mrmf.service.base;

import com.osg.entity.FlipInfo;

import java.util.List;

public interface BaseService<T> {
    public void saveOrUpdate(T t);
    public void deleteById(String id);
    public T queryById(String id);
    public List<T> query();
    public FlipInfo<T> query(FlipInfo<T> fpi);

}
