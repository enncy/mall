package cn.enncy.mall.constant;

import cn.enncy.mall.pojo.*;
import cn.enncy.mall.service.*;

/**
 * //TODO
 * <br/>Created in 14:03 2021/11/25
 *
 * @author enncy
 */
public enum ServiceMapping {
    // 业务映射
    USER("user", "用户", User.class, UserService.class),
    ADDRESS("address", "地址", Address.class, AddressService.class),
    GOODS("goods", "商品", Goods.class, GoodsService.class),
    CART("cart", "购物车", Cart.class, CartService.class),
    ORDER("order", "订单", Order.class, OrderService.class),
    ;
    public String name;
    public String desc;
    public Class<? extends BaseObject> objectClass;
    public Class<? extends BaseService<?,?>> serviceClass;

    ServiceMapping(String name, String desc, Class<? extends BaseObject> objectClass, Class<? extends BaseService<?, ?>> serviceClass) {
        this.name = name;
        this.desc = desc;
        this.objectClass = objectClass;
        this.serviceClass = serviceClass;
    }
}
