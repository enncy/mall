import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:31 2021/11/30
 *
 * @author enncy
 */

public class Test {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(XiaoMing.class.getInterfaces()));
        PersonProxy personProxy = new PersonProxy(new XiaoMingSon());
        Person fundManager = personProxy.createProxy();
        fundManager.buy();
    }
}
interface Person{
    boolean buy();
    boolean sell();
    boolean autoInvestment();
}
interface Son{
    void play();
}

class XiaoMingSon extends  XiaoMing  implements  Son{
    @Override
    public boolean buy() {
        System.out.println("我是小明的孙子");
        return super.buy();
    }

    @Override
    public void play() {
        System.out.println("我要花钱");
    }
}

class XiaoMing implements Person{
    @Override
    public boolean buy() {
        System.out.println("我是小明，我要买基金");
        return false;
    }
    @Override
    public boolean sell() {
        System.out.println("我是小明，我要出售基金");
        return false;
    }
    @Override
    public boolean autoInvestment() {
        System.out.println("我是小明，我要定投基金");
        return false;
    }
}
class PersonProxy implements InvocationHandler {
    Object target;
    PersonProxy(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("方法"+method.getName()+"已经被我代理");
        if("buy".equals(method.getName())){
            method.invoke(target, args);
            System.out.println("我是代理，我将帮你买基金");
        }
        return true;
    }
    public <T> T createProxy(){
        List<Class<?>> interfaceList = new ArrayList<>();
        interfaceList.addAll(Arrays.asList(target.getClass().getInterfaces()));
        interfaceList.addAll(Arrays.asList(target.getClass().getSuperclass().getInterfaces()));

        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),interfaceList.toArray(new Class[]{}), this);
    }
}
