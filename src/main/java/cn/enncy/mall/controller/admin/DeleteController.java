package cn.enncy.mall.controller.admin;


import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.service.BaseService;
import cn.enncy.mall.utils.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.params.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * //TODO
 * <br/>Created in 14:24 2021/11/26
 *
 * @author enncy
 */

@Controller
public class DeleteController {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;


    @Get("/admin/common/delete/user")
    public void deleteUser(@Param("targetId") long targetId) throws IOException {
        delete(targetId, ServiceMapping.USER);
    }

    @Get("/admin/common/delete/address")
    public void deleteAddress(@Param("targetId") long targetId) throws IOException {
        delete(targetId, ServiceMapping.ADDRESS);
    }

    @Get("/admin/common/delete/cart")
    public void deleteCart(@Param("targetId") long targetId) throws IOException {
        delete(targetId, ServiceMapping.CART);
    }

    @Get("/admin/common/delete/goods")
    public void deleteGoods(@Param("targetId") long targetId) throws IOException {
        delete(targetId, ServiceMapping.GOODS);
    }

    @Get("/admin/common/delete/order")
    public void deleteOrder(@Param("targetId") long targetId) throws IOException {
        delete(targetId, ServiceMapping.ORDER);
    }



    public void delete(long targetId, ServiceMapping serviceMapping) throws IOException {
        BaseService<?, ?> resolve = ServiceFactory.resolve(serviceMapping.serviceClass);
        resolve.deleteById(targetId);
        response.sendRedirect("/admin/" + serviceMapping.name);
    }

}
