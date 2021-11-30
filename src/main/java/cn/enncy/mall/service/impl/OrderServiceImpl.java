package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.OrderMapper;
import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.pojo.Order;
import cn.enncy.mall.pojo.OrderDetails;
import cn.enncy.mall.service.*;
import cn.enncy.mybatis.annotation.method.Transaction;
import cn.enncy.mybatis.core.ServiceFactory;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:59 2021/11/30
 *
 * @author enncy
 */
public class OrderServiceImpl  extends ServiceImpl<Order,OrderMapper> implements OrderService {
    OrderDetailsService orderDetailsService = ServiceFactory.resolve(OrderDetailsService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);

    public OrderServiceImpl( ) {
        super(OrderMapper.class);
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public Order findOneByUid(String uid) {
        return mapper.findOneByUid(uid);
    }

    @Override
    public boolean deleteByUid(String uid) {
        return mapper.deleteByUid(uid);
    }


    @Override
    @Transaction
    public void createSingleGoodsOrder(Order order, OrderDetails orderDetails, Goods goods){
        // 添加订单
        mapper.insert(order);
        // 添加订单详情
        orderDetailsService.insert(orderDetails);
        // 减少库存
        goods.setStock(goods.getStock()-orderDetails.getCount());
        goodsService.update(goods);
    }

    @Override
    @Transaction
    public void createOrder(Order order, List<OrderDetails> orderDetailsList, List<Cart> cartList, List<Goods> goodsList){
        // 添加订单
        mapper.insert(order);
        // 添加订单详情
        for (OrderDetails orderDetails : orderDetailsList) {
            orderDetailsService.insert(orderDetails);
        }
        // 删除购物车
        // 减少商品库存
        for (int i = 0; i < cartList.size(); i++) {
            Cart cart = cartList.get(i);
            Goods goods = goodsList.get(i);
            cartService.delete(cart);
            goods.setStock(goods.getStock()-cart.getCount());
            goodsService.update(goods);
        }
    }


}
