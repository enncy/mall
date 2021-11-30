package cn.enncy.mall.controller.restful;


import cn.enncy.mall.bean.ResultBody;
import cn.enncy.mall.pojo.Address;
import cn.enncy.mall.pojo.Order;
import cn.enncy.mall.service.AddressService;
import cn.enncy.mall.service.OrderService;
import cn.enncy.mybatis.core.ServiceFactory;
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
    AddressService addressService = ServiceFactory.resolve(AddressService.class);

    @Get("/user/orders/update/address")
    public ResultBody  updateAddress(@Param("id") int id,@Param("addressId") int addressId){
        Address oneById = addressService.findOneById(addressId);
        Order order = orderService.findOneById(id);
        order.setAddressDetail(oneById.getDetail());
        orderService.update(order);

        return ResultBody.of(order);
    }

}
