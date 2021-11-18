<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.constant.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String favor = "login";
    String cart = "login";
    String orders = "login";
    User user = null;
    if (session.getAttribute("user") != null) {
        user = (User) session.getAttribute("user");
        user.setNickname(user.getNickname().isEmpty()?user.getAccount():user.getNickname());
        favor = "/user/favor";
        cart = "/user/cart";
        orders = "/user/orders";
    }

%>


<nav class="site-header sticky-top py-1">
    <div class="container d-flex flex-column flex-md-row justify-content-between">
        <a class="py-2" href="#" aria-label="Product">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor"
                 stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="d-block mx-auto" role="img"
                 viewBox="0 0 24 24" focusable="false"><title>Product</title>
                <circle cx="12" cy="12" r="10"></circle>
                <path d="M14.31 8l5.74 9.94M9.69 8h11.48M7.38 12l5.74-9.94M9.69 16L3.95 6.06M14.31 16H2.83m13.79-4l-5.74 9.94"></path>
            </svg>
        </a>
        <a class="py-2 d-none d-md-inline-block" href="/">首页</a>
        <a class="py-2 d-none d-md-inline-block" href="/goods">商品</a>
        <%--<a class="py-2 d-none d-md-inline-block" href="/<%=favor%>">收藏夹</a>--%>
        <a class="py-2 d-none d-md-inline-block" href="/<%=cart%>">购物车</a>
        <a class="py-2 d-none d-md-inline-block" href="/<%=orders%>">订单列表</a>


        <% if (user  != null) { %>
        <div class="dropdown">
            <a class=" dropdown-toggle py-2 d-none d-md-inline-block" href="#" role="button" id="dropdownMenuLink"
               data-toggle="dropdown" aria-expanded="false">
                <%=user.getNickname()%>
            </a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" href="/user">个人首页</a>
                <% if(user.getRole().equals(Role.ADMIN.value)){ %>
                <a class="dropdown-item" href="/admin">后台系统</a>
                <% } %>

                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/logout">退出</a>
            </div>
        </div>
        <% } else { %>
        <a class="py-2 d-none d-md-inline-block" href="/login">未登录</a>
        <% } %>


        </a>

    </div>
</nav>


