package cn.enncy.mall.service;


import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.pojo.User;


/**
 * //TODO
 * <br/>Created in 23:35 2021/11/17
 *
 * @author: enncy
 */
public class UserService extends  BaseService<User> {

    public UserService() {
        super(UserMapper.class);
    }

}
