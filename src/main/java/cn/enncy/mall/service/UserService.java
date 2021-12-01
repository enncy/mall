package cn.enncy.mall.service;


import cn.enncy.mall.pojo.User;

import java.util.List;


/**
 * //TODO
 * <br/>Created in 23:35 2021/11/17
 *
 * @author enncy
 */
public interface UserService extends BaseService<User>  {

    User findOneByAccount(String account);

    User findOneByEmail(String email);

    List<User> search(String str, int page, int size);

    boolean deleteInactiveUser();
}
