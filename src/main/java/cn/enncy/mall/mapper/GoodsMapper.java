package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Goods;

import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.method.Select;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 23:23 2021/11/17
 *
 * @author  enncy
 */

@Mapper(table = "goods",target = Goods.class)
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("select * from #{"+ TABLE_NAME+"} where name like '%#{name}%'")
    List<Goods> findByNameLike(@Param("name") String name);

}
