package cn.enncy.mall.mapper;

import cn.enncy.mall.pojo.User;
import cn.enncy.mybatis.annotation.method.Select;
import cn.enncy.mybatis.annotation.type.Mapper;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 14:01 2021/12/6
 *
 * @author enncy
 */


@Mapper(table = "user",target = User.class)
public interface TestMapper {
    @Select("select * from user;")
    List<User> findByAll();
}
