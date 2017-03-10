$(function(){
    $(".hasDatepicker").datepicker({
        format: 'dd-mm-yyyy',
        autoclose: true
    });
    $(".hasFutureDatepicker").each(function () {
        $(this).closest("span").addClass("input-medium");
        $(this).datepicker({format: "dd-mm-yyyy", autoclose: true,endDate: '+0d'}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    });
    $(".timepicker").each(function () {
        $(this).closest("span").addClass("input-small");
        $(this).closest("div").addClass("input-small");
        $(this).timepicker({minuteStep: 1, showSeconds: false, showMeridian: false
        }).next().on(ace.click_event, function () {
                $(this).prev().focus();
            });
    });
});
