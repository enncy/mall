package cn.enncy.mall.service;


import cn.enncy.mall.mapper.GoodsMapper;
import cn.enncy.mall.pojo.Goods;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:18 2021/11/18
 *
 * @author enncy
 */
public class GoodsService extends BaseService<Goods, GoodsMapper>  implements GoodsMapper{


    @Override
    public List<Goods> findByNameLike(String name) {
        return mapper.findByNameLike(name);
    }

    @Override
    public List<Goods> search(String str, int page, int size) {
        return mapper.search(str,page,size == 0 ? 10 : size);
    }
}
