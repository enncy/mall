package cn.enncy.mybatis.core;


import java.lang.reflect.Proxy;

/**
 * //TODO
 * <br/>Created in 15:24 2021/11/6
 *
 * @author enncy
 */
public class SqlSession {
    public static <T> T getMapper(Class<T> target) {
        System.out.println("target "+target);
        return (T) Proxy.newProxyInstance(target.getClassLoader(), new Class[]{target}, new SqlInvokeHandler(target));
    }

}
