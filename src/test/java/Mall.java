import cn.enncy.mall.mapper.TestMapper;
import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.UserService;
import cn.enncy.mall.service.impl.UserServiceImpl;
import cn.enncy.mall.utils.Logger;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.core.SqlSession;
import cn.enncy.mybatis.utils.ParameterizedTypeUtils;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.*;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:31 2021/11/30
 *
 * @author enncy
 */

class Father{ }
class Son{ }
class Person<T>{ }
class XiaoMing extends Person<Son> { }
class ZhangSan extends Person<Father>{ }

public class Mall<T,E>  {

    List<? extends  T> list = new ArrayList<>();

    List<T> list1 = new ArrayList<>();

    List<T[]> list2 = new ArrayList<>();

    List<List<T>> list3 = new ArrayList<>();

    List<T> test(){
        return null;
    }

    List<Map<String,T>> testMap(){
        return null;
    }

    @Test
    public void typeTest() throws NoSuchFieldException, NoSuchMethodException {
        System.out.println(ParameterizedTypeUtils.get(ZhangSan.class.getGenericSuperclass(), 0) );
        System.out.println(ParameterizedTypeUtils.get(XiaoMing.class.getGenericSuperclass(), 0) );

        System.out.println(Arrays.toString(this.getClass().getTypeParameters()));

        Type list = this.getClass().getDeclaredField("list").getGenericType();
        Type list1 = this.getClass().getDeclaredField("list1").getGenericType();
        Type list2 = this.getClass().getDeclaredField("list2").getGenericType();
        Type list3 = this.getClass().getDeclaredField("list3").getGenericType();
        Type test = this.getClass().getDeclaredMethod("test").getGenericReturnType();
        Type testMap = this.getClass().getDeclaredMethod("testMap").getGenericReturnType();

        Type type  = ParameterizedTypeUtils.get(list  , 0);
        Type type1 = ParameterizedTypeUtils.get(list1, 0);
        Type type2 = ParameterizedTypeUtils.get(list2, 0);
        Type type3 = ParameterizedTypeUtils.get(list3, 0);
        Type testType= ParameterizedTypeUtils.get(test, 0);
        Type typeMapType = ParameterizedTypeUtils.get(testMap, 0);

        System.out.println(type.getClass() + " : "+type);
        System.out.println(type2.getClass() + " : "+type1);
        System.out.println(type2.getClass() + " : "+type2.getTypeName());
        System.out.println(type3.getClass() + " : "+type3);
        System.out.println(testType.getClass() + " : "+testType);
        System.out.println(typeMapType.getClass() + " : "+typeMapType);

    }

    @Test
    public void time(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(day-1);
    }


    @Test
    public void findMapper(){
        Mapper mapper = null;
        Class<?> target = UserMapper.class;
        LinkedList<Class<?>> interfaceList = new LinkedList<>();
        interfaceList.add(target);
        interfaceList.add(target.getSuperclass());
        interfaceList.addAll(Arrays.asList(target.getInterfaces()));
        while (!interfaceList.isEmpty()) {
            Class<?> first = interfaceList.pop();
            if (first != null) {
                if (first.isAnnotationPresent(Mapper.class)) {
                    mapper = first.getAnnotation(Mapper.class);
                    break;
                } else {
                    interfaceList.push(first.getSuperclass());
                    interfaceList.addAll(Arrays.asList(first.getInterfaces()));
                }
            }
        }

        System.out.println(mapper);
    }


    @Test
    public void mapper(){
        TestMapper mapper = SqlSession.getMapper(TestMapper.class);
        List<User> users = mapper.findByAll();
        for (User user : users) {
            Logger.log(user.toString());
        }
    }
}
