package cn.enncy.mall.controller.restful;


import cn.enncy.mall.bean.ResultBody;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.*;
import cn.enncy.mybatis.core.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.RestController;
import cn.enncy.spring.mvc.annotation.params.Param;

import javax.servlet.http.HttpSession;

/**
 * //TODO
 * <br/>Created in 22:53 2021/11/25
 *
 * @author enncy
 */

@RestController("search")
public class SearchRestController {
    HttpSession session;
    UserService userService = ServiceFactory.resolve(UserService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    TagService tagService = ServiceFactory.resolve(TagService.class);
    @Get("/user")
    public Object user(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        return ResultBody.of(userService.search(key, page, size));
    }

    @Get("/address")
    public Object address(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        return ResultBody.of(addressService.search(key, page, size));
    }

    @Get("/user/address")
    public Object userAddress(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ResultBody.error("未登录");
        }else{
            return ResultBody.of(addressService.searchByUserId(user.getId(),key, page, size));
        }

    }

    @Get("/goods")
    public Object goods(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        return ResultBody.of(goodsService.search(key, page, size));
    }

    @Get("/tag")
    public Object tag(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        return ResultBody.of(tagService.search(key, page, size));
    }
}
