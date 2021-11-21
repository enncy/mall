
<%@ page import="cn.enncy.mall.utils.Email" %>
<%@ page import="cn.enncy.mall.utils.Security" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<html lang="zh-CN">
<head>
    <meta charset="utf-8">

    <title>Mall 找回密码</title>

    <!-- Bootstrap core CSS -->
    <link href="https://v5.bootcss.com/docs/5.1/dist/css/bootstrap.min.css" rel="stylesheet" >
    <!-- Custom styles for this template -->
    <link href="https://v5.bootcss.com/docs/examples/sign-in/signin.css" rel="stylesheet">
    <link href="/assets/css/common.css" rel="stylesheet">
</head>
<body class="text-center">


<form class="form-signin" method="post">

    <jsp:include page="/common/alert.jsp"/>

    <h1 class="h3 mb-3 font-weight-normal">找回密码</h1>

    <div class="form-floating">
        <input name="email" type="email" id="inputEmail" class="form-control" placeholder="邮箱" required size="42"
               value="${param.email}">
        <label for="inputEmail" class="sr-only">邮箱</label>
    </div>

    <button class="btn btn-lg btn-primary btn-block mt-3 w-100" type="submit">找回</button>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>
</form>


</body>
</html>
