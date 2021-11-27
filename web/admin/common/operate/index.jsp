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
<%@ page import="cn.enncy.mall.constant.ServiceMapping" %>
<%@ page import="cn.enncy.mall.utils.ServiceFactory" %>
<%@ page import="cn.enncy.mall.annotaion.Reference" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<%
    BaseObject object = (BaseObject) request.getAttribute("object");

    ServiceMapping serviceMapping = (ServiceMapping) request.getAttribute("service");
    Map<String, Info> infosMap = BaseObjectUtils.getInfosMap(serviceMapping.objectClass);
    List<Field> allFields = BaseObjectUtils.getAllFields(serviceMapping.objectClass);
    boolean isUpdate = object.getId() != 0;
%>

<div>
    <div class="card">
        <div class="card-body p-5">
            <div class="card-title"><h2><%=object.getId() == 0 ? "添加" : "修改"%><%=serviceMapping.desc%>
            </h2></div>
            <form method="POST">
                <% if (isUpdate) { %>
                <input type="hidden" name="id" value="<%=object.getId()%>">
                <input type="hidden" name="createTime" value="<%=object.getCreateTime()%>">
                <input type="hidden" name="updateTime" value="<%=object.getUpdateTime()%>">
                <% } %>

                <%

                    Map<String, Object> valuesMap = BaseObjectUtils.getValuesMap(object);
                    for (Field field : allFields) {
                        // 属性名
                        String name = field.getName();
                        // 属性值
                        Object value = valuesMap.get(field.getName());
                        // 属性详情
                        Info info = infosMap.get(field.getName());
                        // 格式化器
                        Formatter formatter = info.formatter().newInstance();
                        // 格式化值
                        Object format = formatter.format(value);
                        // 属性所需标签
                        Tag tag = info.tag();
                %>
                <div class="mb-3">
                    <span style="font-weight: bold"><%=info.value()%></span>
                    <% if (tag.equals(Tag.INPUT)) { %>
                    <input <%=info.disabled()?"disabled":""%> name="<%=name%>" type="<%=info.type().value%>"
                                                              class="form-control" id="input<%=name%>"
                                                              data-origin="<%=value%>"
                                                              <%=BigDecimal.class.isAssignableFrom(field.getType())?"step=\"0.01\"":""%>
                                                              value="<%=format%>">
                    <% } else if (tag.equals(Tag.SELECT)) { %>
                    <select class="form-select  " id="input<%=name%>"
                            name="<%=name%>">
                        <option>无</option>
                        <%--获取 options 作为选项--%>
                        <%
                            Select select = field.getAnnotation(Select.class);
                            for (Option option : select.options()) {
                        %>
                        <option  <%=option.value().equals(value.toString()) ? "selected" : ""%>
                                value="<%=option.value()%>"><%=option.description()%>
                        </option>
                        <% } %>

                    </select>

                    <% } else if (tag.equals(Tag.TEXTAREA)) {%>
                    <textarea <%=info.disabled() ? "disabled" : ""%> id="input<%=name%>"
                                                                     name="<%=name%>"><%=value%></textarea>
                    <% } else if (tag.equals(Tag.IMAGE)) {%>
                    <div class="  input-group">

                        <input  class="form-control"   name="<%=name%>" type="text" id="input<%=name%>" value="<%=value%>">
                        <label  class="btn btn-primary" for="inputFile<%=name%>" style="border-top-right-radius: 4px;border-bottom-right-radius: 4px">
                            点击上传图片
                        </label>
                        <input style="display: none" type="file" onchange="imageUpload(this,'input<%=name%>')" accept=".png,.jpg"
                               class="form-control" id="inputFile<%=name%>">
                    </div>
                    <% } else if (tag.equals(Tag.REFERENCE)) { %>

                    <%
                        ServiceMapping mapping = field.getAnnotation(Reference.class).value();
                        // 获取可展示的字段
                        String showFields = BaseObjectUtils.getShowFields(mapping.objectClass).stream().map(Field::getName).map(s -> "'" + s + "'").collect(Collectors.joining(","));
                    %>

                    <div class="d-flex w-100 input-group">
                        <input name="<%=name%>" type="text" class="form-control" id="input<%=name%>" disabled
                               value="<%=Long.parseLong(value.toString())==0?"点击按钮添加"+mapping.desc:value%>">
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary " style="white-space: nowrap"
                                onclick="search('<%=name%>','<%=mapping.name%>',[<%=showFields%>])"
                                data-bs-toggle="modal" data-bs-target="#<%=name%>Modal">
                            添加<%=mapping.desc%>
                        </button>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="<%=name%>Modal" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">

                        <div class="modal-dialog  modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">选择<%=mapping.desc%>
                                    </h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="input-group mb-3">
                                        <input type="text" class="form-control" id="searchInput">
                                        <button class="btn btn-outline-secondary" type="button" id="searchBtn"
                                                onclick="search('<%=name%>','<%=mapping.name%>',[<%=showFields%>])">搜索
                                        </button>
                                    </div>
                                    <div>
                                        <div class="list-group" id="<%=name%>SearchList">
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
                                            onclick="save('<%=name%>')">选择
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%}%>

                </div>
                <% } %>
                <div class=" w-100">
                    <input class="btn btn-primary col-2 float-end" value="<%=object.getId()==0?"添加":"修改"%>"
                           type="submit">
                    <%--<input class="btn btn-secondary col-1 float-end  me-2"  value="重置" typeof="reset">--%>

                </div>


            </form>
        </div>
    </div>


</div>


<jsp:include page="/admin/common/footer.jsp"/>

<script>

    function search(fieldName, serviceName,showList) {
        $.ajax({
            url: '/search/' + serviceName + '?key=' + $("#searchInput").val(),
            dataType: 'json',
            method: 'get',

            success(r) {

                if (r.status) {
                    let html = ""
                    for (let i of r.data) {

                        html += `
                        <label class="list-group-item">
                            <input name="\${fieldName}" class="form-check-input me-1  search-radio" type="radio" value="\${i.id}">
                            \${showList.map(k=>i[k]).join("-")}
                        </label>
                        `
                    }
                    if(html!==""){
                        $("#"+fieldName+"SearchList").html(html)
                    }

                }

            },
            error() {
                alert("请求失败!")
            }
        })
    }

    function save(fieldName) {

        $("#input" + fieldName).val($("[name='" + fieldName + "']:checked").val())

    }

    function imageUpload(el, id) {
        var formData = new FormData();
        let file = el.files.item(0);

        formData.append('image', file); //添加图片信息的参数
        $.ajax({
            url: '/image/upload?name=' + file.name,
            data: formData,
            method: 'post',
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            dataType: "json",
            success(r) {
                console.log(r)
                if (r.status) {
                    $("#"+id).val(r.data);
                }
            },
            error() {
                alert("上传失败!")
            }
        })
    }

</script>
