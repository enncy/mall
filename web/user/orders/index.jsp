<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Order" %>
<%@ page import="cn.enncy.mall.service.GoodsService" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page import="cn.enncy.mall.service.AddressService" %>
<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="cn.enncy.mall.pojo.Address" %>
<%@ page import="cn.enncy.mall.constant.OrderStatus" %>
<%@ page import="cn.enncy.mall.utils.formatter.DateFormatter" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="cn.enncy.mall.service.OrderService" %>
<%@ page import="java.util.stream.Stream" %>
<%@ page import="cn.enncy.mall.service.OrderDetailsService" %>
<%@ page import="cn.enncy.mall.pojo.OrderDetails" %>
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
        width: 70px;
        height: 70px;
    }

    .dark {
        background-color: rgba(0, 0, 0, .03);
    }

    .card-shadow:hover {
        box-shadow: 0px 0px 6px #d5d5d5;
    }

    .btn{
        min-width: 64px;

    }

    .goods-a:hover{
        box-shadow: 0px 0px 2px red;
    }

    #orderNav a{
        text-decoration: none;
        margin: 10px 0;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%

    OrderDetailsService orderDetailsService = ServiceFactory.resolve(OrderDetailsService.class);
    String currentStatus = Optional.ofNullable(request.getParameter("status")).orElse(OrderStatus.RECEIVING.value);
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    List<Order> currentOrders = orders.stream().filter(order -> order.getStatus().equals(currentStatus)).collect(Collectors.toList());

    long paymentCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.PAYMENT.value)).count();
    long receivingCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.RECEIVING.value)).count();
    long finishCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.FINISH.value)).count();
    long returnCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.RETURN.value)).count();
    long cancelCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.CANCEL.value)).count();

%>

<div class="container  p-lg-5 mt-lg-5 mb-lg-5  p-md-2 mt-md-2 mb-md-2 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap  card p-4  col-lg-10 col-md-11 col-12">
        <div class="col-12 mb-4">
            <jsp:include page="/common/alert.jsp"/>

            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <a class="navbar-brand"  >订单分类</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#orderNav" aria-controls="orderNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="orderNav">
                        <div class="navbar-nav" >
                            <a class="me-5" href="/user/orders?status=payment">待付款<span
                                    class="font-secondary">(<%=paymentCount%>)</span></a>
                            <a class="me-5" href="/user/orders?status=receiving">收货中<span
                                    class="font-secondary">(<%=receivingCount%>)</span></a>
                            <a class="me-5" href="/user/orders?status=finished">已收货<span
                                    class="font-secondary">(<%=finishCount%>)</span></a>
                            <a class="me-5" href="/user/orders?status=return">已退货<span
                                    class="font-secondary">(<%=returnCount%>)</span></a>
                            <a class="me-5" href="/user/orders?status=cancel">取消<span
                                    class="font-secondary">(<%=cancelCount%>)</span></a>
                        </div>
                    </div>
                </div>
            </nav>

        </div>
        <% if (currentOrders.size() == 0) { %>
        <div class="card col-12" style="    height: fit-content;">
            <div class="p-2 text-center">
                您的订单列表空空如也~ <a href="/goods">去购物</a>
            </div>
        </div>
        <% } %>

        <div class="col-12">
            <%

                for (Order order : currentOrders) {
                    List<OrderDetails> orderDetailsList = orderDetailsService.findByOrderUid(order.getUid());
                    String status = order.getStatus();

            %>
            <div class="card card-shadow <%=!order.equals(currentOrders.get(0))?"mt-5":""%>">

                <%
                    for (OrderDetails orderDetails : orderDetailsList) {
                %>

                <a class="col-12  goods-a"  href="/goods/detail?id=<%=orderDetails.getGoodsId()%>" style="color: black;text-decoration: none">
                    <div class="p-1 ps-2 d-flex flex-nowrap col-12">
                        <div class=" me-2" style="text-align: center">
                            <img class="goods-img " src="<%=orderDetails.getImg()%>">
                        </div>

                        <div class="w-100  d-flex">
                            <div class="col-6">
                                <div class="d-inline-block font-primary "><%=orderDetails.getDescription()%>
                                </div>
                            </div>
                            <div class="col-6 pe-2 d-flex flex-wrap text-end justify-content-end  align-items-end">
                                <div class="col-12 font-secondary">x <%=orderDetails.getCount()%>
                                </div>
                                <div class="col-12 font-secondary">¥ <%=orderDetails.getPrice()%>
                                </div>
                                <div class="col-12 price">¥ <%=orderDetails.getTotalPrice()%>
                                </div>
                            </div>
                        </div>

                    </div>

                </a>
                <% } %>

                <ul class="list-group list-group-flush">
                    <li class="list-group-item " style="background-color: #f8f8f8">
                        <div class="  font-secondary d-flex flex-wrap">
                            <div class="col-lg-4 col-12">
                                <div>订单编号 : <%=order.getUid()%>
                                </div>
                                <div>创建时间 : <%=DateFormatter.format(order.getCreateTime() )%>
                                </div>
                                <div>最近更新 : <%=DateFormatter.format(order.getUpdateTime() )%>
                                </div>
                            </div>
                            <div class="col-lg-4 col-12">
                                <div>状态 : <%=OrderStatus.getDescription(order.getStatus())%>
                                </div>
                                <%--如果不为未付款和收货中，则显示地址--%>
                                <% if (!status.equals(OrderStatus.PAYMENT.value) && !status.equals(OrderStatus.RECEIVING.value)) { %>
                                <div>地址 : <%=order.getAddressDetail()%>
                                </div>
                                <% } %>
                            </div>
                            <div class="col-lg-4 col-12">
                                <div>总数量 : <%=orderDetailsList.size()%>
                                </div>
                                <div>应付款 : <span class="price">¥<%=order.getTotalPrice()%></span></div>
                            </div>

                        </div>
                    </li>

                    <%--如果订单不是取消和退货状态--%>
                    <% if (!status.equals(OrderStatus.CANCEL.value)  && !status.equals(OrderStatus.RETURN.value)) { %>
                    <li class="list-group-item d-flex" style="background-color: #f8f8f8;white-space: nowrap">
                        <% if (status.equals(OrderStatus.RECEIVING.value) || status.equals(OrderStatus.PAYMENT.value)) { %>
                        <div class="input-group">
                            <input class="form-control form-control-sm"
                                   value="<%=order.getAddressDetail()%>" id="input<%=order.getId()%>"
                                   type="text" disabled>
                            <button type="button" class="btn btn-outline-primary btn-sm" data-bs-toggle="modal"
                                    data-bs-target="#updateAddressModal<%=order.getId()%>"
                                    onclick="search('<%=order.getId()%>',['detail','phone'])">修改地址
                            </button>

                        </div>
                        <div class="modal fade" id="updateAddressModal<%=order.getId()%>" tabindex="-1"
                             aria-labelledby="exampleModalLabel"
                             role="dialog"
                             aria-hidden="true">

                            <div class="modal-dialog  modal-dialog-centered modal-dialog-scrollable">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">选择地址
                                        </h5>
                                        <a type="button" class="btn-close" data-bs-dismiss="modal"
                                           aria-label="Close"></a>
                                    </div>
                                    <div class="modal-body">
                                        <div class="input-group mb-3">
                                            <input type="text" class="form-control"
                                                   id="searchInput<%=order.getId()%>">
                                            <button class="btn btn-outline-secondary" type="button" id="searchBtn"
                                                    onclick="search('<%=order.getId()%>',['alias','detail'])">搜索
                                            </button>
                                        </div>
                                        <div>
                                            <div class="list-group" id="SearchList<%=order.getId()%>">
                                                <label class="list-group-item">
                                                    暂无数据
                                                </label>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消
                                        </button>
                                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal"
                                                onclick="save('<%=order.getId()%>')">选择
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% } %>

                        <% if (status.equals(OrderStatus.PAYMENT.value)) { %>
                        <div class="ms-2  d-flex justify-content-end w-100">
                            <a class="btn btn-outline-dark btn-sm me-2"
                               href="/user/orders/cancel?id=<%=order.getId()%>">取消订单</a>
                            <a class="btn btn-sm btn-danger" href="/user/orders/pay?uid=<%=order.getUid()%>"> 去付款 </a>
                        </div>
                        <% } else if (status.equals(OrderStatus.RECEIVING.value)) { %>

                        <div class="ms-2  d-flex justify-content-end w-100">
                            <a class="btn btn-outline-secondary btn-sm"
                               href="/user/orders/confirm?id=<%=order.getId()%>">确认收货</a>
                        </div>
                        <% } else if (status.equals(OrderStatus.FINISH.value)) {%>
                        <div class="ms-2 d-flex justify-content-end w-100">
                            <a class="btn btn-danger btn-sm " href="/user/orders/return?id=<%=order.getId()%>">退货</a>
                        </div>
                        <% } %>


                    </li>
                    <% } %>


                </ul>


            </div>
            <% } %>

        </div>

    </div>
</div>


<jsp:include page="/common/footer.jsp"/>


<script>
    function search(id, showList) {
        $.ajax({
            url: '/search/address?key=' + $("#searchInput" + id).val(),
            dataType: 'json',
            method: 'get',

            success(r) {

                if (r.status) {
                    let html = ""
                    for (let i of r.data) {
                        html += `
                        <label class="list-group-item">
                            <input name="radio\${id}" class="form-check-input me-1  search-radio" type="radio"  data-id="\${i.id}" value="\${showList.map(k=>i[k]).join("-")}">
                            \${showList.map(k=>i[k]).join("-")}
                        </label>
                        `
                    }
                    if (html !== "") {
                        $("#SearchList" + id).html(html)
                    }

                }

            },
            error() {
                alert("请求失败!")
            }
        })
    }

    function save(id) {
        let address_id = $("[name='radio" + id + "']:checked").attr("data-id")
        $.ajax({
            url: '/user/orders/update/address?id=' + id + "&addressId=" + address_id,
            dataType: 'json',
            method: 'get',
            success(r) {
                if (r.status) {

                }
            },
            error() {
                alert("请求失败!")
            }
        })
        $("#input" + id).val($("[name='radio" + id + "']:checked").val())
    }

</script>