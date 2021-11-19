package cn.enncy.mall.utils;

import java.util.concurrent.*;


/**
 * //TODO
 * <br/>Created in 13:53 2021/11/19
 *
 * @author enncy
 */
public class Logger {

    public static final ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            1, TimeUnit.MINUTES, new SynchronousQueue<>(),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void log(Runnable runnable) {
        executorService.execute(runnable);
    }

    public static void log(String ...str) {
        executorService.execute(()->System.out.println(String.join("\n", str)));
    }
}
