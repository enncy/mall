package cn.enncy.spring.mvc.entity;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * //TODO
 * <br/>Created in 17:06 2021/11/20
 *
 * @author enncy
 */
public class RequestMethod {

    private Method method;

    private String targetPath;
    private String path;

    public RequestMethod(Method method, String targetPath, String path) {
        this.method = method;
        this.targetPath = targetPath;
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "RequestMethod{" +
                "method=" + method +
                ", targetPath='" + targetPath + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
