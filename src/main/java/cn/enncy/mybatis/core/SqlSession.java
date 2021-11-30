package cn.enncy.mybatis.core;


import cn.enncy.mybatis.core.invoke.SqlInvokeHandler;
import cn.enncy.mybatis.core.invoke.TransactionInvokeHandler;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 15:24 2021/11/6
 *
 * @author enncy
 */
public class SqlSession {
    public static <T> T getMapper(Class<T> target) {
        return (T) Proxy.newProxyInstance(target.getClassLoader(), new Class[]{target}, new SqlInvokeHandler(target)) ;
    }

    public static  <T> T  getService(T target){
        List<Class<?>> interfaceList = new ArrayList<>();
        interfaceList.addAll(Arrays.asList(target.getClass().getInterfaces()));
        interfaceList.addAll(Arrays.asList(target.getClass().getSuperclass().getInterfaces()));
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaceList.toArray(new Class[]{}), new TransactionInvokeHandler(target )) ;
    }

}
