<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.mapper.UserMapper" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.utils.Security" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    String msg = null;
    String error = null;
    boolean reset = false;

    String password = request.getParameter("password");
    String confirmPassword = request.getParameter("confirmPassword");
    String email = request.getParameter("email");
    String token = request.getParameter("token");
    UserMapper mapper = SqlSession.getMapper(UserMapper.class);

    // 控制是否显示重置密码表单
    if (!StringUtils.isNullOrEmpty(token) && !StringUtils.isNullOrEmpty(email)) {

        // 验证秘钥
        if (token.equals(Security.stringToMD5(email))) {
            User user = mapper.findOneByEmail(email);
            reset = true;
            msg = user.getNickname() == null ? user.getAccount() : user.getNickname() + " 请重置您的密码";
        } else {
            response.sendRedirect("/error?code=400");
        }
    }


    if (request.getMethod().equals("POST")) {
        if (reset) {
            if (StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(confirmPassword)) {
                error = "不能留空!";
            } else if (!confirmPassword.equals(password)) {
                error = "2次输入的密码不一致!";
            } else {
                User user = mapper.findOneByEmail(email);
                user.setPassword(password);
                mapper.update(user);
                msg = "重置密码成功";
                response.sendRedirect("/login");
            }
        } else {
            response.sendRedirect("/error?code=400");
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


    <% if (reset) { %>

    <label for="inputPassword" class="sr-only ">密码</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required size="20"
           value="<%=(password==null?"":password)%>">

    <label for="inputConfirmPassword" class="sr-only ">重复密码</label>
    <input name="confirmPassword" type="password" id="inputConfirmPassword" class="form-control" placeholder="重复密码"
           required size="20"
           value="<%=(password==null?"":password)%>">


    <button class="btn btn-lg btn-primary btn-block mt-3" type="submit">重置</button>
    <p class="mt-5 mb-3 text-muted">© 2021 mall</p>

    <% } %>

</form>


</body>
</html>
