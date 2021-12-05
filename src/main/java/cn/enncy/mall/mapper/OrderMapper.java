package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Order;
import cn.enncy.mybatis.annotation.method.Delete;
import cn.enncy.mybatis.annotation.method.Executable;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.method.Select;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.result.MapResultHandler;
import cn.enncy.mybatis.core.result.SingleResultHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 14:25 2021/11/18
 *
 * @author  enncy
 */

@Mapper(table = "order",target = Order.class)
public interface OrderMapper extends  BaseMapper<Order> {

    @Select("select * from #{"+ TABLE_NAME+"} where user_id = #{userId}")
    List<Order> findByUserId(@Param("userId") long userId);


    @Select("select * from #{"+ TABLE_NAME+"} where uid = #{uid}")
    Order findOneByUid(@Param("uid") String uid);

    @Delete("DELETE FROM #{"+TABLE_NAME+"} WHERE uid=#{uid}  ;")
    boolean deleteByUid(@Param("uid") String uid);

    // 获取销售额，距离 day 天之内的销售额
    @Executable(singleResult = true,resultMaps = {
            @Result(key = "sum", target = BigDecimal.class)
    })
    @Select("select  sum(total_price) as sum from `order` where  status='finished' and ((unix_timestamp(now()) -  (create_time/1000)) /  (24 * 60 * 60)) <  #{day};")
    BigDecimal getSalesVolume(@Param("day") int day);



    /**
     *  统计 n 天之内，某标签下的商品销售情况
     *
     * @param day
     * @return java.util.List<cn.enncy.mall.pojo.OrderDetails>
     */
    @Executable(resultMaps = {
            @Result(key = "tag", target = String.class),
            @Result(key = "nums", target = int.class),
            @Result(key = "day", target = String.class)
    })
    @Select("select t.name as tag, od.count as nums,  concat('周',dayofweek(from_unixtime( (o.create_time / 1000))) - 1) as day\n" +
            "from `order` as o\n" +
            "         left join order_details od on o.uid = od.order_uid\n" +
            "         left join goods g on g.id = od.goods_id\n" +
            "         left join tag t on g.tag_id = t.id\n" +
            "where o.status = 'finished'\n" +
            "  and  round(((unix_timestamp(now()) - (o.create_time / 1000)) / (24 * 60 * 60)),0) <= #{day}\n" +
            "\n" +
            "group by tag,day;\n")
    List<Map<String,Object>> analysisGoodsSaleOfDay(@Param("day") int day);
}
