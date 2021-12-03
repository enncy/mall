package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.OrderDetails;
import cn.enncy.mybatis.annotation.method.Executable;
import cn.enncy.mybatis.annotation.method.Select;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.result.MapResultHandler;

import java.util.List;
import java.util.Map;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 21:08 2021/11/29
 *
 * @author enncy
 */

@Mapper(table = "order_details",target = OrderDetails.class)
public interface OrderDetailsMapper  extends BaseMapper<OrderDetails>{

    @Select("select * from #{"+TABLE_NAME+"}  where  order_uid = #{orderUid}"  )
    List<OrderDetails> findByOrderUid(@Param("orderUid") String orderUid);

}
