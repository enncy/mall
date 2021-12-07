package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.GoodsMapper;
import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.service.GoodsService;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:59 2021/11/30
 *
 * @author enncy
 */
public class GoodsServiceImpl extends  ServiceImpl<Goods,GoodsMapper> implements GoodsService {

    public GoodsServiceImpl() {
        super(GoodsMapper.class);
    }
    @Override
    public Goods findOneById(long id) {
        // 增加浏览量
        Goods goods = super.findOneById(id);
        goods.setViews(goods.getViews()+1);
        mapper.update(goods);
        return goods;
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
