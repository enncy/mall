package cn.enncy.mall.service;


import cn.enncy.mall.Application;
import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.pojo.User;
import cn.enncy.spring.mvc.PathUtils;
import com.mysql.cj.util.StringUtils;

import java.io.File;
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
