<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    .nav, .nav-item {

        white-space: nowrap;
    }

    .collapse a{
        color: #7d8790;
    }
    .user-nav{
        border: 1px solid #ececec;
        border-right: 0px;
    }
    .user-nav li{
        background-color: #f8f8f8;
        border: 0px;
        border-bottom: 1px solid #ececec;

    }
    .user-nav a{
        color: #655f5f;
    }
    .user-nav{
        background-color: #f8f8f8
    }
</style>

<div class="d-flex col-12 col-lg-2 flex-wrap user-nav" >

    <ul class="list-group col-12 h-100" style="white-space: nowrap;"  >
        <li class="list-group-item">
            <a class="nav-link" href="/user">个人信息</a>
        </li>
        <li class="list-group-item">
            <a class="nav-link" href="/user/balance">我的余额</a>
        </li>
        <li class="list-group-item">
            <a class="nav-link" href="/user/address">收货地址</a>
        </li>
        <li class="list-group-item">
            <a class="nav-link" href="/user/cart">购物车</a>
        </li>
        <li class="list-group-item">
            <a class="nav-link" href="/user/orders">订单列表</a>
        </li>
    </ul>
</div>
