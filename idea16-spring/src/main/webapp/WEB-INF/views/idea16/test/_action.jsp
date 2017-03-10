<%--
  Created by IntelliJ IDEA.
  User: Zora
  Date: 2017/2/22
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(function () {
        //toplink start
        $("a.topLink").click(function () {
            $("html, body").animate({
                    scrollTop: $($(this).attr("href")).offset().top + "px"
                },
                {
                    duration: 500,
                    easing: "swing"
                });
            return false;
        });
        //toplink end

        //nav
        showNav(arr);

        //clock
        aa();
    });
    var arr = [
        {
            id: 1,
            level: 1,
            name: 'a1',
            href: 'http://baidu.com',
            parId: ''
        },
        {
            id: 2,
            level: 2,
            name: 'a2',
            href: 'http://baidu.com',
            parId: 1
        },
        {
            id: 22,
            level: 2,
            name: 'a22',
            href: 'http://baidu.com',
            parId: 1
        },
        {
            id: 222,
            level: 2,
            name: 'a222',
            href: 'http://baidu.com',
            parId: 1
        },
        {
            id: 3,
            level: 3,
            name: 'a3',
            href: 'http://baidu.com',
            parId: 2
        },
        {
            id: 33,
            level: 3,
            name: 'a33',
            href: 'http://baidu.com',
            parId: 22
        },
        {
            id: 333,
            level: 3,
            name: 'a333',
            href: 'http://baidu.com',
            parId: 222
        },
        {
            id: 4,
            level: 1,
            name: 'b1',
            href: 'http://baidu.com',
            parId: ''
        },
        {
            id: 5,
            level: 2,
            name: 'b2',
            href: 'http://baidu.com',
            parId: 4
        },
        {
            id: 6,
            level: 3,
            name: 'b3',
            href: 'http://baidu.com',
            parId: 5
        }
    ];

</script>
