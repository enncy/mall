<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.mapper.UserMapper" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.utils.Security" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html lang="zh-CN">
<head>
    <meta charset="utf-8">

    <title>Mall 找回密码</title>

    <!-- Bootstrap core CSS -->
    <link href="https://v5.bootcss.com/docs/5.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="https://v5.bootcss.com/docs/examples/sign-in/signin.css" rel="stylesheet">
    <link href="/assets/css/common.css" rel="stylesheet">
</head>
<body class="text-center">


<form class="form-signin" method="post">

    <jsp:include page="/common/alert.jsp"/>

    <div class="form-floating">

        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required
               size="20"
               value="${param.password}">

        <label for="inputPassword" class="sr-only ">密码</label>
    </div>

    <div class="form-floating">

        <input name="confirmPassword" type="password" id="inputConfirmPassword" class="form-control" placeholder="重复密码"
               required size="20"
               value="${param.confirmPassword}">
        <label for="inputConfirmPassword" class="sr-only ">重复密码</label>
    </div>


    <button class="btn btn-lg btn-primary btn-block mt-3 w-100" type="submit">重置</button>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>

</form>


</body>
</html>
