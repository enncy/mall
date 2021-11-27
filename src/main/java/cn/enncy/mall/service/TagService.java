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
public class TagService extends BaseService<Tag, TagMapper> implements TagMapper {

    @Override
    public Tag findOneByName(String name) {
        return mapper.findOneByName(name);
    }

    @Override
    public List<Tag> search(String key, int page, int size) {
        return mapper.search(key, page, size == 0 ? 10 : size);
    }
}
