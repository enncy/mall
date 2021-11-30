package cn.enncy.mall.service;


import cn.enncy.mall.mapper.TagMapper;
import cn.enncy.mall.pojo.Tag;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 12:04 2021/11/27
 *
 * @author enncy
 */
public interface TagService extends  BaseService<Tag>{

    Tag findOneByName(String name);

    List<Tag> findByCount();

    List<Tag> search(String key, int page, int size);

}

