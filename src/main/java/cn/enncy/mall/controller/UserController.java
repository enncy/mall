package cn.enncy.mall.controller;


import cn.enncy.mall.pojo.Address;
import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.AddressService;
import cn.enncy.mall.service.CartService;
import cn.enncy.mall.service.GoodsService;
import cn.enncy.mall.service.UserService;
import cn.enncy.mall.utils.RequestUtils;
import cn.enncy.mall.utils.Security;
import cn.enncy.mall.utils.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.params.Body;
import cn.enncy.spring.mvc.annotation.params.Param;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    @Get("/user")
    public String userGet() {
        return "/user/index";
    }

    @Get("/user/address")
    public String address() {
        List<Address> all = addressService.findAll();
        request.setAttribute("addresses",all);
        return "/user/address/index";
    }

    @Get("/user/cart")
    public String cart() {
        List<Cart> all = cartService.findAll();
        request.setAttribute("carts",all);
        return "/user/cart/index";
    }

    @Get("/user/order")
    public String order() {
        return "/user/order/index";
    }

    @Post("/user")
    public String userPost(@Body User user) {
        userService.update(user);
        session.setAttribute("user",user);
        return "/user/index";
    }



    @Get("/user/balance")
    public String balanceGet() {
        return "/user/balance/index";
    }

    @Post("/user/balance")
    public String balancePost(@Param("balance") String balance) {
        if (!StringUtils.isNullOrEmpty(balance)) {
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
        System.out.println(id);
        if(id==0){
            request.setAttribute("address", new Address());
        }else{
            request.setAttribute("address", addressService.findOneById(id));
        }
        return "/user/address/update/index";
    }

    @Post("/user/address/update")
    public String addressUpdatePost(@Body Address address) {
        request.setAttribute("address", address);
        try {
            RequestUtils.replaceObject(request, address);

            if(address.getId()==0){
                addressService.insert(address);
            }else{
                addressService.update(address);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "服务器错误");
        }
        return "/user/address/index";
    }

    @Get("/user/cart/delete")
    public void cartDelete(@Param("id") int id) throws IOException {
        if (id!=0) {
            //  增加库存
            Cart cart = cartService.findOneById(id);
            Goods goods = goodsService.findOneById(cart.getGoodsId());
            goods.setStock(goods.getStock()+cart.getCount());
            goodsService.update(goods);
            cartService.deleteById(id);
            request.setAttribute("msg", "删除成功!");
        } else {
            request.setAttribute("error", "id不能为空!");
        }
        response.sendRedirect("/user/cart");
    }


    @Get("/user/address/delete")
    public void addressDelete(@Param("id") int id) throws IOException {
        if (id!=0) {
            addressService.deleteById(id);
            request.setAttribute("msg", "删除成功!");
        } else {
            request.setAttribute("error", "id不能为空!");
        }
        response.sendRedirect("/user/address");
    }

}
