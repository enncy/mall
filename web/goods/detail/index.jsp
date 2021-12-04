<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Comment" %>
<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page import="cn.enncy.mall.utils.formatter.DateFormatter" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<style>
    .price {
        line-height: 25px;
        font-size: 24px;
        color: #fd3f31;
    }

    .price-div {

        box-shadow: 0px 0px 4px #dcdcdc;
        border-bottom-right-radius: 10px;
        border-bottom-left-radius: 10px;
    }

    .price-bg {
        height: 40px;
        background: url("/assets/img/detailbg.png");
        transform: rotate(180deg);
        opacity: 0.7;
        border-radius: 4px;

    }

    .goods-img {
        border-radius: 4px;
        width: 400px;
        height: 400px;
    }

    .font-primary {
        font-size: 16px;
        font-weight: bold;
    }

    .font-secondary {
        font-weight: normal;
        font-size: 14px;
        color: #5e5e5e;
    }

    .operate{
        display: none;
        float: right;
    }

    .comment:hover .operate{
        display: inline-block;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%

    Goods goods = (Goods) request.getAttribute("goods");
    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
    User user = (User) session.getAttribute("user");
%>

<div class="container p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center   flex-wrap">

    <div class="  col-12">
        <div class="d-flex flex-wrap col-12 mb-5">
            <div class="col-12   col-lg-5 text-center me-lg-4">

                <div><img class="goods-img" src="<%=goods.getImg()%>" alt="图片"></div>
            </div>
            <div class="col-12  col-lg-6">
                <form action="/goods/add" method="POST">
                    <input type="hidden" name="id" value="<%=goods.getId()%>">

                    <div class="p-2 pb-0 p-lg-0">
                        <b><%=goods.getDescription()%>
                        </b>
                        <span class="font-secondary" >
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                              <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                              <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                            </svg>
                            <%=goods.getViews()%>
                        </span>
                    </div>
                    <div class="mt-4 mb-4  price-div">
                        <div class="price-bg"></div>
                        <div class="p-4">
                            <% if (goods.getDiscountPrice().intValue() == 0) { %>
                            原价：<span class="price">¥ <b style="font-size: 32px"><%=goods.getPrice()%></b></span>
                            <% } else { %>
                            <div>原价：
                                <del>¥ <%=goods.getPrice()%>
                                </del>
                            </div>
                            <div>折扣价：<span class="price">¥ <b style="font-size: 32px"><%=goods.getDiscountPrice()%></b></span>
                            </div>
                            <% } %>
                        </div>
                    </div>
                    <div class="mb-3">
                        <span>数量: </span>
                        <label class="w-25 ">
                            <input id="count" min="1"  type="number" name="count" value="1" class="form-control form-control-sm">
                        </label>
                    </div>
                    <div>
                        <span>库存: <%=goods.getStock()%></span>
                    </div>

                    <div class="mt-5 d-flex flex-nowrap">
                        <% if(goods.getStock()==0){ %>
                        <a type="button" class="col-12 btn btn-lg btn-outline-danger disabled" >商品缺货</a>
                        <% }else{ %>
                            <% if (user == null) { %>
                                <a type="button" class="col-6 col-lg-4 me-lg-2 btn btn-lg btn-outline-danger"
                                   href="/login">立即购买</a>
                                <a type="button" class="col-6 col-lg-7 btn btn-lg btn-danger" href="/login">加入购物车</a>
                            <% } else { %>
                                <input type="submit"  name="buy" value="立即购买" class="col-6 col-lg-4 me-lg-2 btn btn-lg btn-outline-danger" id="buy">

                                <label for="add" class="col-6 col-lg-7 btn btn-lg btn-danger">
                                    添加至购物车
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                         class="bi bi-cart" viewBox="0 0 16 16">
                                        <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                    </svg>
                                </label>
                                <input type="submit" style="display: none" class="col-6 me-lg-2 col-lg-4 btn btn-lg btn-outline-danger" id="add">

                            <% } %>
                        <% } %>

                    </div>
                </form>
            </div>
        </div>
        <div class="col-12  ">
            <div class="">
                <%--评论区--%>
                <div class="mt-5 card mb-5">
                    <% if(user==null){ %>
                    <p class="p-4 text-center">点击 <a href="/login">登录</a> 后才能留下您的评论</p>
                    <% }else{ %>
                    <div class="d-flex p-4 ">
                        <div>
                            <img style="border-radius: 4px" src="<%=user.getAvatar()%>" width="64" height="64">
                        </div>
                        <div class="w-100">
                            <form method="POST" action="/goods/comment" id="comment-form">
                                <input  type="hidden" value="<%=goods.getId()%>" name="id">
                                <input  type="hidden" value="0" name="parent">
                                <div class="input-group">
                                    <textarea class="form-control"  style="height: 64px"  placeholder="快来评论一下商品吧"  name="content"></textarea>
                                    <input class="  btn btn-outline-dark"  type="submit" value="发表">
                                </div>
                            </form>

                        </div>
                    </div>
                    <% } %>
                </div>

                    <ul class="list-group list-group-flush">
                        <%
                            UserService userService = ServiceFactory.resolve(UserService.class);
                            for( Comment comment : comments){
                                User commentUser = userService.findOneById(comment.getUserId());
                                Comment parent = comments.stream().filter(c -> c.getId() == comment.getParentId()).findAny().orElse(null);
                        %>
                        <li class="list-group-item p-4 d-flex  comment w-100">
                            <div>
                                <img src="<%=commentUser.getAvatar()%>" width="48" height="48" style="border-radius: 4px"  alt="<%=commentUser.getNickname()%>">

                            </div>
                            <div class="ms-4 w-100">
                                <div class="font-primary ">
                                    <%=commentUser.getNickname()%>
                                    <span class="font-secondary ms-5" style="color:#b3b3b3;"> <%=DateFormatter.format(comment.getCreateTime())%></span>
                                    <span class="font-secondary operate ">
                                        <% if(user!=null && user.getId() == commentUser.getId()){ %>
                                        <a href="/goods/comment/delete?id=<%=comment.getId()%>&userId=<%=commentUser.getId()%>&goodsId=<%=goods.getId()%>">删除</a>
                                        <% } %>

                                        <a style="cursor: pointer" onclick="comment('<%=comment.getId()%>','<%=commentUser.getNickname()%>')">回复</a>
                                    </span>
                                </div>
                                <div class="font-secondary">
                                    <% if(parent==null){ %>
                                    <%=comment.getContent()%>
                                    <% }else{ %>
                                    回复 @<%=userService.findOneById(parent.getUserId()).getNickname()%> : <%=comment.getContent()%>
                                    <% } %>


                                </div>
                            </div>
                        </li>
                        <% } %>


                    </ul>

            </div>
        </div>


    </div>

</div>


<jsp:include page="/common/footer.jsp"/>

<script>
    function  comment(id,name){
        let el = $('[name="content"]')
        $('[name="parent"]').val(id)
        el.attr("placeholder","回复 @"+name+" : ")
        window.scrollTo({
            top:el.offset().top-100,
            behavior:'smooth'
        })
        el.focus()

    }
</script>
