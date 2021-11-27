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

    public TagService( ) {
        super(TagMapper.class);
    }

    @Override
    public Tag findOneByName(String name) {
        return mapper.findOneByName(name);
    }

    @Override
    public List<Tag> findByCount() {
        return mapper.findByCount();
    }

    @Override
    public List<Tag> search(String key, int page, int size) {
        size = size == 0 ? 10 : size;
        return mapper.search(key, page * size, size );
    }
}
