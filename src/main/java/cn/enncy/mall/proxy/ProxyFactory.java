package cn.enncy.mall.proxy;


import cn.enncy.mybatis.core.SqlInvokeHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * //TODO
 * <br/>Created in 14:20 2021/11/19
 *
 * @author enncy
 */
public class ProxyFactory {

    public static  <T> T create(T target, InvocationHandler handler){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler) ;
    }
}
