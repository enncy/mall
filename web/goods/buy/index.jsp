<%@ page import="java.util.Optional" %>
<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="cn.enncy.mall.pojo.Order" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.pojo.Address" %>
<%@ page import="cn.enncy.mall.utils.formatter.DateFormatter" %>
<%@ page import="cn.enncy.mall.constant.OrderStatus" %>
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

    Goods goods = (Goods) request.getAttribute("goods");
    Order order = (Order) request.getAttribute("order");
    User user = (User) session.getAttribute("user");
    Address address = (Address) request.getAttribute("address");
    int count = (int) request.getAttribute("count");
%>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center flex-wrap">

    <form class="p-4 col-12 col-lg-10"  method="POST"  action="/goods/buy">
        <input  style="display: none" name="id" value="<%=order.getId()%>">
        <div class="  mb-4">
            <table class="table table-bordered text-center">
                <thead>
                <tr>
                    <th>图片</th>
                    <th>名字</th>
                    <th>描述</th>
                    <th>单价</th>
                    <th>数量</th>
                </tr>
                </thead>
                <tbody>
                <tr class=" align-items-center ">
                    <td><img style="width: 100%;max-width:100px" src="${requestScope.goods.img}"></td>
                    <td><%=goods.getName()%></td>
                    <td><%=goods.getDescription()%></td>
                    <td><%=goods.getRealPrice()%></td>
                    <td><%=count%></td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="  mb-4">
            <table class="table table-bordered text-center">
                <thead>
                <tr>
                    <th>订单编号</th>
                    <th>账号</th>
                    <th>地址</th>
                    <th>状态</th>
                    <th>创建时间</th>
                </tr>
                </thead>
                <tbody>
                <tr class=" align-items-center ">
                    <td><%=order.getUid()%></td>
                    <td><%=user.getAccount()%></td>
                    <td><%=address.getDetail()%></td>
                    <td><%=OrderStatus.getDescription(order.getStatus())%></td>
                    <td><%=DateFormatter.format(order.getCreateTime(),"yyyy-MM-dd HH:mm:ss")%></td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="card">
            <div class="card-body">

                <div class="col-12">
                    <div class="mt-4 d-flex align-items-end">
                        <div class="col-4  d-flex align-items-end">
                            <span class="font-primary me-2">总价:</span>
                            <span class="price">
                                        ¥<span style="font-size: 32px" class="totalPrice"><%=order.getPrice()%></span>
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
