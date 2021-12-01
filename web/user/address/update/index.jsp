<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.pojo.Address" %>
<%@ page import="cn.enncy.mall.utils.RequestUtils" %>

<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="cn.enncy.mall.service.AddressService" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page import="cn.enncy.mall.utils.Logger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%


%>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<div class="p-1  p-lg-5 mt-lg-5 mb-lg-5  p-md-2 mt-md-2 mb-md-2 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">

        <div>
            <jsp:include page="/common/alert.jsp"/>
        </div>

        <h2>${requestScope.address.id==0 ? "添加":"修改"}地址</h2>

        <div class="card col-12 mt-2">
            <div class="card-body d-flex  ">
                <form method="POST" class="col-12">

                    <div class="mb-3 col-12">
                        <label for="inputAlias">备注</label>
                        <input name="alias" type="text" class="form-control" id="inputAlias" required
                               value="${requestScope.address.alias}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputReceiver">收件人名称</label>
                        <input name="receiver" type="text" class="form-control" id="inputReceiver" required
                               value="${requestScope.address.receiver}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputPhone">收件人电话</label>
                        <input name="phone" type="text" class="form-control" id="inputPhone" required
                               value="${requestScope.address.phone}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputZipCode">邮编</label>
                        <input name="zipCode" type="text" class="form-control" id="inputZipCode" required
                               value="${requestScope.address.zipCode}">
                    </div>
                    <div class="mb-3 col-12">
                        <label for="inputDetail">详细地址</label>
                        <textarea name="detail" class="form-control" id="inputDetail" required
                                  rows="3">${requestScope.address.detail}</textarea>
                    </div>


                    <div class="w-100 col-12">
                        <button type="submit"
                                class="btn w-25 btn-primary float-end">${requestScope.address.id==0 ? "添加": "修改"}
                        </button>
                    </div>

                </form>

            </div>
        </div>
    </div>


</div>


<jsp:include page="/common/footer.jsp"/>





