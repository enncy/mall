package cn.enncy.mall.service;


import cn.enncy.mall.mapper.CartMapper;
import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.service.impl.ServiceImpl;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:21 2021/11/18
 *
 * @author enncy
 */
public interface CartService extends  BaseService<Cart>{
    List<Cart> findByUserId(long userId);
}
