<%@ page import="cn.enncy.mall.pojo.User" %>

<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.File" %>
<%@ page import="cn.enncy.mall.utils.Security" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.mapper.UserMapper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mall.utils.ServiceFactory" %>
<%@ page import="java.text.DecimalFormat" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%
    User user = (User) session.getAttribute("user");
%>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap">


    <jsp:include page="navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">
        <div class="card col-12">
            <div class="card-body d-flex align-items-center" style="white-space: nowrap">
                <div class="col-2 text-center">
                    <% if (user.getAvatar() == null) { %>
                    <svg style="width: 40px;height: 40px;" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-person-fill"
                         viewBox="0 0 16 16">
                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path>
                    </svg>

                    <% } else { %>
                    <img src="${sessionScope.user.avatar}" width="64" height="64" class="rounded mx-auto d-block"
                         alt="${sessionScope.user.avatar}">
                    <% } %>
                </div>
                <div class="ml-2 col-4">
                    <h5 class="card-title">${sessionScope.user.nickname}
                    </h5>
                    <h6 class="card-subtitle mb-2 text-muted">${sessionScope.user.profile}
                    </h6>

                </div>
                <div class="ml-2 d-flex col-6 justify-content-end  align-items-baseline">
                    <div style="color: #7abaff;font-size: xx-large;cursor:pointer;">

                        <%=user.getBalance().toString().equals("0")?"0":new DecimalFormat("#,##0.00").format(user.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP))%>
                    </div>
                    <div class="ml-1" style="color: #7abaff;font-size: xx-large">
                        $
                    </div>
                    <a class="ml-4" style="font-size: 12px" href="/user/balance">充值余额</a>
                </div>


            </div>
        </div>

        <div class="card mt-5 col-12">

            <div class="card-body">
                <h5 class="card-title">个人信息设置</h5>
            </div>

            <div class="p-4">
                <form method="POST" enctype="multipart/form-data">
                    <div class="mb-3 col-12">
                        <label for="inputAccount">账号</label>
                        <input disabled name="account" type="text" class="form-control" id="inputAccount"
                               value="${sessionScope.user.account}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputPassword">密码</label>
                        <input name="password" type="password" class="form-control" id="inputPassword"
                               value="${sessionScope.user.password}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputEmail">邮箱</label>
                        <input name="email" type="email" class="form-control" id="inputEmail"
                               value="${sessionScope.user.email}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputNickname">昵称</label>
                        <input name="nickname" type="text" class="form-control" id="inputNickname"
                               value="${sessionScope.user.nickname}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputProfile">简介</label>
                        <textarea name="profile" class="form-control" id="inputProfile"
                                  rows="3">${sessionScope.user.profile}</textarea>
                    </div>
                    <div class="mb-3 col-12">

                        <label for="inputAvatar" style="width: 100%;">
                            <div class="card" style="border: 3px dotted rgba(0,0,0,.125);">
                                <div class="card-body">
                                    <div class="p-4  text-center" id="filename">点击上传头像</div>
                                </div>
                            </div>
                        </label>
                        <input name="avatar" accept=".png,.jpg" type="file" class="form-control" id="inputAvatar"
                               style="display:none;">

                    </div>

                    <div class=" col-12">
                        <button type="submit" class="btn btn-primary w-25 float-end">修改</button>
                    </div>

                </form>
            </div>

        </div>
    </div>


</div>


<jsp:include page="/common/footer.jsp"/>

<script>
    document.querySelector('[type="file"]').onchange = function (e) {
        console.log(e.target.value)
        document.querySelector('#filename').innerHTML = e.target.value.split('\\').pop()
    }
</script>