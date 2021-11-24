<%@ page import="java.util.Map" %>

<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="cn.enncy.mall.pojo.BaseObject" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mall.annotaion.Info" %>
<%@ page import="cn.enncy.mall.constant.Tag" %>
<%@ page import="cn.enncy.mall.utils.formatter.Formatter" %>
<%@ page import="cn.enncy.mall.annotaion.Select" %>
<%@ page import="cn.enncy.mall.annotaion.Option" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<%

    List<User> users = (List<User>) request.getAttribute("users");
    Map<String, Info> infosMap = BaseObjectUtils.getInfosMap(User.class);
    List<Field> allFields = BaseObjectUtils.getAllFields(User.class);

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
            <a class="btn btn-primary " href="/admin/common/operate/user">添加用户</a>
        </div>

    </form>


    <div class="card">
        <div class="card-body p-4">
            <table data-toggle="table">
                <thead>
                <tr>
                    <% for (Field field : allFields) { %>
                    <th>
                        <%=infosMap.get(field.getName()).value()%>
                        <span style="font-size: 12px;color: #5e5e5e">(<%=field.getName()%>)</span>
                    </th>
                    <% } %>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                <% for (User user : users) { %>
                <tr>
                    <%
                        Map<String, Object> valuesMap = BaseObjectUtils.getValuesMap(user);

                        for (Field field : allFields) {
                            Object value = valuesMap.get(field.getName());
                            Info info = infosMap.get(field.getName());
                            Formatter formatter = info.formatter().newInstance();
                            Object format = formatter.format(value);

                    %>
                    <% if(info.tag().equals(Tag.SELECT)){
                        String desc = Arrays.stream(field.getAnnotation(Select.class).options()).filter(option -> option.value().equals(value.toString())).map(Option::description).findAny().orElse("无");
                    %>
                    <td><%=desc%>
                    </td>
                    <% }else{ %>
                    <td><%=format%>
                    </td>
                    <% } %>

                    <% } %>
                    <td>
                        <a class="btn btn-sm btn-outline-secondary" href="/admin/common/operate/user?targetId=<%=user.getId()%>">修改</a>
                        <a class="btn btn-sm btn-outline-danger" href="/admin/common/delete/user?targetId=<%=user.getId()%>">删除</a>
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
