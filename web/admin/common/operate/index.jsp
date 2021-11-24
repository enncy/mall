<%@ page import="java.util.Map" %>

<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="cn.enncy.mall.pojo.BaseObject" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mall.annotaion.Info" %>
<%@ page import="cn.enncy.mall.service.BaseService" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.constant.Tag" %>
<%@ page import="cn.enncy.mall.annotaion.Select" %>
<%@ page import="cn.enncy.mall.annotaion.Option" %>
<%@ page import="cn.enncy.mall.utils.formatter.Formatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<%
    BaseObject object = (BaseObject) request.getAttribute("object");

    Class<? extends BaseObject> clazz = (Class<? extends BaseObject>) request.getAttribute("class");
    Map<String, Info> infosMap = BaseObjectUtils.getInfosMap(clazz);
    List<Field> allFields = BaseObjectUtils.getAllFields(clazz);

%>

<div>
    <div class="card">
        <div class="card-body p-5">
            <div class="card-title"><h2><%=object.getId()==0?"添加":"修改"%>数据</h2></div>
            <form  method="POST" >
                <input type="hidden" name="id" value="<%=object.getId()%>">
                <input type="hidden" name="createTime" value="<%=object.getCreateTime()%>">
                <input type="hidden" name="updateTime" value="<%=object.getUpdateTime()%>">
                <%
                    assert object != null;
                    Map<String, Object> valuesMap = BaseObjectUtils.getValuesMap(object);

                    for (Field field : allFields) {
                        String name = field.getName();
                        Object value = valuesMap.get(field.getName());
                        Info info = infosMap.get(field.getName());
                        Formatter formatter = info.formatter().newInstance();
                        Object format = formatter.format(value);
                        Tag tag = info.tag();
                %>
                <div class="mb-3">
                    <label for="input<%=name%>" class="form-label"><%=info.value()%>
                    </label>
                    <% if (tag.equals(Tag.INPUT)) { %>
                    <input <%=info.disabled()?"disabled":""%> name="<%=name%>" type="<%=info.type().value%>" class="form-control" id="input<%=name%>" data-origin="<%=value%>"
                           value="<%=format%>">
                    <% } else if (tag.equals(Tag.SELECT)) { %>
                    <select <%=info.disabled()?"disabled":""%>  class="form-select" id="input<%=name%>" name="<%=name%>">
                        <%
                            Select select = field.getAnnotation(Select.class);
                            for (Option option : select.options()) {
                        %>
                        <option value="<%=option.value()%>"><%=option.description()%>
                        </option>
                        <% } %>

                    </select>
                    <% } else if (tag.equals(Tag.TEXTAREA)) {%>
                    <textarea <%=info.disabled()?"disabled":""%>  id="input<%=name%>" name="<%=name%>"><%=value%></textarea>
                    <% } %>

                </div>
                <% } %>
                <div class=" w-100">
                    <input class="btn btn-primary col-2 float-end" value="<%=object.getId()==0?"添加":"修改"%>" type="submit">
                    <%--<input class="btn btn-secondary col-1 float-end  me-2"  value="重置" typeof="reset">--%>

                </div>


            </form>
        </div>
    </div>


</div>

<script>

</script>

<jsp:include page="/admin/common/footer.jsp"/>
