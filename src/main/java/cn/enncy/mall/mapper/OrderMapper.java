package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Order;
import cn.enncy.mybatis.annotation.Mapper;
import cn.enncy.mybatis.annotation.Param;
import cn.enncy.mybatis.annotation.Select;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 14:25 2021/11/18
 *
 * @author  enncy
 */

@Mapper(table = "order",target = Order.class)
public interface OrderMapper extends  BaseMapper<Order> {

    @Select("select * from #{"+ TABLE_NAME+"} where user_id = #{user_id}")
    List<Order> findByUserId(@Param("user_id") long userId);


}
