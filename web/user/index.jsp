<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/common/header.jsp"/>

<jsp:include page="/common/navigation.jsp"/>

<%

    User user = (User) session.getAttribute("user");

%>

<div class="p-5 mt-5 mb-5 d-flex justify-content-center">

    <div class="d-flex" style="width: 10%;">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link" href="/orders">订单</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cart">购物车</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/favor">收藏夹</a>
            </li>
        </ul>
    </div>

    <div class="d-flex flex-wrap" style="width: 40%;">
        <div class="card" style="width: 100%;">
            <div class="card-body">
                <% if (user.getAvatar() == null) { %>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                     class="bi bi-person-fill"
                     viewBox="0 0 16 16">
                    <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                </svg>

                <% } else { %>
                <img src="<%=user.getAvatar()%>" class="rounded mx-auto d-block" alt="<%=user.getAvatar()%>">
                <% } %>


                <h5 class="card-title"><%=user.getNickname() == null ? "无昵称" : user.getAccount()%>
                </h5>
                <h6 class="card-subtitle mb-2 text-muted"><%=user.getProfile() == null ? "无简介" : user.getProfile()%>
                </h6>

            </div>
        </div>

        <div class="card mt-5" style="width: 100%;">

            <div class="card-body">
                <h5 class="card-title">个人信息设置</h5>
            </div>

            <div class="p-4">
                <form  method="POST">
                    <div class="form-group col-12">
                        <label for="inputAccount">账号</label>
                        <input disabled name="account" type="text" class="form-control" id="inputAccount"
                               value="<%=user.getAccount()%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputPassword">密码</label>
                        <input name="password" type="password" class="form-control" id="inputPassword"
                               value="<%=user.getPassword()%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputEmail">邮箱</label>
                        <input name="email" type="email" class="form-control" id="inputEmail"
                               value="<%=user.getEmail()%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputNickname">昵称</label>
                        <input name="nickname" type="text" class="form-control" id="inputNickname"
                               value="<%=user.getNickname()%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputProfile">简介</label>
                        <textarea name="profile" class="form-control" id="inputProfile"
                                  rows="3"><%=user.getProfile() == null ? "无" : user.getProfile()%></textarea>
                    </div>
                    <div class="form-group col-12">

                        <label for="inputAvatar" style="width: 100%;">
                            <div class="card" style="border: 3px dotted rgba(0,0,0,.125);">
                                <div class="card-body">
                                    <div class="p-4">点击上传头像 +</div>
                                </div>
                            </div>
                        </label>
                        <input name="avatar" type="file" class="form-control" id="inputAvatar" style="display:none;">

                    </div>

                    <div class=" col-12">
                        <button type="submit" class="btn btn-primary float-right">修改</button>
                    </div>

                </form>
            </div>

        </div>
    </div>


</div>


<jsp:include page="/common/footer.jsp"/>