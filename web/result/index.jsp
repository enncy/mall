<%@ page import="cn.enncy.mall.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>


    .error {
        height: 100px;
        width: 100px;
        color: #e75f5fed;
    }

    .success {
        height: 100px;
        width: 100px;
        color: #47d160ed;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>


<%
    String error = (String) request.getAttribute("error");
    String msg = (String) request.getAttribute("msg");
%>

<div class="container p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center flex-wrap text-center">
    <div class="p-4">
        <% if (StringUtils.isEmpty(error)) { %>

        <p>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                 class="success bi bi-check-circle-fill" viewBox="0 0 16 16">
                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
            </svg>
        </p>
        <p class="fw-bold fs-2"><%=msg%>
        </p>


        <% } else { %>

        <p>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                 class="error bi bi-x-circle-fill" viewBox="0 0 16 16">
                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z"/>
            </svg>
        </p>

        <p class="fw-bold fs-2"><%=error%>
        </p>

        <% } %>

        <p class="mt-5">
            <span>请您重试，3秒后返回 ${empty requestScope.redirect?header['Referer']:requestScope.redirect} ... <a href="${empty requestScope.redirect?header['Referer']:requestScope.redirect}">立刻跳转</a></span>
        </p>
    </div>

</div>

<jsp:include page="/common/footer.jsp"/>

<script>
    setTimeout(() => {
        window.location.href = '${empty requestScope.redirect?header['Referer']:requestScope.redirect}'
    }, 3000)
</script>

