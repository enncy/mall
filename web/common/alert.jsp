
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="alert alert-success" role="alert" style="display:${empty requestScope.msg ? "none" : "block"}">
    ${requestScope.msg}
</div>
<div class="alert alert-danger" role="alert" style="display:${empty requestScope.error ? "none" : "block"}">
    ${requestScope.error}
</div>