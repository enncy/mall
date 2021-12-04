package cn.enncy.mall.constant;

import cn.enncy.mall.pojo.*;
import cn.enncy.mall.pojo.Tag;
import cn.enncy.mall.service.*;
import cn.enncy.mall.service.goods.GoodsTagService;
import cn.enncy.mall.service.impl.*;

/**
 * //TODO
 * <br/>Created in 14:03 2021/11/25
 *
 * @author enncy
 */
public enum ServiceMapping {
    // 业务映射
    USER("user", "用户", User.class,UserService.class, UserServiceImpl.class),
    ADDRESS("address", "地址", Address.class,AddressService.class, AddressServiceImpl.class),
    GOODS("goods", "商品", Goods.class,GoodsService.class, GoodsServiceImpl.class),
    CART("cart", "购物车", Cart.class,CartService.class, CartServiceImpl.class),
    ORDER("order", "订单", Order.class,OrderService.class, OrderServiceImpl.class),
    ORDER_DETAILS("order_details", "订单详情", OrderDetails.class,OrderDetailsService.class, OrderDetailsServiceImpl.class),
    TAG("tag", "标签", Tag.class, TagService.class,TagServiceImpl.class),
    COMMENT("comment","评论",Comment.class,CommentService.class,CommentServiceImpl.class);


    public String name;
    public String desc;
    public Class<? extends BaseObject> objectClass;
    public Class<? extends BaseService<?>> serviceClass;
    public Class<? extends ServiceImpl<?, ?>> serviceImplClass;
    ServiceMapping(String name, String desc, Class<? extends BaseObject> objectClass,Class<? extends BaseService<?>> serviceClass, Class<? extends ServiceImpl<?, ?>> serviceImplClass) {
        this.name = name;
        this.desc = desc;
        this.objectClass = objectClass;
        this.serviceClass = serviceClass;
        this.serviceImplClass = serviceImplClass;
    }
}
