package cn.enncy.mall.service;


import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mybatis.annotation.method.*;
import cn.enncy.mybatis.annotation.param.Body;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.SqlSession;
import cn.enncy.mybatis.core.result.SingleResultHandler;
import cn.enncy.mybatis.utils.ParameterizedTypeUtils;

import java.time.LocalDateTime;
import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.*;

/**
 * //TODO
 * <br/>Created in 18:12 2021/11/6
 *
 * @author enncy
 */


public interface BaseService<T extends BaseObject> {

    int count();

    boolean insert(T baseObject);

    boolean deleteById(long id);

    boolean delete(T baseObject);

    T findOneById(long id);

    List<T> findByPages(int skip, int limit);

    List<T> findAll();

    boolean update(T baseObject);
}
