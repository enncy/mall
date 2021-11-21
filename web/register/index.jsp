
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html lang="zh-CN">
<head>
    <meta charset="utf-8">

    <title>Mall 注册</title>

    <!-- Bootstrap core CSS -->
    <link href="https://v5.bootcss.com/docs/5.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="https://v5.bootcss.com/docs/examples/sign-in/signin.css" rel="stylesheet">
    <link href="/assets/css/common.css" rel="stylesheet">
</head>
<body class="text-center">


<form class="form-signin" method="post" autocomplete="off">

    <jsp:include page="/common/alert.jsp"/>

    <h1 class="h3 mb-3 font-weight-normal">账户注册</h1>


    <div class="form-floating">
        <input name="account" type="text" id="inputAccount" class="form-control" placeholder="账号" required size="20"
               value="${requestScope.user.account}">
        <label for="inputAccount" >账号</label>
    </div>
    <div class="form-floating">

        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required
               size="20"
               value="${requestScope.user.password}">
        <label for="inputPassword" >密码</label>
    </div>
    <div class="form-floating">

        <input name="confirmPassword" type="password" id="inputConfirmPassword" class="form-control" placeholder="重复密码"
               required size="20" autocomplete="off"
               value="${requestScope.confirmPassword}">
        <label for="inputConfirmPassword"  >重复密码</label>
    </div>


    <div class="form-floating">
        <input name="email" type="email" id="inputEmail" class="form-control" placeholder="邮箱" required size="42"
               value="${requestScope.user.email}">
        <label for="inputEmail" >邮箱</label>
    </div>


    <button class="btn btn-lg btn-primary btn-block mt-3  w-100" type="submit">注册</button>
    <div class="mt-2 text-right"><a href="/login">已有账号？</a></div>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>
</form>


</body>
</html>
