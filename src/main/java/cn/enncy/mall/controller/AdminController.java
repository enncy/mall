package cn.enncy.mall.controller;


import cn.enncy.mall.bean.Pagination;
import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.pojo.Address;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.*;
import cn.enncy.mall.utils.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.params.Body;
import cn.enncy.spring.mvc.annotation.params.Param;
import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * //TODO
 * <br/>Created in 17:15 2021/11/21
 *
 * @author enncy
 */

@Controller
public class AdminController {
    UserService userService = ServiceFactory.resolve(UserService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);
    OrderService orderService = ServiceFactory.resolve(OrderService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);

    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;


    @Get("/admin/user")
    public String user(@Param("page") int page, @Param("size") int size, @Param("name") String name) {
        int pageSize = size == 0 ? 10 : size;

        Pagination pagination = createPagination(page, pageSize, userService.count());
        request.setAttribute("pagination", pagination);
        request.setAttribute("users", userService.findAccountOrNicknameLike(StringUtils.isNullOrEmpty(name) ? "" : name, page, pageSize));
        return "/admin/user/index";
    }

    @Get("/admin/address")
    public String address(@Param("page") int page, @Param("size") int size) {
        int pageSize = size == 0 ? 10 : size;
        int allCount = addressService.count();
        Pagination pagination = createPagination(page, pageSize, allCount);
        request.setAttribute("pagination", pagination);
        request.setAttribute("addresses", addressService.findByPages(page, pageSize));
        return "/admin/address/index";
    }

    @Get("/admin/goods")
    public String goods(@Param("page") int page, @Param("size") int size) {
        int pageSize = size == 0 ? 10 : size;
        int allCount = goodsService.count();
        Pagination pagination = createPagination(page, pageSize, allCount);
        request.setAttribute("pagination", pagination);
        request.setAttribute("goods", goodsService.findByPages(page, pageSize));
        return "/admin/goods/index";
    }

    @Get("/admin/cart")
    public String cart(@Param("page") int page, @Param("size") int size) {
        int pageSize = size == 0 ? 10 : size;
        int allCount = cartService.count();
        Pagination pagination = createPagination(page, pageSize, allCount);
        request.setAttribute("pagination", pagination);
        request.setAttribute("carts", cartService.findByPages(page, pageSize));
        return "/admin/cart/index";
    }

    @Get("/admin/order")
    public String order(@Param("page") int page, @Param("size") int size) {
        int pageSize = size == 0 ? 10 : size;
        int allCount = orderService.count();
        Pagination pagination = createPagination(page, pageSize, allCount);
        request.setAttribute("pagination", pagination);
        request.setAttribute("orders", orderService.findByPages(page, pageSize));
        return "/admin/order/index";
    }

    @Get("/admin/common/operate/user")
    public String operateUser(@Param("targetId") long targetId) throws IOException, InstantiationException, IllegalAccessException {
        return operate(targetId, User.class, UserService.class);
    }

    @Post("/admin/common/operate/user")
    public String operateUserPost(@Param("targetId") long targetId,@Body User user) throws IOException, InstantiationException, IllegalAccessException {

        return operatePost("/admin/user", targetId, user, UserService.class);
    }

    public <T extends BaseObject> String operatePost(String redirect, long targetId, T object, Class<? extends BaseService<T, ?>> baseService) throws IOException, IllegalAccessException, InstantiationException {
        BaseService<T, ?> service = ServiceFactory.resolve(baseService);
        if (targetId == 0) {
            service.insert(object);
        } else {
            System.out.println("operatePost "+object);
            service.update(object);
        }
        response.sendRedirect(redirect);
        return "/admin/common/operate/index";
    }

    public String operate(long targetId, Class<? extends BaseObject> clazz, Class<? extends BaseService<?, ?>> baseService) throws IOException, IllegalAccessException, InstantiationException {
        request.setAttribute("class", clazz);
        request.setAttribute("service", ServiceFactory.resolve(baseService));
        if (targetId == 0) {
            BaseObject baseObject = clazz.newInstance();
            request.setAttribute("object", baseObject);
        } else {
            BaseService<?, ?> service = (BaseService<?, ?>) request.getAttribute("service");
            BaseObject object = service.findOneById(targetId);
            request.setAttribute("object", object);
        }
        return "/admin/common/operate/index";
    }


    /**
     * 分页插件
     *
     * @param page
     * @param size
     * @param allCount
     * @return void
     */
    public Pagination createPagination(int page, int size, int allCount) {
        int allPage = (allCount + size - 1) / size;
        int prePage = Math.max(page - 1, 0);
        int nextPage = Math.min(page + 1, allPage);
        Pagination pagination = new Pagination();
        pagination.setIndex(page);
        pagination.setSize(size);
        pagination.setCount(allPage);
        pagination.setNext(nextPage);
        pagination.setPre(prePage);
        return pagination;
    }

}
