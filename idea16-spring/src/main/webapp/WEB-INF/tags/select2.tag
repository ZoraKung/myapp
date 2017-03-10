<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="java.lang.String" required="true" description="Input Value" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="Select2 Type" %>
<%@ attribute name="title" type="java.lang.String" required="false" description="Title" %>
<%@ attribute name="multiple" type="java.lang.Boolean"  required="false" description="Is Multiple" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="css" %>
<input id="${id}" name="${name}" class="bigdrop ${css}" type="text" value="${value}"/>
<script type="text/javascript">
    $(function () {
        $("#${id}").select2({
            placeholder : "${not empty title ? title : '---Select---'}",
            minimumInputLength: 0,
            multiple: ${not empty multiple ? multiple : false},
            allowClear: true,
            ajax: {
                url: '${ctx}/ajax/select2/${type}',
                dataType: "json",
                quietMillis: 100,
                data: function (term, page) {
                    return {
                        q: term,
                        pageSize: 10 ,
                        pageNo: (page-1)*10
                    };
                },
                results: function (data, page) {
                    var more = (page * 10) < data.total;
                    return {results: data.dataList, more: more};
                }
            },
            initSelection : function (element, callback) {
                var data = {id: element.val(), text: element.val()};
                callback(data);
            },
            formatResult: function(data){
                return data.text;
            },
            formatSelection: function(data){
                return data.id;
            },
            dropdownCssClass: "bigdrop",
            escapeMarkup: function (m) {
                return m;
            }
        });
    });
</script>