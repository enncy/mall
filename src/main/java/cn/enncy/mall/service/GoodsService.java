package cn.enncy.mall.service;


import cn.enncy.mall.mapper.GoodsMapper;
import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.service.impl.ServiceImpl;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:18 2021/11/18
 *
 * @author enncy
 */
public interface GoodsService   extends  BaseService<Goods>{


    public List<Goods> search(String str, int page, int size);

    public List<Goods> searchAll(String str);

    public List<Goods> findByTagName(String tag, int page, int size);

    public int countByTagName(String tag);

}
