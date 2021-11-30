package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mall.service.BaseService;
import cn.enncy.mybatis.core.SqlSession;

import java.util.List;

/**
 * 业务实现类
 * <br/>Created in 17:48 2021/11/30
 *
 * @author enncy
 */
public class ServiceImpl<T extends BaseObject, M extends BaseMapper<T>> implements BaseService<T> {
    public M mapper;

    public ServiceImpl(Class<M> mapperClass) {
        this.mapper = SqlSession.getMapper(mapperClass);

    }

    @Override
    public int count() {
        return mapper.count();
    }

    @Override
    public boolean insert(T baseObject) {
        long l = System.currentTimeMillis();
        baseObject.setCreateTime(l);
        baseObject.setUpdateTime(l);

        return mapper.insert(baseObject);
    }

    /**
     *  设为私有，建议使用 delete(T) 方法
     * @return boolean
     */
    @Override
    public boolean deleteById(long id) {
        return mapper.deleteById(id);
    }

    @Override
    public boolean delete(T baseObject) {
        return mapper.deleteById(baseObject.getId());
    }

    @Override
    public T findOneById(long id) {
        return mapper.findOneById(id);
    }

    @Override
    public List<T> findByPages(int page, int size) {

        size = size == 0 ? 10 : size;
        return mapper.findByPages(page * size, size);
    }

    @Override
    public List<T> findAll() {
        return mapper.findAll();
    }

    @Override
    public boolean update(T baseObject) {
        baseObject.setUpdateTime(System.currentTimeMillis());
        baseObject.setCreateTime(baseObject.getCreateTime());
        return mapper.update(baseObject);
    }
}