package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.OrderDetailsMapper;
import cn.enncy.mall.pojo.OrderDetails;
import cn.enncy.mall.service.OrderDetailsService;

/**
 * //TODO
 * <br/>Created in 17:59 2021/11/30
 *
 * @author enncy
 */
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetails, OrderDetailsMapper> implements OrderDetailsService {
    public OrderDetailsServiceImpl( ) {
        super(OrderDetailsMapper.class);
    }
}
