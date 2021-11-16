package cn.enncy.mall.service;


import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mybatis.annotation.Mapper;
import cn.enncy.mybatis.core.SqlSession;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 18:12 2021/11/6
 *
 * @author enncy
 */


public class BaseService<T extends BaseObject> implements BaseMapper<T> {
    BaseMapper<T> mapper;

    public BaseService(Class<BaseMapper<T>> mapperClass ) {
        this.mapper = SqlSession.getMapper(mapperClass);
    }

    @Override
    public boolean insert(T baseObject) {
        return mapper.insert(baseObject);
    }

    @Override
    public boolean deleteById(long id) {
        return mapper.deleteById(id);
    }

    @Override
    public T findOneById(long id) {
        return mapper.findOneById(id);
    }

    @Override
    public List<T> findByPages(int skip, int size) {
        return mapper.findByPages(skip,size);
    }

    @Override
    public List<T> findAll() {
        return mapper.findAll();
    }

    @Override
    public boolean update(T baseObject) {
        return mapper.update(baseObject);
    }
}
