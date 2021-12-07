<%@ page import="java.util.Optional" %>
<%@ page import="cn.enncy.mall.utils.formatter.DateFormatter" %>
<%@ page import="cn.enncy.mall.constant.OrderStatus" %>
<%@ page import="cn.enncy.mall.pojo.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
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

    .goods-img {
        border-radius: 4px;
        width: 110px;
        height: 120px;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%
    User user = (User) session.getAttribute("user");
    Order order = (Order) request.getAttribute("order");
    List<OrderDetails> orderDetailsList = (List<OrderDetails>) request.getAttribute("orderDetailsList");
%>

<div class="container p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center flex-wrap">

    <form class="p-4 col-lg-10 col-md-11 col-12" method="POST">
        <input style="display: none" name="id" value="<%=order.getId()%>">
        <input style="display: none" name="uid" value="<%=order.getUid()%>">

        <div class="w-100 overflow-auto" >
            <table class="table w-100"  style="white-space: nowrap">
                <thead>
                <tr>
                    <th>订单编号</th>
                    <th>用户账号</th>
                    <th>状态</th>
                    <th>应付款</th>
                    <th>创建时间</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><%=order.getUid()%>
                    </td>
                    <td><%=user.getAccount()%>
                    </td>
                    <td><%=OrderStatus.getDescription(order.getStatus())%>
                    </td>
                    <td><%=order.getTotalPrice()%>
                    </td>
                    <td><%=DateFormatter.format(order.getCreateTime() )%>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>


        <% for (OrderDetails details : orderDetailsList) { %>
        <div class="card mt-2">

            <div class="p-2 d-flex">
                <div class="me-4">
                    <a>
                        <img class="goods-img" src="<%=details.getImg()%>"></img>
                    </a>
                </div>
                <div>
                    <div><span class="font-primary me-2">商品:</span> <span
                            class="font-secondary"><%=details.getDescription()%></span></div>
                    <div><span class="font-primary me-2">数量:</span> <span
                            class="font-secondary">x <%=details.getCount()%></span></div>
                    <div><span class="font-primary me-2">单价:</span> <span
                            class="font-secondary"><%=details.getPrice()%></span></div>
                    <div>
                        <span class="font-primary me-2">总价:</span>
                        <span class="price">
                            ¥
                            <span class="fs-4">
                                <%=details.getPrice().multiply(BigDecimal.valueOf(details.getCount()))%>
                            </span>
                        </span>
                    </div>
                </div>
            </div>

        </div>
        <% } %>

        <div class="card mt-2">
            <div class="card-body">
                <div style="display: inline-block" class="w-100">
                    <span class="fw-bold" style="display: inline-block">
                        收货地址：
                    </span>
                    <div class="input-group" >
                        <input class="form-control form-control-sm"
                               value="<%=order.getAddressDetail()%>" id="input<%=order.getId()%>"
                               type="text" disabled>
                        <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal"
                                data-bs-target="#updateAddressModal<%=order.getId()%>"
                                onclick="search('<%=order.getId()%>',['detail','phone'])">修改地址
                        </button>

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
                    </div>

                </div>
            </div>
        </div>

        <div class="card mt-2">
            <div class="card-body">

                <div class="col-12">
                    <div class="mt-4 d-flex align-items-end  flex-wrap">
                        <div class="col-lg-4  col-12 d-flex align-items-end">
                            <span class="font-primary me-2">应付款:</span>
                            <span class="price">
                                ¥<span style="font-size: 32px"
                                       class="totalPrice"><%=order.getTotalPrice()%></span>
                            </span>
                        </div>

                        <div class="col-lg-8  col-12  mt-4 mt-lg-0  d-flex align-items-end justify-content-end">
                            <a class="btn btn-lg  me-4 btn-outline-dark"
                               href="/user/orders/cancel?id=<%=order.getId()%>">
                                取消订单
                            </a>
                            <input type="submit" class="col-6 col-lg-4 btn btn-lg btn-danger float-end buyBtn"
                                   value="付款">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>


<jsp:include page="/common/footer.jsp"/>


<script>
    function search(id, showList) {
        $.ajax({
            url: '/search/user/address?key=' + $("#searchInput" + id).val(),
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
                console.log(r)
            },
            error() {
                alert("请求失败!")
            }
        })
        $("#input" + id).val($("[name='radio" + id + "']:checked").val())
    }
</script>