package cn.enncy.mall;


import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.service.impl.UserServiceImpl;
import cn.enncy.mall.utils.Logger;
import cn.enncy.mybatis.core.ServiceFactory;
import cn.enncy.mall.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.*;

/**
 * //TODO
 * <br/>Created in 17:38 2021/11/7
 *
 * @author enncy
 */

@WebListener
public class Application implements ServletContextListener {

    public static  String  REAL_PATH = "";


    public void startRegisterTask() {
        // 定时任务，清理超时的注册验证
        UserService userService = ServiceFactory.resolve(UserServiceImpl.class);
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(userService::deleteInactiveUser, 3, 3, TimeUnit.MINUTES);
    }


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Logger.log("tomcat 启动");
        Application.REAL_PATH = servletContextEvent.getServletContext().getRealPath("");
        // 删除未激活的用户
        startRegisterTask();
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}

}
