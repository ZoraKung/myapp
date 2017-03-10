<%--
  Created by IntelliJ IDEA.
  User: Zora
  Date: 2017/2/23
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <style>

        #box1,#box2 {
            position:absolute;
            top:30px;
            width:300px;
            height:300px;
            box-shadow:4px 4px 10px 0 #616161;
        }
        #box1 {
            left:50px;
        }
        #box2 {
            display:none;
            left:360px;
            overflow:hidden;
        }
        #mark {
            display:none;
            position:absolute;
            top:50px;
            left:50px;
            width:100px;
            height:100px;
            opacity:0.4;
            filter:alpha(opacity=40);
            cursor:move;
            background-color:#000;
        }
        #box1 img {
            width:100%;
            height:100%;
        }
        #box2 img {
            display:block;
            width:300%;
            height:300%;
        }
    </style>

<div id="box1">
    <img src="http://www.jq22.com/img/cs/csx.png" />
    <div id="mark">
    </div>
</div>
<div id="box2">
    <img src="http://www.jq22.com/img/cs/csbg.png" />
</div>
<script>
    var $mark = $("#mark");
    var $box1 = $("#box1");
    var $box2 = $("#box2");
    var $img = $box2.children("img");
    $box1.on("mouseenter",
        function(e) {
            //首先让mark遮罩显示
            $mark.stop().show();
            //再让box2显示
            $box2.stop().show();
            move(e)
        }).on("mousemove",
        function(e) {
            $mark.stop().show();
            $box2.stop().show();
            move(e);
        }).on("mouseleave",
        function(e) {
            $mark.stop().hide();
            $box2.stop().hide();
        });

    function move(e) {
        var l = e.clientX - $box1.offset().left - $mark.outerWidth() / 2;
        var t = e.clientY - $box1.offset().top - $mark.outerHeight() / 2;
        var minL = 0,
            minT = 0;
        var maxL = $box1.outerWidth() - $mark.outerWidth();
        var maxT = $box1.outerHeight() - $mark.outerHeight();
        l = l <= minL ? 0 : (l >= maxL ? maxL: l);
        t = t <= minT ? 0 : (t >= maxT ? maxT: t);
        $mark.css({
            left: l,
            top: t
        });
        $img.css({
            marginLeft: -3 * l,
            marginTop: -3 * t
        });
    }</script>





