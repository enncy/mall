<%@ page import="java.util.Optional" %>
<%@ page import="cn.enncy.mall.utils.formatter.DateFormatter" %>
<%@ page import="cn.enncy.mall.constant.OrderStatus" %>
<%@ page import="cn.enncy.mall.pojo.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .price {
        line-height: 25px;
        font-size: 18px;
        color: #fd3f31;
    }

    .font-primary {
        font-size: 16px;
        font-weight: bold;
    }

    .font-secondary {
        font-size: 14px;
        color: #5e5e5e;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%

    Order order = (Order) request.getAttribute("order");
    List<OrderDetails> orderDetailsList = (List<OrderDetails>) request.getAttribute("orderDetailsList");
%>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center flex-wrap">

    <form class="p-4 col-12 col-lg-10"  method="POST" >
        <input  style="display: none" name="id" value="<%=order.getId()%>">

        <% for( OrderDetails details :  orderDetailsList){ %>
        <div class="card">

            <div class="p-2">
                <div>

                </div>
            </div>

        </div>
        <% } %>

        <div class="card">
            <div class="card-body">

                <div class="col-12">
                    <div class="mt-4 d-flex align-items-end">
                        <div class="col-4  d-flex align-items-end">
                            <span class="font-primary me-2">总价:</span>
                            <span class="price">
                                        ¥<span style="font-size: 32px" class="totalPrice"><%=order.getTotalPrice()%></span>
                                    </span>
                        </div>

                        <div class="col-8  d-flex align-items-end justify-content-end">
                            <input type="submit" class="col-6 col-lg-4 me-2 btn btn-sm btn-danger float-end buyBtn"
                                   value="付款">
                            <a class="btn btn-sm btn-danger" href="/user/orders/cancel?id=<%=order.getId()%>">
                                取消订单
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>


<jsp:include page="/common/footer.jsp"/>
