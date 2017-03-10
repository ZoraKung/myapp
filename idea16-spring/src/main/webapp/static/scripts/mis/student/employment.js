/**
 * Created by CHY on 15-3-12.
 */
function getEmployerName(thisSelect, dateFrom, dateTo) {
    thisSelect.prepend("<option value=''>--- Please Select ---</option>");
    $.ajax({
        url: _appContext + "/student/employment/employers",
        type: "POST",
        dataType: "json",
        data: {dateFrom: dateFrom,
            dateTo: dateTo},
        success: function (data) {
            if (jQuery.isEmptyObject(data)) {
                thisSelect.append("<option value='' >Employer Undefined</option>");
            } else {
                $.each(data, function (key, value) {
                    var newkey = escapeHtml(key);

                    if (typeof (val) === 'undefined' || key !== val) {
                        $(selectElement).append("<option value=" + newkey + ">" + value + "</option>");
                    } else {
                        $(selectElement).append("<option value=" + newkey + " selected>" + value + "</option>");
                        $("#s2id_" + selectId).find("span.select2-chosen").html(value);
                    }
                });
                //initialize form data
                doSaveFormDataByElement(selectElement);
            }
        },
        error: function () {
        }
    });
};

$("#employerName").bind("change", function () {
    var employer = $(this).val();
    var dateFrom = $("#as_period_from").val();
    var dateTo = $("#as_period_to").val();
    thisSelect.prepend("<option value=''>--- Please Select ---</option>");
    $.ajax({
        url: _appContext + "/student/employment/supervisors",
        type: "POST",
        dataType: "json",
        data: {aeOrgId: employer,
            dateFrom: dateFrom,
            dateTo: dateTo},
        success: function (data) {
            if (jQuery.isEmptyObject(data)) {
                thisSelect.append("<option value='' >Supervisor Undefined</option>");
            } else {
                $.each(data, function (key, value) {
                    var newkey = escapeHtml(key);

                    if (typeof (val) === 'undefined' || key !== val) {
                        $(selectElement).append("<option value=" + newkey + ">" + value + "</option>");
                    } else {
                        $(selectElement).append("<option value=" + newkey + " selected>" + value + "</option>");
                        $("#s2id_" + selectId).find("span.select2-chosen").html(value);
                    }
                });
                //initialize form data
                doSaveFormDataByElement(selectElement);
            }
        },
        error: function () {
        }
    });

})

$("#supervisor").bind("change", function () {
    var supervisorId = $(this).val();
    $.ajax({
        url: _appContext + "/student/employment/supervisor",
        type: "POST",
        dataType: "json",
        data: {supervisorId: supervisorId},
        success: function (data) {

        }
    });
})