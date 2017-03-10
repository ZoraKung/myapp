/**
 * Created by WTH on 14-4-22.
 */

$(function () {
    $("#btn-login").click(function () {
        var $username = $("#username").val();
        var $password = $("#password").val();
        if ($username == "" || $password == "") {
			if ($username == "" && $password == ""){
				$.gritter.add({
					title: "<i class='icon-warning-sign bigger-120 red'></i> " + "错误",
					text: "用户名及密码不能为空<br/>",
					class_name: "gritter-warning gritter-light"
				});
			}else if ($username == ""){
				$.gritter.add({
					title: "<i class='icon-warning-sign bigger-120 red'></i> " + "错误",
					text: "用户名不能为空<br/>",
					class_name: "gritter-warning gritter-light"
				});
			}else if ($password == ""){
				$.gritter.add({
					title: "<i class='icon-warning-sign bigger-120 red'></i> " + "错误",
					text: "密码不能为空<br/>",
					class_name: "gritter-warning gritter-light"
				});
			}
        }
        else {
            $("#loginForm").submit();
        }
    });

    // Validate
    $("#loginForm").validate({
        rules: {
            // captcha: {remote: _appContext + "/servlet/captchaServlet"}
        },
        messages: {
           //  captcha: {remote: 'Captcha is invalid.'}
        },
        errorContainer: "#messageBox",
        errorPlacement: function (error, element) {
            if (element.is('.captcha')){
                error.insertAfter($('#span_captcha'));
            }else{
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            $.gritter.add({
                title: '<h5><i class="icon-spinner icon-spin orange bigger-125"></i> ' + '登录中 ...' + '</h5>',
                text: '',
                stick: true,
                time: '',
                class_name: 'gritter-info gritter-center gritter-light'
            });
            form.submit();
        }
    });

    $(document).keypress(function (evt) {
        var charCode = evt.charCode || evt.keyCode || evt.which;
        if (charCode == 13) {
            $("#loginForm").submit();
            return false;
        }
    });
});

function show_box(id) {
    jQuery('.widget-box.visible').removeClass('visible');
    jQuery('#' + id).addClass('visible');
}

function clearErrorMessage(){
    document.getElementById("loginErrorPlace").innerHTML = "&nbsp;";
}