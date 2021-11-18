package cn.enncy.mall.service;


import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.pojo.User;

import java.util.List;


/**
 * //TODO
 * <br/>Created in 23:35 2021/11/17
 *
 * @author enncy
 */
public class UserService extends  BaseService<User,UserMapper> implements UserMapper {

    @Override
    public User findOneByAccount(String account) {
        return mapper.findOneByAccount(account);
    }

    @Override
    public User findOneByEmail(String email) {
        return mapper.findOneByEmail(email);
    }

    @Override
    public List<User> findByNickname(String nickname) {
        return mapper.findByNickname(nickname);
    }

    @Override
    public boolean deleteInactiveUser() {
        return mapper.deleteInactiveUser();
    }
}
