/**
 * Created by WTH on 13-12-1.
 */

$(document).ready(function () {
    $("#ajax_spinner").bind("ajaxSend",function () {
        $(this).show();
        }).bind("ajaxStop",function () {
            $(this).hide();
        }).bind("ajaxError", function () {
            $(this).hide();
        });

    $(document)
        .ajaxStart(function(){
            $("ajax_spinner").show();
        })
        .ajaxStop(function(){
            $("ajax_spinner").hide();
        });
});

/*
 Registering responders for prototype library.
 (If you are not using  prototype library then there is no need of the next statement.)
 */
/*
 Ajax.Responders.register({
 onCreate: function () {
 jQuery("#ajax_spinner").show();
 },
 onComplete: function () {
 jQuery("#ajax_spinner").hide();
 }
 });
 */

/*
 Registering responders for jQuery AJAX calls.
 */

jQuery("#ajax_spinner").ajaxStart(function() {
    jQuery(this).show();
});
jQuery("#ajax_spinner").ajaxStop(function() {
    jQuery(this).hide();
});

/*
 Note : If you are not using jQuery you can show/hide the div
 using javascript(e.g. document.getElementById) to do the same
 */
jQuery("#ajax_spinner").hide();