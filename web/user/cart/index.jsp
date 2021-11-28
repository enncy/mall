<%@ page import="cn.enncy.mall.service.CartService" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Cart" %>
<%@ page import="cn.enncy.mall.utils.ServiceFactory" %>
<%@ page import="cn.enncy.mall.service.GoodsService" %>
<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .price {
        line-height: 25px;
        font-size: 18px;
        color: #fd3f31;
    }

    .font-primary {
        font-size: 14px;
        font-weight: bold;
    }

    .font-secondary {
        font-size: 13px;
        color: #5e5e5e;
    }
    .goods-img {
        border-radius: 4px;
        width: 140px;
        height: 160px;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%
    List<Cart> carts = (List<Cart>) request.getAttribute("carts");
%>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">
        <div class="col-12 mb-4">
            <h3>购物车列表</h3>
        </div>
        <% if(carts.size()==0){ %>
        <div class="card col-12" style="    height: fit-content;">
            <div class="p-2 text-center">
                您的购物车空空如也~ <a href="/goods">去购物</a>
            </div>
        </div>
        <% } %>

        <%
            for (Cart cart : carts) {
                GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
                Goods goods = goodsService.findOneById(cart.getGoodsId());
        %>
        <form class="card col-12" method="GET" action="/goods/buy">
            <input value="<%=cart.getId()%>" name="cartId" type="hidden">
            <div class="p-4 d-flex col-12">
                <a class="me-4" style="cursor: pointer"  href="/goods/detail?id=<%=goods.getId()%>" >
                    <img src="<%=goods.getImg()%>"  class="goods-img">
                </a>
                <div class="w-100" style="text-align: left">
                    <div><span class="font-primary me-2">商品名:</span> <span
                            class="font-secondary"><%=goods.getName()%></span></div>
                    <div><span class="font-primary me-2">描述:</span> <span
                            class="font-secondary"><%=goods.getDescription()%></span></div>
                    <div><span class="font-primary me-2">单价:</span> <span
                            class="font-secondary"><%=goods.getRealPrice()%></span></div>
                    <div>
                        <span class="font-primary me-2">数量:</span>
                        <label class="w-25 ">
                            <input value="<%=cart.getCount()%>" id="count"
                                   onchange="changeCount(this,'<%=cart.getId()%>','<%=goods.getRealPrice() %>')" min="0" max="100"
                                   type="number" name="count" class="form-control form-control-sm">
                        </label>

                    </div>
                    <div class="mt-4 d-flex align-items-end">
                        <div class="col-4  d-flex align-items-end">
                            <span class="font-primary me-2">总价:</span>
                            <span class="price">¥<span style="font-size: 32px" class="totalPrice"
                                                       data-index="<%=cart.getId()%>"><%=goods.getRealPrice().multiply(BigDecimal.valueOf(cart.getCount()))%></span></span>
                        </div>

                        <div class="col-8  d-flex align-items-end justify-content-end">
                            <input type="submit" class="col-6 col-lg-4 me-2 btn btn-sm btn-danger float-end buyBtn"
                                   data-index="<%=cart.getId()%>" value="结算">
                            <a href="/user/cart/delete?id=<%=cart.getId()%>" class="btn btn-sm btn-danger">
                                删除
                            </a>
                        </div>
                    </div>

                </div>
            </div>
        </form>
        <% } %>


    </div>
</div>


<jsp:include page="/common/footer.jsp"/>


<script>
    function changeCount(el, id, price) {
        var total = (parseFloat(el.value) * parseFloat(price))

        $(".totalPrice[data-index='" + id + "']").text(total.toFixed(2))
        console.log(total)
        if (total <= 0) {
            $(".buyBtn[data-index='" + id + "']").addClass("disabled")
        } else {
            $(".buyBtn[data-index='" + id + "']").removeClass("disabled")
        }

    }
</script>
