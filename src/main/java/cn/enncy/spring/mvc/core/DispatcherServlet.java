package cn.enncy.spring.mvc.core;


import cn.enncy.common.type.TypeUtils;
import cn.enncy.mall.utils.RequestUtils;
import cn.enncy.reflect.AnnotationUtils;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.RequestMapping;
import cn.enncy.spring.mvc.annotation.params.*;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.entity.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 15:43 2021/11/20
 *
 * @author enncy
 */

@WebFilter(urlPatterns = "/*")
public class DispatcherServlet implements Filter {

    List<Class<?>> controllers;
    AnnotationUtils annotationUtils = new AnnotationUtils();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            String path = Arrays.stream(DispatcherServlet.class.getName().split("\\.")).findAny().get();
            controllers = annotationUtils.getAnnotationClasses(path, Controller.class);
        } catch (URISyntaxException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理请求
     *
     * @param method     控制台的方法
     * @param controller Controller 控制器实例
     * @param req
     * @param resp
     * @return boolean
     */
    private void handleRequest(Method method, Object controller, HttpServletRequest req, HttpServletResponse resp) {

        try {
            List<Object> objects = parseParameters(method, req, resp);
            Object invoke = method.invoke(controller, objects.toArray());

            // 如果请求未被处理
            if (!resp.isCommitted()) {
                if (invoke instanceof String) {
                    String servletPath = (String) invoke;
                    if (!servletPath.endsWith(".jsp")) {
                        servletPath += ".jsp";
                    }
                    req.getRequestDispatcher(servletPath).forward(req, resp);
                } else {
                    req.setAttribute("code", "500");
                    req.getRequestDispatcher("/error/index.jsp").forward(req, resp);
                    throw new Exception("controller method return value is not string : " + method);
                }

            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ServletException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 转换路径
     *
     * @param str
     * @return java.util.List<java.lang.String>
     */
    private List<String> validPath(String str) {
        return Arrays.stream(str.split("/")).filter(s -> !"".equals(s)).collect(Collectors.toList());
    }

    /**
     * 转换请求体，传递到方法参数体中
     *
     * @param method 方法
     * @param req
     * @param resp
     * @return java.util.List<java.lang.Object>
     */
    private List<Object> parseParameters(Method method, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Object> args = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {

            if (parameter.isAnnotationPresent(Param.class)) {

                Param annotation = parameter.getAnnotation(Param.class);
                // 获取请求参数 并转换为相应的类型
                Object target = TypeUtils.stringToTarget(req.getParameter(annotation.value()), parameter.getType());
                req.setAttribute(annotation.value(), target);
                args.add(target);
            } else if (parameter.isAnnotationPresent(Body.class)) {
                args.add(RequestUtils.parseObject(req, parameter.getType()));
            } else if (ServletRequest.class.isAssignableFrom(parameter.getType())) {
                args.add(req);
            } else if (ServletResponse.class.isAssignableFrom(parameter.getType())) {
                args.add(resp);
            } else if (HttpSession.class.isAssignableFrom(parameter.getType())) {
                args.add(req.getSession());
            } else {
                args.add(null);
            }
        }
        return args;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        for (Class<?> controller : controllers) {

            try {
                Object obj = ControllerFactory.resolve(controller, req, resp);

                Class<? extends Annotation> target = null;

                if ("POST".equalsIgnoreCase(req.getMethod())) {
                    target = Post.class;
                } else if ("GET".equalsIgnoreCase(req.getMethod())) {
                    target = Get.class;
                }

                // 获取  controller 的 方法
                List<Method> controllerMethods = annotationUtils.getAnnotationMethods(controller, target);

                // 遍历 controller 的方法
                for (Method requestMethod : controllerMethods) {

                    // 获取 带有 target 注解的方法
                    for (Annotation annotation : requestMethod.getAnnotations()) {

                        if (annotation.annotationType().equals(target)) {
                            String path = "";
                            if ("POST".equalsIgnoreCase(req.getMethod())) {
                                path = ((Post) annotation).value();
                            } else if ("GET".equalsIgnoreCase(req.getMethod())) {
                                path = ((Get) annotation).value();
                            }
                            String validPath = String.join("/", validPath(path));
                            String validTargetPath = String.join("/", validPath(req.getRequestURI()));
                            // 如果路径匹配
                            if (validPath.equalsIgnoreCase(validTargetPath)) {

                                // 处理
                                handleRequest(requestMethod, obj, req, resp);
                                return;
                            }

                        }
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

        }

        if(!resp.isCommitted()){
            filterChain.doFilter(req, resp);
        }
    }
}
