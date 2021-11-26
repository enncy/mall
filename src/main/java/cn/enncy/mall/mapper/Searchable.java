package cn.enncy.mall.mapper;

import cn.enncy.mybatis.annotation.param.Param;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 21:29 2021/11/25
 *
 * @author enncy
 */
public interface Searchable<T> {
    List<T> search(String str,int page,int size);
}
