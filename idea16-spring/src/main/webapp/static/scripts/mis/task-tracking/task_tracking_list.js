var selectUsersCallback ;
$(function () {
    $(function(){
        $('#btn_task_list_criteria_search').bind("click", function(){
            loadTaskGrid();
        });

    });

    $("#btn_task_list_cancel").click(function () {
        location.href = _appContext + "/mzj/task/index";
    })

    $("#criteria_person_name").click(function(){
        select_person();
    })

    $(".icon-male").click(function(){
        select_person();
    })

    function select_person(){
        var userIds = $('#criteria_person_id').val();
        var userNames = $('#criteria_person_name').val();
        var callback = function(uIds,uNames,options){
            if(options !== undefined ){
                if(options.change == "false"){
                    return;
                }
                else{
                    $('#criteria_person_id').val(uIds);
                    $('#criteria_person_name').val(uNames);
                }
            }

        }
        showLog({
                userNames : userNames,
                userIds : userIds
            },callback);
    }

    function loadTaskGrid() {
//        jqGridSearch("grid-table","${ctx}/sampleTest/secondSearch", "sample_searchForm", true);
        $.ajax({
            type: "POST",
            url: _appContext +"/task/ajax/load-grid",
//            postData: $("#task_search_criteria_form").serializeJSON(),
            success: function (response) {
                $("#result").html(response);
            }
        });
    }

});

