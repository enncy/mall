package cn.enncy.mall.service;


import cn.enncy.mall.mapper.OrderMapper;
import cn.enncy.mall.pojo.*;
import cn.enncy.mybatis.annotation.method.Transaction;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.core.ServiceFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 17:21 2021/11/18
 *
 * @author enncy
 */
public interface OrderService extends BaseService<Order> {

    List<Order> findByUserId(long userId);


    List<Order> search(String str, int page, int size);

    Order findOneByUid(String uid);

    boolean deleteByUid(String uid);

    @Transaction
    void createSingleGoodsOrder(Order order, OrderDetails orderDetails, Goods goods);

    @Transaction
    void createOrder(Order order, List<OrderDetails> orderDetailsList, List<Cart> cartList, List<Goods> goodsList);


    @Transaction
    void returnOrder(Order order);

    @Transaction
    void cancelOrder(Order order);

    BigDecimal getSalesVolume(int day);


    List<Map<String,Object>> analysisGoodsSaleOfDay(int day);
}
