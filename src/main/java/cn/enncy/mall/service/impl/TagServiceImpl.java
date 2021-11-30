package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.TagMapper;
import cn.enncy.mall.pojo.Tag;
import cn.enncy.mall.service.TagService;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:59 2021/11/30
 *
 * @author enncy
 */
public class TagServiceImpl  extends ServiceImpl<Tag, TagMapper> implements TagService {

    public TagServiceImpl( ) {
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
