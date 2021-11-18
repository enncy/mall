<%@ page import="cn.enncy.mybatis.core.SqlSession" %>
<%@ page import="cn.enncy.mall.mapper.AddressMapper" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>
<%@ page import="cn.enncy.mall.pojo.Address" %>
<%@ page import="cn.enncy.mall.utils.RequestUtils" %>
<%@ page import="cn.enncy.mall.constant.MallSession" %>
<%@ page import="cn.enncy.mall.pojo.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="cn.enncy.mall.service.AddressService" %>
<%@ page import="cn.enncy.mall.service.ServiceFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%

    AddressService addressService = ServiceFactory.resolve(AddressService.class);

    User user = MallSession.from(session, User.class);
    System.out.println("user " + user);
    String id = request.getParameter("id");
    Address address = null;

    // 如果存在 id ，则为修改订单
    if (!StringUtils.isNullOrEmpty(id)) {
        address = addressService.findOneById(Long.parseLong(id));
    }

    if (request.getMethod().equals("POST")) {
        try {
            Address target = Optional.ofNullable(address).orElse(new Address());
            RequestUtils.replaceObject(request, target);

            // 如果不是修改模式，则插入，否则更新
            if (address == null) {
                addressService.insert(target);
            } else {
                addressService.update(target);
            }
            response.sendRedirect("/user/address");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/error?code=500");
        }

    }


%>

<jsp:include page="/common/header.jsp"/>


<jsp:include page="/common/navigation.jsp"/>

<div class="p-lg-5 mt-lg-5 mb-lg-5 d-flex justify-content-center  flex-lg-nowrap flex-wrap ">


    <jsp:include page="/user/navigation.jsp"/>

    <div class="d-flex flex-wrap   col-lg-6 col-md-8 col-12">

        <h2><%=address != null ? "修改" : "添加"%>地址</h2>

        <div class="card col-12 mt-2">
            <div class="card-body d-flex  ">
                <form method="POST" class="col-12">
                    <input class="d-none" name="userId" value="<%=user!=null?user.getId():"0"%>"/>
                    <div class="form-group col-12">
                        <label for="inputAlias">备注</label>
                        <input name="alias" type="text" class="form-control" id="inputAlias" required
                               value="<%=address!=null?address.getAlias():""%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputReceiver">收件人名称</label>
                        <input name="receiver" type="text" class="form-control" id="inputReceiver" required
                               value="<%=address!=null?address.getReceiver():""%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputPhone">收件人电话</label>
                        <input name="phone" type="text" class="form-control" id="inputPhone" required
                               value="<%=address!=null?address.getPhone():""%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputZipCode">邮编</label>
                        <input name="zipCode" type="text" class="form-control" id="inputZipCode" required
                               value="<%=address!=null?address.getZipCode():""%>">
                    </div>
                    <div class="form-group col-12">
                        <label for="inputDetail">详细地址</label>
                        <textarea name="detail" class="form-control" id="inputDetail" required
                                  rows="3"><%=address != null ? address.getDetail() : ""%></textarea>
                    </div>


                    <div class=" col-12">
                        <button type="submit" class="btn btn-primary float-right"><%=address != null ? "修改" : "添加"%>
                        </button>
                    </div>

                </form>

            </div>
        </div>
    </div>


</div>


<jsp:include page="/common/footer.jsp"/>





