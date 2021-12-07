package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.CartMapper;
import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.service.CartService;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:59 2021/11/30
 *
 * @author enncy
 */
public class CartServiceImpl extends ServiceImpl<Cart, CartMapper> implements CartService {

    public CartServiceImpl() {
        super(CartMapper.class);
    }

    @Override
    public List<Cart> findByUserId(long userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public List<Cart> search(String str, int page, int size) {
        return mapper.search(str, page, size);
    }

}
