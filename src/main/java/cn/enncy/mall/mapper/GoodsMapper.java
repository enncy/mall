package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Goods;
import cn.enncy.mybatis.annotation.Mapper;

/**
 * //TODO
 * <br/>Created in 23:23 2021/11/17
 *
 * @author: enncy
 */

@Mapper(table = "goods",target = Goods.class)
public interface GoodsMapper extends BaseMapper<Goods> {


}
