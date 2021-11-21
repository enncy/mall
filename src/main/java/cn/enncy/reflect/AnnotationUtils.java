package cn.enncy.reflect;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 11:00 2021/11/6
 *
 * @author enncy
 */
public class AnnotationUtils {


    private final PackageScanner scanner;

    public AnnotationUtils() {

        this.scanner = new PackageScanner();
    }

    /**
     *  获取带有 annotation 注解标注的类
     * @param packagePath 包路径
     * @param target  注解类
     * @return: java.util.List<java.lang.Class<?>>
     */
    public <T extends Annotation> List<Class<?>> getAnnotationClasses(String packagePath,Class<T> target) throws URISyntaxException, ClassNotFoundException {
        Class<?>[] classes = scanner.scan(packagePath);
        return Arrays.stream(classes).filter(c -> c.isAnnotationPresent(target)).collect(Collectors.toList());
    }

    /**
     *  获取带有 annotation 注解标注的类方法
     *
     * @param clazz 目标类
     * @param target 目标注解
     * @return: java.util.List<java.lang.Method>
     */
    public <T extends Annotation> List<Method> getAnnotationMethods(Class<?> clazz, Class<T> target) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        return Arrays.stream(declaredMethods).filter(c -> c.isAnnotationPresent(target)).collect(Collectors.toList());
    }

    /**
     *  获取带有 annotation 注解标注的类属性
     *
     * @param clazz 目标类
     * @param target 目标注解
     * @return: java.util.List<java.lang.reflect.Field>
     */
    public <T extends Annotation> List<Field> getAnnotationFields(Class<?> clazz, Class<T> target) {
        Field[] declaredFields = clazz.getDeclaredFields();
        return Arrays.stream(declaredFields).filter(c -> c.isAnnotationPresent(target)).collect(Collectors.toList());
    }

    /**
     *  获取带有 annotation 注解标注的属性参数
     *
     * @param method 目标类
     * @param target 目标注解
     * @return: java.util.List<java.lang.reflect.Parameter>
     */
    public <T extends Annotation> List<Parameter> getAnnotationMethodParams(Method method, Class<T> target) {
        Parameter[] parameters = method.getParameters();
        return Arrays.stream(parameters).filter(p -> p.isAnnotationPresent(target)).collect(Collectors.toList());
    }




}
