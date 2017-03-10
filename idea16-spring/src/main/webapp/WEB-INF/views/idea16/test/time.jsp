<%--
  Created by IntelliJ IDEA.
  User: Zora
  Date: 2017/2/23
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    div {
        text-align: center;
        margin-top: 100px;
        font-size: 30px;
    }
</style>

<div id="div_time">20:20:20</div>
<script>
    $(function () {
        var div = document.querySelector('#div_time');

        function time1() {
            var now = new Date();
            div.innerHTML = now.toTimeString().substring(0, 9);
        }

        time1();
        setInterval(time1, 1000)
    })
</script>
