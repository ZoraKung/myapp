<%@ tag import="java.util.Arrays" %>
<%@ tag import="java.util.List" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Require JS" %>
<%
    String[] requires = id.split("[;,\\s]");
    List<String> requireList = Arrays.asList(requires);
    //request.setAttribute("requires", requires);
    //request.setAttribute("requires", requireList);
%>
<content tag="header.requires">

    <% if (requireList.contains("jquery-ui")) { %>
    <%@include file="/WEB-INF/common/include/jquery-ui-css.jsp" %>
    <%}%>

    <% if (requireList.contains("chosen")) { %>
    <%@include file="/WEB-INF/common/include/chosen-css.jsp" %>
    <%}%>

    <% if (requireList.contains("select2")) { %>
    <%@include file="/WEB-INF/common/include/select2-css.jsp" %>
    <%}%>

    <% if (requireList.contains("datetimepicker")) { %>
    <%@include file="/WEB-INF/common/include/datetimepicker-css.jsp" %>
    <%}%>

    <% if (requireList.contains("jqgrid")) { %>
    <%@include file="/WEB-INF/common/include/jqgrid-css.jsp" %>
    <%}%>

    <% if (requireList.contains("ui.multiselect")) { %>
    <%@include file="/WEB-INF/common/include/ui.multiselect-css.jsp" %>
    <%}%>

    <%--<% if (requireList.contains("jquery.gritter")) { %>--%>
    <%--<%@include file="/WEB-INF/common/include/jquery.gritter-css.jsp" %>--%>
    <%--<%}%>--%>

    <% if (requireList.contains("fullcalendar")) { %>
    <%@include file="/WEB-INF/common/include/fullcalendar-css.jsp" %>
    <%}%>

    <% if (requireList.contains("displaytag")) { %>
    <%@include file="/WEB-INF/common/include/displaytag-css.jsp" %>
    <%}%>

    <% if (requireList.contains("typeahead-bs2")) { %>
    <%-- None--%>
    <%}%>
    <% if (requireList.contains("ckeditor")) { %>
    <%@include file="/WEB-INF/common/include/ckeditor-js.jsp" %>
    <%-- None--%>
    <%}%>
    <% if (requireList.contains("bootstrap-tag")) { %>
    <%-- None--%>
    <%}%>
    <% if (requireList.contains("dropzone")) { %>
    <%@include file="/WEB-INF/common/include/dropzone-css.jsp" %>
    <%}%>

    <% if (requireList.contains("jquery.multiselect")) { %>
    <%@include file="/WEB-INF/common/include/jquery.multiselect-css.jsp" %>
    <%}%>

    <% if (requireList.contains("bootstrap.editable")) { %>
    <%@include file="/WEB-INF/common/include/bootstrap-editable-css.jsp" %>
    <%}%>

    <% if (requireList.contains("ui.tabs.paging")) { %>
    <%@include file="/WEB-INF/common/include/ui.tabs.paging-css.jsp" %>
    <%}%>

    <% if (requireList.contains("uploadify")) { %>
    <%@include file="/WEB-INF/common/include/uploadify-css.jsp" %>
    <%}%>

    <% if (requireList.contains("dynatree")) { %>
    <%@include file="/WEB-INF/common/include/dynatree-css.jsp" %>
    <%}%>

    <% if (requireList.contains("uploadify-browse")) { %>
    <%@include file="/WEB-INF/common/include/uploadify-browse-css.jsp" %>
    <%}%>
    <% if (requireList.contains("jstree")) { %>
    <%@include file="/WEB-INF/common/include/jstree-css.jsp" %>
    <%}%>
</content>

<content tag="footer.requires">
    <% if (requireList.contains("jquery-ui")) { %>
    <%@include file="/WEB-INF/common/include/jquery-ui-js.jsp" %>
    <%}%>

    <% if (requireList.contains("chosen")) { %>
    <%@include file="/WEB-INF/common/include/chosen-js.jsp" %>
    <%}%>

    <% if (requireList.contains("select2")) { %>
    <%@include file="/WEB-INF/common/include/select2-js.jsp" %>
    <%}%>

    <% if (requireList.contains("datetimepicker")) { %>
    <%@include file="/WEB-INF/common/include/datetimepicker-js.jsp" %>
    <%}%>

    <% if (requireList.contains("jqgrid")) { %>
    <%@include file="/WEB-INF/common/include/jqgrid-js.jsp" %>
    <%}%>

    <% if (requireList.contains("ui.multiselect")) { %>
    <%@include file="/WEB-INF/common/include/ui.multiselect-js.jsp" %>
    <%}%>

    <%--<% if (requireList.contains("jquery.gritter")) { %>--%>
    <%--<%@include file="/WEB-INF/common/include/jquery.gritter-js.jsp" %>--%>
    <%--<%}%>--%>

    <% if (requireList.contains("fullcalendar")) { %>
    <%@include file="/WEB-INF/common/include/fullcalendar-js.jsp" %>
    <%}%>

    <% if (requireList.contains("displaytag")) { %>
    <%@include file="/WEB-INF/common/include/displaytag-js.jsp" %>
    <%}%>

    <% if (requireList.contains("typeahead-bs2")) { %>
    <%@include file="/WEB-INF/common/include/typeahead-bs2-js.jsp" %>
    <%}%>

    <% if (requireList.contains("ckeditor")) { %>
    <%@include file="/WEB-INF/common/include/ckeditor-js.jsp" %>
    <%}%>

    <% if (requireList.contains("jquery.populate")) { %>
    <%@include file="/WEB-INF/common/include/jquery.populate-js.jsp" %>
    <%}%>

    <% if (requireList.contains("jquery.validate")) { %>
    <%@include file="/WEB-INF/common/include/jquery.validate-js.jsp" %>
    <%}%>

    <% if (requireList.contains("bootstrap-tag")) { %>
    <%@include file="/WEB-INF/common/include/bootstrap-tag-js.jsp" %>
    <%}%>

    <% if (requireList.contains("dropzone")) { %>
    <%@include file="/WEB-INF/common/include/dropzone-js.jsp" %>
    <%}%>

    <% if (requireList.contains("jquery.multiselect")) { %>
    <%@include file="/WEB-INF/common/include/jquery.multiselect-js.jsp" %>
    <%}%>

    <% if (requireList.contains("bootstrap.editable")) { %>
    <%@include file="/WEB-INF/common/include/bootstrap-editable-js.jsp" %>
    <%}%>

    <% if (requireList.contains("ui.tabs.paging")) { %>
    <%@include file="/WEB-INF/common/include/ui.tabs.paging-js.jsp" %>
    <%}%>

    <% if (requireList.contains("uploadify")) { %>
    <%@include file="/WEB-INF/common/include/uploadify-js.jsp" %>
    <%}%>

    <% if (requireList.contains("jquery.form")) { %>
    <%@include file="/WEB-INF/common/include/jquery.form-js.jsp" %>
    <%}%>

    <% if (requireList.contains("dynatree")) { %>
    <%@include file="/WEB-INF/common/include/dynatree-js.jsp" %>
    <%}%>

    <% if (requireList.contains("jquery.maskedinput")) { %>
    <%@include file="/WEB-INF/common/include/jquery.maskedinput-js.jsp" %>
    <%}%>

    <% if (requireList.contains("uploadify-browse")) { %>
    <%@include file="/WEB-INF/common/include/uploadify-js.jsp" %>
    <%}%>

    <% if (requireList.contains("upload")) { %>
    <%@ include file="/WEB-INF/common/include/upload.jsp" %>
    <%}%>

    <% if (requireList.contains("boactions")) { %>
    <%@ include file="/WEB-INF/common/include/boactions.jsp" %>
    <%}%>
    <% if (requireList.contains("jstree")) { %>
    <%@include file="/WEB-INF/common/include/jstree-js.jsp" %>
    <%}%>
</content>