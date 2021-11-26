package cn.enncy.mall.controller.admin;


import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.pojo.*;
import cn.enncy.mall.service.BaseService;
import cn.enncy.mall.utils.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.params.Body;
import cn.enncy.spring.mvc.annotation.params.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * //TODO
 * <br/>Created in 20:21 2021/11/25
 *
 * @author enncy
 */

@Controller
public class OperateController {

    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;

    @Get("/admin/common/operate/user")
    public String operateUser(@Param("targetId") long targetId) throws IOException, InstantiationException, IllegalAccessException {
        return operate(targetId, ServiceMapping.USER);
    }

    @Get("/admin/common/operate/address")
    public String operateAddress(@Param("targetId") long targetId) throws IOException, InstantiationException, IllegalAccessException {
        return operate(targetId, ServiceMapping.ADDRESS);
    }

    @Get("/admin/common/operate/cart")
    public String operateCart(@Param("targetId") long targetId) throws IOException, InstantiationException, IllegalAccessException {
        return operate(targetId, ServiceMapping.CART);
    }

    @Get("/admin/common/operate/goods")
    public String operateGoods(@Param("targetId") long targetId) throws IOException, InstantiationException, IllegalAccessException {
        return operate(targetId, ServiceMapping.GOODS);
    }

    @Get("/admin/common/operate/order")
    public String operateOrder(@Param("targetId") long targetId) throws IOException, InstantiationException, IllegalAccessException {
        return operate(targetId, ServiceMapping.ORDER);
    }

    @Post("/admin/common/operate/user")
    public void operateUserPost(@Param("targetId") long targetId, @Body User user) throws IOException, InstantiationException, IllegalAccessException {
        operatePost(targetId, user, ServiceMapping.USER);
    }

    @Post("/admin/common/operate/address")
    public void operateAddressPost(@Param("targetId") long targetId, @Body Address address) throws IOException, InstantiationException, IllegalAccessException {
        operatePost(targetId, address, ServiceMapping.ADDRESS);
    }

    @Post("/admin/common/operate/cart")
    public void operateCartPost(@Param("targetId") long targetId, @Body Cart cart) throws IOException, InstantiationException, IllegalAccessException {
        operatePost(targetId, cart, ServiceMapping.CART);
    }

    @Post("/admin/common/operate/goods")
    public void operateGoodsPost(@Param("targetId") long targetId, @Body Goods goods) throws IOException, InstantiationException, IllegalAccessException {
        operatePost(targetId, goods, ServiceMapping.GOODS);
    }

    @Post("/admin/common/operate/order")
    public void operateOrderPost(@Param("targetId") long targetId, @Body Order order) throws IOException, InstantiationException, IllegalAccessException {
        operatePost(targetId, order, ServiceMapping.ORDER);
    }


    // 公共操作方法，如果 targetId 为 0 ，则为添加操作，否则为更新操作
    public String operate(long targetId, ServiceMapping serviceMapping) throws IOException, IllegalAccessException, InstantiationException {
        if (targetId == 0) {
            request.setAttribute("object", serviceMapping.objectClass.newInstance());
        } else {
            BaseService<?, ?> resolve = ServiceFactory.resolve(serviceMapping.serviceClass);
            request.setAttribute("object", resolve.findOneById(targetId));
        }
        request.setAttribute("service", serviceMapping);
        return "/admin/common/operate/index";
    }

    // 公共操作提交方法，如果 targetId 为 0 ，则为添加操作，否则为更新操作
    public void operatePost(long targetId, BaseObject object, ServiceMapping serviceMapping) throws IOException, IllegalAccessException, InstantiationException {
        BaseService<BaseObject, ?> service = (BaseService<BaseObject, ?>) ServiceFactory.resolve(serviceMapping.serviceClass);
        if (targetId == 0) {
            service.insert(object);
        } else {
            service.update(object);
        }
        response.sendRedirect("/admin/" + serviceMapping.name);
    }


}
