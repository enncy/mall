<%@ page import="cn.enncy.mall.service.CartService" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Cart" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
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
        width: 100px;
        height: 120px;
    }

    .card.selected {
        cursor: pointer;
    }

    .card.selected {
        border: 1px solid #f32626;
        box-shadow: 0px 0px 4px #e7b4b4;
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
            <h3>购物车列表 </h3>
        </div>
        <% if (carts.size() == 0) { %>
        <div class="card col-12" style="    height: fit-content;">
            <div class="p-2 text-center">
                您的购物车空空如也~ <a href="/goods">去购物</a>
            </div>
        </div>
        <% } else {%>
        <form class=" col-12" method="POST" action="/goods/buy">
            <%
                for (Cart cart : carts) {
                    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
                    Goods goods = goodsService.findOneById(cart.getGoodsId());
            %>
            <div class="d-flex col-12 flex-nowrap pt-3">
                <div class="card col-10" id="cartDiv<%=cart.getId()%>">
                    <div class="p-2 d-flex col-12">
                        <a class="me-4" style="cursor: pointer" href="/goods/detail?id=<%=goods.getId()%>">
                            <img src="<%=goods.getImg()%>" class="goods-img">
                        </a>
                        <div class="w-100" style="text-align: left">
                            <div><span class="font-primary me-2">商品描述:</span> <span
                                    class="font-secondary"><%=goods.getDescription()%></span></div>
                            <div><span class="font-primary me-2" >单价:</span> <span
                                    class="font-secondary" id="price<%=cart.getId()%>"><%=goods.getRealPrice()%></span></div>

                            <div class="mt-2 d-flex align-items-end justify-content-end">
                                <div class="col-4 d-flex align-items-end">
                                    <span class="font-primary me-2">数量:</span>
                                    <label class="col-6">
                                        <input value="<%=cart.getCount()%>" id="count<%=cart.getId()%>" name="count"
                                               onchange="changeCount(this,'<%=cart.getId()%>','<%=goods.getRealPrice() %>')"
                                               min="0"
                                               max="100"
                                               type="number" class="form-control form-control-sm">
                                    </label>
                                </div>
                                <div class="col-4  d-flex align-items-end">
                                    <span class="font-primary me-2">总价:</span>
                                    <span class="price">
                                            ¥<span style="font-size: 24px" id="cartTotalPrice<%=cart.getId()%>">
                                                <%=goods.getRealPrice().multiply(BigDecimal.valueOf(cart.getCount()))%>
                                            </span>
                                        </span>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
                <input class="d-none" value="<%=cart.getId()%>" id="cart<%=cart.getId()%>" name="cartId"
                       type="checkbox">
                <label class="col-1  " style="writing-mode: vertical-rl" for="cart<%=cart.getId()%>">
                    <a  onclick="selectedDiv('<%=cart.getId()%>',this)" class="btn btn-sm btn-outline-success h-100">
                        选择
                    </a>
                </label>

                <div class="col-1  " style="writing-mode: vertical-rl">
                    <a href="/user/cart/delete?id=<%=cart.getId()%>" class="btn btn-sm btn-outline-danger h-100">
                        删除
                    </a>
                </div>
            </div>

            <% } %>

            <div class="col-12 mt-5  d-flex align-items-end justify-content-end">
                <div class="me-4" >已选中 <span id="totalCount"></span> 个商品</div>
                <span class="me-2">应付款:</span>
                <span class="price me-5">¥ <span style="font-size: 36px" id="totalPrice">0</span></span>
                <input type="submit" class="col-6 col-lg-4  btn btn-lg btn-danger float-end " id="buyBtn" value="结算">
            </div>
        </form>
        <% } %>
    </div>
</div>


<jsp:include page="/common/footer.jsp"/>


<script>
    function changeCount(el, id, price) {
        var total = (parseFloat(el.value) * parseFloat(price))
        console.log(total)
        $("#cartTotalPrice" + id + "").text(total.toFixed(2))
        console.log(total)
        if (total <= 0) {
            $("#buyBtn").addClass("disabled")
        } else {
            $("#buyBtn").removeClass("disabled")
        }

    }

    let price = 0
    let count = 0


    function  selectedDiv(id,el){
        let cartPrice =    parseInt($("#count"+id).val()) * parseInt($("#price"+id).text())
        console.log(cartPrice)
        if($(el).hasClass("btn-outline-success")){
            $(el).removeClass("btn-outline-success")
            $(el).addClass("btn-outline-dark")
            $(el).text("取消")
            price += cartPrice;
            count+=1
        }else{
            $(el).removeClass("btn-outline-dark")
            $(el).addClass("btn-outline-success")
            $(el).text("选择")
            price -= cartPrice;
            count-=1
        }

        $("#totalPrice").text(price)
        $("#totalCount").text(count)

        if ($("#cartDiv"+id).hasClass("selected")) {
            $("#cartDiv"+id).removeClass("selected")
        } else {
            $("#cartDiv"+id).addClass("selected")
        }
    }

</script>
