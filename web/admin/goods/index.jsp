<%@ page import="java.util.Map" %>
<%@ page import="cn.enncy.mall.pojo.BaseObjectUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<div>
    <form method="GET" class="d-flex">
        <div class="input-group col-lg-4 p-0 w-25 ">
            <input type="text" name="name" class="form-control" placeholder="名字或描述搜索">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="submit">搜索</button>
            </div>
        </div>
        <div class="d-flex ms-3 ">
            <a class="btn btn-primary " href="/admin/common/operate/goods">添加商品</a>
        </div>

    </form>



    <jsp:include page="/admin/common/service/index.jsp"/>
</div>


<jsp:include page="/admin/common/footer.jsp"/>
