<%--style="display: none"--%>
<div class="ace-settings-container hide" id="ace-settings-container">

    <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
        <i class="icon-cog bigger-150"></i>
    </div>

    <div class="ace-settings-box" id="ace-settings-box">
        <div>
            <div class="pull-left">
                <select id="skin-colorpicker" class="hide" onchange="javascript:matrix.onSkinChange();">
                    <option data-skin="default" value="#438EB9">#438EB9</option>
                    <option data-skin="skin-1" value="#222A2D" <c:if test="${skinClass == 'skin-1'}">selected</c:if>>
                        #222A2D
                    </option>
                    <option data-skin="skin-2" value="#C6487E" <c:if test="${skinClass == 'skin-2'}">selected</c:if>>
                        #C6487E
                    </option>
                    <option data-skin="skin-3" value="#D0D0D0" <c:if test="${skinClass == 'skin-3'}">selected</c:if>>
                        #D0D0D0
                    </option>
                </select>
            </div>
            <span>&nbsp; 换肤</span>
        </div>

        <div>
            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar"/>
            <label class="lbl" for="ace-settings-navbar">固定导航栏</label>
        </div>

        <div>
            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar"/>
            <label class="lbl" for="ace-settings-sidebar">固定边导航</label>
        </div>

        <div>
            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs"/>
            <label class="lbl" for="ace-settings-breadcrumbs">固定面包屑导航</label>
        </div>

        <%--<div>--%>
        <%--<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl"/>--%>
        <%--<label class="lbl" for="ace-settings-rtl">Right To Left (rtl)</label>--%>
        <%--</div>--%>

        <div>
            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container"/>
            <label class="lbl" for="ace-settings-add-container">

                Inside container
            </label>
        </div>
    </div>
</div>
<!-- /#ace-settings-container -->
