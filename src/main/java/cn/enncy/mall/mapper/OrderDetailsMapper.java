package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.OrderDetails;
import cn.enncy.mybatis.annotation.type.Mapper;

/**
 * //TODO
 * <br/>Created in 21:08 2021/11/29
 *
 * @author enncy
 */

@Mapper(table = "order_details",target = OrderDetails.class)
public interface OrderDetailsMapper  extends BaseMapper<OrderDetails>{


}
