package cn.enncy.mall.service;


import cn.enncy.mall.mapper.OrderDetailsMapper;
import cn.enncy.mall.pojo.OrderDetails;
import cn.enncy.mall.service.impl.ServiceImpl;
import cn.enncy.mybatis.annotation.param.Param;

import java.util.List;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 21:08 2021/11/29
 *
 * @author enncy
 */
public interface OrderDetailsService extends  BaseService<OrderDetails> {

    List<OrderDetails> findByOrderUid(String orderUid);


}


