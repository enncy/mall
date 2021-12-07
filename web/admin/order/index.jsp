
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/admin/common/header.jsp"/>

<jsp:include page="/admin/common/navigation.jsp"/>

<div>
    <form method="GET" class="d-flex">
        <div class="input-group col-lg-4 p-0 w-25 ">
            <input type="text" name="search" value="${param.search}" class="form-control" placeholder="用户id搜索">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="submit">搜索</button>
            </div>
        </div>

    </form>


    <jsp:include page="/admin/common/service/index.jsp"/>
</div>


<jsp:include page="/admin/common/footer.jsp"/>
