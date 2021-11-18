package cn.enncy.mall.service;


import cn.enncy.mall.mapper.CartMapper;
import cn.enncy.mall.pojo.Cart;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:21 2021/11/18
 *
 * @author enncy
 */
public class CartService extends BaseService<Cart, CartMapper> implements  CartMapper{


    @Override
    public List<Cart> findByUserId(long userId) {
        return mapper.findByUserId(userId);
    }
}
