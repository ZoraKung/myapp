<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="selectValue" type="java.lang.String" required="false" description="Radio Value" %>

<c:choose>
<c:when test="${not empty selectValue && selectValue == 1}">
    <input name="${name}" type="radio" checked="checked" class="ace" value="1"/>
    <span class="lbl">Yes</span>
    <input name="${name}" type="radio" class="ace" value="0"/>
    <span class="lbl">No</span>
</c:when>
<c:when test="${not empty selectValue && selectValue == 0}">
    <input name="${name}" type="radio"  class="ace" value="1"/>
    <span class="lbl">Yes</span>
    <input name="${name}" type="radio" class="ace"checked="checked" value="0"/>
    <span class="lbl">No</span>
</c:when>
</c:choose>


