
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<div>
    <form method="GET" class="d-flex">

        <div class="d-flex ms-3 ">
            <a class="btn btn-primary " href="/admin/common/operate/cart">添加购物车</a>
        </div>

    </form>



    <jsp:include page="/admin/common/service/index.jsp"/>
</div>


<jsp:include page="/admin/common/footer.jsp"/>
