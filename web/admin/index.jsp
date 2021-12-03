<%@ page import="cn.enncy.mall.service.UserService" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page import="cn.enncy.mall.service.GoodsService" %>
<%@ page import="cn.enncy.mall.service.OrderService" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.enncy.mall.pojo.Goods" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="cn.enncy.mall.pojo.Order" %>
<%@ page import="cn.enncy.mall.constant.OrderStatus" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="cn.enncy.mall.utils.formatter.DateFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="common/header.jsp"/>

<jsp:include page="common/navigation.jsp"/>


<%

    UserService userService = ServiceFactory.resolve(UserService.class);
    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    OrderService orderService = ServiceFactory.resolve(OrderService.class);

    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    int userCount = userService.count();
    int goodsCount = goodsService.count();
    int orderCount = orderService.count();
    BigDecimal salesVolume = orderService.getSalesVolume(day - 1);

%>


<div class="d-flex justify-content-between flex-wrap  align-items-center pt-3 pb-2 mb-3 border-bottom">
    <h1 class="h2">仪表盘</h1>
    <div class="col-12  mt-2 mb-5 d-flex flex-wrap flex-lg-nowrap">

        <div class="row  col-12">
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card p-4  col-12">
                    <div class="col-12 ">
                        <div class="float-start">
                            <svg style="width: 64px;height: 64px;" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                            </svg>
                        </div>
                        <div class="float-end  text-end">
                            <b>注册用户</b>
                            <p class="fw-bold mb-0 fs-2"><%=userCount%></p>
                        </div>
                    </div>
                </div>

            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card p-4 d-flex">
                    <div class="col-12  ">
                        <div class="float-start">
                            <svg style="width: 64px;height: 64px;" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                                <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                            </svg>
                        </div>
                        <div class="float-end text-end">
                            <b>上架商品</b>
                            <p class="fw-bold mb-0 fs-2"><%=goodsCount%></p>
                        </div>
                    </div>

                </div>

            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card p-4">

                    <div class="col-12 ">
                        <div class="float-start">
                            <svg style="width: 64px;height: 64px;" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-coin" viewBox="0 0 16 16">
                                <path d="M5.5 9.511c.076.954.83 1.697 2.182 1.785V12h.6v-.709c1.4-.098 2.218-.846 2.218-1.932 0-.987-.626-1.496-1.745-1.76l-.473-.112V5.57c.6.068.982.396 1.074.85h1.052c-.076-.919-.864-1.638-2.126-1.716V4h-.6v.719c-1.195.117-2.01.836-2.01 1.853 0 .9.606 1.472 1.613 1.707l.397.098v2.034c-.615-.093-1.022-.43-1.114-.9H5.5zm2.177-2.166c-.59-.137-.91-.416-.91-.836 0-.47.345-.822.915-.925v1.76h-.005zm.692 1.193c.717.166 1.048.435 1.048.91 0 .542-.412.914-1.135.982V8.518l.087.02z"/>
                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                <path d="M8 13.5a5.5 5.5 0 1 1 0-11 5.5 5.5 0 0 1 0 11zm0 .5A6 6 0 1 0 8 2a6 6 0 0 0 0 12z"/>
                            </svg>
                        </div>
                        <div class="float-end  text-end">
                            <b>订单总数</b>
                            <p class="fw-bold mb-0 fs-2"><%=orderCount%></p>
                        </div>
                    </div>
                </div>

            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card p-4">
                    <div class="col-12 ">
                        <div class="float-start">
                            <svg style="width: 64px;height: 64px;" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bar-chart-fill" viewBox="0 0 16 16">
                                <path d="M1 11a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1v-3zm5-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1V2z"/>
                            </svg>
                        </div>
                        <div class="float-end  text-end">
                            <b>本周销售额</b>
                            <p class="fw-bold mb-0 fs-2"><%=new DecimalFormat("#,##0.00").format(salesVolume)%></p>
                        </div>
                    </div>


                </div>


            </div>
        </div>
    </div>
</div>


<div class="row">
    <div class="col">
        <img id="chart" class="w-100" src="">
    </div>
    <div class="col">
        <img id="chart2"  class="w-100"  src="">
    </div>
</div>


<h2>近期订单</h2>
<div class="table-responsive">
    <table class="table table-striped table-sm">
        <thead>
        <tr>
            <th>id</th>
            <th>编号</th>
            <th>用户</th>
            <th>应付款</th>
            <th>状态</th>
            <th>地址</th>
            <th>更新时间</th>
            <th>创建时间</th>

        </tr>
        </thead>
        <tbody>
        <%

            List<Order> orders = orderService.findByPages(0, 20);

            for (Order order : orders) {
                User user = userService.findOneById(order.getUserId());
        %>
        <tr>
            <td><%=order.getId()%></td>
            <td><%=order.getUid()%></td>
            <td><%=user!=null?user.getNickname()+"-"+user.getAccount():"用户不存在"%></td>
            <td><%=order.getTotalPrice()%></td>
            <td><%=OrderStatus.getDescription(order.getStatus())%></td>
            <td><%=order.getAddressDetail()%></td>
            <td><%=DateFormatter.format(order.getUpdateTime(),"yyyy-MM-dd HH:mm:ss")%></td>
            <td><%=DateFormatter.format(order.getCreateTime(),"yyyy-MM-dd HH:mm:ss")%></td>
        </tr>
         <% } %>
        </tbody>
    </table>
</div>


<jsp:include page="common/footer.jsp"/>


<script>
    var active = "index";

     $.ajax({
         method:"get",
         url:"/chart",
         dataType:"json",
         success(r){

             if(r.status){
                 $("#chart").attr("src",r.data)

             }else{
                 alert("请求失败")
             }
         },
         error(e){
             alert("请求失败",e)
         }
     })

    $.ajax({
        method:"get",
        url:"/pie",
        dataType:"json",
        success(r){
            if(r.status){
                $("#chart2").attr("src",r.data)
            }else{
                alert("请求失败")
            }
        },
        error(e){
            alert("请求失败",e)
        }
    })
</script>


