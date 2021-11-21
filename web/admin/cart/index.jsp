<%@ page import="java.util.Map" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Address" %>
<%@ page import="cn.enncy.mall.pojo.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<%
    List<Cart> carts = (List<Cart>) request.getAttribute("carts");
%>

<div>
    <form method="POST">


    </form>
    <div class="card">
        <div class="card-body p-4">
            <table data-toggle="table">
                <thead>
                <tr>
                    <% for (Map.Entry<String, String> entry : BaseObjectUtils.getInfosMap(Cart.class).entrySet()) { %>
                    <th>
                        <%=entry.getValue()%>
                        <span style="font-size: 12px;color: #5e5e5e">(<%=entry.getKey()%>)</span>
                    </th>
                    <% } %>
                </tr>
                </thead>
                <tbody>

                <% for (Cart cart : carts) { %>
                <tr>
                    <% for (Map.Entry<String, Object> entry : BaseObjectUtils.getValuesMap(cart).entrySet()) { %>
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
    var active = "cart"
</script>

<jsp:include page="/admin/common/footer.jsp"/>
