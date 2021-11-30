package cn.enncy.mall.service;


import cn.enncy.mall.mapper.OrderMapper;
import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.pojo.Order;
import cn.enncy.mall.pojo.OrderDetails;
import cn.enncy.mybatis.annotation.method.Transaction;
import cn.enncy.mybatis.core.ServiceFactory;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:21 2021/11/18
 *
 * @author enncy
 */
public interface OrderService extends BaseService<Order> {

    List<Order> findByUserId(long userId);


    Order findOneByUid(String uid);

    boolean deleteByUid(String uid);

    @Transaction
    void createSingleGoodsOrder(Order order, OrderDetails orderDetails, Goods goods);

    @Transaction
    void createOrder(Order order, List<OrderDetails> orderDetailsList, List<Cart> cartList, List<Goods> goodsList);

}
