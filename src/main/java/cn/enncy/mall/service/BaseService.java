package cn.enncy.mall.service;


import cn.enncy.mall.pojo.BaseObject;
import java.util.List;

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
