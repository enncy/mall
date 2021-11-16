<%@ page import="cn.enncy.mall.pojo.User" %>

<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.util.stream.Stream" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="org.apache.commons.fileupload.FileItemHeaders" %>
<%@ page import="java.util.*" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="cn.enncy.mall.utils.Security" %>
<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.mapper.UserMapper" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<%

    User user = (User) session.getAttribute("user");

    // 是否为表单提交
    if (request.getMethod().equals("POST")) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(request);
            Map<String, FileItem> map = items.stream().collect(Collectors.toMap(FileItem::getFieldName,fileItem -> fileItem));
            String password = map.remove("password").getString("utf-8");
            String email = map.remove("email").getString("utf-8");
            String nickname = map.remove("nickname").getString("utf-8");
            String profile = map.remove("profile").getString("utf-8");
            FileItem avatarItem = map.remove("avatar");

            // 获取文件路径
            String avatarPath = "/assets/img/avatar/" + Security.stringToMD5(user.getAccount()) + ".png";
            String filePath = request.getServletContext().getRealPath(avatarPath);
            // 保存文件，如果存在则删除
            File file = new File(filePath);
            if((file.exists() && file.delete()) || !file.exists()){
                avatarItem.write(file);
            }

            user.setPassword(password);
            user.setEmail(email);
            user.setNickname(nickname);
            user.setProfile(profile);
            user.setAvatar(avatarPath);
            UserMapper mapper = SqlSession.getMapper(UserMapper.class);
            mapper.update(user);
            // 刷新缓存
            user.setAvatar(avatarPath+"?t="+System.currentTimeMillis());
        } catch (Exception fue) {
            fue.printStackTrace();
        }
    }

%>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap">


    <jsp:include page="navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">
        <div class="card col-12">
            <div class="card-body d-flex ">
                <div  >
                    <% if (user.getAvatar() == null) { %>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-person-fill"
                         viewBox="0 0 16 16">
                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                    </svg>

                    <% } else { %>
                    <img src="<%=user.getAvatar()%>" width="64" height="64" class="rounded mx-auto d-block" alt="<%=user.getAvatar()%>">
                    <% } %>
                </div>
                <div  class="ml-2">
                    <h5 class="card-title"><%=user.getNickname() == null ? user.getAccount() :  user.getNickname() %>
                    </h5>
                    <h6 class="card-subtitle mb-2 text-muted"><%=user.getProfile() == null ? "无简介" : user.getProfile()%>
                    </h6>

                </div>




            </div>
        </div>

        <div class="card mt-5 col-12">

            <div class="card-body">
                <h5 class="card-title">个人信息设置</h5>
            </div>

            <div class="p-4">
                <form method="POST" enctype="multipart/form-data">
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
                                    <div class="p-4  text-center" id="filename">点击上传头像</div>
                                </div>
                            </div>
                        </label>
                        <input name="avatar" accept=".png,.jpg" type="file" class="form-control" id="inputAvatar" style="display:none;">

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

<script>
    document.querySelector('[type="file"]').onchange = function (e){
        console.log(e.target.value)
        document.querySelector('#filename').innerHTML = e.target.value.split('\\').pop()
    }
</script>