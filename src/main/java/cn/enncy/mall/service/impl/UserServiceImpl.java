package cn.enncy.mall.service.impl;


import cn.enncy.mall.Application;
import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.UserService;
import cn.enncy.spring.mvc.PathUtils;
import com.mysql.cj.util.StringUtils;

import java.io.File;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:59 2021/11/30
 *
 * @author enncy
 */
public class UserServiceImpl extends ServiceImpl<User, UserMapper> implements UserService  {
    public UserServiceImpl() {
        super(UserMapper.class);
    }

    @Override
    public boolean update(User baseObject) {
        User old = mapper.findOneById(baseObject.getId());

        // 删除旧图片
        if (!StringUtils.isNullOrEmpty(old.getAvatar()) && !old.getAvatar().equals(baseObject.getAvatar())) {
            List<String> path = PathUtils.splitPath(Application.REAL_PATH);
            path.addAll(PathUtils.splitPath(old.getAvatar()));
            String absPath = String.join("/", path);
            File file = new File(absPath);

            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
        return super.update(baseObject);
    }

    @Override
    public User findOneByAccount(String account) {
        return mapper.findOneByAccount(account);
    }

    @Override
    public User findOneByEmail(String email) {
        return mapper.findOneByEmail(email);
    }

    @Override
    public List<User> search(String str, int page, int size) {
        size = size == 0 ? 10 : size;
        return mapper.search(str, page * size, size);
    }

    @Override
    public boolean deleteInactiveUser() {
        return mapper.deleteInactiveUser();
    }

}
