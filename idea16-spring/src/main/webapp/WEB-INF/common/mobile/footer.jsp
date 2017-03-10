<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="icon-double-angle-up icon-only bigger-110"></i>
</a>
</div><!-- /.main-container -->
<!-- basic scripts -->
<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='${ace3}/assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="${ace3}/assets/js/bootstrap.min.js"></script>
<!--[if lte IE 8]>
<script src="${ace3}/assets/js/excanvas.min.js" type="text/javascript" ></script>
<![endif]-->
<%@ include file="/WEB-INF/common/include/bootbox-js.jsp" %>
<%@ include file="/WEB-INF/common/include/jquery.gritter-js.jsp" %>

<script src="${ace3}/assets/js/spin.min.js" type="text/javascript"></script>
<!-- ace scripts -->
<script src="${ace3}/assets/js/ace-elements.min.js"></script>
<script src="${ace3}/assets/js/ace.min.js"></script>
<decorator:getProperty property="page.footer.requires"/>
<!-- WJXINFO scripts -->
<%--<script src="${ctx}/static/scripts/ui.tabs.paging.js"></script>--%>
<!--jquery serialize -->
<%--<script src="${ctx}/static/scripts/jquery.serializejson.min.js"></script>--%>
<script src="${ctx}/static/scripts/jquery.i18n.properties-min-1.0.9.js"></script>
<script src="${ctx}/static/scripts/wjxinfo.js"></script>
<script src="${ctx}/static/scripts/jquery.PrintArea.js"></script>

<script src="${ace3}/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>

<!--warning before session timeout-->
<%--<script src="${ctx}/static/scripts/wjxinfo.session.js"></script>--%>

<!-- inline scripts related to this page -->
<%@ include file="/WEB-INF/common/gritter-messenger.jsp" %>
<script src="${ctx}/static/scripts/spinner.js"></script>

<decorator:getProperty property="page.footer.script"/>
<decorator:getProperty property="page.footer.plugins"/>

<script type="text/javascript">
    $(document).ready(function () {
        appInit();
    });

    window.onload = function () {
        //onload call init session monitor
        //initSessionMonitor();

        //onload init form data
        $(document).find('form').each(function (index, item) {
            var _form_id = $(item).attr("id");
            if (_form_id != undefined && _form_id != null && _form_id != '') {
                $('#' + _form_id).data('serialize', $('#' + _form_id).serialize());
            }
        });
        window.addEventListener("beforeunload", function (e) {
            var confirmationMessage = jQuery.i18n.prop('data_changed');
            var _changeFlag = false;
            if (_is_submit) {
                return undefined;
            }
            $(document).find('form').each(function (index, item) {
                var _form_id = $(item).attr("id");
                if (_form_id != undefined && _form_id != null && _form_id != '') {
                    if ($('#' + _form_id).serialize() != $('#' + _form_id).data('serialize')) {
                        _changeFlag = true;
                    }
                }
            });
            if (_changeFlag) {
                (e || window.event).returnValue = confirmationMessage;
                return confirmationMessage;
            } else {
                return undefined;
            }
        });
    };
</script>
