<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<style>
    .price {
        line-height: 25px;
        font-size: 24px;
        color: #fd3f31;
    }

    .price-div {

        box-shadow: 0px 0px 4px #dcdcdc;
        border-bottom-right-radius: 10px;
        border-bottom-left-radius: 10px;
    }

    .price-bg {
        height: 40px;
        background: url("/assets/img/detailbg.png");
        transform: rotate(180deg);
        opacity: 0.7;
        border-radius: 4px;

    }

    .goods-img {
        border-radius: 4px;
        width: 100%;
        max-width: 400px;
        min-width: 300px;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%

    Goods goods = (Goods) request.getAttribute("goods");
    User user = (User) session.getAttribute("user");
%>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center   flex-wrap">

    <div class="col-12 d-flex justify-content-center ">
        <div class="col-lg-6 col-12">
            <jsp:include page="/common/alert.jsp"/>
        </div>
    </div>

    <div class="col-12 col-md-10  col-lg-8">
        <div class="d-flex flex-wrap col-12">
            <div class="col-12   col-lg-5 text-center me-lg-4">

                <div><img class="goods-img" src="<%=goods.getImg()%>" alt="图片"></div>
            </div>
            <div class="col-12  col-lg-6">
                <form action="/goods/add" method="POST">
                    <input type="hidden" name="id" value="<%=goods.getId()%>">
                    <div class="p-2 pb-0 p-lg-0">
                        <p>名字：<b><%=goods.getName()%>
                        </b></p>
                        <p>描述：<b><%=goods.getDescription()%>
                        </b>
                        </p>
                    </div>
                    <div class="mt-4 mb-4  price-div">
                        <div class="price-bg"></div>
                        <div class="p-4">
                            <% if (goods.getDiscountPrice().intValue() == 0) { %>
                            原价：<span class="price">¥ <b style="font-size: 32px"><%=goods.getPrice()%></b></span>
                            <% } else { %>
                            <div>原价：
                                <del>¥ <%=goods.getPrice()%>
                                </del>
                            </div>
                            <div>折扣价：<span class="price">¥ <b style="font-size: 32px"><%=goods.getDiscountPrice()%></b></span>
                            </div>
                            <% } %>
                        </div>
                    </div>
                    <div class="mb-3">
                        <span>数量: </span>
                        <label class="w-25 ">
                            <input id="count" min="1" type="number" name="count" value="1" class="form-control form-control-sm">
                        </label>
                    </div>
                    <div>
                        <span>库存: <%=goods.getStock()%></span>
                    </div>

                    <div class="mt-5 d-flex flex-nowrap">
                        <% if (user == null) { %>
                        <a type="button" class="col-6 col-lg-4 me-lg-2 btn btn-lg btn-outline-danger"
                           href="/login">立即购买</a>
                        <a type="button" class="col-6 col-lg-7 btn btn-lg btn-danger" href="/login">加入购物车</a>
                        <% } else { %>
                        <a type="button" class="col-6 col-lg-4 me-lg-2 btn btn-lg btn-outline-danger"
                           href="/goods/buy?id=<%=goods.getId()%>">立即购买</a>
                        <label for="add" class="col-6 col-lg-7 btn btn-lg btn-danger">
                            添加至购物车
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-cart" viewBox="0 0 16 16">
                                <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                            </svg>
                        </label>
                        <input type="submit" style="display: none" class="col-6 me-lg-2 col-lg-4 btn btn-lg btn-outline-danger" id="add">

                        <% } %>

                    </div>
                </form>
            </div>
        </div>
        <div class="col-12 col-md-10  col-lg-8">
            <div class="p-5">
                评论区1
            </div>
        </div>


    </div>

</div>


<jsp:include page="/common/footer.jsp"/>
