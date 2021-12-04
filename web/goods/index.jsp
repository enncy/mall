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
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#goodsNav" aria-controls="goodsNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="goodsNav">
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
                                <a class="navbar-brand">当前共有 <span class="font-primary"> <%=goodsList.size()%> </span>  件商品</a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#goodsNav2" aria-controls="goodsNav2" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="goodsNav2">
                                    <div class="me-3  font-secondary">目前分类 : <span class="font-primary">${ empty param.tag?"无":param.tag}</span> </div>
                                    <div class="me-3  font-secondary">价格排序 :</div>
                                    <ul class="navbar-nav">
                                        <li class="nav-item" >
                                            <label for="order" class="nav-link font-primary"  style="cursor: pointer">${param.order=='desc'?"降序△":"升序▽"}</label>
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
                                                    <span class="font-secondary   ms-2" >
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                                          <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                                          <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                                        </svg>
                                                        <%=goods.getViews()%>
                                                    </span>
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
