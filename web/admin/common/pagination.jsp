<%@ page import="cn.enncy.mall.bean.Pagination" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2021/11/21
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<%
    Pagination pagination = (Pagination) request.getAttribute("pagination");
%>



<%--分页--%>
<div class="mt-4 d-flex justify-content-center">
    <nav class="mr-2" aria-label="Page navigation example">
        <ul class="pagination  justify-content-center">
            <li class="page-item">
                <% if (pagination.getPre() == 0) { %>
                <span class="page-link " style="cursor: not-allowed"><</span>

                <% } else { %>
                <a class="page-link" href="?page=<%=pagination.getPre()%>&size=<%=pagination.getSize()%>"
                   aria-label="Previous">
                    <
                </a>
                <% } %>
            </li>

            <% if (pagination.getCount() == 0) { %>
            <span class="page-link  link-secondary" style="cursor:not-allowed">0</span>
            <% } else { %>
            <% for (int i = 0; i < pagination.getCount(); i++) { %>
            <li class="page-item">
                <% if (i == pagination.getIndex()) { %>
                <span class="page-link  link-secondary" style="cursor:not-allowed"><%=i + 1%></span>
                <% } else { %>
                <a class="page-link" href="?page=<%=i%>&size=<%=pagination.getSize()%>"><%=i + 1%>
                </a>
                <% } %>
            </li>

            <% } %>
            <% } %>


            <li class="page-item">
                <% if (pagination.getNext() == pagination.getCount()) { %>
                <span class="page-link" style="cursor: not-allowed">></span>
                <% } else { %>
                <a class="page-link"
                   href="?page=<%=pagination.getNext()%>&size=<%=pagination.getSize()%>"
                   aria-label="Next">
                    >
                </a>
                <% } %>

            </li>
        </ul>
    </nav>

    <label>
        <select class="form-select p-0  ms-2" style="height: 36px;width: 100px;    padding-left: 4px !important;" onchange="changeSize(this)">
            <%
                int[] sizes = new int[]{10, 20, 50, 100};
                for (int i = 0; i < sizes.length; i++) { %>
            <option  <%=sizes[i] == pagination.getSize() ? "selected" : ""%> value="<%=sizes[i]%>"><%=sizes[i]%>条/页
            </option>
            <% } %>
        </select>
    </label>

</div>


<script>
    var active = "users"

    function changeSize(e) {
        let page = <%=pagination.getIndex()%>
            window.location.href = "?page=" + page + "&size=" + e.value;
    }
</script>
