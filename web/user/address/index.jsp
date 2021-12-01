
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.pojo.Address" %>
<%@ page import="java.util.List" %>

<%@ page import="cn.enncy.mall.service.AddressService" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>



<style>

    .card .card-link{
        display: none;
    }


    .card:hover .card-link{
        display: inline-block;
    }
</style>

<%

    // 遍历地址并显示
    User user = (User) session.getAttribute("user");
    assert user != null;
    long defaultAddressId = user.getDefaultAddressId();
    List<Address> addressList = (List<Address>) request.getAttribute("addresses");
    addressList.sort(Comparator.comparing(address -> address.getId()!=defaultAddressId));

%>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<div class="p-1  p-lg-5 mt-lg-5 mb-lg-5  p-md-2 mt-md-2 mb-md-2 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">

        <% for( Address address : addressList){ %>

        <div class="card col-12 w-100 mt-2" style="width: 18rem;">
            <div class="card-body">

                <h5 class="card-title"><%=address.getAlias()%> <span class="fs-6"><%=address.getId()==defaultAddressId?"(默认)":""%></span></h5>
                <h6 class="card-subtitle mb-2 text-muted"><%=address.getReceiver() +" : "+address.getPhone()%></h6>
                <p class="card-text"><%=address.getDetail()%></p>
                <div>
                    <a href="/user/address/update?id=<%=address.getId()%>" class="card-link">修改</a>
                    <a href="/user/address/delete?id=<%=address.getId()%>" class="card-link">删除</a>
                    <% if(address.getId()!=defaultAddressId){ %>
                    <a href="/user/address/default?id=<%=address.getId()%>" class="card-link">设为默认</a>
                    <% }  %>


                </div>
            </div>
        </div>

        <% } %>

        <div class="col-12 p-5 mt-2" style="cursor: pointer;border: 3px dotted #e7e7e7;border-radius: 10px;" onclick="window.location.href = '/user/address/update'">
            <div class=" text-center " >点击添加地址</div>
        </div>


    </div>

</div>


<jsp:include page="/common/footer.jsp"/>


