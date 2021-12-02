<%@ page import="cn.enncy.mall.constant.ServiceMapping" %>
<%@ page import="cn.enncy.mybatis.core.ServiceFactory" %>
<%@ page import="cn.enncy.mall.service.TagService" %>
<%@ page import="cn.enncy.mall.pojo.Tag" %>
<%@ page import="cn.enncy.mall.service.impl.TagServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    TagService tagService = ServiceFactory.resolve(TagServiceImpl.class);
%>

</main>

<footer class="container py-5" style="background-color: white">
    <div class="row">
        <div class="col-12 col-md">
            <svg t="1638442084571" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2475" width="32" height="32"><path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64z m256 253.7l-40.8 39.1c-3.6 2.7-5.3 7.1-4.6 11.4v287.7c-0.7 4.4 1 8.8 4.6 11.4l40 39.1v8.7H566.4v-8.3l41.3-40.1c4.1-4.1 4.1-5.3 4.1-11.4V422.5l-115 291.6h-15.5L347.5 422.5V618c-1.2 8.2 1.7 16.5 7.5 22.4l53.8 65.1v8.7H256v-8.7l53.8-65.1c5.8-5.9 8.3-14.3 7-22.4V392c0.7-6.3-1.7-12.4-6.5-16.7l-47.8-57.6V309H411l114.6 251.5 100.9-251.3H768v8.5z" p-id="2476" fill="#707070"></path></svg>
            <small class="d-block mb-3 text-muted">© 2021 mall</small>
        </div>
        <div class="col-6 col-md">
            <h5>商品分类</h5>
            <ul class="list-unstyled text-small">
                <% for( Tag tag :  tagService.findByPages(0,10)){ %>
                <li><a class="text-muted" href="/goods?tag=<%=tag.getName()%>"><%=tag.getName()%></a></li>
                <% } %>

            </ul>
        </div>
        <div class="col-6 col-md">
            <h5>友情链接</h5>
            <ul class="list-unstyled text-small">
                <li><a class="text-muted" href="https://www.jd.com/" target="_blank">京东</a></li>
                <li><a class="text-muted" href="https://www.taobao.com/" target="_blank">淘宝</a></li>
                <li><a class="text-muted" href="https://www.pinduoduo.com/" target="_blank">拼多多</a></li>
                <li><a class="text-muted" href="https://www.suning.com/" target="_blank">苏宁易购</a></li>
                <li><a class="text-muted" href="https://www.vip.com/" target="_blank">唯品会</a></li>
                <li><a class="text-muted" href="http://gz.jumei.com/" target="_blank">聚美优品</a></li>
            </ul>
        </div>

        <div class="col-6 col-md">
            <h5>关于</h5>
            <ul class="list-unstyled text-small">
                <li><a class="text-muted" href="#">网站</a></li>
                <li><a class="text-muted" href="#">地址</a></li>
                <li><a class="text-muted" href="#">隐私</a></li>
                <li><a class="text-muted" href="#">团队成员</a></li>
            </ul>
        </div>
    </div>
</footer>

<script src="https://v5.bootcss.com/docs/5.1/dist/js/bootstrap.bundle.min.js"  ></script>

</body>
</html>


