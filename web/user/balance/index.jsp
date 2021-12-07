<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/common/header.jsp"/>
<jsp:include page="/common/navigation.jsp"/>
<%
    User user = (User) session.getAttribute("user");
%>

<div class="container  p-lg-5 mt-lg-5 mb-lg-5  p-md-2 mt-md-2 mb-md-2 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap  card p-4  col-lg-10 col-md-11 col-12">
        <div >
            <div class="fw-bold">我的余额</div>

            <div class="mt-4 mb-4 fs-1">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-coin" viewBox="0 0 16 16">
                    <path d="M5.5 9.511c.076.954.83 1.697 2.182 1.785V12h.6v-.709c1.4-.098 2.218-.846 2.218-1.932 0-.987-.626-1.496-1.745-1.76l-.473-.112V5.57c.6.068.982.396 1.074.85h1.052c-.076-.919-.864-1.638-2.126-1.716V4h-.6v.719c-1.195.117-2.01.836-2.01 1.853 0 .9.606 1.472 1.613 1.707l.397.098v2.034c-.615-.093-1.022-.43-1.114-.9H5.5zm2.177-2.166c-.59-.137-.91-.416-.91-.836 0-.47.345-.822.915-.925v1.76h-.005zm.692 1.193c.717.166 1.048.435 1.048.91 0 .542-.412.914-1.135.982V8.518l.087.02z"/>
                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                    <path d="M8 13.5a5.5 5.5 0 1 1 0-11 5.5 5.5 0 0 1 0 11zm0 .5A6 6 0 1 0 8 2a6 6 0 0 0 0 12z"/>
                </svg>

                <%=user.getBalance().toString().equals("0") ? "0" : new DecimalFormat("#,##0.00").format(user.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP))%>
                <span class="ms-4 fs-4"> CNY</span>
            </div>
        </div>
        <form method="POST" action="/user/balance/in">
            <div class="fw-bold mt-4 mb-4">
                余额充值
            </div>
            <div class="input-group mb-3">
                <input name="balance" type="number" value="0" min="0" step="0.01" class="form-control" >
                <input class="btn btn-outline-secondary" value="充值" type="submit">
            </div>
            <input class="btn btn-outline-secondary" value="100" type="submit"  name="money">
            <input class="btn btn-outline-secondary" value="1000" type="submit"  name="money">
            <input class="btn btn-outline-secondary" value="10000" type="submit"  name="money">
        </form>
        <form method="POST"  action="/user/balance/out">
            <div class="fw-bold mt-4 mb-4">
                余额提现
            </div>
            <div class="input-group mb-3">
                <input name="balance" type="number" value="0" min="0" step="0.01" class="form-control" >
                <input class="btn btn-outline-secondary" value="提现" type="submit">
            </div>
            <input class="btn btn-outline-secondary" value="全部" type="submit" name="operate">
            <input class="btn btn-outline-secondary" value="1/2" type="submit" name="operate">
            <input class="btn btn-outline-secondary" value="100" type="submit"  name="money">
            <input class="btn btn-outline-secondary" value="1000" type="submit"  name="money">
            <input class="btn btn-outline-secondary" value="10000" type="submit"  name="money">
        </form>

    </div>
</div>


<jsp:include page="/common/footer.jsp"/>

