package cn.enncy.mall.service;


import cn.enncy.mall.mapper.OrderMapper;
import cn.enncy.mall.pojo.Order;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:21 2021/11/18
 *
 * @author enncy
 */
public class OrderService extends BaseService<Order, OrderMapper> implements OrderMapper{


    public OrderService( ) {
        super(OrderMapper.class);
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return mapper.findByUserId(userId);
    }
}
