package cn.enncy.mall.service;


import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mall.pojo.User;
import cn.enncy.mybatis.annotation.Mapper;
import cn.enncy.mybatis.core.SqlSession;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        ParameterizedType aClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypes = aClass.getActualTypeArguments();
        for (Type actualType : actualTypes) {
            Class<?> type = ((Class<?>) actualType);
            if (BaseMapper.class.isAssignableFrom(type)) {
                this.mapper = (M) SqlSession.getMapper(type);
            }
        }
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
    public List<T> findByPages(int page, int size) {
        return mapper.findByPages(page * size, size);
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
