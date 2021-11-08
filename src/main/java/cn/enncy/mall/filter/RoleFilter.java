package cn.enncy.mall.filter;


import cn.enncy.mall.pojo.MallSession;
import cn.enncy.mall.pojo.Role;
import cn.enncy.mall.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 20:10 2021/11/7
 *
 * @author enncy
 */

@WebFilter(urlPatterns = "/*")
public class RoleFilter implements Filter {

    List<String> userRolePaths = Collections.singletonList("/user");
    List<String> adminRolePaths = Collections.singletonList("/admin");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = Arrays.stream(request.getServletPath().split("/")).filter(p -> !p.contains("jsp")).collect(Collectors.joining("/"));

        // 后台权限验证
        if(adminRolePaths.stream().anyMatch(path->request.getServletPath().startsWith(path))){
            User user = (User) request.getSession().getAttribute("user");
            if(user!=null && user.getRole().equals(Role.ADMIN.value)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                MallSession.save(request.getSession(),MallSession.ORIGIN,origin);
                response.sendRedirect("/login");
            }
        }else
        // 用户权限验证，如果未登录，则跳转到登录界面
        if(userRolePaths.stream().anyMatch(path->request.getServletPath().startsWith(path))){
            Object user = request.getSession().getAttribute("user");
            if(user!=null){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                MallSession.save(request.getSession(),MallSession.ORIGIN,origin);
                response.sendRedirect("/login");
            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
