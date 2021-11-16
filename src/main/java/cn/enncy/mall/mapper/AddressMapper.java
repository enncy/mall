package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Address;
import cn.enncy.mybatis.annotation.Mapper;
import cn.enncy.mybatis.annotation.Param;
import cn.enncy.mybatis.annotation.Select;
import static cn.enncy.mybatis.core.SqlConstant.*;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 10:34 2021/11/16
 *
 * @author: enncy
 */

@Mapper(table = "address",target = Address.class)
public interface AddressMapper extends BaseMapper<Address> {

    /**
     *  根据别名查找地址
     *
     * @param alias  别名
     * @return cn.enncy.mall.pojo.Address
     */
    @Select("select * from #{"+ TABLE_NAME+"} where alias = #{alias}")
    Address findOneByAlias(@Param("alias") String alias);

    /**
     *  根据用户id查找地址
     *
     * @param userId
     * @return cn.enncy.mall.pojo.Address
     */
    @Select("select * from #{"+ TABLE_NAME+"} where user_id = #{user_id}")
    List<Address> findByUserId(@Param("user_id") long userId);

}
