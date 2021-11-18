package cn.enncy.mall;


import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.service.ServiceFactory;
import cn.enncy.mall.service.UserService;
import cn.enncy.mybatis.core.SqlSession;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * //TODO
 * <br/>Created in 17:38 2021/11/7
 *
 * @author enncy
 */

@WebListener
public class Application implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("tomcat 启动");
        startRegisterTask();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public void startRegisterTask() {
        // 定时任务，清理超时的注册验证

        UserService userService = ServiceFactory.resolve(UserService.class);

        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(userService::deleteInactiveUser, 3, 3, TimeUnit.MINUTES);
    }
}
