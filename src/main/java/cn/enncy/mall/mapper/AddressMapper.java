package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Address;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.method.Select;
import static cn.enncy.mybatis.core.SqlConstant.*;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 10:34 2021/11/16
 *
 * @author enncy
 */

@Mapper(table = "address",target = Address.class)
public interface AddressMapper extends BaseMapper<Address> , Searchable<Address> {

    /**
     *  根据别名查找地址
     *
     * @param alias  别名
     * @return cn.enncy.mall.pojo.Address
     */
    @Select("select * from address where user_id = #{user_id} and alias = '#{alias}'")
    Address findOneByUserAlias(@Param("user_id") long userId,@Param("alias") String alias);

    /**
     *  根据用户id查找地址
     *
     * @param userId 用户id
     * @return cn.enncy.mall.pojo.Address
     */
    @Select("select * from address where user_id = #{user_id}")
    List<Address> findByUserId(@Param("user_id") long userId);

    @Override
    @Select("select * from address where alias like '%#{str}%' or receiver like '%#{str}%'  or detail like '%#{str}%'  LIMIT #{skip} ,#{limit}; ")
    List<Address> search(@Param("str") String str,@Param("skip") int skip, @Param("limit") int limit);

}
