<%@ page contentType="text/html;charset=UTF-8" language="java" %>


</main>
</div>
</div>

 
<script src="https://v5.bootcss.com/docs/5.1/dist/js/bootstrap.bundle.min.js"
></script>

<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"></script>
<%--table--%>
<script src="https://unpkg.com/bootstrap-table@1.15.3/dist/bootstrap-table.min.js"></script>
<script>
    // 切换链接 active状态
    $(function () {
        var active = window.active
        $(".active")[0].classList.remove("active")
        $("#" + active)[0].classList.add("active")
    })
</script>


</body>
</html>