<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.mapper.AddressMapper" %>
<%@ page import="cn.enncy.mall.constant.MallSession" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="cn.enncy.mall.pojo.Address" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.service.AddressService" %>
<%@ page import="cn.enncy.mall.utils.ServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>


<%

    AddressService addressService = ServiceFactory.resolve(AddressService.class);


    String id = request.getParameter("id");

    // 删除地址
    String mode = request.getParameter("mode");
    if(!StringUtils.isNullOrEmpty(id) && !StringUtils.isNullOrEmpty(mode)){
        if(mode.equals("delete")){
            addressService.deleteById(Long.parseLong(id));
        }
    }

    // 遍历地址并显示
    User user = MallSession.from(session, User.class);
    assert user != null;
    List<Address> addressList = addressService.findByUserId(user.getId());


%>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">

        <% for( Address address : addressList){ %>
        <div class="card col-12 mt-2" style="width: 18rem;">
            <div class="card-body">

                <h5 class="card-title"><%=address.getAlias()%></h5>
                <h6 class="card-subtitle mb-2 text-muted"><%=address.getReceiver() +" : "+address.getPhone()%></h6>
                <p class="card-text"><%=address.getDetail()%></p>
                <a href="/user/address/update?id=<%=address.getId()%>" class="card-link">修改</a>
                <a href="/user/address?id=<%=address.getId()%>&mode=delete" class="card-link">删除</a>
            </div>
        </div>

        <% } %>

        <div class="col-12 p-5 mt-2" style="cursor: pointer;border: 3px dotted #e7e7e7;border-radius: 10px;" onclick="window.location.href = '/user/address/update'">
            <div class=" text-center " >点击添加地址</div>
        </div>


    </div>

</div>


<jsp:include page="/common/footer.jsp"/>


