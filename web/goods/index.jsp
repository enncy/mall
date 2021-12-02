<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
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

<div class="container p-lg-5 pt-lg-0 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap">
    <form>
        <input type="hidden" value="${param.tag}" name="tag">
        <div class="col-12  p-2 pt-0 mb-5 d-flex flex-wrap justify-content-center">
            <div class="col-12 d-flex justify-content-center flex-wrap">
                <div class="col-12 col-lg-10">

                    <div class="input-group mb-3 ">
                        <input type="text" class="form-control" name="search" placeholder="输入商品名字"
                               value="${param.search}">
                        <button class="btn btn-outline-secondary" type="submit">搜索商品</button>
                    </div>
                </div>
            </div>

            <nav class="navbar navbar-expand-lg navbar-light bg-light col-12">
                <div class="container-fluid">
                    <a class="navbar-brand">商品分类</a>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/goods">全部</a>
                            </li>
                            <% for (Tag tag : tagService.findByCount()) { %>
                            <li class="nav-item">
                                <a class="nav-link" href="/goods?search=${param.search}&tag=<%=tag.getName()%>">
                                    <span><%=tag.getName()%></span>
                                    <span style="font-size: 12px;color: #525252">(<%=tag.getCount()%>)</span>
                                </a>
                            </li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="d-flex col-12  pt-4 flex-wrap   justify-content-center">

                <div class="col-12  ">
                    <form class="me-2 d-flex ">

                        <nav class="navbar navbar-expand-lg navbar-light bg-light col-12">
                            <div class="container-fluid"  >
                                <div class="me-3 font-secondary">当前共有 <span class="font-primary"> <%=goodsList.size()%> </span>  件商品</div>
                                <div class="me-3  font-secondary">目前分类 : <span class="font-primary">${ empty param.tag?"无":param.tag}</span> </div>
                                <div class="me-3  font-secondary">价格排序 :</div>
                                <div class="collapse navbar-collapse font-primary" id="navbarNav2">
                                    <ul class="navbar-nav">
                                        <li class="nav-item" >
                                            <label for="order" style="cursor: pointer">${param.order=='desc'?"降序△":"升序▽"}</label>
                                            <input style="display:none;cursor:pointer;" type="submit" name="order" id="order"
                                                   value="${param.order=='desc'?"asc":"desc"}">
                                        </li>

                                    </ul>
                                </div>
                            </div>
                        </nav>


                        <div class=" d-flex flex-wrap   mt-4" >
                            <% for (Goods goods : goodsList) { %>
                            <%
                                BigDecimal discountPrice = goods.getDiscountPrice();
                                BigDecimal price = goods.getPrice();
                                String description = goods.getDescription();
                                if (description.length() > 20) {
                                    description = description.substring(0, 20) + "...";
                                }

                            %>
                            <div class="pt-3 pe-3 ps-3 pb-1 col-lg-3  col-md-4 col-6 goods-card">
                                <a href="/goods/detail?id=<%=goods.getId()%>" style="color: black;text-decoration:none">
                                    <div class="text-center">
                                        <img src="<%=goods.getImg()%>" class="card-img-top goods-img"
                                             alt="<%=description%>">
                                    </div>
                                    <div class="p-2">
                                        <div class="d-flex flex-wrap">

                                            <span class="col-12 font-secondary"
                                                  style="height: 40px; overflow: hidden; "><%=goods.getDescription()%>
                                            </span>

                                            <div class="mt-2 d-flex col-12  align-items-baseline">
                                                <span class="me-2 price">
                                                    <span style="font-size: 16px;margin-right: 2px">
                                                        ¥ <%=goods.getRealPrice()%>
                                                    </span>
                                                    <% if (discountPrice.intValue() != 0) { %>
                                                    <del class="me-2 font-secondary">¥<%=price%> </del>
                                                    <% } %>
                                                </span>
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
