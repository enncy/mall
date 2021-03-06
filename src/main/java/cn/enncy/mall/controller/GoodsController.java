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
    CommentService commentService = ServiceFactory.resolve(CommentService.class);

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
            // ???????????????????????????????????????????????????????????? search ?????????
            if (StringUtils.isEmpty(search)) {
                goodsList = goodsService.findByTagName(tag, page, size);
            } else {
                goodsList = goodsService.findByTagName(tag, page, size).stream().filter(goods -> goods.getDescription().contains(search)).collect(Collectors.toList());
            }
        }

        // ??????
        if (StringUtils.notEmpty(order)) {
            if ("desc".equals(order)) {
                goodsList = goodsList.stream().sorted(Comparator.comparing(Goods::getRealPrice)).collect(Collectors.toList());
            } else {
                goodsList = goodsList.stream().sorted(Comparator.comparing(Goods::getRealPrice).reversed()).collect(Collectors.toList());
            }
        }

        // ??????
        request.setAttribute("pagination", pagination);
        request.setAttribute("goods", goodsList);

        return "/goods/index";
    }


    @Get("/goods/detail")
    public String detail(@Param("id") int id) {
        Goods goods = goodsService.findOneById(id);
        List<Comment> comments = commentService.findByGoodsId(goods.getId());
        request.setAttribute("comments",comments);
        request.setAttribute("goods", goods);
        return "/goods/detail/index";
    }


    @Post("/goods/comment")
    public void comment(@Param("id") int id,@Param("content") String content,@Param("parent") int parent) throws IOException {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login");
        }else{
            Goods goods = goodsService.findOneById(id);
            Comment comment = new Comment();
            comment.setGoodsId(goods.getId());
            comment.setContent(content);
            comment.setUserId(user.getId());
            comment.setParentId(parent);
            commentService.insert(comment);
            request.setAttribute("goods", goods);

            response.sendRedirect("/goods/detail?id="+id);
        }

    }
    @Get("/goods/comment/delete")
    public void commentDelete(@Param("id") int id,@Param("userId") int userId,@Param("goodsId") int goodsId) throws IOException {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login");
        }else{
            if(user.getId()==userId){
                commentService.deleteById(id);
                response.sendRedirect("/goods/detail?id="+goodsId);
            }else{
                response.sendRedirect("/error?code=400");
            }
        }

    }

    @Post("/goods/buy")
    public String postBuy(@Param("id") int id,@Param("count") int count) throws IOException {
        String error = null;
        User user = (User) session.getAttribute("user");
        String[] ids = request.getParameterValues("cartId");

        if (user == null) {
            response.sendRedirect("/login");
        } else if(ids==null && id==0){
            error = "???????????????";
        }else{
            try {
                // ??????????????????
                ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
                // ???????????????
                ArrayList<Cart> cartList = new ArrayList<>();
                // ????????????
                ArrayList<Goods> goodsList = new ArrayList<>();

                // ????????????
                Address defaultAddress = addressService.findOneById(user.getDefaultAddressId());

                if (defaultAddress == null) {
                    error = "????????????????????????????????????????????????????????????????????????????????????";
                } else {
                    // ?????????
                    BigDecimal totalPrice = new BigDecimal("0.00");
                    // ????????????
                    Order order = new Order();
                    String uid = Order.createUid(user.getId());
                    order.setUserId(user.getId());
                    order.setAddressDetail(defaultAddress.createOrderAddressDetails());
                    order.setStatus(OrderStatus.PAYMENT.value);
                    order.setUid(uid);

                    // ???????????? ????????????
                    if (id != 0) {

                        Goods goods = goodsService.findOneById(id);
                        if (count > goods.getStock()) {
                            error = "?????? <a href='/goods/detail?id=" + goods.getId() + "'>" + goods.getSimpleDescription() + "</a> ????????????";
                        } else {
                            OrderDetails orderDetails = OrderDetails.createOrderDetails(order.getUid(), count, goods);
                            orderDetailsList.add(orderDetails);
                            // ????????????
                            totalPrice = totalPrice.add(goods.getRealPrice().multiply(BigDecimal.valueOf(count)));
                            order.setTotalPrice(totalPrice);
                            // ????????????
                            orderService.createSingleGoodsOrder(order, orderDetails, goods);
                        }
                    }
                    // ??????????????? ??????????????????
                    else {
                        List<Integer> counts = Arrays.stream(request.getParameterValues("count")).map(Integer::parseInt).collect(Collectors.toList());
                        List<Long> cartIds = Arrays.stream(ids).map(Long::parseLong).collect(Collectors.toList());
                        for (int i = 0; i < cartIds.size(); i++) {
                            int c = counts.get(i);
                            Cart cart = cartService.findOneById(cartIds.get(i));
                            Goods goods = goodsService.findOneById(cart.getGoodsId());
                            if (c > goods.getStock()) {
                                error = "?????? <a href='/goods/detail?id=" + goods.getId() + "'>" + goods.getSimpleDescription() + "</a> ????????????";
                                break;
                            }
                            cartList.add(cart);
                            goodsList.add(goods);
                            // ??????????????????
                            orderDetailsList.add(OrderDetails.createOrderDetails(uid, c, goods));
                            // ????????????
                            totalPrice = totalPrice.add(goods.getRealPrice().multiply(BigDecimal.valueOf(c)));
                        }

                        if (error == null) {
                            order.setTotalPrice(totalPrice);
                            // ?????????????????????
                            orderService.createOrder(order, orderDetailsList, cartList, goodsList);
                        }
                    }

                    if (error == null) {
                        response.sendRedirect("/goods/pay?uid=" + order.getUid());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                error = "???????????????";
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
            request.setAttribute("error", "???????????????");
        } else {
            Order order = orderService.findOneByUid(uid);
            String status = order.getStatus();
            if(status.equals(OrderStatus.PAYMENT.value)){
                if (user.getBalance().compareTo(order.getTotalPrice()) < 0) {
                    request.setAttribute("error", "???????????????");
                } else {
                    user.setBalance(user.getBalance().subtract(order.getTotalPrice()));
                    order.setStatus(OrderStatus.RECEIVING.value);
                    userService.update(user);
                    orderService.update(order);
                    request.setAttribute("msg", "???????????????");
                }
            }else{
                request.setAttribute("error", "????????????????????????");
            }

        }

        request.setAttribute("redirect","/user/orders?status=payment");
        return "/result/index";
    }


    @Post("/goods/add/cart")
    public String add(@Param("id") int id, @Param("count") int count, @Param("buy") String buy) throws IOException, ServletException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
        } else {
            if ("????????????".equals(buy)) {
                return postBuy(id,count);
            } else {

                Goods goods = goodsService.findOneById(id);

                // ????????????????????????????????????????????????
                List<Cart> userCartList = cartService.findByUserId(user.getId());
                for (Cart cart : userCartList) {
                    if(cart.getGoodsId() == goods.getId()){
                        cart.setCount(cart.getCount() + count);
                        cartService.update(cart);
                        response.sendRedirect("/user/cart?id=" + cart.getId());
                        return "/user/cart/index";
                    }
                }
                // ???????????????
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
