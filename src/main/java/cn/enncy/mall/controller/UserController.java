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
import java.math.RoundingMode;
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
    OrderService orderService = ServiceFactory.resolve(OrderService.class);
    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    @Get("/user")
    public String userGet() {
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

    @Post("/user/balance/in")
    public String balancePost(@Param("balance") BigDecimal balance, @Param("money") BigDecimal money) {
        User user = (User) session.getAttribute("user");
        if (money != null) {
            balance = money;
        }
        if (balance.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (user != null) {
                BigDecimal origin = user.getBalance();
                user.setBalance(origin.add(balance));
                userService.update(user);
                return "/user/balance/index";
            }
        } else {
            request.setAttribute("error", "余额不能为0!");
        }
        request.setAttribute("redirect", "/user/balance");
        return "/result/index";
    }
    @Post("/user/balance/out")
    public String balanceOutPost(@Param("balance") BigDecimal balance, @Param("money") BigDecimal money, @Param("operate") String operate) {
        User user = (User) session.getAttribute("user");
        if (StringUtils.notEmpty(operate)) {
            balance = "全部".equals(operate) ? user.getBalance() : user.getBalance().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        } else if (money != null) {
            balance = money;
        }
        if (balance.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (user != null) {
                BigDecimal origin = user.getBalance();
                if (origin.compareTo(balance) < 0) {
                    request.setAttribute("error", "提现额度不能大于余额!");
                } else {
                    user.setBalance(origin.subtract(balance));
                    userService.update(user);
                    return "/user/balance/index";
                }
            }
        } else {
            request.setAttribute("error", "余额不能为0!");
        }
        request.setAttribute("redirect", "/user/balance");
        return "/result/index";
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
        address.setDetail(address.getDetail().replaceAll("\\n", " "));
        if (address.getId() == 0) {
            if (addressService.findOneByUserAlias(user.getId(),address.getAlias()) == null) {
                addressService.insert(address);
            } else {
                request.setAttribute("error", "此备注已经被占用！");
                return "/user/address/update/index";
            }
        } else {
            addressService.update(address);
        }
        response.sendRedirect("/user/address");

        return "/user/address/update/index";
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

    @Get("/user/orders/cancel")
    public String cancel(@Param("id") int id) throws IOException {
        Order order = orderService.findOneById(id);
        if (order.getStatus().equals(OrderStatus.PAYMENT.value)) {
            orderService.cancelOrder(order);
            response.sendRedirect("/user/orders");
        }
        request.setAttribute("error", "必须在付款或结算前才能取消订单!");
        request.setAttribute("redirect", "/user/orders?status=payment");
        return "/result/index";
    }


    @Get("/user/orders/confirm")
    public String confirm(@Param("id") int id) throws IOException {
        Order order = orderService.findOneById(id);

        if (order.getStatus().equals(OrderStatus.RECEIVING.value)) {
            changeOrderStatus(order, OrderStatus.FINISH);
            response.sendRedirect("/user/orders");
        }
        request.setAttribute("error", "此订单未结算或者已经完成!");
        request.setAttribute("redirect", "/user/orders?status=receiving");
        return "/result/index";
    }

    @Get("/user/orders/return")
    public String returnOrder(@Param("id") int id) throws IOException {

        Order order = orderService.findOneById(id);
        if (order.getStatus().equals(OrderStatus.FINISH.value)) {
            orderService.returnOrder(order);
            response.sendRedirect("/user/orders");
        }
        request.setAttribute("error", "未收货前不能退货!");
        request.setAttribute("redirect", "/user/orders?status=finish");
        return "/result/index";
    }


    public void changeOrderStatus(Order order, OrderStatus orderStatus) {
        order.setStatus(orderStatus.value);
        orderService.update(order);
    }
}
