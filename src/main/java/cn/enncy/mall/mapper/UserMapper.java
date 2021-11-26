package cn.enncy.mall.mapper;

import cn.enncy.mall.pojo.User;
import cn.enncy.mybatis.annotation.method.Delete;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.method.Select;
import static cn.enncy.mybatis.core.SqlConstant.*;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 19:27 2021/11/6
 *
 * @author enncy
 */

@Mapper(table = "user",target = User.class)
public interface UserMapper extends BaseMapper<User> , Searchable<User> {

    /**
     *  根据 账号 查找用户
     *
     * @param account  账号
     * @return cn.enncy.mall.pojo.User
     */
    @Select("select * from #{"+TABLE_NAME+"} where account = '#{account}'")
    User findOneByAccount(@Param("account") String account);


    /**
     *  根据 邮箱 查找用户
     *
     * @param email  邮箱
     * @return cn.enncy.mall.pojo.User
     */
    @Select("select * from #{"+TABLE_NAME+"} where email = '#{email}'")
    User findOneByEmail(@Param("email") String email);


    /**
     *  根据 昵称 或 账号 查找用户

     * @return cn.enncy.mall.pojo.User
     */
    @Override
    @Select("select * from #{"+ TABLE_NAME +"} where account like '%#{str}%'  or  nickname like '%#{str}%'  limit #{page},#{size};")
    List<User> search(@Param("str") String str,@Param("page") int page, @Param("size") int size);



    /**
     *  删除不活跃的用户
     * @return boolean
     */
    @Delete("delete from #{"+TABLE_NAME+"} where active=0")
    boolean deleteInactiveUser();


}
