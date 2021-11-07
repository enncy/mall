<%@ page import="cn.enncy.mall.mapper.UserMapper" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.utils.Email" %>
<%@ page import="cn.enncy.mall.utils.Security" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.pojo.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN">
<head>
    <meta charset="utf-8">

    <title>Mall 注册</title>

    <!-- Bootstrap core CSS -->
    <link href="https://v4.bootcss.com/docs/4.6/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>


    <!-- Custom styles for this template -->
    <style>
        html,
        body {
            height: 100%;
        }

        body {
            display: -ms-flexbox;
            display: flex;
            -ms-flex-align: center;
            align-items: center;
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }

        .form-signin .checkbox {
            font-weight: 400;
        }

        .form-signin .form-control {
            position: relative;
            box-sizing: border-box;
            height: auto;
            padding: 10px;
            font-size: 16px;
        }

        .form-signin .form-control:focus {
            z-index: 2;
        }

        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }

        .alert{
            line-height: 1 !important;
        }

    </style>
</head>
<body class="text-center">

<%

    String account = request.getParameter("account");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String token = request.getParameter("token");

    String msg = null;
    String error = null;

    if (request.getMethod().equals("POST")) {
        if (StringUtils.isNullOrEmpty(account) || StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(email)) {
            error = "不能留空!";
        } else {
            UserMapper mapper = SqlSession.getMapper(UserMapper.class);

            if (mapper.findByAccount(account) != null) {
                error = "账号已被占用!";
            } else if (mapper.findByEmail(email) != null) {
                error = "邮箱已被占用!";
            } else {
                try {

                    // 创建激活 秘钥 和 链接
                    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/register?account=" + account + "&token=" + Security.stringToMD5(account);
                    // 发送注册右键
                    Email.register(email, url);
                    // 创建用户
                    User user = new User();
                    user.setAccount(account);
                    user.setPassword(password);
                    user.setRole(Role.USER.value);
                    user.setEmail(email);
                    mapper.insert(user);
                    msg = "邮箱已发送，请在邮箱中验证您的账号!";
                } catch (Exception e) {
                    e.printStackTrace();
                    error = "服务器错误";
                }

            }
        }
    } else {

        if (!StringUtils.isNullOrEmpty(token) && !StringUtils.isNullOrEmpty(account)) {
            UserMapper mapper = SqlSession.getMapper(UserMapper.class);
            // 验证秘钥
            if (token.equals(Security.stringToMD5(account))) {
                User user = mapper.findByAccount(account);
                user.setActive(true);
                mapper.update(user);
                session.setAttribute("user",user);
                response.sendRedirect("/");
                msg = "账号验证成功！";
            } else {
                error = "账号验证失败！";
            }
        }

    }

%>

<form class="form-signin" method="post">

    <div class="alert alert-success" role="alert" style="display: <%=msg == null ? "none" : "block"%> ">
        <%=msg == null ? "" : msg%>
    </div>
    <div class="alert alert-danger" role="alert" style="display: <%=error == null ? "none" : "block"%> ">
        <%=error == null ? "" : error%>
    </div>

    <h1 class="h3 mb-3 font-weight-normal">账户注册</h1>


    <label for="inputAccount" class="sr-only">账号</label>
    <input name="account" type="text" id="inputAccount" class="form-control" placeholder="账号" required size="20"
           value="<%=(account==null?"":account)%>">

    <label for="inputPassword" class="sr-only ">密码</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required size="20"
           value="<%=(password==null?"":password)%>">

    <label for="inputEmail" class="sr-only">邮箱</label>
    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="邮箱" required size="42"
           value="<%=(email==null?"":email)%>">


    <button class="btn btn-lg btn-primary btn-block mt-3" type="submit">注册</button>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>
</form>


</body>
</html>
