<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="attr" type="java.lang.String" required="true" description="attr Name" %>
<%@ attribute name="selectName" type="java.lang.String" required="true" description="select name" %>
<%@ attribute name="selectValue" type="java.lang.String" required="true" description="select value" %>
<form:input path="${attr}" cssClass="input-medium hidden"/>
<select class="select2 input-large" name="${selectName}">
    <option value=''>--- 请选择 ---</option>
    <c:forEach items="${fns:getMasMenuList()}" var="parentMenu">
        <c:if test="${empty parentMenu.parent.id && not empty parentMenu.privilegeIdentifier}">
            <shiro:hasPermission name="${parentMenu.privilegeIdentifier}">
                <optgroup label="${parentMenu.name}">
                    <c:forEach items="${fns:getMasMenuList()}" var="menu">
                        <c:if test="${menu.parent.id eq parentMenu.id && not empty menu.privilegeIdentifier}">
                            <shiro:hasPermission name="${menu.privilegeIdentifier}">
                                <option value="${menu.id}"
                                        <c:if test="${selectValue eq menu.id}"> selected="selected" </c:if>>${menu.name}</option>
                            </shiro:hasPermission>
                        </c:if>
                    </c:forEach>
                </optgroup>
            </shiro:hasPermission>
        </c:if>
    </c:forEach>
</select>
