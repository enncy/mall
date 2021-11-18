<%@ page import="cn.enncy.mall.mapper.UserMapper" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.utils.Email" %>
<%@ page import="cn.enncy.mall.utils.Security" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.constant.Role" %>
<%@ page import="java.io.IOException" %>
<%@ page import="cn.enncy.mall.constant.MallSession" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mall.service.ServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String msg = null;
    String error = null;

    String account = request.getParameter("account");
    String password = request.getParameter("password");

    // 如果已经登录，则无需登录
    if(session.getAttribute("user")!=null){
        request.getRequestDispatcher("/").forward(request, response);
    }else{
        if (request.getMethod().equals("POST")) {
            if (StringUtils.isNullOrEmpty(account) || StringUtils.isNullOrEmpty(password)) {
                error = "不能留空!";
            } else {

                UserService userService = ServiceFactory.resolve(UserService.class);
                User user = userService.findOneByAccount(account);
                if(user!=null && user.getPassword().equals(password)){
                    session.setAttribute("user", user);
                    msg = "登录成功!";
                    // 将用户存入 session
                    MallSession.save(request.getSession(),MallSession.USER,user);
                    String origin = (String) MallSession.from(request.getSession(), MallSession.ORIGIN);
                    if(StringUtils.isNullOrEmpty(origin)){
                        response.sendRedirect("/");
                    }else{
                        response.sendRedirect(origin);
                    }
                }else{
                    error = "登录失败，账号或者密码错误!";
                }
            }
        }

    }


%>



<html lang="zh-CN">
<head>
    <meta charset="utf-8">

    <title>Mall 注册</title>

    <!-- Bootstrap core CSS -->
    <link href="https://v4.bootcss.com/docs/4.6/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/css/common.css" rel="stylesheet">
</head>
<body class="text-center">


<form class="form-signin" method="post">


    <div class="alert alert-success" role="alert" style="display: <%=msg == null ? "none" : "block"%> ">
        <%=msg == null ? "" : msg%>
    </div>
    <div class="alert alert-danger" role="alert" style="display: <%=error == null ? "none" : "block"%> ">
        <%=error == null ? "" : error%>
    </div>


    <h1 class="h3 mb-3 font-weight-normal">账户登录</h1>

    <label for="inputAccount" class="sr-only">账号</label>
    <input name="account" type="text" id="inputAccount" class="form-control" placeholder="账号" required size="20"
           value="<%=(account==null?"":account)%>">

    <label for="inputPassword" class="sr-only ">密码</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required size="20"
           value="<%=(password==null?"":password)%>">

    <div class="mb-2 text-right"> <a href="/forget">忘记密码</a> </div>

    <button class="btn btn-lg btn-primary btn-block mt-3" type="submit">登录</button>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>
</form>


</body>
</html>
