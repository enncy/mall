

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN">
<head>
    <meta charset="utf-8">

    <title>Mall 登录</title>

    <!-- Bootstrap core CSS -->
    <link href="https://v5.bootcss.com/docs/5.1/dist/css/bootstrap.min.css" rel="stylesheet" >
    <!-- Custom styles for this template -->
    <link href="https://v5.bootcss.com/docs/examples/sign-in/signin.css" rel="stylesheet">
    <link href="/assets/css/common.css" rel="stylesheet">
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>



</head>
<body class="text-center">


<form class="form-signin" method="post">


    <jsp:include page="/common/alert.jsp"/>

    <h1 class="h3 mb-3 font-weight-normal">账户登录</h1>

    <div class="form-floating">
        <input name="account" type="text" id="inputAccount" class="form-control" placeholder="账号" required size="20"
               value="${requestScope['account']}">
        <label for="inputAccount"  >账号</label>
    </div>
    <div class="form-floating">

        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required size="20"
               value="${requestScope['password']}">
        <label for="inputPassword"  >密码</label>
    </div>

    <div class="mb-2 text-right"> <a href="/forget">忘记密码</a> </div>

    <button class="btn btn-lg btn-primary w-100" type="submit">登录</button>
    <a class=" btn btn-lg btn-outline-secondary btn-block mt-3 w-100" href="/register">注册</a>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>
</form>


</body>
</html>
