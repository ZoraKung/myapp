<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="FromId" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="java.lang.String" required="false" description="Input Value" %>
<%@ attribute name="clazz" type="java.lang.String" required="false" description="class" %>
<%@ attribute name="groupId" type="java.lang.String" required="true" description="groupId" %>
<%@ attribute name="toId" type="java.lang.String" required="false" description="ToId" %>
<%@ attribute name="code" type="java.lang.String" required="true" description="urlcode" %>
<%@ attribute name="defaultParentVal" type="java.lang.String" required="false" description="parentVal" %>
<%@ attribute name="idTop" type="java.lang.String" required="false" description="firstSelected" %>

<input type="hidden" value="${groupId}_input"/>
<select id="${id}_Id" name="${name}" class="${clazz}">
    <option>--Select--</option>
</select>
<input type="hidden" value="${code}"/>
        <script type="text/javascript">
            $(document).ready(function () {
                var groud = "${groupId}_input";
                var id = "${id}_Id";
                var top = "${idTop}";
                var code = "${code}";
                var value = "${value}";
                var toId = "${toId}_Id";
                var parentValue = "${defaultParentVal}";
                var clearIndex = 0;
                var url =_appContext +"/common/service/label-value/"+ code +"/"+parentValue;
                if(top == "true"){
                    url =_appContext +"/common/service/label-value/"+ code;
                }
                $.post(url,function(data,textStatus){
                        $("#"+id).empty();
                        var dataobj = eval('(' + data + ')');
                        $("#"+id).append('<option>--Select--</option>');
                        for(var index in dataobj){
                            var opn ='<option value='+index+'>'+dataobj[index]+'</option>';
                            if(index == value){
                                opn = '<option value='+index+' selected>'+dataobj[index]+'</option>';
                            }
                            $("#"+id).append(opn);
                        }
                    });


                $('#'+id).bind("change",function(){
                    var parent = $(this).val();
                    if(parent == "--Select--"){
                        clearSelected(groud,id);
                    }else{
                    var childCode = $('#'+toId).next("input").val();
                    $.post(_appContext +"/common/service/label-value/"+ childCode+"/"+parent,function(data,textStatus){
                        $('#'+toId).empty();
                        var dataobj = eval('(' + data + ')');
                        $('#'+toId).append('<option>--Select--</option>');
                        for(var index in dataobj){
                            var opn ='<option value='+index+'>'+dataobj[index]+'</option>';
                            $('#'+toId).append(opn);
                        }
                        clearSelected(groud,$('#'+toId).attr("id"));
                    });
                    }
                })

                function clearSelected(gid,mainId){
                    $('input[value='+gid+']').each(function(){
                        if($(this).next().attr("id") == mainId){
                            clearIndex =1;
                            return;
                        }
                        if(clearIndex != 0){
                            var $select = $(this).next("select");
                            $select.empty();
                            $select.append('<option>--Select--</option>');
                        }
                    });
                    clearIndex = 0;
                }
            });

</script>
