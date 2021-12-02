
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
    <a href="/"><svg t="1638442084571" class="icon mb-2 mt-2"    viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2475" width="64" height="64"><path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64z m256 253.7l-40.8 39.1c-3.6 2.7-5.3 7.1-4.6 11.4v287.7c-0.7 4.4 1 8.8 4.6 11.4l40 39.1v8.7H566.4v-8.3l41.3-40.1c4.1-4.1 4.1-5.3 4.1-11.4V422.5l-115 291.6h-15.5L347.5 422.5V618c-1.2 8.2 1.7 16.5 7.5 22.4l53.8 65.1v8.7H256v-8.7l53.8-65.1c5.8-5.9 8.3-14.3 7-22.4V392c0.7-6.3-1.7-12.4-6.5-16.7l-47.8-57.6V309H411l114.6 251.5 100.9-251.3H768v8.5z" p-id="2476" fill="#707070"></path></svg></a>

    <h2 class="h3 mb-3 font-weight-normal">账户注册</h2>


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
