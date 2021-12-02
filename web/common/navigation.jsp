<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.constant.Role" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.math.BigDecimal" %>

<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mall.service.impl.UserServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    UserService userService = ServiceFactory.resolve(UserServiceImpl.class);
    User user = null;
    if (session.getAttribute("user") != null) {
        user = (User) session.getAttribute("user");

        user = userService.findOneById(user.getId());
        session.setAttribute("user",user);
    }

%>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid col-lg-8 cool-md-10 col-12">
            <a class="navbar-brand" href="#">Mall</a>
            <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false"
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse " id="navbarCollapse" style="">
                <ul class="navbar-nav me-auto mb-2 justify-content-center  mb-md-0 w-100">
                    <li class="nav-item">
                        <a class="nav-link" href="/">首页</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/goods">商品</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/user/cart">购物车</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/user/orders">订单列表</a>
                    </li>

                    <li class="nav-item ms-lg-5 me-lg-5 ">
                        <form class="d-flex" style="margin-block-end: 0px;white-space: nowrap">
                            <div class="input-group ">
                                <input class="form-control" type="search"  name="search" placeholder="输入商品名字"
                                       value="${param.search}">
                                <button class="btn btn-secondary" type="submit">搜索</button>
                            </div>

                        </form>
                    </li>


                    <% if (user != null) { %>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <%=user.getNickname()%>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="/user">个人首页</a></li>
                            <% if (user.getRole().equals(Role.ADMIN.value)) { %>
                            <li><a class="dropdown-item" href="/admin">后台系统</a></li>
                            <% } %>

                            <li><a class="dropdown-item" href="/logout">退出</a></li>
                        </ul>
                    </li>

                    <li  class="nav-item ms-lg-4">
                        <a class="nav-link d-flex align-items-center" href="/user/balance">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-currency-dollar" viewBox="0 0 16 16">
                                <path d="M4 10.781c.148 1.667 1.513 2.85 3.591 3.003V15h1.043v-1.216c2.27-.179 3.678-1.438 3.678-3.3 0-1.59-.947-2.51-2.956-3.028l-.722-.187V3.467c1.122.11 1.879.714 2.07 1.616h1.47c-.166-1.6-1.54-2.748-3.54-2.875V1H7.591v1.233c-1.939.23-3.27 1.472-3.27 3.156 0 1.454.966 2.483 2.661 2.917l.61.162v4.031c-1.149-.17-1.94-.8-2.131-1.718H4zm3.391-3.836c-1.043-.263-1.6-.825-1.6-1.616 0-.944.704-1.641 1.8-1.828v3.495l-.2-.05zm1.591 1.872c1.287.323 1.852.859 1.852 1.769 0 1.097-.826 1.828-2.2 1.939V8.73l.348.086z"/>
                            </svg>
                            <%=user.getBalance().toString().equals("0") ? "0" : new DecimalFormat("#,##0.00").format(user.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP))%>
                        </a>
                    </li>

                    <% } else { %>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">未登录</a>
                    </li>
                    <% } %>

                </ul>


            </div>
        </div>
    </nav>


</header>

<main>