<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.mapper.UserMapper" %>
<%@ page import="cn.enncy.mall.constant.MallSession" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mall.utils.ServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    String balance = request.getParameter("balance");

    if (request.getMethod().equals("POST") && !StringUtils.isNullOrEmpty(balance)) {
        UserService userService = ServiceFactory.resolve(UserService.class);
        User user = MallSession.from(session, User.class);
        if (user != null) {
            BigDecimal origin = user.getBalance();
            user.setBalance(origin.add(BigDecimal.valueOf(Double.parseDouble(balance))));
            userService.update(user);
            response.sendRedirect("/user");
        }


    }

%>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">

        <div class="card col-12 mt-2" style="width: 18rem;">
            <div class="card-body">

                <form method="POST">
                    <div class="card-title">余额充值</div>
                    <div class="card-text">
                        <input name="balance" type="number" value="0" step="0.01"/>
                    </div>

                    <input class="btn btn-primary mt-4" value="充值" type="submit">

                </form>
            </div>
        </div>

    </div>
</div>


<jsp:include page="/common/footer.jsp"/>

