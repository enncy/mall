<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<jsp:include page="/common/header.jsp"/>

<jsp:include page="/common/navigation.jsp"/>

<div class="p-5 d-flex justify-content-center">

    <%
        String code = request.getParameter("code");
        String msg;
        String detail;

        if (code.equals("404")) {
            msg = "404 NOT FOUND";
            detail = "您正在访问的页面不存在了，请尝试访问其他页面。";
        } else if (code.equals("400")) {

            msg = "400 Bad Request";
            detail = "错误的请求方式或参数，请尝试访问其他页面。";
        } else if (code.equals("403")) {

            msg = "403 Forbidden";
            detail = "服务器禁止您访问该页面，可能是您的权限不足导致。";
        } else {
            msg = "500 INTERNAL SERVER ERROR";
            detail = "服务器内部出现了问题，请您尝试访问其他页面";

        }

        exception.printStackTrace();
    %>

    <div class="jumbotron col-lg-6 col-sm-12">
        <h1><%=msg%>
        </h1>
        <p class="lead"><%=detail%>
        </p>
        <a class="btn btn-lg btn-primary float-right m-1" role="button" onclick="window.history.back()">返回上一页</a>
        <a class="btn btn-lg btn-primary float-right m-1" href="/" role="button">回到首页</a>

    </div>

</div>

<jsp:include page="/common/footer.jsp"/>