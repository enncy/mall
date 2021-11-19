package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Cart;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.method.Select;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 14:25 2021/11/18
 *
 * @author  enncy
 */

@Mapper(table = "cart",target = Cart.class)
public interface CartMapper extends BaseMapper<Cart> {
    @Select("select * from #{"+ TABLE_NAME+"} where user_id = #{user_id}")
    List<Cart> findByUserId(@Param("user_id") long userId);

}
