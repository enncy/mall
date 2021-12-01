package cn.enncy.mall.controller;


import cn.enncy.mall.constant.OrderStatus;
import cn.enncy.mall.pojo.*;
import cn.enncy.mall.service.*;
import cn.enncy.mall.utils.StringUtils;
import cn.enncy.mybatis.core.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.params.Body;
import cn.enncy.spring.mvc.annotation.params.Param;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 23:51 2021/11/20
 *
 * @author enncy
 */

@Controller
public class UserController {

    UserService userService = ServiceFactory.resolve(UserService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    OrderService orderService = ServiceFactory.resolve(OrderService.class);

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    @Get("/user")
    public String userGet() {
        // 刷新用户
        User user = (User) session.getAttribute("user");
        user = userService.findOneById(user.getId());
        session.setAttribute("user",user);
        return "/user/index";
    }

    @Get("/user/address")
    public String address() {
        User user = (User) session.getAttribute("user");
        List<Address> all = addressService.findByUserId(user.getId());
        request.setAttribute("addresses", all);
        return "/user/address/index";
    }

    @Get("/user/cart")
    public String cart() {
        User user = (User) session.getAttribute("user");
        List<Cart> all = cartService.findByUserId(user.getId());
        request.setAttribute("carts", all);
        return "/user/cart/index";
    }

    @Get("/user/orders")
    public String order() {
        User user = (User) session.getAttribute("user");
        List<Order> all = orderService.findByUserId(user.getId());
        request.setAttribute("orders", all);
        return "/user/orders/index";
    }

    @Post("/user")
    public String userPost(@Body User user) {
        userService.update(user);
        session.setAttribute("user", user);
        return "/user/index";
    }


    @Get("/user/balance")
    public String balanceGet() {
        return "/user/balance/index";
    }

    @Post("/user/balance")
    public String balancePost(@Param("balance") String balance) {
        if (StringUtils.notEmpty(balance)) {
            UserService userService = ServiceFactory.resolve(UserService.class);
            User user = (User) session.getAttribute("user");
            if (user != null) {
                BigDecimal origin = user.getBalance();
                user.setBalance(origin.add(BigDecimal.valueOf(Double.parseDouble(balance))));
                userService.update(user);
                return "/user/index";
            }
        } else {
            request.setAttribute("error", "余额不能为0!");
        }
        return "/user/balance/index";
    }

    @Get("/user/address/update")
    public String addressUpdate(@Param("id") long id) {

        if (id == 0) {
            request.setAttribute("address", new Address());
        } else {
            request.setAttribute("address", addressService.findOneById(id));
        }
        return "/user/address/update/index";
    }

    @Post("/user/address/update")
    public String addressUpdatePost(@Body Address address) throws IOException {
        User user = (User) session.getAttribute("user");
        address.setUserId(user.getId());
        address.setDetail(address.getDetail().replaceAll("\\n"," "));
        if (address.getId() == 0) {
            if(addressService.findOneByAlias(address.getAlias())==null){
                addressService.insert(address);
            }else{
                request.setAttribute("error","此备注已经被占用！");
                return "/user/address/update/index";
            }
        } else {
            addressService.update(address);
        }
        response.sendRedirect("/user/address");

        return "/user/address/update/index";
    }

    @Get("/user/cart/delete")
    public void cartDelete(@Param("id") int id) throws IOException {
        if (id != 0) {
            cartService.deleteById(id);
            request.setAttribute("msg", "删除成功!");
        } else {
            request.setAttribute("error", "id不能为空!");
        }
        response.sendRedirect("/user/cart");
    }


    @Get("/user/address/delete")
    public void addressDelete(@Param("id") int id) throws IOException {
        if (id != 0) {
            addressService.deleteById(id);
            request.setAttribute("msg", "删除成功!");
        } else {
            request.setAttribute("error", "id不能为空!");
        }
        response.sendRedirect("/user/address");
    }

    @Get("/user/address/default")
    public void addressDefault(@Param("id") int id) throws IOException {
        User user = (User) session.getAttribute("user");
        if (id != 0) {
            user.setDefaultAddressId(id);
            userService.update(user);
            request.setAttribute("msg", "设置成功!");
        } else {
            request.setAttribute("error", "id不能为空!");
        }
        response.sendRedirect("/user/address");
    }

    @Get("/user/orders/cancel")
    public void cancel(@Param("id") int id) throws IOException {
        Order order = orderService.findOneById(id);
        orderService.cancelOrder(order);
        request.setAttribute("msg", "取消成功!");
        response.sendRedirect("/user/orders");
    }


    @Get("/user/orders/confirm")
    public void confirm(@Param("id") int id) throws IOException {
        Order order = orderService.findOneById(id);
        changeOrderStatus(order,OrderStatus.FINISH);
        request.setAttribute("msg", "收货成功!");
        response.sendRedirect("/user/orders");
    }

    @Get("/user/orders/return")
    public void returnOrder(@Param("id") int id) throws IOException {
        Order order = orderService.findOneById(id);
        orderService.returnOrder(order);
        request.setAttribute("msg", "退货成功!");
        response.sendRedirect("/user/orders");
    }


    public void changeOrderStatus(Order order,OrderStatus orderStatus){
        order.setStatus(orderStatus.value);
        orderService.update(order);
    }
}
