package cn.enncy.mall.service.impl;


import cn.enncy.mall.constant.OrderStatus;
import cn.enncy.mall.mapper.OrderMapper;
import cn.enncy.mall.pojo.*;
import cn.enncy.mall.service.*;
import cn.enncy.mybatis.annotation.method.Transaction;
import cn.enncy.mybatis.core.ServiceFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 17:59 2021/11/30
 *
 * @author enncy
 */
public class OrderServiceImpl extends ServiceImpl<Order, OrderMapper> implements OrderService {
    OrderDetailsService orderDetailsService = ServiceFactory.resolve(OrderDetailsService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    UserService userService = ServiceFactory.resolve(UserService.class);
    public OrderServiceImpl() {
        super(OrderMapper.class);
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public List<Order> search(String str, int page, int size) {
        return mapper.search(str, page, size);
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
    public void createSingleGoodsOrder(Order order, OrderDetails orderDetails, Goods goods) {
        initTime(order);
        // 添加订单
        mapper.insert(order);
        // 添加订单详情
        orderDetailsService.insert(orderDetails);
        // 减少库存
        goods.setStock(goods.getStock() - orderDetails.getCount());
        goodsService.update(goods);
    }

    @Override
    @Transaction
    public void createOrder(Order order, List<OrderDetails> orderDetailsList, List<Cart> cartList, List<Goods> goodsList) {
        initTime(order);
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
            goods.setStock(goods.getStock() - cart.getCount());
            goodsService.update(goods);
        }
    }

    /**
     * 退货，并且退款
     */
    @Override
    @Transaction
    public void returnOrder(Order order) {
        returnGoods(order);
        // 退款
        User user = userService.findOneById(order.getUserId());
        user.setBalance(user.getBalance().add(order.getTotalPrice()));
        userService.update(user);

        // 更新状态
        order.setStatus(OrderStatus.RETURN.value);
        update(order);
    }

    /**
     * 退货
     */
    @Override
    @Transaction
    public void cancelOrder(Order order) {
        returnGoods(order);
        // 更新状态
        order.setStatus(OrderStatus.CANCEL.value);
        update(order);
    }

    @Override
    public BigDecimal getSalesVolume(int day) {
        return mapper.getSalesVolume(day);
    }

    /**
     * 退货
     */
    public void returnGoods(Order order) {
        String uid = order.getUid();
        List<OrderDetails> orderDetailsList = orderDetailsService.findByOrderUid(uid);
        // 返还库存
        for (OrderDetails orderDetails : orderDetailsList) {
            Goods goods = goodsService.findOneById(orderDetails.getGoodsId());
            if (goods != null) {
                goods.setStock(goods.getStock() + orderDetails.getCount());
                goodsService.update(goods);
            }
        }
    }

    private void initTime(Order order) {
        long l = System.currentTimeMillis();
        order.setCreateTime(l);
        order.setUpdateTime(l);
    }


    @Override
    public List<Map<String, Object>> analysisGoodsSaleOfDay(int day) {
        return mapper.analysisGoodsSaleOfDay(day);
    }
}
