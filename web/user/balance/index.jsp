
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<div class="p-1  p-lg-5 mt-lg-5 mb-lg-5  p-md-2 mt-md-2 mb-md-2 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">

        <div class="card col-12 w-100 mt-2" style="width: 18rem;">
            <div class="card-body">

                <form method="POST">
                    <div class="card-title">余额充值</div>
                    <div class="card-text">
                        <input name="balance" type="number" value="0" min="0" step="0.01"/>
                    </div>

                    <input class="btn btn-primary mt-4" value="充值" type="submit">

                </form>
            </div>
        </div>

    </div>
</div>


<jsp:include page="/common/footer.jsp"/>

