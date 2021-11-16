<%@ page import="cn.enncy.mall.mapper.UserMapper" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.utils.Email" %>
<%@ page import="cn.enncy.mall.utils.Security" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.pojo.Role" %>
<%@ page import="java.io.IOException" %>
<%@ page import="cn.enncy.mall.pojo.MallSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    String msg = null;
    String error = null;

    String email = request.getParameter("email");


    if (request.getMethod().equals("POST")) {
        // 创建激活 秘钥 和 链接
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/forget/reset?email=" + email + "&token=" + Security.stringToMD5(email);
        // 发送注册右键
        try {
            Email.reset(email, url);
            msg = "邮箱发送成功，请在邮箱里面点击链接找回密码。";
        } catch (Exception e) {
            e.printStackTrace();
            error = "服务器错误";
        }

    }

%>


<html lang="zh-CN">
<head>
    <meta charset="utf-8">

    <title>Mall 找回密码</title>

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


    <h1 class="h3 mb-3 font-weight-normal">找回密码</h1>

    <label for="inputEmail" class="sr-only">邮箱</label>
    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="邮箱" required size="42"
           value="<%=(email==null?"":email)%>">


    <button class="btn btn-lg btn-primary btn-block mt-3" type="submit">找回</button>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>
</form>


</body>
</html>
