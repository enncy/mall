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
        text-align: left;
    }


    .goods-img {
        border-radius: 4px;
        width: 180px;
        height: 200px;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%

    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    AddressService addressService = ServiceFactory.resolve(AddressService.class);
    OrderService orderService = ServiceFactory.resolve(OrderService.class);
    String currentStatus = Optional.ofNullable(request.getParameter("status")).orElse(OrderStatus.RECEIVING.value);
    List<Order> orders = orderService.findAll();
    List<Order> currentOrders = orders.stream().filter(order -> order.getStatus().equals(currentStatus)).collect(Collectors.toList());

    long paymentCount = orders.stream().map(Order::getStatus).filter(s->s.equals(OrderStatus.PAYMENT.value)).count();
    long receivingCount = orders.stream().map(Order::getStatus).filter(s->s.equals(OrderStatus.RECEIVING.value)).count();
    long finishCount = orders.stream().map(Order::getStatus).filter(s->s.equals(OrderStatus.FINISH.value)).count();
    long cancelCount = orders.stream().map(Order::getStatus).filter(s->s.equals(OrderStatus.CANCEL.value)).count();

%>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">
        <%--<div class="col-12 mb-4">--%>
        <%--    <jsp:include page="/common/alert.jsp"/>--%>
        <%--    <h3>订单列表</h3>--%>
        <%--    <div class="d-flex ">--%>
        <%--        <a class="me-5" href="/user/orders?status=payment">待付款<span class="font-secondary">(<%=paymentCount%>)</span></a>--%>
        <%--        <a  class="me-5" href="/user/orders?status=receiving">收货中<span class="font-secondary">(<%=receivingCount%>)</span></a>--%>
        <%--        <a  class="me-5" href="/user/orders?status=finished">已收货<span class="font-secondary">(<%=finishCount%>)</span></a>--%>
        <%--        <a  class="me-5" href="/user/orders?status=cancel">取消<span class="font-secondary">(<%=cancelCount%>)</span></a>--%>
        <%--    </div>--%>
        <%--</div>--%>
        <%--<% if(orders.size()==0){ %>--%>
        <%--<div class="card col-12" style="    height: fit-content;">--%>
        <%--    <div class="p-2 text-center">--%>
        <%--        您的订单列表空空如也~ <a href="/goods">去购物</a>--%>
        <%--    </div>--%>
        <%--</div>--%>
        <%--<% } %>--%>

        <%--<div class="col-12">--%>
        <%--    <%--%>

        <%--        for( Order order : currentOrders){--%>
        <%--            Goods goods = goodsService.findOneById(order.getGoodsId());--%>
        <%--            Address address = addressService.findOneById(order.getAddressId());--%>
        <%--            String status = order.getStatus();--%>

        <%--    %>--%>
        <%--    <div style="border: 1px solid rgba(0,0,0,.125);" class="  col-12  mb-3">--%>
        <%--        <div class="p-2 d-flex flex-wrap col-12">--%>
        <%--            <a class=" me-lg-4  col-lg-3 col-12" style="text-align: center"  href="/goods/detail?id=<%=goods.getId()%>">--%>
        <%--                <img class="goods-img " src="<%=goods.getImg()%>">--%>
        <%--            </a>--%>
        <%--            <div class="col-lg-8 col-12">--%>
        <%--                <div><div class="d-inline-block font-primary col-2">订单编号:</div><span class="font-secondary"><%=order.getUid()%></span></div>--%>
        <%--                <div><div class="d-inline-block font-primary col-2">创建时间:</div><span class="font-secondary"><%=DateFormatter.format(order.getCreateTime(),"yyyy-MM-dd HH:mm:ss")%></span></div>--%>

        <%--                <div><div class="d-inline-block font-primary col-2">商品描述:</div><span class="font-secondary"><%=goods.getDescription()%></span></div>--%>
        <%--                <div><div class="d-inline-block font-primary col-2">购买数量:</div><span class="font-secondary"><%=order.getCount()%></span></div>--%>
        <%--                <div><div class="d-inline-block font-primary col-2">单价:</div><span class="font-secondary">¥<%=goods.getRealPrice()%></span></div>--%>
        <%--                <div><div class="d-inline-block font-primary col-2">应付款:</div><span class="price">¥<%=order.getPrice()%></span></div>--%>
        <%--                <div>--%>
        <%--                    <div class="d-inline-block font-primary col-2">地址:</div>--%>
        <%--                    <% if(status.equals(OrderStatus.PAYMENT.value) || status.equals(OrderStatus.RECEIVING.value)){ %>--%>
        <%--                    <div  style="display: inline-block" >--%>
        <%--                        <div class="input-group " style="font-size: .1rem;">--%>
        <%--                            <input class="form-control form-control-sm" value="<%=address.getAlias()+"-"+address.getDetail()%>"  id="input<%=order.getId()%>"  type="text"  disabled>--%>
        <%--                            <button class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#updateAddressModal<%=order.getId()%>"  onclick="search('<%=order.getId()%>',['alias','detail'])">修改地址</button>--%>

        <%--                            <div class="modal fade" id="updateAddressModal<%=order.getId()%>" tabindex="-1"--%>
        <%--                                 aria-labelledby="exampleModalLabel"--%>
        <%--                                 role="dialog"--%>
        <%--                                 aria-hidden="true">--%>

        <%--                                <div class="modal-dialog  modal-dialog-centered modal-dialog-scrollable">--%>
        <%--                                    <div class="modal-content">--%>
        <%--                                        <div class="modal-header">--%>
        <%--                                            <h5 class="modal-title" id="exampleModalLabel">选择地址--%>
        <%--                                            </h5>--%>
        <%--                                            <button type="button" class="btn-close" data-bs-dismiss="modal"--%>
        <%--                                                    aria-label="Close"></button>--%>
        <%--                                        </div>--%>
        <%--                                        <div class="modal-body">--%>
        <%--                                            <div class="input-group mb-3">--%>
        <%--                                                <input type="text" class="form-control" id="searchInput<%=order.getId()%>">--%>
        <%--                                                <button class="btn btn-outline-secondary" type="button" id="searchBtn"--%>
        <%--                                                        onclick="search('<%=order.getId()%>',['alias','detail'])">搜索--%>
        <%--                                                </button>--%>
        <%--                                            </div>--%>
        <%--                                            <div>--%>
        <%--                                                <div class="list-group" id="SearchList<%=order.getId()%>">--%>
        <%--                                                    <label class="list-group-item">--%>
        <%--                                                        暂无数据--%>
        <%--                                                    </label>--%>

        <%--                                                </div>--%>
        <%--                                            </div>--%>
        <%--                                        </div>--%>
        <%--                                        <div class="modal-footer">--%>
        <%--                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消--%>
        <%--                                            </button>--%>
        <%--                                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal"--%>
        <%--                                                    onclick="save('<%=order.getId()%>')">选择--%>
        <%--                                            </button>--%>
        <%--                                        </div>--%>
        <%--                                    </div>--%>
        <%--                                </div>--%>
        <%--                            </div>--%>
        <%--                        </div>--%>

        <%--                    </div>--%>
        <%--                    <% }else{ %>--%>
        <%--                    <span class="font-secondary"><%=address.getAlias() + "-" + address.getDetail()%></span>--%>
        <%--                    <% } %>--%>
        <%--                </div>--%>
        <%--                <div class="d-flex align-items-end col-12 mt-2">--%>
        <%--                    <div class="d-inline-block font-primary col-2">状态:</div>--%>
        <%--                    <% if(status.equals(OrderStatus.PAYMENT.value)   ){ %>--%>
        <%--                        <div class="mt-2">--%>
        <%--                            <a href="/user/orders/pay?id=<%=order.getId()%>" class="btn btn-sm btn-danger" style="float: right"> 去付款  </a>--%>
        <%--                        </div>--%>
        <%--                    <% }else if(status.equals(OrderStatus.RECEIVING.value)){ %>--%>
        <%--                        <span class="font-secondary me-4"><%=OrderStatus.getDescription(order.getStatus())%></span>--%>
        <%--                        <div  class="  d-flex justify-content-end" >--%>
        <%--                            <a class="btn btn-outline-secondary btn-sm"  href="/user/orders/confirm?id=<%=order.getId()%>">确认收货</a>--%>
        <%--                        </div>--%>
        <%--                    <% }else { %>--%>
        <%--                        <span class="font-secondary me-4"><%=OrderStatus.getDescription(order.getStatus())%></span>--%>
        <%--                    <% } %>--%>

        <%--                </div>--%>


        <%--            </div>--%>
        <%--        </div>--%>
        <%--    </div>--%>
        <%--    <% } %>--%>

        <%--</div>--%>

    </div>
</div>


<jsp:include page="/common/footer.jsp"/>


<script>
    function search(id,showList){
        $.ajax({
            url: '/search/address?key=' + $("#searchInput"+id).val(),
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
                    if(html!==""){
                        $("#SearchList"+id).html(html)
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
            url: '/user/orders/update/address?id='+id+"&addressId="+address_id,
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