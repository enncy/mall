<%@ page import="cn.enncy.mall.pojo.BaseObject" %>
<%@ page import="cn.enncy.mall.annotaion.Info" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="cn.enncy.mall.constant.ServiceMapping" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="cn.enncy.mall.utils.formatter.Formatter" %>
<%@ page import="cn.enncy.mall.constant.Tag" %>
<%@ page import="cn.enncy.mall.annotaion.Select" %>
<%@ page import="cn.enncy.mall.annotaion.Option" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%

    List<BaseObject> objects = (List<BaseObject>) request.getAttribute("objects");
    ServiceMapping serviceMapping = (ServiceMapping) request.getAttribute("service");
    Map<String, Info> infosMap = BaseObjectUtils.getInfosMap(serviceMapping.objectClass);
    List<Field> allFields = BaseObjectUtils.getAllFields(serviceMapping.objectClass);

%>

<div>
    <div class="card">
        <div class="card-body p-4">
            <div class="d-flex">
                <div class="col-8 col-lg-11">
                    <table data-toggle="table"  >
                        <thead style="     position: sticky;  top: 0px;">
                        <tr>
                            <% for (Field field : allFields) { %>
                            <th>
                                <%=infosMap.get(field.getName()).value()%>
                                <span style="font-size: 12px;color: #5e5e5e">(<%=field.getName()%>)</span>
                            </th>
                            <% } %>

                        </tr>
                        </thead>
                        <tbody>

                        <%

                            for (BaseObject object : objects) { %>
                        <tr>
                            <%
                                Map<String, Object> valuesMap = BaseObjectUtils.getValuesMap(object);

                                for (Field field : allFields) {
                                    Object value = valuesMap.get(field.getName());
                                    Info info = infosMap.get(field.getName());
                                    Formatter formatter = info.formatter().newInstance();
                                    Object format = formatter.format(value);

                            %>
                            <% if (info.tag().equals(Tag.SELECT)) {
                                String desc = Arrays.stream(field.getAnnotation(Select.class).options()).filter(option -> option.value().equals(value.toString())).map(Option::description).findAny().orElse("无");
                            %>
                            <td><%=desc%>
                            </td>
                            <% } else { %>
                            <td><%=format%>
                            </td>
                            <% } %>

                            <% } %>

                        </tr>
                        <% } %>

                        </tbody>
                    </table>
                </div>
                <%--固定最后一列--%>
                <div class="col-4 col-lg-1" style="min-width: 120px">
                    <table data-toggle="table"  >
                        <thead>
                        <tr>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (BaseObject object : objects) { %>
                        <tr>
                            <td>
                                <a class="btn btn-sm btn-outline-secondary"
                                   href="/admin/common/operate/<%=serviceMapping.name%>?targetId=<%=object.getId()%>">修改
                                </a>
                                <a class="btn btn-sm btn-outline-danger"
                                   href="/admin/common/delete/<%=serviceMapping.name%>?targetId=<%=object.getId()%>">删除
                                </a>
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>

                </div>
            </div>


            <jsp:include page="/admin/common/pagination.jsp"/>
        </div>

    </div>

</div>
<script>
    var active = '<%=serviceMapping.name%>'
</script>