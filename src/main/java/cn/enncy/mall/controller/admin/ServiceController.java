package cn.enncy.mall.controller.admin;


import cn.enncy.mall.bean.Pagination;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.pojo.*;
import cn.enncy.mall.service.*;
import cn.enncy.mall.service.impl.ServiceImpl;
import cn.enncy.mybatis.core.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.params.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:15 2021/11/21
 *
 * @author enncy
 */

@Controller
public class ServiceController {
    UserService userService = ServiceFactory.resolve(UserService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);
    OrderService orderService = ServiceFactory.resolve(OrderService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    TagService tagService = ServiceFactory.resolve(TagService.class);

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;


    @Get("/admin/user")
    public String user(@Param("page") int page, @Param("size") int size, @Param("search") String search) {
        return service(ServiceMapping.USER, userService.search(search, page, size), page, size);
    }

    @Get("/admin/address")
    public String address(@Param("page") int page, @Param("size") int size, @Param("search") String search) {
        return service(ServiceMapping.ADDRESS, addressService.search(search, page, size), page, size);
    }

    @Get("/admin/goods")
    public String goods(@Param("page") int page, @Param("size") int size, @Param("search") String search) {
        return service(ServiceMapping.GOODS, goodsService.search(search, page, size), page, size);
    }

    @Get("/admin/cart")
    public String cart(@Param("page") int page, @Param("size") int size) {
        return service(ServiceMapping.CART, cartService.findByPages(page, size), page, size);
    }

    @Get("/admin/order")
    public String order(@Param("page") int page, @Param("size") int size) {
        return service(ServiceMapping.ORDER, orderService.findByPages(page, size), page, size);
    }

    @Get("/admin/tag")
    public String tag(@Param("page") int page, @Param("size") int size, @Param("search") String search) {
        return service(ServiceMapping.TAG, tagService.search(search, page, size), page, size);
    }

    /**
     * 公共业务方法
     */
    public String service(ServiceMapping serviceMapping, List<? extends BaseObject> list, int page, int size) {
        BaseService<?> service = ServiceFactory.resolve(serviceMapping.serviceClass);

        size = size == 0 ? 10 : size;
        Pagination pagination = Pagination.createPagination(page, size, service.count());
        // 分页
        request.setAttribute("pagination", pagination);
        // 查询结果
        request.setAttribute("objects", list);
        // 传递业务映射对象
        request.setAttribute("service", serviceMapping);
        return "/admin/" + serviceMapping.name + "/index";
    }

}
