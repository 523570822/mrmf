package com.mrmf.service.base;

import com.osg.entity.FlipInfo;
import com.osg.framework.mongodb.EMongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
@Service(value="baseService")
@SuppressWarnings("unchecked")
@Lazy(value=true)//延迟初始化，防止自身实例化时，调用构造BaseServiceImpl方法出现object类型转换异常
public class BaseServiceImpl<T> implements BaseService<T> {
    private Class clazz;//存储了具体操作的类,
    public BaseServiceImpl(){

        System.out.println(this);
        System.out.println(this.getClass());
        System.out.println(this.getClass().getSuperclass());
        System.out.println(this.getClass().getGenericSuperclass());
        Type type= this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType=(ParameterizedType) type;
        clazz= (Class) parameterizedType.getActualTypeArguments()[0];
        System.out.println(clazz);
    }
    @Autowired
    private EMongoTemplate mongoTemplate;


    @Override
    public void saveOrUpdate(T t) {
        mongoTemplate.save(t);
    }

    @Override
    public void deleteById(int id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,clazz);

    }

    @Override
    public  T queryById(int id) {
        Object dd = mongoTemplate.findById(id, clazz);
        return (T)dd;
    }

    @Override
    public List<T> query() {
        mongoTemplate.findAll(clazz);
        return null;
    }

    @Override
    public FlipInfo<T> query(FlipInfo<T> fpi) {

        fpi.setSortField("createTime");
        fpi.setSortOrder("DESC");
     //   mongoTemplate.by

        fpi = mongoTemplate.findByPage(null,fpi,clazz);


        return fpi;
    }
}
