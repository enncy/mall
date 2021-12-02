package cn.enncy.mall.controller;


import cn.enncy.mall.bean.Pagination;
import cn.enncy.mall.constant.OrderStatus;
import cn.enncy.mall.pojo.*;
import cn.enncy.mall.service.*;
import cn.enncy.mall.utils.StringUtils;
import cn.enncy.mybatis.core.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.params.Param;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 16:13 2021/11/27
 *
 * @author enncy
 */


@Controller
public class GoodsController {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);
    OrderService orderService = ServiceFactory.resolve(OrderService.class);
    UserService userService = ServiceFactory.resolve(UserService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    OrderDetailsService orderDetailsService = ServiceFactory.resolve(OrderDetailsService.class);

    @Get("/goods")
    public String goods(@Param("page") int page, @Param("size") int size, @Param("search") String search, @Param("tag") String tag, @Param("order") String order) {
        size = size == 0 ? 20 : size;

        List<Goods> goodsList;
        Pagination pagination;
        if (StringUtils.isEmpty(tag)) {
            List<Goods> searchAll = goodsService.searchAll(search);
            pagination = Pagination.createPagination(page, size, searchAll.size());
            goodsList = searchAll.stream().skip((long) page * size).limit(size).collect(Collectors.toList());
        } else {
            pagination = Pagination.createPagination(page, size, goodsService.countByTagName(tag));
            // 按照标签以及页码进行查询，并且筛选出符合 search 的商品
            if (StringUtils.isEmpty(search)) {
                goodsList = goodsService.findByTagName(tag, page, size);
            } else {
                goodsList = goodsService.findByTagName(tag, page, size).stream().filter(goods -> goods.getDescription().contains(search)).collect(Collectors.toList());
            }
        }

        // 排序
        if (StringUtils.notEmpty(order)) {
            if ("desc".equals(order)) {
                goodsList = goodsList.stream().sorted(Comparator.comparing(Goods::getRealPrice)).collect(Collectors.toList());
            } else {
                goodsList = goodsList.stream().sorted(Comparator.comparing(Goods::getRealPrice).reversed()).collect(Collectors.toList());
            }
        }

        // 分页
        request.setAttribute("pagination", pagination);
        request.setAttribute("goods", goodsList);

        return "/goods/index";
    }


    @Get("/goods/detail")
    public String detail(@Param("id") int id) {
        Goods goods = goodsService.findOneById(id);
        request.setAttribute("goods", goods);
        return "/goods/detail/index";
    }

    @Post("/goods/buy")
    public String postBuy(@Param("id") int id,@Param("count") int count) throws IOException {
        String error = null;
        User user = (User) session.getAttribute("user");
        String[] ids = request.getParameterValues("cartId");
        if(ids==null && id==0){
            error = "未选择商品";
        }else{

            if (user == null) {
                response.sendRedirect("/login");
            } else {

                try {
                    // 订单详情列表
                    ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
                    // 购物车列表
                    ArrayList<Cart> cartList = new ArrayList<>();
                    // 商品列表
                    ArrayList<Goods> goodsList = new ArrayList<>();

                    // 默认地址
                    Address defaultAddress = addressService.findOneById(user.getDefaultAddressId());

                    if (defaultAddress == null) {
                        error = "您未设置默认地址，或者默认地址已被删除，请设置后重新结算";
                    } else {
                        // 应付款
                        BigDecimal totalPrice = new BigDecimal("0.00");
                        // 创建订单
                        Order order = new Order();
                        order.setUserId(user.getId());
                        order.setAddressDetail(defaultAddress.createOrderAddressDetails());
                        order.setStatus(OrderStatus.PAYMENT.value);
                        order.setUid(Order.createUid(user.getId()));


                        // 直接购买 （单个）
                        if (id != 0) {

                            Goods goods = goodsService.findOneById(id);
                            if (count > goods.getStock()) {
                                error = "商品 <a href='/goods/detail?id=" + goods.getId() + "'>" + goods.getSimpleDescription() + "</a> 库存不足";
                            } else {
                                OrderDetails orderDetails = OrderDetails.createOrderDetails(order.getUid(), count, goods);
                                orderDetailsList.add(orderDetails);
                                // 计算价格
                                totalPrice = totalPrice.add(goods.getRealPrice().multiply(BigDecimal.valueOf(count)));
                                order.setTotalPrice(totalPrice);
                                // 创建订单
                                orderService.createSingleGoodsOrder(order, orderDetails, goods);
                            }
                        }
                        // 购物车添加 （多个商品）
                        else {
                            List<Integer> counts = Arrays.stream(request.getParameterValues("count")).map(Integer::parseInt).collect(Collectors.toList());

                            List<Long> cartIds = Arrays.stream(ids).map(Long::parseLong).collect(Collectors.toList());

                            for (int i = 0; i < cartIds.size(); i++) {

                                int c = counts.get(i);
                                Cart cart = cartService.findOneById(cartIds.get(i));
                                Goods goods = goodsService.findOneById(cart.getGoodsId());

                                if (c > goods.getStock()) {
                                    error = "商品 <a href='/goods/detail?id=" + goods.getId() + "'>" + goods.getSimpleDescription() + "</a> 库存不足";
                                    break;
                                }
                                cartList.add(cart);
                                goodsList.add(goods);
                                // 添加订单详情
                                orderDetailsList.add(OrderDetails.createOrderDetails(order.getUid(), c, goods));
                                // 计算价格
                                totalPrice = totalPrice.add(goods.getRealPrice().multiply(BigDecimal.valueOf(c)));
                            }

                            if (error == null) {
                                order.setTotalPrice(totalPrice);
                                // 生成多商品订单
                                orderService.createOrder(order, orderDetailsList, cartList, goodsList);
                            }
                        }

                        if (error == null) {
                            response.sendRedirect("/goods/pay?uid=" + order.getUid());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    error = "服务器错误";
                }
            }
        }



        request.setAttribute("error", error);
        return "/result/index";
    }


    @Get("/goods/pay")
    public String getPay(@Param("uid") String uid) throws IOException {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login");
        }else{
            Order order = orderService.findOneByUid(uid);
            List<OrderDetails> orderDetailsList = orderDetailsService.findByOrderUid(order.getUid());
            request.setAttribute("order", order);
            request.setAttribute("orderDetailsList", orderDetailsList);
        }

        return "/goods/pay/index";
    }

    @Post("/goods/pay")
    public String postPay(@Param("uid") String uid) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "/login/index";
        }
        if (StringUtils.isEmpty(uid)) {
            request.setAttribute("error", "参数错误！");
        } else {
            Order order = orderService.findOneByUid(uid);
            String status = order.getStatus();
            if(status.equals(OrderStatus.PAYMENT.value)){
                if (user.getBalance().compareTo(order.getTotalPrice()) < 0) {
                    request.setAttribute("error", "余额不足！");
                } else {
                    user.setBalance(user.getBalance().subtract(order.getTotalPrice()));
                    order.setStatus(OrderStatus.RECEIVING.value);
                    userService.update(user);
                    orderService.update(order);
                    request.setAttribute("msg", "付款成功！");
                }
            }else{
                request.setAttribute("error", "此订单已经结算！");
            }

        }

        request.setAttribute("redirect","/user/orders?status=payment");
        return "/result/index";
    }


    @Post("/goods/add")
    public String add(@Param("id") int id, @Param("count") int count, @Param("buy") String buy) throws IOException, ServletException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
        } else {
            if ("立即购买".equals(buy)) {
                return postBuy(id,count);
            } else {

                Goods goods = goodsService.findOneById(id);

                // 如果购物车已经存在，则只添加数量
                List<Cart> userCartList = cartService.findByUserId(user.getId());
                for (Cart cart : userCartList) {
                    if(cart.getGoodsId() == goods.getId()){
                        cart.setCount(cart.getCount() + count);
                        cartService.update(cart);
                        response.sendRedirect("/user/cart?id=" + cart.getId());
                    }
                }

                // 添加购物车
                Cart cart = new Cart();
                cart.setGoodsId(goods.getId());
                cart.setCount(count);
                cart.setUserId(user.getId());
                cartService.insert(cart);
                response.sendRedirect("/user/cart?id=" + cart.getId());
            }

        }

        return "/user/cart/index";

    }
}
