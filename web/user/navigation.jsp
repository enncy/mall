<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    .nav,.nav-item{

        white-space: nowrap;
    }
</style>

<div class="d-flex col-12 col-lg-1">
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link" href="/user">个人信息</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/balance">余额充值</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/address">收货地址</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/cart">购物车</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/orders">订单</a>
        </li>


        <%--<li class="nav-item">--%>
        <%--    <a class="nav-link" href="/user/favor">收藏夹</a>--%>
        <%--</li>--%>

    </ul>
</div>
