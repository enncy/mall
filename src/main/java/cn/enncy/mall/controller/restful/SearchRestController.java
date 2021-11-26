package cn.enncy.mall.controller.restful;


import cn.enncy.mall.bean.ResultBody;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.*;
import cn.enncy.mall.utils.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.RestController;
import cn.enncy.spring.mvc.annotation.params.Param;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 22:53 2021/11/25
 *
 * @author enncy
 */

@RestController("search")
public class SearchRestController {

    UserService userService = ServiceFactory.resolve(UserService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);

    @Get("/user")
    public Object user(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        return ResultBody.of(userService.search(key, page, size));
    }

    @Get("/address")
    public Object address(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        return ResultBody.of(addressService.search(key, page, size));
    }

    @Get("/goods")
    public Object goods(@Param("key") String key, @Param("page") int page, @Param("size") int size) {
        return ResultBody.of(goodsService.search(key, page, size));
    }

}
