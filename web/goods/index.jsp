<%@ page import="cn.enncy.mall.utils.ServiceFactory" %>
<%@ page import="cn.enncy.mall.service.TagService" %>
<%@ page import="cn.enncy.mall.pojo.Tag" %>
<%@ page import="cn.enncy.mall.service.GoodsService" %>
<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2021/11/7
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<style>
    .price {
        line-height: 25px;
        font-size: 18px;
        color: #fd3f31;
    }

    .font-primary {
        font-size: 14px;
        font-weight: bold;
    }

    .font-secondary {
        font-size: 13px;
        color: #5e5e5e;
    }

    .goods-img {
        width: 200px;
        height: 200px;
        background-size: 100% 100%
    }

    .goods-card {
        border: 1px #f6f6f6 solid;
        max-width: 240px;
        min-width: 200px;
        cursor: pointer;
    }

    .goods-card:hover {
        border: 1px #e79595 solid;
        box-shadow: 0px 0px 6px #dcdcdc;

    }

    .tag {
        margin-left: 12px;
    }
</style>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<%

    TagService tagService = ServiceFactory.resolve(TagService.class);

    List<Goods> goodsList = (List<Goods>) request.getAttribute("goods");

%>

<div class="p-lg-5 pt-lg-0 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap">
    <form>
        <input type="hidden" value="${param.tag}" name="tag">
        <div class="col-12 col-lg-12 p-2 pt-0 mb-5 d-flex flex-wrap justify-content-center">
            <div class="col-12 d-flex justify-content-center flex-wrap">
                <div class="col-12 col-lg-4 col-sm-8">

                    <div class="input-group mb-3 ">
                        <input type="text" class="form-control" name="search" placeholder="输入商品名字"
                               value="${param.search}">
                        <button class="btn btn-outline-secondary" type="submit">搜索商品</button>
                    </div>

                </div>


            </div>

            <div class="col-12 d-flex justify-content-center ">
                <div>

                    商品分类 ：
                    <a class="tag" href="/goods">首页</a>
                    <% for (Tag tag : tagService.findByCount()) { %>
                    <a class="tag" href="/goods?tag=<%=tag.getName()%>">
                        <span><%=tag.getName()%></span>
                        <span style="font-size: 12px;color: #525252">(<%=tag.getCount()%>)</span></li>
                    </a>
                    <% } %>
                </div>
            </div>

            <div class="d-flex col-12 col-sm-11 col-lg-10  pt-4 flex-wrap   justify-content-center">

                <div class="col-12 col-sm-10 col-log-8 ms-lg-5">
                    <form class="me-2">

                        <div class="mb-2 mt-2 card">

                            <div class="d-flex p-2">
                                <span class="me-lg-5 me-1 fw-bold">当前共有 <%=goodsList.size()%> 件商品</span>
                                <span class="me-lg-5 me-1"> <span
                                        class="fw-bold">目前分类 : </span> ${ empty param.tag?"无":param.tag} </span>
                                <span class="me-2 fw-bold">价格排序 : </span>
                                <label for="order">${param.order=='desc'?"降序△":"升序▽"}</label>
                                <input style="display:none;cursor:pointer;" type="submit" name="order" id="order"
                                       value="${param.order=='desc'?"asc":"desc"}">
                            </div>

                        </div>
                        <div class=" d-flex flex-wrap">
                            <% for (Goods goods : goodsList) { %>
                            <%
                                BigDecimal discountPrice = goods.getDiscountPrice();
                                BigDecimal price = goods.getPrice();
                                String name = goods.getName();
                                if (name.length() > 14) {
                                    name = name.substring(0, 14) + "...";
                                }

                            %>
                            <div class="pt-3 pe-3 ps-3 pb-1 col-lg-3  col-md-4 col-6 goods-card">
                                <a href="/goods/detail?id=<%=goods.getId()%>" style="color: black;text-decoration:none">
                                    <div class="text-center">
                                        <img src="<%=goods.getImg()%>" class="card-img-top goods-img" alt="<%=name%>">
                                    </div>
                                    <div class="p-2">
                                        <div class="d-flex flex-wrap">
                                    <span class="col-12 font-primary"
                                      style="white-space:nowrap;overflow: hidden "><%=name%></span>
                                            <span class="col-12 font-secondary"
                                                  style="height: 40px; overflow: hidden; "><%=goods.getDescription()%></span>
                                            <div class="mt-2 d-flex col-12  align-items-baseline">

                                    <span class="me-2 price"><span
                                            style="font-size: 16px;margin-right: 2px">¥</span><%=discountPrice.intValue() == 0 ? price : discountPrice%></span>
                                                <% if (discountPrice.intValue() != 0) { %>
                                                <del class="me-2 font-secondary">¥<%=price%>
                                                </del>
                                                <% } %>

                                                <%--<span class="font-secondary"--%>
                                                <%--      style="font-size: 10px">库存<%=goods.getStock()%></span>--%>
                                            </div>


                                        </div>

                                    </div>
                                </a>
                            </div>
                            <% } %>
                        </div>


                        <div class="mt-4">
                            <jsp:include page="/admin/common/pagination.jsp"/>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </form>

</div>


<jsp:include page="/common/footer.jsp"/>
