package cn.enncy.mall.service;


import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mybatis.core.SqlSession;
import cn.enncy.mybatis.utils.ParameterizedTypeUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 18:12 2021/11/6
 *
 * @author enncy
 */


public class BaseService<T extends BaseObject, M extends BaseMapper<T>> implements BaseMapper<T> {
    public M mapper;

    public BaseService() {
        // 获取第2个泛型，并且实例化
        Class<?> type = ParameterizedTypeUtils.getByType(this.getClass(), BaseMapper.class);
        this.mapper = (M) SqlSession.getMapper(type);
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

    @Override
    public boolean deleteById(long id) {
        return mapper.deleteById(id);
    }

    @Override
    public T findOneById(long id) {
        return mapper.findOneById(id);
    }

    @Override
    public List<T> findByPages(int page, int size) {
        return mapper.findByPages(page * size, size);
    }

    @Override
    public List<T> findAll() {
        return mapper.findAll();
    }

    @Override
    public boolean update(T baseObject) {
        System.out.println("update "+baseObject);
        baseObject.setUpdateTime(System.currentTimeMillis());
        baseObject.setCreateTime(baseObject.getCreateTime());
        return mapper.update(baseObject);
    }
}
