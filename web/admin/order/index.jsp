<%@ page import="java.util.Map" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="cn.enncy.mall.pojo.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>

<div>
    <form method="POST">


    </form>

    <div class="card">
        <div class="card-body p-4">
            <table data-toggle="table">
                <thead>
                <tr>
                    <% for (Map.Entry<String, String> entry : BaseObjectUtils.getInfosMap(Order.class).entrySet()) { %>
                    <th>
                        <%=entry.getValue()%>
                        <span style="font-size: 12px;color: #5e5e5e">(<%=entry.getKey()%>)</span>
                    </th>
                    <% } %>
                </tr>
                </thead>
                <tbody>

                <% for (Order order : orders) { %>
                <tr>
                    <% for (Map.Entry<String, Object> entry : BaseObjectUtils.getValuesMap(order).entrySet()) { %>
                    <td><%=entry.getValue()%>
                    </td>
                    <% } %>
                </tr>
                <% } %>
                </
                >
                </tbody>
            </table>

            <jsp:include page="/admin/common/pagination.jsp"/>
        </div>

    </div>

</div>
<script>
    var active = "order"
</script>

<jsp:include page="/admin/common/footer.jsp"/>
