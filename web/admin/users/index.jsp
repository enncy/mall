<%@ page import="java.util.Map" %>
<%@ page import="cn.enncy.mall.utils.ServiceFactory" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<%
    int pageIndex = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("0"));
    int pageSize = Integer.parseInt(Optional.ofNullable(request.getParameter("size")).orElse("10"));
    UserService userService = ServiceFactory.resolve(UserService.class);
    int allCount = userService.count();
    List<User> users = userService.findByPages(pageIndex, pageSize);

%>

<div>
    <form method="POST">
        <div class="input-group mb-3 col-lg-4 p-0">
            <input type="text" class="form-control" placeholder="输入用户名搜索">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button">搜索</button>
            </div>
        </div>

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
                    </tr>
                    </thead>
                    <tbody>

                    <% for (User user : users) { %>
                    <tr>
                        <% for (Map.Entry<String, Object> entry : BaseObjectUtils.getValuesMap(user).entrySet()) { %>
                        <td><%=entry.getValue()%>
                        </td>
                        <% } %>

                    </tr>
                    <% } %>
                    </tbody>
                </table>

                <%--分页--%>
                <div class="mt-4 d-flex justify-content-center">
                    <nav class="mr-2" aria-label="Page navigation example">
                        <ul class="pagination  justify-content-center">
                            <li class="page-item">
                                <a class="page-link" href="?page=<%=Math.max(pageIndex - 1, 0)%>&size=<%=pageSize%>" aria-label="Previous">
                                    <
                                </a>
                            </li>
                            <% for (int i = pageIndex>5?pageIndex-5:1; i < pageIndex;i++){ %>
                            <li class="page-item"><a class="page-link" href="?page=<%=i%>&size=<%=pageSize%>"><%=i+1%></a></li>

                            <% } %>

                            <li class="page-item">
                                <a class="page-link" href="?page=<%=Math.min(pageIndex + 1, allCount)%>&size=<%=pageSize%>" aria-label="Next">
                                    >
                                </a>
                            </li>
                        </ul>
                    </nav>

                    <select class="custom-select-sm" onchange="changeSize(this)">
                        <%
                            int[] sizes = new int[]{10,20,50,100};
                            for(int i=0;i< sizes.length;i++){ %>
                        <option  <%=sizes[i]==pageSize?"selected":""%>  value="<%=sizes[i]%>"><%=sizes[i]%>条/页</option>
                        <% } %>

                    </select>
                </div>
            </div>


        </div>
    </form>

</div>


<script>
    var active = "users"

    function changeSize(e){

        let page=  <%=pageIndex%>
        window.location.href= "?page="+page+"&size="+e.value;
    }
</script>


<jsp:include page="/admin/common/footer.jsp"/>
