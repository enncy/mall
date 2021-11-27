package cn.enncy.mall.service;


import cn.enncy.mall.mapper.GoodsMapper;
import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.pojo.Tag;
import cn.enncy.mall.utils.ServiceFactory;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:18 2021/11/18
 *
 * @author enncy
 */
public class GoodsService extends BaseService<Goods, GoodsMapper> implements GoodsMapper {

    public GoodsService() {
        super(GoodsMapper.class);
    }

    @Override
    public List<Goods> findByNameLike(String name) {
        return mapper.findByNameLike(name);
    }

    @Override
    public List<Goods> search(String str, int page, int size) {
        size =  size == 0 ? 10 : size;
        return mapper.search(str, page * size, size );
    }

    @Override
    public List<Goods> searchAll(String str) {
        return mapper.searchAll(str);
    }

    @Override
    public List<Goods> findByTagName(String tag,int page, int size) {
        size = size == 0 ? 10 : size;
        return mapper.findByTagName(tag,page * size,  size);
    }

    @Override
    public int countByTagName(String tag) {
        return mapper.countByTagName(tag);
    }

}
