<%@ page import="java.util.Map" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<%
    List<User> users = (List<User>) request.getAttribute("users");

%>

<div>
    <form method="GET" class="d-flex">
        <div class="input-group col-lg-4 p-0 w-25 ">
            <input type="text" name="name" class="form-control" placeholder="账号或昵称搜索">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="submit">搜索</button>
            </div>
        </div>
        <div class="d-flex ms-3 ">
            <button class="btn btn-primary ">添加用户</button>
        </div>

    </form>


    <div class="card">
        <div class="card-body p-4">
            <table data-toggle="table">
                <thead>
                <tr>
                    <% for (Map.Entry<String, String> entry : BaseObjectUtils.getInfosMap(User.class).entrySet()) { %>
                    <th>
                        <%=entry.getValue()%>
                        <span style="font-size: 12px;color: #5e5e5e">(<%=entry.getKey()%>)</span>
                    </th>
                    <% } %>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                <% for (User user : users) { %>
                <tr>
                    <% for (Map.Entry<String, Object> entry : BaseObjectUtils.getValuesMap(user).entrySet()) { %>
                    <td><%=entry.getValue()%>
                    </td>
                    <% } %>
                    <td >
                        <a class="btn btn-sm btn-outline-secondary">修改</a>
                        <a  class="btn btn-sm btn-outline-danger">删除</a>
                    </td>
                </tr>
                <% } %>

                </tbody>
            </table>

            <jsp:include page="/admin/common/pagination.jsp"/>
        </div>

    </div>

</div>
<script>
    var active = "user"
</script>

<jsp:include page="/admin/common/footer.jsp"/>
