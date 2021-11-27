package cn.enncy.mall.controller.admin;


import cn.enncy.mall.bean.Pagination;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.pojo.*;
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
public class ServiceController {
    UserService userService = ServiceFactory.resolve(UserService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);
    OrderService orderService = ServiceFactory.resolve(OrderService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    TagService  tagService =  ServiceFactory.resolve(TagService.class);

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
    public String tag(@Param("page") int page, @Param("size") int size,@Param("search") String search) {
        return service(ServiceMapping.TAG, tagService.search(search,page, size), page, size);
    }

    /**
     * 公共业务方法
     */
    public String service(ServiceMapping serviceMapping, List<? extends BaseObject> list, int page, int size) {
        size = size == 0 ? 10 : size;
        Pagination pagination = createPagination(page, size, userService.count());
        // 分页
        request.setAttribute("pagination", pagination);
        // 查询结果
        request.setAttribute("objects", list);
        // 传递业务映射对象
        request.setAttribute("service", serviceMapping);
        return "/admin/" + serviceMapping.name + "/index";
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
