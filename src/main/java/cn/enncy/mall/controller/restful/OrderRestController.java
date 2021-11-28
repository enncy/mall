package cn.enncy.mall.controller.restful;


import cn.enncy.mall.bean.ResultBody;
import cn.enncy.mall.pojo.Order;
import cn.enncy.mall.service.OrderService;
import cn.enncy.mall.utils.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.RestController;
import cn.enncy.spring.mvc.annotation.params.Param;

/**
 * //TODO
 * <br/>Created in 20:28 2021/11/28
 *
 * @author enncy
 */

@RestController
public class OrderRestController {
    OrderService orderService = ServiceFactory.resolve(OrderService.class);


    @Get("/user/orders/update/address")
    public ResultBody  updateAddress(@Param("id") int id,@Param("addressId") int addressId){

        Order order = orderService.findOneById(id);
        order.setAddressId(addressId);
        orderService.update(order);

        return ResultBody.of(order);
    }

}
