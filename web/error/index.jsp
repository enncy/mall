

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/common/header.jsp"/>

<jsp:include page="/common/navigation.jsp"/>

<div class="p-5 d-flex justify-content-center">

    <%
        String code = request.getParameter("code");
        String msg;
        String detail ;
        if(code.equals("404")){
            msg = "404 NOT FOUND";
            detail = "您正在访问的页面不存在了，请尝试访问其他页面。";
        }else{
            msg = "500 INTERNAL SERVER ERROR";
            detail = "服务器内部出现了问题，请您尝试访问其他页面";
        }
    %>

    <div class="jumbotron" style="width: 50%;">
        <h1><%=msg%></h1>
        <p class="lead"><%=detail%></p>
        <a class="btn btn-lg btn-primary float-right" href="/" role="button">回到首页</a>
    </div>

</div>

<jsp:include page="/common/footer.jsp"/>