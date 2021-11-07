package cn.enncy.mall.mapper;

import cn.enncy.mall.pojo.User;
import cn.enncy.mybatis.annotation.Delete;
import cn.enncy.mybatis.annotation.Mapper;
import cn.enncy.mybatis.annotation.Param;
import cn.enncy.mybatis.annotation.Select;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 19:27 2021/11/6
 *
 * @author enncy
 */

@Mapper(table = "user",target = User.class)
public interface UserMapper extends BaseMapper<User> {

    /**
     *  根据 账号 查找用户
     *
     * @param account  账号
     * @return cn.enncy.mall.pojo.User
     */
    @Select("select * from #{TABLE_NAME} where account = '#{account}'")
    User findByAccount(@Param("account") String account);


    /**
     *  根据 邮箱 查找用户
     *
     * @param email  邮箱
     * @return cn.enncy.mall.pojo.User
     */
    @Select("select * from #{TABLE_NAME} where email = '#{email}'")
    User findByEmail(@Param("email") String email);


    /**
     *  根据 昵称 查找用户
     *
     * @param nickname  昵称
     * @return cn.enncy.mall.pojo.User
     */
    @Select("select * from #{TABLE_NAME} where nickname like '%#{nickname}%'")
    List<User> findByNickname(@Param("nickname") String nickname);


    /**
     *  删除不活跃的用户
     * @return boolean
     */
    @Delete("delete from #{TABLE_NAME} where active=0")
    boolean deleteInactiveUser();
}
