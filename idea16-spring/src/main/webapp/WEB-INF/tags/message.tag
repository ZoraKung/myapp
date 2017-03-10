<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="content" type="java.lang.String" required="true" description="content" %>
<%@ attribute name="type" type="java.lang.String" description="type：info、success、warning、error、loading" %>
<c:if test="${not empty content}">
    <content tag="footer.plugins">
        <c:if test="${not empty type}">
            <c:set var="ctype" value="${type}"/>
        </c:if>
        <c:if test="${empty type}">
            <c:set var="ctype" value="info"/>
        </c:if>
        <c:if test="${ctype == 'error'}">
            <script type="text/javascript">
                matrix.showError('${content}', 'Error');
            </script>
        </c:if>
        <c:if test="${ctype == 'info'}">
            <script type="text/javascript">
                matrix.showMessage('${content}', 'Information');
            </script>
        </c:if>
        <c:if test="${ctype == 'warning'}">
            <script type="text/javascript">
                matrix.showMessage('${content}', 'Warning');
            </script>
        </c:if>
        <c:if test="${ctype == 'success'}">
            <script type="text/javascript">
                matrix.showMessage('${content}', 'Successfully');
            </script>
        </c:if>
    </content>
</c:if>
