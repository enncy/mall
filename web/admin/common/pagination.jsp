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

<input type="hidden" value="<%=pagination.getIndex()%>" name="page" id="page">
<input type="hidden" value="<%=pagination.getSize()%>" name="size" id="size">

<%--分页--%>
<div class="mt-4 d-flex justify-content-center">
    <nav class="mr-2" aria-label="Page navigation example">
        <ul class="pagination  justify-content-center">
            <li class="page-item">
                <% if (pagination.getPre() == 0) { %>
                <span class="page-link " style="cursor: not-allowed"><</span>
                <% } else { %>
                <a class="page-link" onclick="pre()"
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
                <a class="page-link" onclick="index(<%=i%>)"><%=i + 1%>
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
                   onclick="next()"
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
    var page = <%=pagination.getIndex()%>;
    var size = <%=pagination.getSize()%>;

    var searchLists =  window.location.search.split("&").filter(s=>!!s).map(s=>(
        {
            key:s.split("=")[0].replace("?",""),
            value:s.split("=")[1] || ""
        }
    ))

    function add(obj){
        searchLists = searchLists.filter(s=>s.key!==obj.key)
        searchLists.push(obj)
    }

    function changeSize(e) {
        let page = <%=pagination.getIndex()%>;
        add({
            key:"page",
            value: page
        })
        add({
            key:"size",
            value: e.value
        })
          window.location.href = "?"+ searchLists.map(s=>s.key+"="+s.value).join("&")
    }

    function pre(){
        add({
            key:"page",
            value: page-1
        })
        add({
            key:"size",
            value: size
        })
          window.location.href = "?"+ searchLists.map(s=>s.key+"="+s.value).join("&")

    }

    function next(){
        add({
            key:"page",
            value: page+1
        })
        add({
            key:"size",
            value: size
        })
          window.location.href = "?"+ searchLists.map(s=>s.key+"="+s.value).join("&")
    }

    function index(p){
        add({
            key:"page",
            value: p
        })
        add({
            key:"size",
            value: size
        })

         window.location.href = "?"+ searchLists.map(s=>s.key+"="+s.value).join("&")
    }

</script>
